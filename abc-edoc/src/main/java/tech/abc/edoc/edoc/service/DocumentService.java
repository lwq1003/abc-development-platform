package tech.abc.edoc.edoc.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 文档 服务接口类
 *
 * @author wqliu
 * @date 2024-02-03
 */
public interface DocumentService extends BaseService<Document> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 上传文件块
    *
    * @param fileChunk
    * @return 如是最后一块, 返回附件实体实体标识, 否则返回null
    */
   String uploadChunk(FileChunk fileChunk);

   /**
    * 合并文件块
    * @param fileInfo 文件信息
    * @return {@link String} 文件标识
    */
   String mergeChunks(FileInfo fileInfo);


   /**
    * 获取文件流
    * @param id 文件标识
    * @return 文件流
    */
   InputStream getFile(String id);

   /**
    * 更名
    * @param id 文档标识
    * @param name 名称
    */
   void rename(String id, String name);

   /**
    * 附带权限验证的分页查询
    * @param page 分页对象
    * @param queryWrapper 查询条件
    * @param folderId 从属文件夹标识
    * @return 文档列表
    */
   IPage<Document> pageWithPermission(IPage<Document> page, Wrapper<Document> queryWrapper, String folderId);

   /**
    * 复制文档
    * @param documentId 文档标识
    * @param folderId 目标文件夹标识
    */
   void copy(String documentId, String folderId);

   /**
    * 移动文档
    * @param documentId 文档标识
    * @param folderId 目标文件夹标识
    */
   void move(String documentId, String folderId);




   /**
    * 获取访问令牌
    * @param id 文档标识
    * @return 令牌
    */
   String getToken(String id);

   /**
    *
    * 获取访问令牌
    * @param id 文档标识
    * @param validHours 有效时长
    * @return 令牌
    */
   String getToken(String id, BigDecimal validHours);

   /**
    * 通过访问令牌获取文件流
    * @param token 令牌
    * @return 文件流
    */
   InputStream getStreamByToken(String token);


   /**
    * 锁定
    * @param id 文档标识
    */
   void lock(String id);

   /**
    * 解锁
    * @param id 文档标识
    */
   void unlock(String id);


   /**
    * 更新文档
    * @param fileChunk 文件块
    * @param documentId 文档标识
    * @param documentVersion 文档版本
    * @param versionTag 版本标签
    */
   void updateVersion(FileChunk fileChunk, String documentId, String documentVersion, String versionTag);

   /**
    * 下载某版本的文档
    * @param id 文档标识
    * @param version 版本号
    * @return 文件流
    */
   InputStream getFile(String id, String version);

   /**
    * 通过访问令牌获取指定版本文件流
    * @param token 令牌
    * @param version 版本
    * @return 文件流
    */
   InputStream getStreamByToken(String token, String version);


   /**
    * 获取某文档指定版本的大小
    * @param id 文档标识
    * @param version 版本号
    * @return 文档字节数
    */
   long getFileSize(String id, String version);

   /**
    * 恢复指定版本
    * @param id 文档标识
    * @param version 指定版本
    */
   void restore(String id, String version);


}

