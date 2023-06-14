package tech.abc.platform.support.controller;


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
import tech.abc.platform.support.entity.ContentTemplate;
import tech.abc.platform.support.service.ContentTemplateService;
import tech.abc.platform.support.vo.ContentTemplateVO;
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
* 内容模板 前端控制器类
*
* @author wqliu
* @date 2023-05-31
*/
@RestController
@RequestMapping("/support/contentTemplate")
@Slf4j
public class ContentTemplateController extends BaseController {
    @Autowired
    private ContentTemplateService contentTemplateService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ContentTemplate entity=contentTemplateService.init();
        ContentTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "内容模板-新增")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ContentTemplateVO vo) {
        ContentTemplate entity=convert2Entity(vo);
        contentTemplateService.add(entity);
        ContentTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "内容模板-修改")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ContentTemplateVO vo) {
        ContentTemplate entity=convert2Entity(vo);
        contentTemplateService.modify(entity);
        ContentTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "内容模板-删除")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        contentTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "内容模板-分页")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:query')")
    public ResponseEntity<Result> page(ContentTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<ContentTemplate> page = new Page<ContentTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<ContentTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplate.class,queryVO,sortInfo);

        //查询数据
        contentTemplateService.page(page, queryWrapper);
        //转换vo
        IPage<ContentTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ContentTemplateVO>  contentTemplateVOList=convert2VO(page.getRecords());
        pageVO.setRecords(contentTemplateVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "内容模板-列表")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:query')")
    public ResponseEntity<Result> list(ContentTemplateVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<ContentTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplate.class, queryVO,sortInfo);
        List<ContentTemplate> list= contentTemplateService.list(queryWrapper);
        //转换vo
        List<ContentTemplateVO>  contentTemplateVOList=convert2VO(list);
        return ResultUtil.success(contentTemplateVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "内容模板-详情")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ContentTemplate entity = contentTemplateService.query(id);
        ContentTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "内容模板-复制新增")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        contentTemplateService.addByCopy(id);
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
    private ContentTemplateVO convert2VO(ContentTemplate entity){
        ContentTemplateVO vo=mapperFacade.map(entity,ContentTemplateVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("ContentTemplateCategory", entity.getCategory()));
        vo.setTypeName(dictionaryUtil.getNameByCode("ContentTemplateType", entity.getType()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<ContentTemplateVO> convert2VO(List<ContentTemplate> entityList) {
        List<ContentTemplateVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ContentTemplateVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private ContentTemplate convert2Entity(ContentTemplateVO vo){
        ContentTemplate entity=mapperFacade.map(vo,ContentTemplate.class);
        return entity;
    }

    //endregion
 }