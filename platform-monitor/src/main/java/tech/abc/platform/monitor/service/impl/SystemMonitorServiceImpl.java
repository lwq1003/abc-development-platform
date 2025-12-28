package tech.abc.platform.monitor.service.impl;

import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import tech.abc.platform.monitor.entity.*;
import tech.abc.platform.monitor.service.SystemMonitorService;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统监控服务实现
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Service
public class SystemMonitorServiceImpl implements SystemMonitorService {

    /**
     * 单例模式：SystemInfo 创建成本高，复用避免性能损耗
     */
    private final SystemInfo systemInfo = new SystemInfo();
    private final OperatingSystem os = systemInfo.getOperatingSystem();
    private final HardwareAbstractionLayer hal = systemInfo.getHardware();
    private final CentralProcessor processor = hal.getProcessor();
    private final GlobalMemory memory = hal.getMemory();

    /**
     * 采样间隔（毫秒）：1000ms 是行业通用值，太短误差大，太长实时性差
     */
    private static final long SAMPLE_INTERVAL = 1000L;

    /**
     * 多次采样平均（提升稳定性，可选）
     */
    private static final int AVG_SAMPLING_TIMES = 3;

    /**
     * 上次整体采样数据
     */
    private long[] prevTicks = processor.getSystemCpuLoadTicks();
    private long lastSystemSampleTime = System.currentTimeMillis();

    /**
     * 上次各核心采样数据
     */
    private long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();
    private long lastCoreSampleTime = System.currentTimeMillis();

    @Override
    public SystemMonitor getSystemMonitorInfo() {
        SystemMonitor systemMonitor = new SystemMonitor();

        // 获取操作系统信息
        systemMonitor.setOsInfo(getOsInfo());

        // 获取CPU信息
        systemMonitor.setCpuInfo(getCpuInfo());

        // 获取内存信息
        systemMonitor.setMemoryInfo(getMemoryInfo());

        // 获取磁盘信息
        systemMonitor.setDiskInfo(getDiskInfo());

        // 获取JVM信息
        systemMonitor.setJvmInfo(getJvmInfo());

        return systemMonitor;
    }


    /**
     * 获取操作系统信息
     */
    private OsInfo getOsInfo() {
        OsInfo osInfo = new OsInfo();
        osInfo.setName(os.getFamily() + " " + os.getVersionInfo().getVersion());
        osInfo.setVersion(os.getVersionInfo().toString());
        osInfo.setArch(System.getProperty("os.arch"));
        osInfo.setHostName(os.getNetworkParams().getHostName());
        osInfo.setStartTime(os.getSystemBootTime() * 1000L);
        osInfo.setRunningTime(System.currentTimeMillis() - osInfo.getStartTime());
        return osInfo;
    }

    /**
     * 获取CPU信息
     */
    private CpuInfo getCpuInfo() {
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setName(processor.getProcessorIdentifier().getName());
        cpuInfo.setModel(processor.getProcessorIdentifier().getModel());
        cpuInfo.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());
        cpuInfo.setLogicalProcessorCount(processor.getLogicalProcessorCount());

        // 获取整体CPU使用率（多次采样取平均，提升稳定性）
        double totalUsage = 0.0;
        for (int i = 0; i < AVG_SAMPLING_TIMES; i++) {
            totalUsage += calculateSingleSystemCpuUsage();
            // 非最后一次采样，间隔短一点避免总耗时过长
            if (i < AVG_SAMPLING_TIMES - 1) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // 计算平均值并校准（避免超过100%或负数）
        double avgUsage = totalUsage / AVG_SAMPLING_TIMES;
        cpuInfo.setUsage(Math.max(0.0, Math.min(100.0, Math.round(avgUsage * 100) / 100.0)));

        // 获取各核心CPU使用率
        List<Double> coreUsageList = new ArrayList<>();
        double[] perCpuUsages = calculatePerCoreCpuUsage();
        for (double coreUsage : perCpuUsages) {
            coreUsageList.add(Math.max(0.0, Math.min(100.0, Math.round(coreUsage * 100) / 100.0)));
        }
        cpuInfo.setCoreUsages(coreUsageList);

        return cpuInfo;
    }

    /**
     * 单次系统整体CPU使用率计算（核心逻辑）
     */
    private double calculateSingleSystemCpuUsage() {
        // 第一次采样：获取各状态累计 Tick
        long[] currTicks = processor.getSystemCpuLoadTicks();
        long currentTime = System.currentTimeMillis();

        // 确保采样间隔足够
        long elapsedTime = currentTime - lastSystemSampleTime;
        if (elapsedTime < SAMPLE_INTERVAL) {
            try {
                Thread.sleep(SAMPLE_INTERVAL - elapsedTime);
                currTicks = processor.getSystemCpuLoadTicks();
                currentTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 计算两次采样的 Tick 差值（适配不同系统的 Tick 类型）
        long user = currTicks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long system = currTicks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = currTicks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        // 兼容 Windows（无 NICE 状态）
        long nice = currTicks.length > CentralProcessor.TickType.NICE.getIndex()
                ? currTicks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()]
                : 0;

        // 兼容 Windows（无 IOWAIT 状态）
        long iowait = currTicks.length > CentralProcessor.TickType.IOWAIT.getIndex()
                ? currTicks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()]
                : 0;

        // 总耗时 = 所有非空闲状态耗时之和
        long total = user + system + nice + idle + iowait;
        if (total == 0) {
            return 0.0;
        }

        // 使用率 = (总耗时 - 空闲耗时) / 总耗时 × 100
        double usage = (1.0 - (double) idle / total) * 100;

        // 更新采样数据
        prevTicks = currTicks;
        lastSystemSampleTime = currentTime;

        return usage;
    }

    /**
     * 计算每个核心的CPU使用率（适配大小核）
     */
    private double[] calculatePerCoreCpuUsage() {
        // 第一次采样：获取各核心状态累计 Tick
        long[][] currProcTicks = processor.getProcessorCpuLoadTicks();
        long currentTime = System.currentTimeMillis();

        // 确保采样间隔足够
        long elapsedTime = currentTime - lastCoreSampleTime;
        if (elapsedTime < SAMPLE_INTERVAL) {
            try {
                Thread.sleep(SAMPLE_INTERVAL - elapsedTime);
                currProcTicks = processor.getProcessorCpuLoadTicks();
                currentTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int coreCount = Math.min(prevProcTicks.length, currProcTicks.length);
        double[] perCpuUsages = new double[coreCount];

        for (int i = 0; i < coreCount; i++) {
            long[] prev = prevProcTicks[i];
            long[] curr = currProcTicks[i];

            // 计算两次采样的 Tick 差值
            long user = curr[CentralProcessor.TickType.USER.getIndex()] - prev[CentralProcessor.TickType.USER.getIndex()];
            long system = curr[CentralProcessor.TickType.SYSTEM.getIndex()] - prev[CentralProcessor.TickType.SYSTEM.getIndex()];
            long idle = curr[CentralProcessor.TickType.IDLE.getIndex()] - prev[CentralProcessor.TickType.IDLE.getIndex()];
            long nice = curr.length > CentralProcessor.TickType.NICE.getIndex()
                    ? curr[CentralProcessor.TickType.NICE.getIndex()] - prev[CentralProcessor.TickType.NICE.getIndex()]
                    : 0;
            long iowait = curr.length > CentralProcessor.TickType.IOWAIT.getIndex()
                    ? curr[CentralProcessor.TickType.IOWAIT.getIndex()] - prev[CentralProcessor.TickType.IOWAIT.getIndex()]
                    : 0;

            long total = user + system + nice + idle + iowait;
            perCpuUsages[i] = total == 0 ? 0.0 : (1.0 - (double) idle / total) * 100;
        }

        // 更新采样数据
        prevProcTicks = currProcTicks;
        lastCoreSampleTime = currentTime;

        return perCpuUsages;
    }

    /**
     * 获取内存信息
     */
    private MemoryInfo getMemoryInfo() {
        MemoryInfo memoryInfo = new MemoryInfo();

        // 物理内存
        memoryInfo.setTotal(memory.getTotal());
        memoryInfo.setAvailable(memory.getAvailable());
        memoryInfo.setUsed(memory.getTotal() - memory.getAvailable());
        memoryInfo.setUsage((double) memoryInfo.getUsed() / memoryInfo.getTotal() * 100.0);

        // 交换区内存
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        memoryInfo.setSwapTotal(virtualMemory.getSwapTotal());
        memoryInfo.setSwapUsed(virtualMemory.getSwapUsed());
        memoryInfo.setSwapAvailable(virtualMemory.getSwapTotal() - virtualMemory.getSwapUsed());
        memoryInfo.setSwapUsage(virtualMemory.getSwapTotal() > 0 ? (double) virtualMemory.getSwapUsed() / virtualMemory.getSwapTotal() * 100.0 : 0);

        return memoryInfo;
    }

    /**
     * 获取磁盘信息
     */
    private DiskInfo getDiskInfo() {
        DiskInfo diskInfo = new DiskInfo();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();

        long total = 0;
        long used = 0;
        List<DiskInfo.DiskPartitionVO> partitions = new ArrayList<>();

        for (OSFileStore store : fileStores) {
            if (store.getTotalSpace() <= 0) {
                continue;
            }

            total += store.getTotalSpace();
            used += (store.getTotalSpace() - store.getUsableSpace());

            DiskInfo.DiskPartitionVO partitionVO = new DiskInfo.DiskPartitionVO();
            partitionVO.setName(store.getName());
            partitionVO.setPath(store.getMount());
            partitionVO.setType(store.getType());
            partitionVO.setTotal(store.getTotalSpace());
            partitionVO.setAvailable(store.getUsableSpace());
            partitionVO.setUsed(store.getTotalSpace() - store.getUsableSpace());
            partitionVO.setUsage((double) partitionVO.getUsed() / partitionVO.getTotal() * 100.0);

            partitions.add(partitionVO);
        }

        diskInfo.setTotal(total);
        diskInfo.setUsed(used);
        diskInfo.setAvailable(total - used);
        diskInfo.setUsage(total > 0 ? (double) used / total * 100.0 : 0);
        diskInfo.setPartitions(partitions);

        return diskInfo;
    }

    /**
     * 获取JVM信息
     */
    private JvmInfo getJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();

        // JVM基本信息
        jvmInfo.setName(System.getProperty("java.vm.name"));
        jvmInfo.setVersion(System.getProperty("java.version"));
        jvmInfo.setVendor(System.getProperty("java.vm.vendor"));

        // JVM启动时间和运行时长
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        jvmInfo.setStartTime(startTime);
        jvmInfo.setRunningTime(System.currentTimeMillis() - startTime);

        // 堆内存信息
        MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
        jvmInfo.setInitHeapMemory(heapMemoryUsage.getInit());
        jvmInfo.setUsedHeapMemory(heapMemoryUsage.getUsed());
        jvmInfo.setCommittedHeapMemory(heapMemoryUsage.getCommitted());
        jvmInfo.setMaxHeapMemory(heapMemoryUsage.getMax());

        // 非堆内存信息
        MemoryUsage nonHeapMemoryUsage = memoryMxBean.getNonHeapMemoryUsage();
        jvmInfo.setInitNonHeapMemory(nonHeapMemoryUsage.getInit());
        jvmInfo.setUsedNonHeapMemory(nonHeapMemoryUsage.getUsed());
        jvmInfo.setCommittedNonHeapMemory(nonHeapMemoryUsage.getCommitted());
        // 非堆内存总量，max可能为-1表示无限制
        long maxNonHeap = nonHeapMemoryUsage.getMax();
        jvmInfo.setTotalNonHeapMemory(maxNonHeap > 0 ? maxNonHeap : nonHeapMemoryUsage.getCommitted());

        // 线程信息
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        jvmInfo.setThreadCount(threadMxBean.getThreadCount());
        jvmInfo.setDaemonThreadCount(threadMxBean.getDaemonThreadCount());
        jvmInfo.setPeakThreadCount(threadMxBean.getPeakThreadCount());

        // 类加载信息
        jvmInfo.setLoadedClassCount((long) ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());
        jvmInfo.setUnloadedClassCount(ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount());
        jvmInfo.setTotalLoadedClassCount(ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount());

        return jvmInfo;
    }


}