package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 标识模型
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class BaseIdEntity {
    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


}
