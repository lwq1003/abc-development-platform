package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.SerialNo;

import java.util.List;
import java.util.Map;

/**
 * 流水号 服务接口类
 *
 * @author wqliu
 * @date 2023-05-30
 */
public interface SerialNoService extends BaseService<SerialNo> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 生成单个流水号
     *
     * @param code
     * @return
     */
    String generateSingle(String code);

    /**
     * 生成单个流水号
     *
     * @param id 标识
     * @return
     */
    String generateSingleById(String id);


    /**
     * 批量生成若干个流水号
     *
     * @param code  编码
     * @param count 数量
     * @return
     */
    List<String> generateBatch(String code, int count);

    /**
     * 重置流水号
     */
    void resetSerialNo();

}

