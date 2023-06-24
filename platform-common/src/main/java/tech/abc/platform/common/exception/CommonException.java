package tech.abc.platform.common.exception;

import lombok.Getter;

/**
 * 公用异常信息
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum CommonException implements ExceptionInterface {


    /**
     * 未找到类
     */
    CLASS_NOT_FOUND("未找到类"),
    /**
     * 属性访问出错
     */
    PROPERTY_ACCESS_ERROR("属性访问出错"),
    /**
     * 对象不存在
     */
    NOT_EXIST("对象不存在"),
    /**
     * 编码已存在
     */
    CODE_EXIST("编码已存在"),
    /**
     * 名称已存在
     */
    NAME_EXIST("名称已存在"),

    /**
     * XXX属性已存在
     */
    PROPERTY_EXIST("已存在:{0}"),

    /**
     * 同节点下XXXX属性已存在
     */
    PROPERTY_EXIST_IN_SAME_NODE("同节点下已存在:{0}"),

    /**
     * 编码已存在(带标识）
     */
    CODE_EXIST_WITH_ID("编码已存在:{0}"),
    /**
     * 名称已存在(带标识）
     */
    NAME_EXIST_WITH_ID("名称已存在:{0}"),
    /**
     * 记录已存在
     */
    RECORD_EXIST("记录已存在"),
    /**
     * 无权限
     */
    NO_PRIVILEGE("无权限"),
    /**
     * 同节点下名称已存在
     */
    NAME_EXIST_IN_SAME_NODE("同节点下名称已存在"),
    /**
     * 标识不能为空
     */
    ID_EMPTY("标识不能为空"),
    /**
     * 重试次数达到上限
     */
    TRY_MAX_COUNT("重试次数达到上限"),
    /**
     * 更新失败
     */
    UPDATE_ERROR("更新失败"),
    /**
     * 无流程启动权限
     */
    NONE_START_PROCESS_PERMISSION("无流程启动权限"),
    /**
     * 数据验证失败
     */
    DATA_VALIDATE_FAILURE("数据验证失败：{0}"),
    /**
     * 无权修改数据
     */
    NO_PRIVILEGE_MODIFY_DATA("无权修改数据"),
    /**
     * 存在下级，不能删除
     */
    HAS_CHILDREN("存在下级，不能删除"),
    /**
     * 上级不能为自身
     */
    UP_CANNOT_SELF("上级不能为自身"),
    /**
     * 上级不能为子节点
     */
    UP_CANNOT_BE_CHILD("上级不能为子节点"),
    /**
     * 未找到对应的枚举类型
     */
    ENUM_TYPE_NOT_FOUNT("未找到对应的枚举类型：【{0}】"),

    ;

    private String message;

    CommonException(String message) {
        this.message = message;
    }
}
