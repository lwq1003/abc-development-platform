package tech.abc.platform.cip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.cip.entity.ActiveMessage;
import tech.abc.platform.cip.service.ActiveMessageService;
import tech.abc.platform.cip.vo.ActiveMessageVO;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 活跃消息 前端控制器类
 *
 * @author wqliu
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/cip/activeMessage")
@Slf4j
public class ActiveMessageController extends BaseController {
    @Autowired
    private ActiveMessageService activeMessageService;


    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ActiveMessage entity = activeMessageService.init();
        ActiveMessageVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "活跃消息-新增")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ActiveMessageVO vo) {
        ActiveMessage entity = convert2Entity(vo);
        activeMessageService.add(entity);
        ActiveMessageVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "活跃消息-修改")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ActiveMessageVO vo) {
        ActiveMessage entity = convert2Entity(vo);
        activeMessageService.modify(entity);
        ActiveMessageVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "活跃消息-删除")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        activeMessageService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "活跃消息-分页")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:query')")
    public ResponseEntity<Result> page(ActiveMessageVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ActiveMessage> page = new Page<ActiveMessage>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<ActiveMessage> queryWrapper = QueryGenerator.generateQueryWrapper(ActiveMessage.class, queryVO, sortInfo);

        // 查询数据
        activeMessageService.page(page, queryWrapper);
        // 转换vo
        IPage<ActiveMessageVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ActiveMessageVO> activeMessageVOList = convert2VO(page.getRecords());
        pageVO.setRecords(activeMessageVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "活跃消息-列表")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:query')")
    public ResponseEntity<Result> list(ActiveMessageVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ActiveMessage> queryWrapper = QueryGenerator.generateQueryWrapper(ActiveMessage.class, queryVO, sortInfo);
        List<ActiveMessage> list = activeMessageService.list(queryWrapper);
        // 转换vo
        List<ActiveMessageVO> activeMessageVOList = convert2VO(list);
        return ResultUtil.success(activeMessageVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "活跃消息-详情")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ActiveMessage entity = activeMessageService.query(id);
        ActiveMessageVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "活跃消息-复制新增")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        activeMessageService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 消息重发，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PutMapping("/resend/{id}")
    @SystemLog(value = "活跃消息-重发")
    @PreAuthorize("hasPermission(null,'cip:activeMessage:resend')")
    public ResponseEntity<Result> resend(@PathVariable("id") String id) {
        activeMessageService.resend(id);
        return ResultUtil.success();
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    protected ActiveMessageVO convert2VO(ActiveMessage entity) {
        ActiveMessageVO vo = mapperFacade.map(entity, ActiveMessageVO.class);
        vo.setResponseResultName(dictionaryUtil.getNameByCode("MessageResponseResult", entity.getResponseResult()));
        vo.setStatusName(dictionaryUtil.getNameByCode("MessageStatus", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    protected List<ActiveMessageVO> convert2VO(List<ActiveMessage> entityList) {
        List<ActiveMessageVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ActiveMessageVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ActiveMessage convert2Entity(ActiveMessageVO vo) {
        ActiveMessage entity = mapperFacade.map(vo, ActiveMessage.class);
        return entity;
    }

    // endregion
}