package tech.abc.platform.system.service.impl;

import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.system.entity.UserPasswordChangeLog;
import tech.abc.platform.system.mapper.UserPasswordChangeLogMapper;
import tech.abc.platform.system.service.UserPasswordChangeLogService;

/**
 * 用户密码修改日志 服务实现类
 *
 * @author wqliu
 * @date 2020-05-05
 */
@Service
public class UserPasswordChangeLogServiceImpl extends BaseServiceImpl<UserPasswordChangeLogMapper, UserPasswordChangeLog> implements UserPasswordChangeLogService {

}
