package tech.abc.edoc.edoc.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * excel2003及以下版本文件读取器（后缀名xls)
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class Excel2003FileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        HSSFWorkbook hssfWorkbook = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            hssfWorkbook = new HSSFWorkbook(fis);
            ExcelExtractor excelExtractor = new ExcelExtractor(hssfWorkbook);
            excelExtractor.setFormulasNotResults(true);
            excelExtractor.setIncludeSheetNames(true);
            return excelExtractor.getText();

        } catch (IOException e) {
            throw new CustomException(FileException.READ_FAILURE, e.getMessage());
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != hssfWorkbook) {
                    hssfWorkbook.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败", e);
            }
        }
    }
}