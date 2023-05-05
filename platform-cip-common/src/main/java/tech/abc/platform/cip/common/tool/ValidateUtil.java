package tech.abc.platform.cip.common.tool;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;

/**
 * 数据验证工具类
 *
 * @author wqliu
 * @date 2022-1-13
 **/
@UtilityClass
public class ValidateUtil {

    private final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 验证日期字符串格式是否正确， true 符合格式 ，false 不符合格式
     *
     * @param dateString
     * @param format
     * @return
     */
    public static boolean dateIsFormat(String dateString, String format) {


        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dateFormat.parse(dateString);
        } catch (Exception e) {
            return false;

        }
        return true;
    }

    /**
     * 验证日期字符串格式是否正确， true 符合格式 ，false 不符合格式
     *
     * @param dateString
     * @param format
     * @return
     */
    public static boolean dateIsFormat(String dateString) {

        return dateIsFormat(dateString, LONG_DATE_FORMAT);
    }


}
