package tech.abc.edoc.edoc.reader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import tech.abc.edoc.edoc.config.EdocConfig;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 文件读取器工厂
 *
 * @author wqliu
 * @date 2021-4-9
 **/
public class FileReaderFactory {

    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    public static final String PPT = "ppt";
    public static final String PPTX = "pptx";
    public static final String PDF = "pdf";

    /**
     * 根据文件后缀获取文件读取器
     *
     * @param filename
     * @return
     */
    public static FileReader getReader(String filename) {

        FileReader fileReader = null;
        //获取文件后缀
        String fileExtension = FilenameUtils.getExtension(filename);
        if(StringUtils.isNotBlank(fileExtension)){
            String extension=fileExtension.toLowerCase();
            //获取配置
            EdocConfig edocConfig = SpringUtil.getBean(EdocConfig.class);
            if (edocConfig.getTextFileType().contains(extension)) {
                fileReader = new TextFileReader();
            } else if (DOC.equals(extension)) {
                fileReader = new Word2003FileReader();
            } else if (DOCX.equals(extension)) {
                fileReader = new Word2007FileReader();
            } else if (XLS.equals(extension)) {
                fileReader = new Excel2003FileReader();
            } else if (XLSX.equals(extension)) {
                fileReader = new Excel2007FileReader();
            } else if (PPT.equals(extension)) {
                fileReader = new PowerPoint2003FileReader();
            } else if (PPTX.equals(extension)) {
                fileReader = new PowerPoint2007FileReader();
            }else if (PDF.equals(extension)) {
                fileReader = new PdfFileReader();
            }

        }
        return fileReader;
    }
}
