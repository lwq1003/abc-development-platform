package tech.abc.platform.support.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.AllowAuthenticated;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.Notice;
import tech.abc.platform.support.exception.PortalExceptionEnum;
import tech.abc.platform.support.service.NoticeService;
import tech.abc.platform.support.vo.NoticeVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通知公告 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/support/notice")
@Slf4j
public class NoticeController extends BaseController {
    public static final int MAX_COUNT = 100;
    @Autowired
    private NoticeService noticeService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Notice entity = noticeService.init();
        NoticeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "通知公告-新增")
    @PreAuthorize("hasPermission(null,'support:notice:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody NoticeVO vo) {
        Notice entity = convert2Entity(vo);
        noticeService.add(entity);
        NoticeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "通知公告-修改")
    @PreAuthorize("hasPermission(null,'support:notice:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody NoticeVO vo) {
        Notice entity = convert2Entity(vo);
        noticeService.modify(entity);
        NoticeVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "通知公告-删除")
    @PreAuthorize("hasPermission(null,'support:notice:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        noticeService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "通知公告-分页")
    @PreAuthorize("hasPermission(null,'support:notice:query')")
    public ResponseEntity<Result> page(NoticeVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Notice> page = new Page<Notice>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<Notice> queryWrapper = QueryGenerator.generateQueryWrapper(Notice.class, queryVO, sortInfo);

        // 查询数据
        noticeService.page(page, queryWrapper);
        // 转换vo
        IPage<NoticeVO> pageVO = mapperFacade.map(page, IPage.class);
        List<NoticeVO> noticeVOList = convert2VO(page.getRecords());
        pageVO.setRecords(noticeVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "通知公告-列表")
    @PreAuthorize("hasPermission(null,'support:notice:query')")
    public ResponseEntity<Result> list(NoticeVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Notice> queryWrapper = QueryGenerator.generateQueryWrapper(Notice.class, queryVO, sortInfo);
        List<Notice> list = noticeService.list(queryWrapper);
        // 转换vo
        List<NoticeVO> noticeVOList = convert2VO(list);
        return ResultUtil.success(noticeVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "通知公告-详情")
    @PreAuthorize("hasPermission(null,'support:notice:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Notice entity = noticeService.query(id);
        NoticeVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "通知公告-复制新增")
    @PreAuthorize("hasPermission(null,'support:notice:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        noticeService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 浏览列表
     */
    @GetMapping("/viewList")
    @SystemLog(value = "通知公告-分页")
    @PreAuthorize("hasPermission(null,'support:notice:noticeViewList')")
    public ResponseEntity<Result> viewList(NoticeVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<Notice> page = new Page<Notice>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 强制设置只能查询正常状态的数据，避免普通用户查看超出范围的数据
        queryVO.setStatus(StatusEnum.NORMAL.name());

        // 构造查询条件
        QueryWrapper<Notice> queryWrapper = QueryGenerator.generateQueryWrapper(Notice.class, queryVO, sortInfo);

        // 查询数据
        noticeService.page(page, queryWrapper);
        // 转换vo
        IPage<NoticeVO> pageVO = mapperFacade.map(page, IPage.class);
        List<NoticeVO> noticeVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            NoticeVO vo = convert2VO(page.getRecords().get(i));
            noticeVOList.add(vo);
        }
        pageVO.setRecords(noticeVOList);
        ;
        return ResultUtil.success(pageVO);
    }

    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "通知公告-启用")
    @PreAuthorize("hasPermission(null,'support:notice:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        noticeService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "通知公告-停用")
    @PreAuthorize("hasPermission(null,'support:notice:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        noticeService.disable(id);
        return ResultUtil.success();
    }

    /**
     * 置顶
     */
    @PutMapping("/{id}/setTop")
    @SystemLog(value = "通知公告-置顶")
    @PreAuthorize("hasPermission(null,'support:notice:setTop')")
    public ResponseEntity<Result> setTop(@PathVariable("id") String id) {

        noticeService.setTop(id);
        return ResultUtil.success();
    }

    /**
     * 取消置顶
     */
    @PutMapping("/{id}/cancelTop")
    @SystemLog(value = "通知公告-取消置顶")
    @PreAuthorize("hasPermission(null,'support:notice:cancelTop')")
    public ResponseEntity<Result> cancelTop(@PathVariable("id") String id) {

        noticeService.cancelTop(id);
        return ResultUtil.success();
    }

    /**
     * 更新阅读次数
     */

    @PutMapping("/{id}/updateReadCount")
    @SystemLog(value = "通知公告-更新阅读次数")
    @AllowAuthenticated
    public ResponseEntity<Result> updateReadCount(@PathVariable("id") String id) {

        noticeService.updateReadCount(id);
        return ResultUtil.success();
    }


    /**
     * 获取组件数据
     */
 
    @GetMapping("/portlet")
    public ResponseEntity<Result> getPortletData(@RequestParam Map<String, String> map) {

        // 验证数量参数
        String countString = StringUtils.getDigits(map.get("count"));
        Integer count = Integer.parseInt(countString);
        if (count <= 0 || count > MAX_COUNT) {
            throw new CustomException(PortalExceptionEnum.PORTLET_PARAM_ERROR, "通知公告", "显示数量不正确");
        }

        List<Notice> list = noticeService.getPortletData(count);
        List<NoticeVO> noticeVOList = mapperFacade.mapAsList(list, NoticeVO.class);
        return ResultUtil.success(noticeVOList);
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private NoticeVO convert2VO(Notice entity) {
        NoticeVO vo = mapperFacade.map(entity, NoticeVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        vo.setImportantFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getImportantFlag()));
        vo.setTopFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getTopFlag()));
        vo.setCommentFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getCommentFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<NoticeVO> convert2VO(List<Notice> entityList) {
        List<NoticeVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            NoticeVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Notice convert2Entity(NoticeVO vo) {
        Notice entity = mapperFacade.map(vo, Notice.class);
        return entity;
    }

    // endregion
}