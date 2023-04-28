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
import tech.abc.platform.system.entity.Param;
import tech.abc.platform.system.service.ParamService;
import tech.abc.platform.system.vo.ParamVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
* 系统参数 前端控制器类
*
* @author wqliu
* @date 2023-04-17
*/
@RestController
@RequestMapping("/system/param")
@Slf4j
public class ParamController extends BaseController {
    @Autowired
    private ParamService paramService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Param entity=paramService.init();
        ParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "系统参数-新增")
    @PreAuthorize("hasPermission(null,'system:param:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ParamVO vo) {
        Param entity=convert2Entity(vo);
        paramService.add(entity);
        ParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "系统参数-修改")
    @PreAuthorize("hasPermission(null,'system:param:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ParamVO vo) {
        Param entity=convert2Entity(vo);
        paramService.modify(entity);
        ParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "系统参数-删除")
    @PreAuthorize("hasPermission(null,'system:param:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        paramService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "系统参数-分页")
    @PreAuthorize("hasPermission(null,'system:param:query')")
    public ResponseEntity<Result> page(ParamVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Param> page = new Page<Param>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<Param> queryWrapper = QueryGenerator.generateQueryWrapper(Param.class,queryVO,sortInfo);

        //查询数据
        paramService.page(page, queryWrapper);
        //转换vo
        IPage<ParamVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ParamVO>  paramVOList=convert2VO(page.getRecords());
        pageVO.setRecords(paramVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "系统参数-列表")
    @PreAuthorize("hasPermission(null,'system:param:query')")
    public ResponseEntity<Result> list(ParamVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Param> queryWrapper = QueryGenerator.generateQueryWrapper(Param.class, queryVO,sortInfo);
        List<Param> list= paramService.list(queryWrapper);
        //转换vo
        List<ParamVO>  paramVOList=convert2VO(list);
        return ResultUtil.success(paramVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "系统参数-详情")
    @PreAuthorize("hasPermission(null,'system:param:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Param entity = paramService.query(id);
        ParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "系统参数-复制新增")
    @PreAuthorize("hasPermission(null,'system:param:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        paramService.addByCopy(id);
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
    private ParamVO convert2VO(Param entity){
        ParamVO vo=mapperFacade.map(entity,ParamVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<ParamVO> convert2VO(List<Param> entityList) {
        List<ParamVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ParamVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Param convert2Entity(ParamVO vo){
        Param entity=mapperFacade.map(vo,Param.class);
        return entity;
    }

    //endregion
 }