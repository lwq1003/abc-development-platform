package tech.abc.platform.entityconfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.abc.platform.entityconfig.entity.EntityModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 实体模型 Mapper 接口
 *
 * @author wqliu
 * @date 2023-04-13
 */
public interface EntityModelMapper extends BaseMapper<EntityModel> {

    /**
     * 创建表
     *
     * @param tableName          表名
     * @param tableComment       表备注
     * @param tableFieldInfoList 字段信息列表
     */
    void createTable(@Param("tableName") String tableName, @Param("tableComment") String tableComment, @Param("tableFieldInfoList") List tableFieldInfoList);
}

