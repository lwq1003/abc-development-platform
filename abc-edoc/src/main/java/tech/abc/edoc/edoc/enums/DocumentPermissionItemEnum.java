package tech.abc.edoc.edoc.enums;

import lombok.Getter;

/**
 * 文档权限项
 * @author wqliu
 */
@Getter
public enum DocumentPermissionItemEnum {
    /**
     * 查看文件夹
     */
    VIEW_FOLDER,
    /**
     * 创建文件夹
     */
    CREATE_FOLDER,
    /**
     * 更名文件夹
     */
    RENAME_FOLDER,
    /**
     * 删除文件夹
     */
    REMOVE_FOLDER,
    /**
     * 复制文件夹
     */
    COPY_FOLDER,
    /**
     * 移动文件夹
     */
    MOVE_FOLDER,
    /**
     * 按用户组授权
     */
    GRANT_PERMISSION_BY_USER_GROUP,
    /**
     * 按组织机构授权
     */
    GRANT_PERMISSION_BY_ORGANIZATION,
    /**
     * 预览文档
     */
    PREVIEW_DOCUMENT,
    /**
     * 上传文档
     */
    UPLOAD_DOCUMENT,
    /**
     * 下载文档
     */
    DOWNLOAD_DOCUMENT,
    /**
     * 更名文档
     */
    RENAME_DOCUMENT,
    /**
     * 更新文档
     */
    UPDATE_DOCUMENT,
    /**
     * 分享文档
     */
    SHARE_DOCUMENT,
    /**
     * 删除文档
     */
    REMOVE_DOCUMENT,
    /**
     * 复制文档
     */
    COPY_DOCUMENT,
    /**
     * 移动文档
     */
    MOVE_DOCUMENT,
    /**
     * 锁定文档
     */
    LOCK_DOCUMENT,
    /**
     * 解锁文档
     */
    UNLOCK_DOCUMENT,
    /**
     * 查看版本列表
     */
    VIEW_DOCUMENT_VERSION,
    /**
     * 恢复版本
     */
    RESTORE_DOCUMENT_VERSION,
    ;



}
