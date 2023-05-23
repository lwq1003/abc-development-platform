package tech.abc.platform.support.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.constant.TableFieldConstant;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.TreeVO;
import tech.abc.platform.support.entity.Notice;
import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.support.service.NoticeReceiverService;
import tech.abc.platform.support.service.NoticeService;
import tech.abc.platform.support.vo.NoticeReceiverVO;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.service.OrganizationService;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 通知公告接收组织机构 前端控制器
 *
 * @author wqliu
 */
@RestController
@RequestMapping("/support/noticeReceiver")
public class NoticeReceiverController extends BaseController {

    @Autowired
    private NoticeReceiverService noticeReceiverService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 获取指定通知公告的发布机构
     */
    @ApiOperation(value = "获取指定通知公告的发布机构")
    @ApiImplicitParam(name = "noticeId", value = "通知公告", required = true, dataType = "String", paramType = "query")
    @GetMapping("/getOrganization")
    @SystemLog(value = "通知公告-机构-详情")
    public ResponseEntity<Result> getOrganization(@RequestParam("noticeId") String noticeId) {
        NoticeReceiverVO vo = new NoticeReceiverVO();
        // 填充角色信息
        Notice notice = noticeService.getById(noticeId);
        vo.setNoticeId(notice.getId());
        vo.setNoticeTitle(notice.getTitle());
        // 获取已发布的机构范围
        QueryWrapper<NoticeReceiver> noticeReceiverQueryWrapper = new QueryWrapper<>();
        noticeReceiverQueryWrapper.lambda().eq(NoticeReceiver::getNoticeId, noticeId);
        List<NoticeReceiver> noticeReceiverList = noticeReceiverService.list(noticeReceiverQueryWrapper);
        List<String> organizationIdList = noticeReceiverList.stream()
                .map(noticeReceiver -> noticeReceiver.getOrganizationId())
                .collect(Collectors.toList());
        // 查询所有机构
        QueryWrapper<Organization> organizationQueryWrapper = new QueryWrapper<>();
        organizationQueryWrapper.lambda().eq(Organization::getStatus, StatusEnum.NORMAL.toString());
        organizationQueryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<Organization> organizationList = organizationService.list(organizationQueryWrapper);

        // 转化成树结构数据
        List<TreeVO> treeList = organizationList.stream()
                .map(e -> convert2TreeVO(e, organizationIdList)).collect(Collectors.toList());
        List<TreeVO> treeVOList = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        vo.setTreeData(treeVOList);
        return ResultUtil.success(vo);
    }

    /**
     * 保存通知公告与机构的对应关系
     */
    @ApiOperation(value = "保存通知公告与机构的对应关系")
    @PostMapping("/saveNoticeOrganization")
    @SystemLog(value = "通知公告-机构-保存")
    public ResponseEntity<Result> saveNoticeOrganization(@RequestBody NoticeReceiverVO vo) {
        String noticeId = vo.getNoticeId();
        List<String> organizationIdList = vo.getOrganizationIdList();
        noticeReceiverService.saveNoticeOrganization(noticeId, organizationIdList);
        return ResultUtil.success();
    }

    /**
     * 通知公告转化为树视图对象
     *
     * @param entity             机构实体
     * @param organizationIdList 公告拥有发布机构集合
     * @return
     */
    private TreeVO convert2TreeVO(Organization entity, List<String> organizationIdList) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getOrganization());
        tree.setLabel(entity.getName());
        if (organizationIdList.contains(entity.getId())) {
            // 如果当前角色拥有该权限，则设置为勾选状态
            tree.setChecked(true);
        }
        return tree;
    }
}
