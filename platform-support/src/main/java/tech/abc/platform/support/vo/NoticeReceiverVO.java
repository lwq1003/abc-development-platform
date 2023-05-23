package tech.abc.platform.support.vo;

import tech.abc.platform.common.base.BaseVO;
import tech.abc.platform.common.vo.TreeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 通知公告接收组织机构 视图对象
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

public class NoticeReceiverVO extends BaseVO  {

    private static final long serialVersionUID = 1L;


    /**
     * 通知公告标识
     */
    private String noticeId;

    private String noticeTitle;

    /**
     * 组织机构标识
     */
    private String organizationId;

    /**
     * 拥有的权限集合
     */
    private List<String> organizationIdList;

    /**
     * 相关权限信息
     */
    private List<TreeVO> treeData;


}
