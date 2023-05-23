package tech.abc.platform.support.vo;

import lombok.Data;

/**
 * 图片上传返回视图对象
 *
 * @author wqliu
 * @date 2023-05-22
 */
@Data
public class ImageUploadReturnVO {
    Integer errno;
    ImageVO data;

}
