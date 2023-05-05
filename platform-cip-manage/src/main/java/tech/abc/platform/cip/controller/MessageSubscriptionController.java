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
import tech.abc.platform.cip.entity.MessageSubscription;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessageSubscriptionService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.cip.vo.MessageSubscriptionVO;
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
 * 消息订阅 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/messageSubscription")
@Slf4j
public class MessageSubscriptionController extends BaseController {
    @Autowired
    private MessageSubscriptionService messageSubscriptionService;
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
        MessageSubscription entity = messageSubscriptionService.init();
        MessageSubscriptionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "消息订阅-新增")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody MessageSubscriptionVO vo) {
        MessageSubscription entity = convert2Entity(vo);
        messageSubscriptionService.add(entity);
        MessageSubscriptionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "消息订阅-修改")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody MessageSubscriptionVO vo) {
        MessageSubscription entity = convert2Entity(vo);
        messageSubscriptionService.modify(entity);
        MessageSubscriptionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息订阅-删除")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        messageSubscriptionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "消息订阅-分页")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:query')")
    public ResponseEntity<Result> page(MessageSubscriptionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<MessageSubscription> page = new Page<MessageSubscription>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<MessageSubscription> queryWrapper = QueryGenerator.generateQueryWrapper(MessageSubscription.class, queryVO, sortInfo);

        // 查询数据
        messageSubscriptionService.page(page, queryWrapper);
        // 转换vo
        IPage<MessageSubscriptionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<MessageSubscriptionVO> messageSubscriptionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(messageSubscriptionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "消息订阅-列表")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:query')")
    public ResponseEntity<Result> list(MessageSubscriptionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<MessageSubscription> queryWrapper = QueryGenerator.generateQueryWrapper(MessageSubscription.class, queryVO, sortInfo);
        List<MessageSubscription> list = messageSubscriptionService.list(queryWrapper);
        // 转换vo
        List<MessageSubscriptionVO> messageSubscriptionVOList = convert2VO(list);
        return ResultUtil.success(messageSubscriptionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "消息订阅-详情")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        MessageSubscription entity = messageSubscriptionService.query(id);
        MessageSubscriptionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "消息订阅-复制新增")
    @PreAuthorize("hasPermission(null,'cip:messageSubscription:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        messageSubscriptionService.addByCopy(id);
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
    private MessageSubscriptionVO convert2VO(MessageSubscription entity) {
        MessageSubscriptionVO vo = mapperFacade.map(entity, MessageSubscriptionVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<MessageSubscriptionVO> convert2VO(List<MessageSubscription> entityList) {
        List<MessageSubscriptionVO> voList = new ArrayList<>(entityList.size());

        // 获取 应用 集合
        List<String> appList = entityList.stream().map(x -> x.getApp()).collect(Collectors.toList());
        Map<String, String> appNameMap = appService.getNameMap(appList);
        // 获取 消息主题 集合
        List<String> messageTopicList = entityList.stream().map(x -> x.getMessageTopic()).collect(Collectors.toList());
        Map<String, String> messageTopicNameMap = messageTopicService.getNameMap(messageTopicList);
        entityList.stream().forEach(x -> {
            MessageSubscriptionVO vo = convert2VO(x);
            // 设置 应用
            vo.setAppName(appNameMap.get(x.getApp()));
            // 设置 消息主题
            vo.setMessageTopicName(messageTopicNameMap.get(x.getMessageTopic()));
            voList.add(vo);
        });
        return voList;
    }


    private MessageSubscription convert2Entity(MessageSubscriptionVO vo) {
        MessageSubscription entity = mapperFacade.map(vo, MessageSubscription.class);
        return entity;
    }

    // endregion
}