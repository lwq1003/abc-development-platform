package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;


/**
 * 实体 基类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class BaseEntity extends BaseMapEntity {

    /**
     * 逻辑删除
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    protected String deleteFlag;
}
