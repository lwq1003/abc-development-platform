package tech.abc.platform.common.utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件工具类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Slf4j
@UtilityClass
public class FileUtil {


    private static final String BYTE = "B";
    private static final String KB = "KB";
    private static final String MB = "MB";
    private static final String GB = "GB";
    private static final long UNIT_SIZE = 1024;


    /**
     * 友好显示文件大小
     *
     * @param fileSize
     * @return
     */
    public String getFileSize(long fileSize) {
        return FileUtils.byteCountToDisplaySize(fileSize);
    }


    /**
     * 获取文件编码
     *
     * @param file
     * @return
     */
    public static String getFileEncoding(File file) {
        // 默认设置为utf-8
        String encoding = "utf-8";
        try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            CharsetDetector detector = new CharsetDetector();
            detector.setText(data);
            CharsetMatch match = detector.detect();
            if (match != null) {
                encoding = match.getName();
                log.info("类库识别文件编码：{}", encoding);
            } else {
                log.info("未识别出编码，使用默认");
            }
        } catch (IOException exception) {
            // 读取文件失败，不处理异常
        }
        return encoding;
    }

}
