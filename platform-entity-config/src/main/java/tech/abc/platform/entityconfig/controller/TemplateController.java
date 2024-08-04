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
import tech.abc.platform.entityconfig.entity.Template;
import tech.abc.platform.entityconfig.service.TemplateService;
import tech.abc.platform.entityconfig.vo.TemplateVO;
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
* 模板 前端控制器类
*
* @author wqliu
* @date 2024-07-23
*/
@RestController
@RequestMapping("/entityconfig/template")
@Slf4j
public class TemplateController extends BaseController {
    @Autowired
    private TemplateService templateService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Template entity=templateService.init();
        TemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "模板-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:template:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody TemplateVO vo) {
        Template entity=convert2Entity(vo);
        templateService.add(entity);
        TemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "模板-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:template:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody TemplateVO vo) {
        Template entity=convert2Entity(vo);
        templateService.modify(entity);
        TemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "模板-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:template:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        templateService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "模板-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:template:query')")
    public ResponseEntity<Result> page(TemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Template> page = new Page<Template>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<Template> queryWrapper = QueryGenerator.generateQueryWrapper(Template.class,queryVO,sortInfo);

        //查询数据
        templateService.page(page, queryWrapper);
        //转换vo
        IPage<TemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<TemplateVO>  templateVOList=convert2VO(page.getRecords());
        pageVO.setRecords(templateVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "模板-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:template:query')")
    public ResponseEntity<Result> list(TemplateVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Template> queryWrapper = QueryGenerator.generateQueryWrapper(Template.class, queryVO,sortInfo);
        List<Template> list= templateService.list(queryWrapper);
        //转换vo
        List<TemplateVO>  templateVOList=convert2VO(list);
        return ResultUtil.success(templateVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "模板-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:template:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Template entity = templateService.query(id);
        TemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "模板-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:template:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        templateService.addByCopy(id);
        return ResultUtil.success();
    }


    /**
    * 复制新增单条数据，返回复制后的对象
    */
    @PostMapping("/{id}/addSingleByCopy")
    @SystemLog(value = "模板-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:template:addByCopy')")
    public ResponseEntity<Result> addSingleByCopy(@PathVariable("id") String id) {
        Template entity = templateService.addSingleByCopy(id);
        TemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
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
    protected TemplateVO convert2VO(Template entity){
        TemplateVO vo=mapperFacade.map(entity,TemplateVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<TemplateVO> convert2VO(List<Template> entityList) {
        List<TemplateVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            TemplateVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Template convert2Entity(TemplateVO vo){
        Template entity=mapperFacade.map(vo,Template.class);
        return entity;
    }

    //endregion
 }