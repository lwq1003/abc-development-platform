package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.ApiService;
import tech.abc.platform.cip.entity.ApiServicePermission;
import tech.abc.platform.cip.exception.ApiServiceExceptionEnum;
import tech.abc.platform.cip.mapper.ApiServiceMapper;
import tech.abc.platform.cip.service.ApiServicePermissionService;
import tech.abc.platform.cip.service.ApiServiceService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API服务 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class ApiServiceServiceImpl extends BaseServiceImpl<ApiServiceMapper, ApiService> implements ApiServiceService {
    @Autowired
    private ApiServicePermissionService apiServicePermissionService;

    @Override
    public ApiService init() {
        ApiService entity = new ApiService();
        // 默认值处理
        entity.setCategory("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(ApiService entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(ApiService::getName, entity.getName())
                    .eq(ApiService::getCategory, entity.getCategory()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(ApiService::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(ApiService entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(ApiService::getName, entity.getName())
                    .eq(ApiService::getCategory, entity.getCategory())
                    .ne(ApiService::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(ApiService::getCode, entity.getCode())
                    .ne(ApiService::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ApiService> list = this.lambdaQuery().in(ApiService::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(ApiService entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        ApiService entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        ApiService entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public List<ApiService> queryApiPermission(QueryWrapper<ApiService> queryWrapper, String appId, String hasPermission) {
        // 查询出所有接口服务
        List<ApiService> serviceList = this.list(queryWrapper);
        // 查询当前应用标识拥有权限的接口服务标识列表
        List<ApiServicePermission> hasPermissionList =
                apiServicePermissionService.lambdaQuery().eq(ApiServicePermission::getApp, appId).list();
        List<String> hasPermissionIdList = hasPermissionList.stream().map(x -> x.getApiService()).collect(Collectors.toList());
        // 设置授权标志位
        serviceList.stream().forEach(x -> {
            if (CollectionUtils.isNotEmpty(hasPermissionIdList)) {
                // 有至少1条权限记录
                if (hasPermissionIdList.contains(x.getId())) {
                    x.setHasPermission(YesOrNoEnum.YES.name());
                } else {
                    x.setHasPermission(YesOrNoEnum.NO.name());
                }
            } else {
                // 无任何权限，所有标识位置为NO
                x.setHasPermission(YesOrNoEnum.NO.name());
            }
        });
        if (StringUtils.isBlank(hasPermission)) {
            // 未设置查询条件，查询全部
            return serviceList;

        } else {
            // 只查询对应的数据列表
            return serviceList.stream().filter(x -> x.getHasPermission().equals(hasPermission)).collect(Collectors.toList());
        }

    }

    @Override
    public String getHandlerByCode(String code) {

        ApiService entity = getByCode(code);
        String handler = entity.getHandler();
        if (StringUtils.isBlank(handler)) {
            throw new CustomException(ApiServiceExceptionEnum.API_SERVICE_NOT_SET_HANDLER);
        }
        return handler;

    }

    @Override
    public ApiService getByCode(String code) {
        List<ApiService> list = this.lambdaQuery().eq(ApiService::getCode, code).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            throw new CustomException(ApiServiceExceptionEnum.API_SERVICE_NOT_EXIST);
        }

    }
}


