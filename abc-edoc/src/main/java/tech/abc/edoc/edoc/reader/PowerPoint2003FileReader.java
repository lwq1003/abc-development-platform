package tech.abc.edoc.edoc.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * PowerPoint2003及以下版本文件读取器（后缀名ppt)
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class PowerPoint2003FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {

        PowerPointExtractor extractor = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            extractor = new PowerPointExtractor(fis);
            return extractor.getText();

        } catch (IOException e) {
            throw new CustomException(FileException.READ_FAILURE, e.getMessage());
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != extractor) {
                    extractor.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败", e);
            }
        }
    }
}