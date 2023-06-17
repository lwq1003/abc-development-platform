package tech.abc.platform.support.service.impl;

import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.support.mapper.NoticeReceiverMapper;
import tech.abc.platform.support.service.NoticeReceiverService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
/**
* 通知接收 服务实现类
*
* @author wqliu
* @date 2023-06-15
*/
@Service
@Slf4j
public class NoticeReceiverServiceImpl extends BaseServiceImpl<NoticeReceiverMapper, NoticeReceiver> implements NoticeReceiverService {
    @Override
    public NoticeReceiver init() {
        NoticeReceiver entity=new NoticeReceiver();
        //默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(NoticeReceiver entity) {
        //唯一性验证
    }

    @Override
    public void beforeModify(NoticeReceiver entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<NoticeReceiver> list = this.lambdaQuery().in(NoticeReceiver::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getOrganization());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(NoticeReceiver entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setOrganization (entity.getOrganization() + " 副本");
    }

}

