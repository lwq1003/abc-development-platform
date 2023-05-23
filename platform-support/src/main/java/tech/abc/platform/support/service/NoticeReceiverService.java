package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.NoticeReceiver;

import java.util.List;


/**
 * 通知公告接收组织机构 服务类
 * @author wqliu
 * *
 */
public interface NoticeReceiverService extends BaseService<NoticeReceiver> {


    /**
     * 保存通知公告与机构的对应关系
     *
     * @param noticeId           通知公告标识
     * @param organizationIdList 组织机构标识列表
     */
    void saveNoticeOrganization(String noticeId, List<String> organizationIdList);
}
