package tech.abc.platform.support.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告 视图对象类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class NoticeVO extends BaseVO {
    /**
     * 标题
     */
    @NotBlank(message = "【标题】不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "【内容】不能为空")
    private String content;

    /**
     * 状态
     */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
     * 是否重要
     */
    @NotBlank(message = "【是否重要】不能为空")
    private String importantFlag;

    /**
     * 是否置顶
     */
    @NotBlank(message = "【是否置顶】不能为空")
    private String topFlag;

    /**
     * 允许评论
     */
    @NotBlank(message = "【允许评论】不能为空")
    private String commentFlag;

    /**
     * 阅读次数
     */
    private Integer readCount;

    /**
     * 发布人
     */
    private String publisher;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;


    /********非库表存储属性*****/


    /********字典类*****/
    /**
     * 状态
     */
    private String statusName;

    /**
     * 是否重要
     */
    private String importantFlagName;

    /**
     * 是否置顶
     */
    private String topFlagName;

    /**
     * 允许评论
     */
    private String commentFlagName;


    /********实体类*****/

    /********范围查询*****/
    /**
     * 发布时间起
     */
    private String publishTimeBeginForQuery;

    /**
     * 发布时间止
     */
    private String publishTimeEndForQuery;

    /********自定义扩展*****/

    /**
     * 组织机构标识列表
     */

    private List<String> organizationIdList;


}
