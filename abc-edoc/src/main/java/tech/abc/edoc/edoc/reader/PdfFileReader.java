package tech.abc.edoc.edoc.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * PDF文件读取器
 * @author wqliu
 * @date 2021-4-9
 **/
@Slf4j
public class PdfFileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        PDDocument pdfDocument = null;
        InputStream fis = null;

        try {
            fis = new FileInputStream(filePath);
            PDFTextStripper stripper = new PDFTextStripper();
            pdfDocument = PDDocument.load(fis);
            StringWriter writer = new StringWriter();
            stripper.writeText(pdfDocument, writer);
            return writer.getBuffer().toString();

        }catch (IOException e) {
            throw  new CustomException(FileException.READ_FAILURE,e.getMessage());
        }finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != pdfDocument) {
                    pdfDocument.close();
                }
            } catch (IOException e) {
                log.error("关闭文件资源失败",e);
            }
        }
    }


}
