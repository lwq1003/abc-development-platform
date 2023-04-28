package tech.abc.platform.common.constant;

import lombok.experimental.UtilityClass;

/**
 * 通用属性常量类
 *
 * @author wqliu
 * @date 2023-03-08
 */

@UtilityClass
public class CommonPropertyConstant {

    /**
     * 创建人Id
     */
    public static final String CREATE_ID = "createId";

    /**
     * 更新人Id
     */
    public static final String UPDATE_ID = "updateId";

    /**
     * 创建时间
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 创建时间
     */
    public static final String UPDATE_TIME = "updateTime";

    /**
     * 乐观锁
     */
    public static final String VERSION = "version";

    /**
     * 逻辑删除
     */
    public static final String DELETE_FLAG = "deleteFlag";
}
