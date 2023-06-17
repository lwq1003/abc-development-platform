package tech.abc.platform.support.service;

import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 通知接收 服务接口类
 *
 * @author wqliu
 * @date 2023-06-15
 */
public interface NoticeReceiverService extends BaseService<NoticeReceiver> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

