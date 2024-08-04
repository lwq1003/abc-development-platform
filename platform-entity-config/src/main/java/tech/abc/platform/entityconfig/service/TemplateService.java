package tech.abc.platform.entityconfig.service;

import tech.abc.platform.entityconfig.entity.Template;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 模板 服务接口类
 *
 * @author wqliu
 * @date 2024-07-23
 */
public interface TemplateService extends BaseService<Template> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

