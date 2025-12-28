package tech.abc.platform.monitor.vo;

import lombok.Data;

import java.util.List;

/**
 * 磁盘信息
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class DiskInfoVO {
    /**
     * 总磁盘空间(字节)
     */
    private Long total;

    /**
     * 已用磁盘空间(字节)
     */
    private Long used;

    /**
     * 可用磁盘空间(字节)
     */
    private Long available;

    /**
     * 磁盘使用率(%)
     */
    private Double usage;

    /**
     * 磁盘分区列表
     */
    private List<DiskPartitionVO> partitions;

    /**
     * 磁盘分区信息
     */
    @Data
    public static class DiskPartitionVO {
        /**
         * 分区名称
         */
        private String name;

        /**
         * 分区路径
         */
        private String path;

        /**
         * 文件系统类型
         */
        private String type;

        /**
         * 总大小(字节)
         */
        private Long total;

        /**
         * 已用大小(字节)
         */
        private Long used;

        /**
         * 可用大小(字节)
         */
        private Long available;

        /**
         * 使用率(%)
         */
        private Double usage;
    }
}