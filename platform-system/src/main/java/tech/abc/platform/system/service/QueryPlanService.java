package tech.abc.platform.system.service;

import tech.abc.platform.system.entity.QueryPlan;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 查询方案 服务接口类
 *
 * @author wqliu
 * @date 2024-09-04
 */
public interface QueryPlanService extends BaseService<QueryPlan> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

