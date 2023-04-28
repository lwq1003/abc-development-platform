package tech.abc.platform.common.utils;

import tech.abc.platform.common.constant.CacheConstant;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.DataDictionaryExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 字典工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Component
public class DictionaryUtil {

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 根据字典项编码获取字典项名称
     *
     * @param dictionaryTypeCode
     * @param code
     * @return
     */
    public String getNameByCode(String dictionaryTypeCode, String code) {
        if (StringUtils.isNotBlank(code)) {
            String name = cacheUtil.get(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX +
                    "_" + dictionaryTypeCode + "_" + code);
            return name;
        }
        return StringUtils.EMPTY;

    }

    /**
     * 根据字典项名称获取字典项编码
     *
     * @param dictionaryTypeCode
     * @param code
     * @return
     */
    public String getCodeByName(String dictionaryTypeCode, String name) {
        if (StringUtils.isNotBlank(name)) {
            // 通过名称反向查编码
            String code = cacheUtil.get(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX +
                    "_" + dictionaryTypeCode + "_" + name);
            if (StringUtils.isNotBlank(code)) {
                return code;
            }
        }
        // 如未找到说明数据不规范，例如通过excel导入数据场景下，因此需要抛出异常
        throw new CustomException(DataDictionaryExceptionEnum.CANT_FIND_DICTIONARY_ITEM_CODE, name);

    }
}
