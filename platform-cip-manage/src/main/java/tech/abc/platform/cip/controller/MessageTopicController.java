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
import tech.abc.platform.cip.entity.MessageTopic;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.cip.vo.MessageTopicVO;
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
 * 消息主题 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/messageTopic")
@Slf4j
public class MessageTopicController extends BaseController {
    @Autowired
    private MessageTopicService messageTopicService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        MessageTopic entity = messageTopicService.init();
        MessageTopicVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "消息主题-新增")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody MessageTopicVO vo) {
        MessageTopic entity = convert2Entity(vo);
        messageTopicService.add(entity);
        MessageTopicVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "消息主题-修改")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody MessageTopicVO vo) {
        MessageTopic entity = convert2Entity(vo);
        messageTopicService.modify(entity);
        MessageTopicVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息主题-删除")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        messageTopicService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "消息主题-分页")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:query')")
    public ResponseEntity<Result> page(MessageTopicVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<MessageTopic> page = new Page<MessageTopic>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<MessageTopic> queryWrapper = QueryGenerator.generateQueryWrapper(MessageTopic.class, queryVO, sortInfo);

        // 查询数据
        messageTopicService.page(page, queryWrapper);
        // 转换vo
        IPage<MessageTopicVO> pageVO = mapperFacade.map(page, IPage.class);
        List<MessageTopicVO> messageTopicVOList = convert2VO(page.getRecords());
        pageVO.setRecords(messageTopicVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "消息主题-列表")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:query')")
    public ResponseEntity<Result> list(MessageTopicVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<MessageTopic> queryWrapper = QueryGenerator.generateQueryWrapper(MessageTopic.class, queryVO, sortInfo);
        List<MessageTopic> list = messageTopicService.list(queryWrapper);
        // 转换vo
        List<MessageTopicVO> messageTopicVOList = convert2VO(list);
        return ResultUtil.success(messageTopicVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "消息主题-详情")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        MessageTopic entity = messageTopicService.query(id);
        MessageTopicVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "消息主题-复制新增")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        messageTopicService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @PutMapping("/{id}/enable")
    @SystemLog(value = "消息主题-启用")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        messageTopicService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "消息主题-停用")
    @PreAuthorize("hasPermission(null,'cip:messageTopic:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        messageTopicService.disable(id);
        return ResultUtil.success();
    }

    /**
     * 消息服务权限查询
     */
    @GetMapping("/queryMessagePermission")
    @PreAuthorize("hasPermission(null,'cip:app:queryMessagePermission')")
    public ResponseEntity<Result> queryMessagePermission(MessageTopicVO queryVO, SortInfo sortInfo) {
        // 暂存传入的参数
        String appId = queryVO.getAppId();
        String hasPermission = queryVO.getHasPermission();
        // 移除hasPermission参数，否则会构建sql语句，而该字段并不存在
        queryVO.setHasPermission(null);
        // 构造查询条件
        QueryWrapper<MessageTopic> queryWrapper = QueryGenerator.generateQueryWrapper(MessageTopic.class
                , queryVO, sortInfo);
        List<MessageTopic> list = messageTopicService.queryMessageTopicPermission(queryWrapper, appId, hasPermission);
        // 转换vo
        List<MessageTopicVO> voList = convert2VO(list);
        return ResultUtil.success(voList);
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private MessageTopicVO convert2VO(MessageTopic entity) {
        MessageTopicVO vo = mapperFacade.map(entity, MessageTopicVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("ApiMessageTopicCategory", entity.getCategory()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<MessageTopicVO> convert2VO(List<MessageTopic> entityList) {
        List<MessageTopicVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            MessageTopicVO vo = convert2VO(x);
            vo.setHasPermissionName(dictionaryUtil.getNameByCode("YesOrNo", vo.getHasPermission()));
            voList.add(vo);
        });
        return voList;
    }


    private MessageTopic convert2Entity(MessageTopicVO vo) {
        MessageTopic entity = mapperFacade.map(vo, MessageTopic.class);
        return entity;
    }

    // endregion
}