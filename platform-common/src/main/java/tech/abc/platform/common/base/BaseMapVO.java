package tech.abc.platform.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 映射关系 视图对象
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class BaseMapVO extends BaseIdVO {



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


}
