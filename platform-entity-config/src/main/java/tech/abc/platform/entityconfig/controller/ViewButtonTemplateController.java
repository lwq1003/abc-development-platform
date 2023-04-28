package tech.abc.platform.entityconfig.controller;


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
import tech.abc.platform.entityconfig.entity.ViewButtonTemplate;
import tech.abc.platform.entityconfig.service.ViewButtonTemplateService;
import tech.abc.platform.entityconfig.vo.ViewButtonTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
* 视图按钮模板 前端控制器类
*
* @author wqliu
* @date 2023-04-16
*/
@RestController
@RequestMapping("/entityconfig/viewButtonTemplate")
@Slf4j
public class ViewButtonTemplateController extends BaseController {
    @Autowired
    private ViewButtonTemplateService viewButtonTemplateService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ViewButtonTemplate entity=viewButtonTemplateService.init();
        ViewButtonTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "视图按钮模板-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ViewButtonTemplateVO vo) {
        ViewButtonTemplate entity=convert2Entity(vo);
        viewButtonTemplateService.add(entity);
        ViewButtonTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "视图按钮模板-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ViewButtonTemplateVO vo) {
        ViewButtonTemplate entity=convert2Entity(vo);
        viewButtonTemplateService.modify(entity);
        ViewButtonTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "视图按钮模板-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        viewButtonTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "视图按钮模板-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:query')")
    public ResponseEntity<Result> page(ViewButtonTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<ViewButtonTemplate> page = new Page<ViewButtonTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<ViewButtonTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ViewButtonTemplate.class,queryVO,sortInfo);

        //查询数据
        viewButtonTemplateService.page(page, queryWrapper);
        //转换vo
        IPage<ViewButtonTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ViewButtonTemplateVO>  viewButtonTemplateVOList=convert2VO(page.getRecords());
        pageVO.setRecords(viewButtonTemplateVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "视图按钮模板-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:query')")
    public ResponseEntity<Result> list(ViewButtonTemplateVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<ViewButtonTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ViewButtonTemplate.class, queryVO,sortInfo);
        List<ViewButtonTemplate> list= viewButtonTemplateService.list(queryWrapper);
        //转换vo
        List<ViewButtonTemplateVO>  viewButtonTemplateVOList=convert2VO(list);
        return ResultUtil.success(viewButtonTemplateVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "视图按钮模板-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ViewButtonTemplate entity = viewButtonTemplateService.query(id);
        ViewButtonTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "视图按钮模板-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:viewButtonTemplate:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        viewButtonTemplateService.addByCopy(id);
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
    private ViewButtonTemplateVO convert2VO(ViewButtonTemplate entity){
        ViewButtonTemplateVO vo=mapperFacade.map(entity,ViewButtonTemplateVO.class);
        vo.setButtonTypeName(dictionaryUtil.getNameByCode("ViewButtonType", entity.getButtonType()));
        vo.setConfirmFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getConfirmFlag()));
        vo.setPermissionFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getPermissionFlag()));
        vo.setMoreFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getMoreFlag()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<ViewButtonTemplateVO> convert2VO(List<ViewButtonTemplate> entityList) {
        List<ViewButtonTemplateVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ViewButtonTemplateVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ViewButtonTemplate convert2Entity(ViewButtonTemplateVO vo){
        ViewButtonTemplate entity=mapperFacade.map(vo,ViewButtonTemplate.class);
        return entity;
    }

    //endregion
 }