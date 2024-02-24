package tech.abc.edoc.edoc.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * word2007及以上版本文件读取器（后缀名docx)
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class Word2007FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        XWPFWordExtractor extractor = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(fis);
            extractor = new XWPFWordExtractor(document);
            return extractor.getText();

        }catch (IOException e) {
            throw  new CustomException(FileException.READ_FAILURE,e.getMessage());
        }finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != extractor) {
                    extractor.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败",e);
            }
        }
    }


}
