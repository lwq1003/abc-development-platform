package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.SerialNo;

import java.util.List;


/**
 * 单据流水号 服务类
 *
 * @author wqliu
 * *
 */
public interface SerialNoService extends BaseService<SerialNo> {

    /**
     * 生成单个流水号
     *
     * @param code
     * @return
     */
    String generateSingle(String code);


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
