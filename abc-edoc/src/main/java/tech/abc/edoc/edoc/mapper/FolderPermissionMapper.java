package tech.abc.edoc.edoc.mapper;

import org.apache.ibatis.annotations.Param;
import tech.abc.edoc.edoc.entity.FolderPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 文件夹权限 Mapper 接口
 *
 * @author wqliu
 * @date 2024-02-04
 */
public interface FolderPermissionMapper extends BaseMapper<FolderPermission> {
    /**
     * 获取某用户依据用户组授权模式对某文件夹的权限编码
     * @param folderIdList 文件夹标识列表(要获取权限的文件夹及其所有上级的标识)
     * @param userId 用户标识
     * @return 权限列表
     */
    List<String> getPermissionByUserGroup(@Param("folderIdList") List<String> folderIdList, @Param("userId") String userId);

    /**
     * 获取某用户依据组织机构授权模式对某文件夹的权限编码
     * @param folderIdList 文件夹标识列表(要获取权限的文件夹及其所有上级的标识)
     * @param organizationIdList 组织机构列表
     * @return 权限列表
     */
    List<String> getPermissionByOrganization(@Param("folderIdList") List<String> folderIdList,
                                             @Param("organizationIdList") List<String> organizationIdList);
}

