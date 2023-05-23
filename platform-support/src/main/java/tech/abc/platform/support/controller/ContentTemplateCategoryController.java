package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.constant.TableFieldConstant;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.common.vo.TreeVO;
import tech.abc.platform.support.entity.ContentTemplateCategory;
import tech.abc.platform.support.service.ContentTemplateCategoryService;
import tech.abc.platform.support.vo.ContentTemplateCategoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内容模板分类 前端控制器
 *
 * @author wqliu
 */
@RestController
@RequestMapping("/support/contentTemplateCategory")
@Slf4j
public class ContentTemplateCategoryController extends BaseController {
    @Autowired
    private ContentTemplateCategoryService contentTemplateCategoryService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ContentTemplateCategory entity = contentTemplateCategoryService.init();
        ContentTemplateCategoryVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "内容模板分类-新增")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ContentTemplateCategoryVO vo) {
        ContentTemplateCategory entity = convert2Entity(vo);
        contentTemplateCategoryService.add(entity);
        ContentTemplateCategoryVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "内容模板分类-修改")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ContentTemplateCategoryVO vo) {
        ContentTemplateCategory entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        contentTemplateCategoryService.modify(entity);
        ContentTemplateCategoryVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "内容模板分类-删除")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        contentTemplateCategoryService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "内容模板分类-分页")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:query')")
    public ResponseEntity<Result> page(ContentTemplateCategoryVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {


        // 构造分页对象
        IPage<ContentTemplateCategory> page = new Page<ContentTemplateCategory>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setParentId(null);
        }
        // 构造查询条件
        QueryWrapper<ContentTemplateCategory> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplateCategory.class, queryVO, sortInfo);


        // 查询数据
        contentTemplateCategoryService.page(page, queryWrapper);
        // 转换vo
        IPage<ContentTemplateCategoryVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ContentTemplateCategoryVO> contentTemplateCategoryVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ContentTemplateCategoryVO vo = convert2VO(page.getRecords().get(i));
            contentTemplateCategoryVOList.add(vo);
        }
        pageVO.setRecords(contentTemplateCategoryVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "内容模板分类-列表")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:query')")
    public ResponseEntity<Result> list(ContentTemplateCategoryVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ContentTemplateCategory> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplateCategory.class, queryVO, sortInfo);
        List<ContentTemplateCategory> list = contentTemplateCategoryService.list(queryWrapper);
        // 转换vo
        List<ContentTemplateCategoryVO> contentTemplateCategoryVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContentTemplateCategoryVO vo = convert2VO(list.get(i));
            contentTemplateCategoryVOList.add(vo);
        }
        return ResultUtil.success(contentTemplateCategoryVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "内容模板分类-详情")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ContentTemplateCategory entity = contentTemplateCategoryService.query(id);
        ContentTemplateCategoryVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 获取树数据
     *
     * @return
     */
    @ApiOperation(value = "获取树状数据")
    @GetMapping("/tree")
    @PreAuthorize("hasPermission(null,'support:contentTemplateCategory:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<ContentTemplateCategory> queryWrapper = new QueryWrapper<>();

        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<ContentTemplateCategory> list = contentTemplateCategoryService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);

    }


    private ContentTemplateCategoryVO convert2VO(ContentTemplateCategory entity) {
        ContentTemplateCategoryVO vo = mapperFacade.map(entity, ContentTemplateCategoryVO.class);
        return vo;
    }

    private ContentTemplateCategory convert2Entity(ContentTemplateCategoryVO vo) {

        ContentTemplateCategory entity = mapperFacade.map(vo, ContentTemplateCategory.class);
        return entity;
    }

    /**
     * 转换为树视图对象
     */
    private TreeVO convert2TreeVO(ContentTemplateCategory entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getParentId());
        tree.setLabel(entity.getName());
        return tree;
    }

}
