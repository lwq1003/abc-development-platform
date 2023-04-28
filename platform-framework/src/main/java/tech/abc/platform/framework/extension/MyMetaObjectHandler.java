package tech.abc.platform.framework.extension;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import tech.abc.platform.common.constant.CommonPropertyConstant;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.SessionExpiredException;
import tech.abc.platform.common.utils.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 全局自动填充字段处理
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = getUserId();
        this.strictInsertFill(metaObject, CommonPropertyConstant.CREATE_ID, String.class, userId);
        this.strictInsertFill(metaObject, CommonPropertyConstant.CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonPropertyConstant.UPDATE_ID, String.class, userId);
        this.strictInsertFill(metaObject, CommonPropertyConstant.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonPropertyConstant.DELETE_FLAG, String.class, YesOrNoEnum.NO.name());
        this.strictInsertFill(metaObject, CommonPropertyConstant.VERSION, Integer.class, 1);


    }

    private String getUserId() {
        String userId = "";
        try {
            userId = UserUtil.getId();
        } catch (SessionExpiredException e) {
            // 登录时，账号正确密码错误，更新用户表失败次数时会自动写入修改人
            // 此时用户并不是登录状态，所以忽略该情况，不作为异常进行处理
        }
        return userId;
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = getUserId();
        this.strictUpdateFill(metaObject, CommonPropertyConstant.UPDATE_ID, String.class, userId);
        this.strictUpdateFill(metaObject, CommonPropertyConstant.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());

    }
}
