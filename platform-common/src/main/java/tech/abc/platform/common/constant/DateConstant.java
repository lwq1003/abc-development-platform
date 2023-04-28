package tech.abc.platform.common.constant;

import lombok.experimental.UtilityClass;

/**
 * 日期常量类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@UtilityClass
public class DateConstant {

    /**
     * 时间格式:年月日 时分秒
     */
    public final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

    /**
     * UTC格式
     */
    public final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    /**
     * 当天起始 00:00:00
     */
    public final String BEGIN_OF_DAY = "00:00:00";

    /**
     * 当天结束 23:59:59
     */
    public final String END_OF_DAY = "23:59:59";
}
