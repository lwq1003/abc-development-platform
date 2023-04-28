package tech.abc.platform.common.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * 通用工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */

public class CommonUtil {

    private static final long serialVersionUID = 1L;
    public static final String UNDERLINE = "_";


    /**
     * 将驼峰命名转化成下划线
     *
     * @param camelString
     * @return
     */
    public static String camelToUnderline(String camelString) {

        StringBuilder sb = new StringBuilder(camelString);
        // 偏移计数
        int count = 0;
        // 遍历字符串每一位字符，发现是大写字母，则前面插入下划线
        for (int i = 0; i < camelString.length(); i++) {
            if (Character.isUpperCase(camelString.charAt(i))) {
                sb.insert(i + count, "_");
                count += 1;
            }
        }
        return sb.toString().toLowerCase();
    }


    /**
     * 将下划线命名的字符串转换为驼峰式。
     */
    public static String underlineToCamelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (StringUtils.isEmpty(name)) {
            // 没必要转换
            return "";
        } else if (!name.contains(UNDERLINE)) {
            // 不含下划线，仅将首字母小写
            return name.toLowerCase();
        }
        // 用下划线将原始字符串分割
        String[] camels = StringUtils.split(name, UNDERLINE);
        result.append(camels[0].toLowerCase());
        for (int i = 1; i < camels.length; i++) {

            // 其他的驼峰片段，首字母大写
            result.append(camels[i].substring(0, 1).toUpperCase());
            result.append(camels[i].substring(1).toLowerCase());
        }
        return result.toString();
    }
}
