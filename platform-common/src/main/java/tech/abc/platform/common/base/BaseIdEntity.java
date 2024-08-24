package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 标识模型
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
@TableName("cfg_base_entity")
public class BaseIdEntity {
    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


}
