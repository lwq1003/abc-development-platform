package tech.abc.platform.elasticsearch.reader;

/**
 * 文件读取接口
 *
 * @author wqliu
 * @date 2021-4-9
 **/
public interface FileReader {

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    String readFile(String filePath);
}
