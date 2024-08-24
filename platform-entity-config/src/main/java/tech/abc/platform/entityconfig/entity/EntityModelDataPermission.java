package tech.abc.platform.entityconfig.entity;

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
 * 实体模型数据权限 实体类
 *
 * @author wqliu
 * @date 2024-08-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_entity_model_data_permission")
public class EntityModelDataPermission extends BaseEntity {

    /**
    * 模型标识
    */
    @TableField("model_id")
    private String modelId;

    /**
    * 表名
    */
    @TableField("table_name")
    private String tableName;

    /**
    * 规则
    */
    @TableField("rule")
    private String rule;

    /**
    * sql片段
    */
    @TableField("sql_part")
    private String sqlPart;

    /**
    * 备注
    */
    @TableField("remark")
    private String remark;

    /********非库表存储属性*****/
}
