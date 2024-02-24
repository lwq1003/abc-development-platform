package tech.abc.edoc.edoc.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import tech.abc.edoc.edoc.entity.DocumentFavorite;
import tech.abc.edoc.edoc.entity.PopDocument;
import tech.abc.edoc.edoc.exception.DocumentFavoriteExceptionEnum;
import tech.abc.edoc.edoc.mapper.DocumentFavoriteMapper;
import tech.abc.edoc.edoc.service.DocumentFavoriteService;
import tech.abc.edoc.edoc.service.DocumentService;
import tech.abc.edoc.edoc.service.FolderService;
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
import tech.abc.platform.common.utils.UserUtil;

/**
* 文档收藏夹 服务实现类
*
* @author wqliu
* @date 2024-02-04
*/
@Service
@Slf4j
public class DocumentFavoriteServiceImpl extends BaseServiceImpl<DocumentFavoriteMapper, DocumentFavorite> implements DocumentFavoriteService {

    @Autowired
    private FolderService folderService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private DocumentFavoriteMapper documentFavoriteMapper;

    @Override
    public DocumentFavorite init() {
        DocumentFavorite entity=new DocumentFavorite();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        return entity;
    }

    @Override
    protected void beforeAdd(DocumentFavorite entity) {
        long  count = this.lambdaQuery().eq(DocumentFavorite::getObjectId, entity.getObjectId())
                .eq(DocumentFavorite::getObjectType, entity.getObjectType())
                .eq(DocumentFavorite::getUserId, UserUtil.getId()).count();
        if(count>0){
            throw new CustomException(DocumentFavoriteExceptionEnum.FAVORITE_ITEM_EXIST);
        }
    }

    @Override
    protected void beforeRemove(DocumentFavorite entity) {
        //权限验证，只能移除自己的收藏项
        if(!entity.getUserId().equals(UserUtil.getId())){
            throw new CustomException(DocumentFavoriteExceptionEnum.CANT_REMOVE_OTHER_DATA);
        }
    }

    @Override
    public void beforeModify(DocumentFavorite entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        return result;
    }

    @Override
    protected void copyPropertyHandle(DocumentFavorite entity, String... value) {
    }

    @Override
    public void addFavorite(PopDocument popDocument) {
        DocumentFavorite entity=new DocumentFavorite();
        entity.setObjectId(popDocument.getId());
        entity.setObjectType(popDocument.getObjectType());
        entity.setUserId(UserUtil.getId());
        add(entity);
    }



    @Override
    public List<DocumentFavorite> getFavoriteList(String name) {
        //获取当前用户的收藏列表
        return documentFavoriteMapper.getFavoriteList(UserUtil.getId(),name);


    }


}

