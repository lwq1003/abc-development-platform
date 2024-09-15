package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 查询方案 实体类
 *
 * @author wqliu
 * @date 2024-09-04
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_query_plan")
public class QueryPlan extends BaseEntity {

    /**
    * 用户标识
    */
    @TableField("user_id")
    private String userId;

    /**
    * 实体模型编码
    */
    @TableField("entity_model_code")
    private String entityModelCode;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 内容
    */
    @TableField("content")
    private String content;

    /********非库表存储属性*****/
}
