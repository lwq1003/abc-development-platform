package tech.abc.platform.system.controller;


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
import tech.abc.platform.system.entity.Log;
import tech.abc.platform.system.service.LogService;
import tech.abc.platform.system.vo.LogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
* 日志 前端控制器类
*
* @author wqliu
* @date 2023-04-26
*/
@RestController
@RequestMapping("/system/log")
@Slf4j
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Log entity=logService.init();
        LogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "日志-新增")
    @PreAuthorize("hasPermission(null,'system:log:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody LogVO vo) {
        Log entity=convert2Entity(vo);
        logService.add(entity);
        LogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "日志-修改")
    @PreAuthorize("hasPermission(null,'system:log:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody LogVO vo) {
        Log entity=convert2Entity(vo);
        logService.modify(entity);
        LogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "日志-删除")
    @PreAuthorize("hasPermission(null,'system:log:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        logService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "日志-分页")
    @PreAuthorize("hasPermission(null,'system:log:query')")
    public ResponseEntity<Result> page(LogVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Log> page = new Page<Log>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<Log> queryWrapper = QueryGenerator.generateQueryWrapper(Log.class,queryVO,sortInfo);

        //查询数据
        logService.page(page, queryWrapper);
        //转换vo
        IPage<LogVO> pageVO = mapperFacade.map(page, IPage.class);
        List<LogVO>  logVOList=convert2VO(page.getRecords());
        pageVO.setRecords(logVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "日志-列表")
    @PreAuthorize("hasPermission(null,'system:log:query')")
    public ResponseEntity<Result> list(LogVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Log> queryWrapper = QueryGenerator.generateQueryWrapper(Log.class, queryVO,sortInfo);
        List<Log> list= logService.list(queryWrapper);
        //转换vo
        List<LogVO>  logVOList=convert2VO(list);
        return ResultUtil.success(logVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "日志-详情")
    @PreAuthorize("hasPermission(null,'system:log:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Log entity = logService.query(id);
        LogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "日志-复制新增")
    @PreAuthorize("hasPermission(null,'system:log:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        logService.addByCopy(id);
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
    * @return {@link LogVO} 视图对象
    */
    private LogVO convert2VO(Log entity){
        LogVO vo=mapperFacade.map(entity,LogVO.class);
        vo.setLogTypeName(dictionaryUtil.getNameByCode("LogType", entity.getLogType()));
        vo.setResponseCodeName(dictionaryUtil.getNameByCode("ExecuteResult", entity.getResponseCode()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link LogVO}> 视图对象列表
    */
    private List<LogVO> convert2VO(List<Log> entityList) {
        List<LogVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            LogVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Log convert2Entity(LogVO vo){
        Log entity=mapperFacade.map(vo,Log.class);
        return entity;
    }

    //endregion
 }