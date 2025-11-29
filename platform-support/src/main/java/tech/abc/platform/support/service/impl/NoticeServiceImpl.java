package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.support.entity.Attachment;
import tech.abc.platform.support.entity.Notice;
import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.support.mapper.NoticeMapper;
import tech.abc.platform.support.service.AttachmentService;
import tech.abc.platform.support.service.NoticeReceiverService;
import tech.abc.platform.support.service.NoticeService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通知公告 服务实现类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Service
@Slf4j
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeReceiverService noticeReceiverService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public Notice init() {
        Notice entity = new Notice();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        entity.setStatus("NORMAL");
        entity.setImportantFlag("NO");
        entity.setTopFlag("NO");
        entity.setCommentFlag("YES");
        // 初始化访问量
        entity.setReadCount(0);
        return entity;
    }

    @Override
    public void beforeAdd(Notice entity) {
        
    }

    @Override
    public void beforeModify(Notice entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Notice> list = this.lambdaQuery().in(Notice::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getTitle());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Notice entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setTitle(entity.getTitle() + " 副本");
    }

    @Override
    protected void afterQuery(Notice entity) {
        // 查询关联发布机构
        QueryWrapper<NoticeReceiver> noticeReceiverQueryWrapper = new QueryWrapper<>();
        noticeReceiverQueryWrapper.lambda().eq(NoticeReceiver::getNotice, entity.getId());
        List<NoticeReceiver> noticeReceiverList = noticeReceiverService.list(noticeReceiverQueryWrapper);
        String organizationIdList = noticeReceiverList.stream().map(x -> x.getOrganization()).collect(Collectors.joining(","));
        entity.setPublishScope(organizationIdList);

    }


    @Override
    protected void beforeAddOrModifyOp(Notice entity) {
        // 先清空组织机构
        clearOrganizationData(entity);

        // 再重新插入当前选择的组织 机构
        String[] organizationList = StringUtils.split(entity.getPublishScope(), ',');
        if (ArrayUtils.isNotEmpty(organizationList)) {
            for (String organizationId : organizationList) {
                NoticeReceiver noticeReceiver = new NoticeReceiver();
                noticeReceiver.setNotice(entity.getId());
                noticeReceiver.setOrganization(organizationId);
                noticeReceiverService.add(noticeReceiver);
            }
        }
        // 更新发布人
        entity.setPublisher(UserUtil.getName());
        entity.setPublishTime(LocalDateTime.now());
    }

    private void clearOrganizationData(Notice entity) {
        QueryWrapper<NoticeReceiver> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoticeReceiver::getNotice, entity.getId());
        noticeReceiverService.remove(queryWrapper);
    }


    @Override
    public void afterRemove(Notice entity) {
        //  清除组织机构
        clearOrganizationData(entity);
        // 删除附件文件
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Attachment::getEntity, entity.getId());
        attachmentService.remove(queryWrapper);
    }


    @Override
    public List<Notice> getPortletData(int count) {
        List<Notice> noticeList = noticeMapper.getPortletData(UserUtil.getOrganizationId(), count);
        return noticeList;
    }


    @Override
    public IPage<Notice> getNoticeViewList(IPage<Notice> page, QueryWrapper<Notice> queryWrapper) {

        return noticeMapper.getNoticeViewList(page, queryWrapper, UserUtil.getOrganizationId());
    }


    @Override
    public void enable(String id) {
        Notice entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        Notice entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


    @Override
    public void setTop(String id) {
        Notice entity = getEntity(id);
        entity.setTopFlag(YesOrNoEnum.YES.name());
        modify(entity);
    }

    @Override
    public void cancelTop(String id) {
        Notice entity = getEntity(id);
        entity.setTopFlag(YesOrNoEnum.NO.name());
        modify(entity);
    }

    @Override
    public void updateReadCount(String id) {
        Notice entity = getEntity(id);
        lambdaUpdate().eq(Notice::getId, id).set(Notice::getReadCount, entity.getReadCount() + 1).update();

    }

    @Override
    public Notice view(String id) {
        // 查看前验证下数据权限，该条通知公告是否发布到当前用户的组织机构
        if (this.checkDataPermission(id) == false) {
            throw new CustomException(CommonException.NO_PRIVILEGE);
        }
        return query(id);
    }


    /**
     * 检查数据权限
     *
     * @param id 通知公告标识
     * @return true 有权限 false 无权限
     */
    private boolean checkDataPermission(String id) {

        Long count = noticeReceiverService.lambdaQuery()
                .eq(NoticeReceiver::getOrganization, UserUtil.getOrganizationId())
                .eq(NoticeReceiver::getNotice, id).count();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


}


