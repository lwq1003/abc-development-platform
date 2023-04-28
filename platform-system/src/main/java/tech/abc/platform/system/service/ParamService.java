package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统参数 服务接口类
 *
 * @author wqliu
 * @date 2023-04-17
 */
public interface ParamService extends BaseService<Param> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取参数对象
     *
     * @param key
     * @return
     */
    Param getParamByKey(String key);


    /**
     * 获取参数值
     *
     * @param key
     * @return
     */
    String getParamValue(String key);


}

