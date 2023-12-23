package tech.abc.platform.oss.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tech.abc.platform.oss.config.OssConfig;

import java.util.Calendar;

/**
 * 对象存储服务接口抽象实现类
 * @author wqliu
 * @date 2023-11-23
 */
public abstract class BaseObjectStoreService implements ObjectStoreService{



    @Autowired
    protected OssConfig ossConfig;

    @Override
    public String generateRelativePath(String moduleCode, String entityType) {
        // 生成附件上传路径 根路径/模块名/实体类型名/年份/月份
        Calendar calendar = Calendar.getInstance();
        StringBuilder sbRelativePath = new StringBuilder()
                .append(moduleCode).append("/")
                .append(entityType).append("/")
                .append(calendar.get(Calendar.YEAR)).append("/")
                // 月份左边补零到2位
                .append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0"))
                .append("/");
        return sbRelativePath.toString();


    }

    /**
     * 获取文件全路径
     *
     * @param relativePath
     * @return
     */
    @Override
    public String getFullPath(String relativePath) {
        String basePath = ossConfig.getBasePath();
        return basePath+relativePath;
    }
}
