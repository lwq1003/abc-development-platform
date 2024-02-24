package tech.abc.edoc.edoc.service.impl;

import tech.abc.edoc.edoc.entity.DocumentVersion;
import tech.abc.edoc.edoc.mapper.DocumentVersionMapper;
import tech.abc.edoc.edoc.service.DocumentVersionService;
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
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* 文档版本 服务实现类
*
* @author wqliu
* @date 2024-02-04
*/
@Service
@Slf4j
public class DocumentVersionServiceImpl extends BaseServiceImpl<DocumentVersionMapper, DocumentVersion> implements DocumentVersionService {

    @Override
    public DocumentVersion init() {
        DocumentVersion entity=new DocumentVersion();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(DocumentVersion entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(DocumentVersion entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DocumentVersion> list = this.lambdaQuery().in(DocumentVersion::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getDocumentVersion());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(DocumentVersion entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setDocumentVersion (entity.getDocumentVersion() + " 副本");
    }

    @Override
    public DocumentVersion getNewestVersion(String documentId) {
        List<DocumentVersion> list = getList(documentId);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<DocumentVersion> getList(String documentId) {
        List<DocumentVersion> list = this.lambdaQuery().eq(DocumentVersion::getDocumentId, documentId)
                .orderByDesc(DocumentVersion::getCreateTime).list();
        return list;
    }

}

