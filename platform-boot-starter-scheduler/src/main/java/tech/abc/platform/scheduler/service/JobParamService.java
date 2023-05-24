package tech.abc.platform.scheduler.service;

import tech.abc.platform.scheduler.entity.JobParam;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 任务参数 服务接口类
 *
 * @author wqliu
 * @date 2023-05-24
 */
public interface JobParamService extends BaseService<JobParam> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

