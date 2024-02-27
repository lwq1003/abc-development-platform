package tech.abc.platform.elasticsearch.reader;


import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;
import tech.abc.platform.common.utils.FileUtil;


import java.io.*;

/**
 * 文本类型文件读取器
 *
 * @author wqliu
 * @date 2021-4-9
 **/
public class TextFileReader implements FileReader {
    @Override
    public String readFile(String filePath) {
        // 识别编码类型
        String encoding = FileUtil.getFileEncoding(new File(filePath));
        StringBuffer sb = new StringBuffer();
        try {
            // 逐行读取内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), encoding));
            String line = new String();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new CustomException(FileException.READ_FAILURE, e.getMessage());
        }
        return sb.toString();
    }


}
