package tech.abc.platform.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 视图对象 基类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class BaseVO {


    /**
     * 标识
     */
    private String id;
    /**
     * 创建人标识
     */


    private String createId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人标识
     */

    private String updateId;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;

    /**
     * 版本
     */

    private Integer version;

    /**
     * 删除标志
     */

    private String deleteFlag;
}
