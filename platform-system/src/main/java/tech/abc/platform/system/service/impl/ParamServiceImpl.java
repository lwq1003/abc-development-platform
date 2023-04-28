package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.system.entity.Param;
import tech.abc.platform.system.mapper.ParamMapper;
import tech.abc.platform.system.service.ParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统参数 服务实现类
 *
 * @author wqliu
 * @date 2023-04-17
 */
@Service
@Slf4j
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements ParamService {
    @Override
    public Param init() {
        Param entity = new Param();
        // 默认值处理
        entity.setParamName("");
        return entity;
    }

    @Override
    public void beforeAdd(Param entity) {
        // 唯一性验证
        // 验证 参数名称 全局唯一
        long countParamName = this.lambdaQuery().eq(Param::getParamName, entity.getParamName()).count();
        if (countParamName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【参数名称】");
        }
        // 验证 参数编码 全局唯一
        long countParamKey = this.lambdaQuery().eq(Param::getParamKey, entity.getParamKey()).count();
        if (countParamKey > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【参数编码】");
        }
    }

    @Override
    public void beforeModify(Param entity) {
        // 唯一性验证
        // 验证 参数名称 全局唯一
        long countParamName = this.lambdaQuery().eq(Param::getParamName, entity.getParamName())
                .ne(Param::getId, entity.getId()).count();
        if (countParamName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【参数名称】");
        }
        // 验证 参数编码 全局唯一
        long countParamKey = this.lambdaQuery().eq(Param::getParamKey, entity.getParamKey())
                .ne(Param::getId, entity.getId()).count();
        if (countParamKey > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【参数编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Param> list = this.lambdaQuery().in(Param::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getParamName());
                });
            }
        }
        return result;
    }

    @Override
    public Param getParamByKey(String key) {
        // 根据系统参数编码获取参数值
        QueryWrapper<Param> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Param::getParamKey, key);
        return this.getOne(queryWrapper);
    }

    @Override
    public String getParamValue(String key) {
        Param param = getParamByKey(key);
        if (param != null) {
            return param.getParamValue();
        }
        return "";

    }

}

