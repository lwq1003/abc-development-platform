package tech.abc.edoc.edoc.service;

import tech.abc.edoc.edoc.entity.DocumentVersion;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 文档版本 服务接口类
 *
 * @author wqliu
 * @date 2024-02-04
 */
public interface DocumentVersionService extends BaseService<DocumentVersion> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 获取最新版本信息
    * @param documentId 文档标识
    * @return
    */
   DocumentVersion getNewestVersion(String documentId);

   /**
    * 获取版本列表
    * @param documentId
    * @return
    */
   List<DocumentVersion> getList(String documentId);
}

