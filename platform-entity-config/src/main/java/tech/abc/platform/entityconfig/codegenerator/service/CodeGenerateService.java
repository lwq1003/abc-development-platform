package tech.abc.platform.entityconfig.codegenerator.service;

/**
 * 代码生成服务
 * 代码生成服务接口
 *
 * @author wqliu
 * @date 2022-11-1
 */
public interface CodeGenerateService {
    /**
     * 生成实体代码
     *
     * @param entityCode 实体编码
     */
    void generateCode(String entityCode);

    /**
     * 生成库表
     *
     * @param entityCode 实体代码
     */
    void generateTable(String entityCode);
}

