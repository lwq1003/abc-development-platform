package tech.abc.edoc.edoc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 文件类型全局配置
 * @author wqliu
 * @date 2021-3-24
 **/
@Slf4j
public class FileTypeConfig {

    /**
     * 文件类型集合
     */
    private static Map<String,String>  fileTypeMap= new HashMap<>(50);


    private static final FileTypeConfig  instance=new FileTypeConfig();

    private FileTypeConfig(){

    }

    public static FileTypeConfig getInstance(){
        if(fileTypeMap.size()==0){
            load();
        }
        return instance;
    }


    /**
     * 从配置文件中加载数据
     */
    private static void load() {
        Configurations configs = new Configurations();
        try {
            //读取配置文件
            ClassPathResource resource = new ClassPathResource("fileType.yml");
            YAMLConfiguration config=new YAMLConfiguration();
            config.read(resource.getInputStream());
            //填充集合
            Iterator<String> keys = config.getKeys();
            while (keys.hasNext()){
                String key= keys.next();
                fileTypeMap.put(key, config.getString(key));
            }

        } catch (Exception e) {
            log.error("加载文件类型配置文件出错",e);
        }

    }

    /**
     * 根据文件后缀名获取文件类型名称
     * @param suffix 文件后缀
     * @return 文件类型名称
     */
    public  String getTypeName(String suffix){
        return fileTypeMap.getOrDefault(suffix.toLowerCase(),"其他");
    }

}
