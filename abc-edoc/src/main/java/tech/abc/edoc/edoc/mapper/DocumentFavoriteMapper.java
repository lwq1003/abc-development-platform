package tech.abc.edoc.edoc.mapper;

import org.apache.ibatis.annotations.Param;
import tech.abc.edoc.edoc.entity.DocumentFavorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 文档收藏夹 Mapper 接口
 *
 * @author wqliu
 * @date 2024-02-04
 */
public interface DocumentFavoriteMapper extends BaseMapper<DocumentFavorite> {

    /**
     * 获取收藏列表
     *
     * @param userId 用户
     * @param name  名称
     * @return {@link List}<{@link DocumentFavorite}>
     */
    List<DocumentFavorite> getFavoriteList(@Param("userId") String userId,@Param("name") String name);
}

