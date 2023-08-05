package tech.abc.platform.businessflow.service;

import tech.abc.platform.businessflow.entity.Leave;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 请假单 服务接口类
 *
 * @author wqliu
 * @date 2023-07-03
 */
public interface LeaveService extends BaseService<Leave> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

