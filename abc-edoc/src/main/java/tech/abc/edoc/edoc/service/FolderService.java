package tech.abc.edoc.edoc.service;

import tech.abc.edoc.edoc.entity.Folder;
import tech.abc.edoc.edoc.entity.PopDocument;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.common.vo.SortInfo;

import java.util.List;
import java.util.Map;

/**
 * 文件夹 服务接口类
 *
 * @author wqliu
 * @date 2024-02-01
 */
public interface FolderService extends BaseService<Folder> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 获取所有上级文件夹标识（包括自身）
    * @param folderId
    * @return
    */
   List<String> getParentFolderId(String folderId);

   /**
    *  移动文件夹
    * @param srcId 源文件夹标识
    * @param targetId 目标文件夹标识
    * @param retainPermission 是否保留权限  YES 是 NO 否
    */
   void move(String srcId, String targetId, String retainPermission);

   /**
    * 复制文件夹
    * @param srcId 源文件夹标识
    * @param targetId 目标文件夹标识
    */
   void copy(String srcId, String targetId);


   /**
    * 更名
    * @param id  文件夹标识
    * @param name 名称
    */
   void rename(String id, String name);

   /**
    * 获取子对象
    *
    * @param id           文件夹标识
    * @param name  名称
    * @param ignoreParent 忽略上级
    * @param sortInfo     排序
    * @return 子对象
    */
   List<PopDocument> getChildren(String id, String name, Boolean ignoreParent, SortInfo sortInfo);

   /**
    * 混合删除
    * @param list 文件夹与文件混合对象
    */
   void mixRemove(List<PopDocument> list);

   /**
    * 混合复制
    * @param list  要复制的文件夹和文档列表
    * @param targetFolderId 目标文件夹标识
    */
   void mixCopy(List<PopDocument> list, String targetFolderId);

   /**
    * 混合移动
    * @param list  要复制的文件夹和文档列表
    * @param targetFolderId 目标文件夹标识
    * @param retainPermission 是否保留权限  YES 是 NO 否
    */
   void mixMove(List<PopDocument> list, String targetFolderId, String retainPermission);
}

