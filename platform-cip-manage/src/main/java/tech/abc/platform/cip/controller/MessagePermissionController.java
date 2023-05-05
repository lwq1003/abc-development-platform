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
import tech.abc.platform.cip.entity.MessagePermission;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessagePermissionService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.cip.vo.MessagePermissionVO;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息权限 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/messagePermission")
@Slf4j
public class MessagePermissionController extends BaseController {
    @Autowired
    private MessagePermissionService messagePermissionService;
    @Autowired
    private AppService appService;
    @Autowired
    private MessageTopicService messageTopicService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        MessagePermission entity = messagePermissionService.init();
        MessagePermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "消息权限-新增")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody MessagePermissionVO vo) {
        MessagePermission entity = convert2Entity(vo);
        messagePermissionService.add(entity);
        MessagePermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "消息权限-修改")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody MessagePermissionVO vo) {
        MessagePermission entity = convert2Entity(vo);
        messagePermissionService.modify(entity);
        MessagePermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息权限-删除")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        messagePermissionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "消息权限-分页")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:query')")
    public ResponseEntity<Result> page(MessagePermissionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<MessagePermission> page = new Page<MessagePermission>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<MessagePermission> queryWrapper = QueryGenerator.generateQueryWrapper(MessagePermission.class, queryVO, sortInfo);

        // 查询数据
        messagePermissionService.page(page, queryWrapper);
        // 转换vo
        IPage<MessagePermissionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<MessagePermissionVO> messagePermissionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(messagePermissionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "消息权限-列表")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:query')")
    public ResponseEntity<Result> list(MessagePermissionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<MessagePermission> queryWrapper = QueryGenerator.generateQueryWrapper(MessagePermission.class, queryVO, sortInfo);
        List<MessagePermission> list = messagePermissionService.list(queryWrapper);
        // 转换vo
        List<MessagePermissionVO> messagePermissionVOList = convert2VO(list);
        return ResultUtil.success(messagePermissionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "消息权限-详情")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        MessagePermission entity = messagePermissionService.query(id);
        MessagePermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "消息权限-复制新增")
    @PreAuthorize("hasPermission(null,'cip:messagePermission:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        messagePermissionService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private MessagePermissionVO convert2VO(MessagePermission entity) {
        MessagePermissionVO vo = mapperFacade.map(entity, MessagePermissionVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<MessagePermissionVO> convert2VO(List<MessagePermission> entityList) {
        List<MessagePermissionVO> voList = new ArrayList<>(entityList.size());

        // 获取 应用 集合
        List<String> appList = entityList.stream().map(x -> x.getApp()).collect(Collectors.toList());
        Map<String, String> appNameMap = appService.getNameMap(appList);
        // 获取 消息主题 集合
        List<String> messageTopicList = entityList.stream().map(x -> x.getMessageTopic()).collect(Collectors.toList());
        Map<String, String> messageTopicNameMap = messageTopicService.getNameMap(messageTopicList);
        entityList.stream().forEach(x -> {
            MessagePermissionVO vo = convert2VO(x);
            // 设置 应用
            vo.setAppName(appNameMap.get(x.getApp()));
            // 设置 消息主题
            vo.setMessageTopicName(messageTopicNameMap.get(x.getMessageTopic()));
            voList.add(vo);
        });
        return voList;
    }


    private MessagePermission convert2Entity(MessagePermissionVO vo) {
        MessagePermission entity = mapperFacade.map(vo, MessagePermission.class);
        return entity;
    }

    // endregion
}