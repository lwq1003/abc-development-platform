package tech.abc.platform.elasticsearch.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.extractor.WordExtractor;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * word2003及以下版本文件读取器（后缀名doc)
 *
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class Word2003FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        WordExtractor extractor = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            extractor = new WordExtractor(fis);
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
