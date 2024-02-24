package tech.abc.edoc.edoc.service;

import tech.abc.edoc.edoc.entity.FolderPermission;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 文件夹权限 服务接口类
 *
 * @author wqliu
 * @date 2024-02-04
 */
public interface FolderPermissionService extends BaseService<FolderPermission> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);


   /**
    * 保存某文件夹权限
    * @param entity 文件夹标识
    * @param permissionCodeList 权限编码列表
    */
   void savePermission(FolderPermission entity,List<String> permissionCodeList);

   /**
    * 获取获取某文件夹对应的某组织机构或用户组的权限项
    * @param folderId 文件夹标识
    * @param objectId 授权对象标识
    * @param objectType 授权对象类型
    * @return 权限项
    */
   List<String> getFolderPermission(String folderId, String objectId, String objectType);


   /**
    * 获取当前用户对某文件夹的权限
    * @param id 文件夹标识
    * @return 权限编码列表
    */
   List<String> getFolderPermission(String id);

   /**
    * 获取当前用户对某文档的权限
    * @param id 文档标识
    * @return 权限编码列表
    */
   List<String> getDocumentPermission(String id);


   /**
    * 验证当前用户对某文件夹是否有某项权限
    * @param folderId 文件夹标识
    * @param permissionCode 权限编码
    */
   void checkPermission(String folderId,String permissionCode);

   /**
    * 验证当前用户对某文件夹是否有某项权限
    * @param folderId 文件夹标识
    * @param permissionCode 权限编码
    * @return true，有权限，false 无权限
    */
   boolean checkPermissionFlag(String folderId, String permissionCode);

   /**
    * 清理某文件夹的权限
    * @param folderId 文件夹标识
    */
   void clearPermission(String folderId);



}

