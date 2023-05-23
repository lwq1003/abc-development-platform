package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.SerialNo;
import tech.abc.platform.support.service.SerialNoService;
import tech.abc.platform.support.vo.SerialNoVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据流水号 前端控制器
 * @author wqliu
 * *
 */
@RestController
@RequestMapping("/support/serialNo")
@Slf4j
public class SerialNoController extends BaseController {
 @Autowired
 private SerialNoService serialNoService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        SerialNo entity=serialNoService.init();
        SerialNoVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "单据流水号-新增")
    @PreAuthorize("hasPermission(null,'support:serialNo:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody SerialNoVO vo) {
        SerialNo entity=convert2Entity(vo);
        serialNoService.add(entity);
        SerialNoVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "单据流水号-修改")
    @PreAuthorize("hasPermission(null,'support:serialNo:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody SerialNoVO vo)  {
        SerialNo entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        serialNoService.modify(entity);
        SerialNoVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "单据流水号-删除")
    @PreAuthorize("hasPermission(null,'support:serialNo:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        serialNoService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "单据流水号-分页")
    @PreAuthorize("hasPermission(null,'support:serialNo:query')")
    public ResponseEntity<Result> page(SerialNoVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<SerialNo> page = new Page<SerialNo>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<SerialNo> queryWrapper = QueryGenerator.generateQueryWrapper(SerialNo.class,queryVO,sortInfo);

        //查询数据
        serialNoService.page(page, queryWrapper);
        //转换vo
        IPage<SerialNoVO> pageVO = mapperFacade.map(page, IPage.class);
        List<SerialNoVO>  serialNoVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            SerialNoVO vo = convert2VO(page.getRecords().get(i));
            serialNoVOList.add(vo);
        }
        pageVO.setRecords(serialNoVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "单据流水号-列表")
    @PreAuthorize("hasPermission(null,'support:serialNo:query')")
    public ResponseEntity<Result> list(SerialNoVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<SerialNo> queryWrapper = QueryGenerator.generateQueryWrapper(SerialNo.class, queryVO,sortInfo);
        List<SerialNo> list= serialNoService.list(queryWrapper);
        //转换vo
        List<SerialNoVO>  serialNoVOList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SerialNoVO vo = convert2VO(list.get(i));
            serialNoVOList.add(vo);
        }
        return ResultUtil.success(serialNoVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "单据流水号-详情")
    @PreAuthorize("hasPermission(null,'support:serialNo:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        SerialNo entity = serialNoService.query(id);
        SerialNoVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    private SerialNoVO convert2VO(SerialNo entity){
        SerialNoVO vo=mapperFacade.map(entity,SerialNoVO.class);
        vo.setResetStrategyName(dictionaryUtil.getNameByCode("SerialNoResetStrategy", entity.getResetStrategy()));

        return vo;
    }

    private SerialNo convert2Entity(SerialNoVO vo){

        SerialNo entity=mapperFacade.map(vo,SerialNo.class);
        return entity;
    }

}
