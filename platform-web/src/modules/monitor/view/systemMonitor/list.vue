<template>
  <div class="system-monitor">
    <div class="monitor-header">
      <el-card shadow="hover" class="header-card">
        <div class="header-content">
          <div class="system-info">
            <h2>系统监控</h2>
            <div class="basic-info">
              <el-descriptions :column="4" border>
                <el-descriptions-item label="操作系统">{{
                  systemInfo.osInfo?.name || '-'
                }}</el-descriptions-item>
                <el-descriptions-item label="主机名">{{
                  systemInfo.osInfo?.hostName || '-'
                }}</el-descriptions-item>
                <el-descriptions-item label="运行时间">{{
                  formatRunningTime(systemInfo.osInfo?.runningTime)
                }}</el-descriptions-item>
                <el-descriptions-item label="JVM版本">{{
                  systemInfo.jvmInfo?.version || '-'
                }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <div class="monitor-body">
      <!-- CPU 监控 -->
      <el-card shadow="hover" class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>CPU监控</span>
          </div>
        </template>
        <div class="cpu-content">
          <div class="cpu-summary">
            <div class="cpu-usage">
              <el-progress
                type="dashboard"
                :percentage="systemInfo.cpuInfo?.usage || 0"
                :format="formatCpuUsage"
              ></el-progress>
              <div class="usage-text"
                >CPU使用率: {{ (systemInfo.cpuInfo?.usage || 0).toFixed(1) }}%</div
              >
            </div>
            <div class="cpu-info">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="CPU型号">{{
                  systemInfo.cpuInfo?.name || '-'
                }}</el-descriptions-item>
                <el-descriptions-item label="核心数"
                  >{{ systemInfo.cpuInfo?.physicalProcessorCount }}/{{
                    systemInfo.cpuInfo?.logicalProcessorCount
                  }}</el-descriptions-item
                >
              </el-descriptions>
            </div>
          </div>
          <div
            class="cpu-cores"
            v-if="systemInfo.cpuInfo?.coreUsages && systemInfo.cpuInfo.coreUsages.length > 0"
          >
            <el-divider>核心使用率</el-divider>
            <div class="cores-container">
              <div
                v-for="(usage, index) in systemInfo.cpuInfo.coreUsages"
                :key="index"
                class="core-item"
              >
                <div class="core-label">核心 {{ index + 1 }}</div>
                <el-progress
                  :percentage="usage"
                  :stroke-width="10"
                  :format="formatCoreUsage"
                ></el-progress>
              </div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 内存监控 -->
      <el-card shadow="hover" class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>内存监控</span>
          </div>
        </template>
        <div class="memory-content">
          <div class="memory-chart">
            <div ref="memoryChartRef" class="chart-container"></div>
          </div>
          <div class="memory-details">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="总内存">{{
                formatBytes(systemInfo.memoryInfo?.total || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="已用内存">{{
                formatBytes(systemInfo.memoryInfo?.used || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="可用内存">{{
                formatBytes(systemInfo.memoryInfo?.available || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="内存使用率"
                >{{ (systemInfo.memoryInfo?.usage || 0).toFixed(1) }}%</el-descriptions-item
              >
            </el-descriptions>
          </div>
        </div>
      </el-card>

      <!-- 磁盘监控 -->
      <el-card shadow="hover" class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>磁盘监控</span>
          </div>
        </template>
        <div class="disk-content">
          <div class="disk-summary">
            <el-progress
              type="line"
              :percentage="systemInfo.diskInfo?.usage || 0"
              :format="formatDiskUsage"
              :stroke-width="20"
            ></el-progress>
            <div class="disk-summary-text">
              <div>磁盘使用率: {{ (systemInfo.diskInfo?.usage || 0).toFixed(1) }}%</div>
              <div>总磁盘空间: {{ formatBytes(systemInfo.diskInfo?.total || 0) }}</div>
            </div>
          </div>
          <div
            class="disk-partitions"
            v-if="systemInfo.diskInfo?.partitions && systemInfo.diskInfo.partitions.length > 0"
          >
            <el-divider>磁盘分区</el-divider>
            <el-table :data="systemInfo.diskInfo.partitions" border style="width: 100%">
              <el-table-column prop="name" label="分区名称"></el-table-column>
              <el-table-column prop="path" label="挂载路径"></el-table-column>
              <el-table-column prop="type" label="文件系统"></el-table-column>
              <el-table-column
                prop="total"
                label="总大小"
                :formatter="formatColumnBytes"
              ></el-table-column>
              <el-table-column
                prop="used"
                label="已用大小"
                :formatter="formatColumnBytes"
              ></el-table-column>
              <el-table-column
                prop="available"
                label="可用大小"
                :formatter="formatColumnBytes"
              ></el-table-column>
              <el-table-column prop="usage" label="使用率">
                <template #default="scope">
                  <el-progress
                    :percentage="roundToTwoDecimal(scope.row.usage)"
                    :stroke-width="8"
                  ></el-progress>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>

      <!-- JVM 监控 -->
      <el-card shadow="hover" class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>JVM监控</span>
          </div>
        </template>
        <div class="jvm-content">
          <div class="jvm-chart">
            <div ref="jvmChartRef" class="chart-container"></div>
          </div>
          <div class="jvm-details">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="堆内存总量">{{
                formatBytes(systemInfo.jvmInfo?.maxHeapMemory || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="已用堆内存">{{
                formatBytes(systemInfo.jvmInfo?.usedHeapMemory || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="非堆内存总量">{{
                formatBytes(systemInfo.jvmInfo?.totalNonHeapMemory || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="已用非堆内存">{{
                formatBytes(systemInfo.jvmInfo?.usedNonHeapMemory || 0)
              }}</el-descriptions-item>
              <el-descriptions-item label="线程数">{{
                systemInfo.jvmInfo?.threadCount || 0
              }}</el-descriptions-item>
              <el-descriptions-item label="加载类数量">{{
                systemInfo.jvmInfo?.loadedClassCount || 0
              }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { ElMessage } from 'element-plus'
import { monitor } from '@/modules/monitor/api'

// 响应式数据
const systemInfo = reactive({
  osInfo: null,
  cpuInfo: null,
  memoryInfo: null,
  diskInfo: { partitions: [] },
  jvmInfo: null
})

// 图表引用
const memoryChartRef = ref<HTMLElement | null>(null)
const jvmChartRef = ref<HTMLElement | null>(null)
let memoryChart: echarts.ECharts | null = null
let jvmChart: echarts.ECharts | null = null

// 定时器
let updateTimer: number | null = null

// 加载系统监控信息
const loadSystemInfo = async () => {
  try {
    const response = await monitor.getInfo()

    // 更新系统信息
    Object.assign(systemInfo, response.data)
  } catch (error) {
    ElMessage.error('获取系统监控信息失败')
    console.error('获取系统监控信息失败:', error)
  }
}

// 格式化运行时间
const formatRunningTime = (time: number | undefined) => {
  if (!time) return '-'
  const days = Math.floor(time / (1000 * 60 * 60 * 24))
  const hours = Math.floor((time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
  const minutes = Math.floor((time % (1000 * 60 * 60)) / (1000 * 60))
  return `${days}d ${hours}h ${minutes}m`
}
// 格式化表格列字节数
const formatColumnBytes = (row: any, column: any, cellValue: any) => {
  return formatBytes(cellValue)
}

// 格式化字节数
const formatBytes = (bytes: any) => {
  // 检查bytes是否为有效数字
  if (!bytes || typeof bytes !== 'number' || isNaN(bytes)) return '-'

  const units = ['B', 'KB', 'MB', 'GB', 'TB']
  let unitIndex = 0
  let size = bytes

  while (size >= 1024 && unitIndex < units.length - 1) {
    size /= 1024
    unitIndex++
  }

  return `${size.toFixed(2)} ${units[unitIndex]}`
}

// CPU 使用率格式化
const formatCpuUsage = (percentage: number) => {
  return `${percentage.toFixed(1)}%`
}

// 核心使用率格式化
const formatCoreUsage = (percentage: number) => {
  return `${percentage.toFixed(1)}%`
}

// 磁盘使用率格式化
const formatDiskUsage = (percentage: number) => {
  return `${percentage.toFixed(1)}%`
}

// 保留两位小数且保持数值类型的方法
const roundToTwoDecimal = (num: number): number => {
  // 方法1: 使用Math.round()
  // return Math.round(num * 100) / 100;

  // 方法2: 使用Number.parseFloat()结合toFixed()
  return Number.parseFloat(num.toFixed(2))

  // 方法3: 使用+运算符结合toFixed()
  // return +num.toFixed(2);

  // 方法4: 处理浮点数精度问题的方式
  // return Math.round((num + Number.EPSILON) * 100) / 100;
}

// 初始化内存图表
const initMemoryChart = () => {
  if (!memoryChartRef.value) return

  memoryChart = echarts.init(memoryChartRef.value)

  const option = {
    title: {
      text: '内存使用情况',
      left: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '内存使用',
        type: 'pie',
        radius: '50%',
        data: [
          { value: systemInfo.memoryInfo?.used || 0, name: '已用内存' },
          { value: systemInfo.memoryInfo?.available || 0, name: '可用内存' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }

  memoryChart.setOption(option)
}

// 初始化 JVM 图表
const initJvmChart = () => {
  if (!jvmChartRef.value) return

  jvmChart = echarts.init(jvmChartRef.value)

  const option = {
    title: {
      text: 'JVM 内存使用情况',
      left: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'JVM 内存',
        type: 'pie',
        radius: '50%',
        data: [
          { value: systemInfo.jvmInfo?.usedHeapMemory || 0, name: '已用堆内存' },
          {
            value:
              (systemInfo.jvmInfo?.maxHeapMemory || 0) - (systemInfo.jvmInfo?.usedHeapMemory || 0),
            name: '可用堆内存'
          },
          { value: systemInfo.jvmInfo?.usedNonHeapMemory || 0, name: '已用非堆内存' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }

  jvmChart.setOption(option)
}

// 更新图表
const updateCharts = () => {
  if (memoryChart) {
    memoryChart.setOption({
      series: [
        {
          data: [
            { value: systemInfo.memoryInfo?.used || 0, name: '已用内存' },
            { value: systemInfo.memoryInfo?.available || 0, name: '可用内存' }
          ]
        }
      ]
    })
  }

  if (jvmChart) {
    jvmChart.setOption({
      series: [
        {
          data: [
            { value: systemInfo.jvmInfo?.usedHeapMemory || 0, name: '已用堆内存' },
            {
              value:
                (systemInfo.jvmInfo?.maxHeapMemory || 0) -
                (systemInfo.jvmInfo?.usedHeapMemory || 0),
              name: '可用堆内存'
            },
            { value: systemInfo.jvmInfo?.usedNonHeapMemory || 0, name: '已用非堆内存' }
          ]
        }
      ]
    })
  }
}

// 窗口大小变化时重新调整图表
const handleResize = () => {
  memoryChart?.resize()
  jvmChart?.resize()
}

// 生命周期钩子
onMounted(() => {
  // 加载初始数据
  loadSystemInfo()

  // 初始化图表
  setTimeout(() => {
    initMemoryChart()
    initJvmChart()
  }, 100)

  // 设置定时器，定时更新数据
  updateTimer = window.setInterval(() => {
    loadSystemInfo()
    updateCharts()
  }, 5000)

  // 监听窗口大小变化
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  // 清除定时器
  if (updateTimer) {
    window.clearInterval(updateTimer)
  }

  // 销毁图表
  memoryChart?.dispose()
  jvmChart?.dispose()

  // 移除事件监听
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
.system-monitor {
  padding: 20px;
}

.monitor-header {
  margin-bottom: 20px;
}

.header-card {
  width: 100%;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.system-info h2 {
  margin-bottom: 16px;
  font-size: 24px;
  color: #303133;
}

.monitor-body {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.monitor-card {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header span {
  font-size: 16px;
  font-weight: bold;
}

/* CPU 监控样式 */
.cpu-content {
  padding: 20px 0;
}

.cpu-summary {
  display: flex;
  align-items: center;
  gap: 40px;
}

.cpu-usage {
  flex: 1;
  text-align: center;
}

.usage-text {
  margin-top: 16px;
  font-size: 14px;
  color: #606266;
}

.cpu-info {
  flex: 2;
}

.cpu-cores {
  margin-top: 30px;
}

.cores-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
  margin-top: 16px;
}

.core-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.core-label {
  margin-bottom: 8px;
  font-size: 14px;
  color: #606266;
}

/* 内存监控样式 */
.memory-content {
  padding: 20px 0;
}

.memory-chart {
  height: 300px;
  margin-bottom: 20px;
}

.chart-container {
  width: 100%;
  height: 100%;
}

/* 磁盘监控样式 */
.disk-content {
  padding: 20px 0;
}

.disk-summary {
  margin-bottom: 30px;
}

.disk-summary-text {
  margin-top: 16px;
  display: flex;
  justify-content: space-around;
  font-size: 14px;
  color: #606266;
}

/* JVM 监控样式 */
.jvm-content {
  padding: 20px 0;
}

.jvm-chart {
  height: 300px;
  margin-bottom: 20px;
}
</style>
