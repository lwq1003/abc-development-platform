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
import tech.abc.platform.cip.entity.ApiServiceLog;
import tech.abc.platform.cip.service.ApiServiceLogService;
import tech.abc.platform.cip.vo.ApiServiceLogVO;
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
* API服务日志 前端控制器类
*
* @author wqliu
* @date 2023-05-03
*/
@RestController
@RequestMapping("/cip/apiServiceLog")
@Slf4j
public class ApiServiceLogController extends BaseController {
    @Autowired
    private ApiServiceLogService apiServiceLogService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ApiServiceLog entity=apiServiceLogService.init();
        ApiServiceLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "API服务日志-新增")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ApiServiceLogVO vo) {
        ApiServiceLog entity=convert2Entity(vo);
        apiServiceLogService.add(entity);
        ApiServiceLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "API服务日志-修改")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ApiServiceLogVO vo) {
        ApiServiceLog entity=convert2Entity(vo);
        apiServiceLogService.modify(entity);
        ApiServiceLogVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "API服务日志-删除")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        apiServiceLogService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "API服务日志-分页")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:query')")
    public ResponseEntity<Result> page(ApiServiceLogVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<ApiServiceLog> page = new Page<ApiServiceLog>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<ApiServiceLog> queryWrapper = QueryGenerator.generateQueryWrapper(ApiServiceLog.class,queryVO,sortInfo);

        //查询数据
        apiServiceLogService.page(page, queryWrapper);
        //转换vo
        IPage<ApiServiceLogVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ApiServiceLogVO>  apiServiceLogVOList=convert2VO(page.getRecords());
        pageVO.setRecords(apiServiceLogVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "API服务日志-列表")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:query')")
    public ResponseEntity<Result> list(ApiServiceLogVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<ApiServiceLog> queryWrapper = QueryGenerator.generateQueryWrapper(ApiServiceLog.class, queryVO,sortInfo);
        List<ApiServiceLog> list= apiServiceLogService.list(queryWrapper);
        //转换vo
        List<ApiServiceLogVO>  apiServiceLogVOList=convert2VO(list);
        return ResultUtil.success(apiServiceLogVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "API服务日志-详情")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ApiServiceLog entity = apiServiceLogService.query(id);
        ApiServiceLogVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "API服务日志-复制新增")
    @PreAuthorize("hasPermission(null,'cip:apiServiceLog:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        apiServiceLogService.addByCopy(id);
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
    private ApiServiceLogVO convert2VO(ApiServiceLog entity){
        ApiServiceLogVO vo=mapperFacade.map(entity,ApiServiceLogVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<ApiServiceLogVO> convert2VO(List<ApiServiceLog> entityList) {
        List<ApiServiceLogVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ApiServiceLogVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ApiServiceLog convert2Entity(ApiServiceLogVO vo){
        ApiServiceLog entity=mapperFacade.map(vo,ApiServiceLog.class);
        return entity;
    }

    //endregion
 }