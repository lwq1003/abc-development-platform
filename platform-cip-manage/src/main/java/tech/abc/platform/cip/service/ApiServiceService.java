package tech.abc.platform.cip.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.cip.entity.ApiService;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * API服务 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface ApiServiceService extends BaseService<ApiService> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 启用
     *
     * @param id 标识
     */
    void enable(String id);

    /**
     * 停用
     *
     * @param id 标识
     */
    void disable(String id);

    /**
     * 查询指定应用对应的接口服务权限列表
     *
     * @param queryWrapper  查询条件
     * @param appId         应用标识
     * @param hasPermission 是否已授权
     * @return
     */
    List<ApiService> queryApiPermission(QueryWrapper<ApiService> queryWrapper, String appId, String hasPermission);

    /**
     * 按服务代码获取处理器
     *
     * @param serviceCode 服务代码
     * @return {@link String} 处理器的完整类名
     */
    String getHandlerByCode(String serviceCode);


    /**
     * 按服务编码获取API服务
     *
     * @param serviceCode 服务编码
     * @return {@link ApiService}
     */
    ApiService getByCode(String serviceCode);
}


