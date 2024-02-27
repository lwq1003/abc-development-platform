package tech.abc.platform.elasticsearch.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * PowerPoint2007及以上版本文件读取器（后缀名pptx)
 *
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class PowerPoint2007FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        XMLSlideShow slide = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            slide = new XMLSlideShow(fis);
            XSLFPowerPointExtractor extractor = new XSLFPowerPointExtractor(slide);
            return extractor.getText();
        } catch (IOException e) {
            throw new CustomException(FileException.READ_FAILURE, e.getMessage());
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != slide) {
                    slide.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败", e);
            }
        }
    }
}