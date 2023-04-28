package tech.abc.platform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户 Mapper 接口
 *
 * @author wqliu
 * @date 2023-04-23
 */
public interface UserMapper extends BaseMapper<User> {


    /**
     * 获取用户权限
     *
     * @param id 用户标识
     * @return 用户权限列表
     */
    List<PermissionItem> getPermission(String id);

    /**
     * 验证权限
     *
     * @param id             用户标识
     * @param permissionCode 权限编码
     * @return 查询结果数量
     */
    int checkPermission(@Param("id") String id, @Param("permissionCode") String permissionCode);
}

