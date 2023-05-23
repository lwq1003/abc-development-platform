package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.support.mapper.NoticeReceiverMapper;
import tech.abc.platform.support.service.NoticeReceiverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知公告接收组织机构 服务实现类
 * @author wqliu
 * *
 */
@Service
public class NoticeReceiverServiceImpl extends BaseServiceImpl<NoticeReceiverMapper, NoticeReceiver> implements NoticeReceiverService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNoticeOrganization(String noticeId, List<String> organizationIdList) {
        //先清空公告拥有的机构
        QueryWrapper<NoticeReceiver> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(NoticeReceiver::getNoticeId,noticeId);
        remove(queryWrapper);
        //再重新插入当前选择的机构
        List<NoticeReceiver>  noticeReceiverList = organizationIdList.stream().map(organizationId->{
            NoticeReceiver noticeReceiver = new NoticeReceiver();
            noticeReceiver.setNoticeId(noticeId);
            noticeReceiver.setOrganizationId(organizationId);
            return noticeReceiver;
        }).collect(Collectors.toList());
        saveBatch(noticeReceiverList);
    }
}
