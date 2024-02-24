package tech.abc.edoc.edoc.service;

import tech.abc.edoc.edoc.entity.DocumentFavorite;
import tech.abc.edoc.edoc.entity.PopDocument;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 文档收藏夹 服务接口类
 *
 * @author wqliu
 * @date 2024-02-04
 */
public interface DocumentFavoriteService extends BaseService<DocumentFavorite> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 添加收藏
    * @param entity 文件夹与文档对象
    */
   void addFavorite(PopDocument entity);

   /**
    * 获取收藏列表
    * @param name 名称
    * @return 收藏列表
    */
   List<DocumentFavorite> getFavoriteList(String name);
}

