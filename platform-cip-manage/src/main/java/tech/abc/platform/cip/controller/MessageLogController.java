package tech.abc.platform.cip.controller;


import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.cip.vo.MessageLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 消息日志 前端控制器类
*
* @author wqliu
* @date 2023-05-03
*/
@RestController
@RequestMapping("/cip/messageLog")
@Slf4j
public class MessageLogController extends BaseController {
    @Autowired
    private MessageLogService messageLogService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        MessageLog entity=messageLogService.init();
        MessageLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "消息日志-新增")
    @PreAuthorize("hasPermission(null,'cip:messageLog:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody MessageLogVO vo) {
        MessageLog entity=convert2Entity(vo);
        messageLogService.add(entity);
        MessageLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "消息日志-修改")
    @PreAuthorize("hasPermission(null,'cip:messageLog:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody MessageLogVO vo) {
        MessageLog entity=convert2Entity(vo);
        messageLogService.modify(entity);
        MessageLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息日志-删除")
    @PreAuthorize("hasPermission(null,'cip:messageLog:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        messageLogService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "消息日志-分页")
    @PreAuthorize("hasPermission(null,'cip:messageLog:query')")
    public ResponseEntity<Result> page(MessageLogVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<MessageLog> page = new Page<MessageLog>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<MessageLog> queryWrapper = QueryGenerator.generateQueryWrapper(MessageLog.class,queryVO,sortInfo);

        //查询数据
        messageLogService.page(page, queryWrapper);
        //转换vo
        IPage<MessageLogVO> pageVO = mapperFacade.map(page, IPage.class);
        List<MessageLogVO>  messageLogVOList=convert2VO(page.getRecords());
        pageVO.setRecords(messageLogVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "消息日志-列表")
    @PreAuthorize("hasPermission(null,'cip:messageLog:query')")
    public ResponseEntity<Result> list(MessageLogVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<MessageLog> queryWrapper = QueryGenerator.generateQueryWrapper(MessageLog.class, queryVO,sortInfo);
        List<MessageLog> list= messageLogService.list(queryWrapper);
        //转换vo
        List<MessageLogVO>  messageLogVOList=convert2VO(list);
        return ResultUtil.success(messageLogVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "消息日志-详情")
    @PreAuthorize("hasPermission(null,'cip:messageLog:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        MessageLog entity = messageLogService.query(id);
        MessageLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "消息日志-复制新增")
    @PreAuthorize("hasPermission(null,'cip:messageLog:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        messageLogService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private MessageLogVO convert2VO(MessageLog entity){
        MessageLogVO vo=mapperFacade.map(entity,MessageLogVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("MessageStatus", entity.getStatus()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<MessageLogVO> convert2VO(List<MessageLog> entityList) {
        List<MessageLogVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            MessageLogVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private MessageLog convert2Entity(MessageLogVO vo){
        MessageLog entity=mapperFacade.map(vo,MessageLog.class);
        return entity;
    }

    //endregion
 }