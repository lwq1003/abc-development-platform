package tech.abc.edoc.edoc.controller;


import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.edoc.edoc.entity.PopDocument;
import tech.abc.edoc.edoc.vo.MixPopDocumentVO;
import tech.abc.edoc.edoc.vo.PopDocumentVO;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.edoc.edoc.entity.Folder;
import tech.abc.edoc.edoc.service.FolderService;
import tech.abc.edoc.edoc.vo.FolderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.TreeVO;
import tech.abc.platform.system.service.UserService;

/**
* 文件夹 前端控制器类
*
* @author wqliu
* @date 2024-02-01
*/
@RestController
@RequestMapping("/edoc/folder")
@Slf4j
public class FolderController extends BaseController {
    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;
    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Folder entity=folderService.init();
        FolderVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "文件夹-新增")
    @PreAuthorize("hasPermission(null,'edoc:folder:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody FolderVO vo) {
        Folder entity=convert2Entity(vo);
        folderService.add(entity);
        FolderVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "文件夹-修改")
    @PreAuthorize("hasPermission(null,'edoc:folder:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody FolderVO vo) {
        Folder entity=convert2Entity(vo);
        folderService.modify(entity);
        FolderVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "文件夹-删除")
    @PreAuthorize("hasPermission(null,'edoc:folder:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        folderService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "文件夹-分页")
    @PreAuthorize("hasPermission(null,'edoc:folder:query')")
    public ResponseEntity<Result> page(FolderVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Folder> page = new Page<Folder>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setParentId(null);
        }

        //构造查询条件
        QueryWrapper<Folder> queryWrapper = QueryGenerator.generateQueryWrapper(Folder.class,queryVO,sortInfo);

        //查询数据
        folderService.page(page, queryWrapper);
        //转换vo
        IPage<FolderVO> pageVO = mapperFacade.map(page, IPage.class);
        List<FolderVO>  folderVOList=convert2VO(page.getRecords());
        pageVO.setRecords(folderVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "文件夹-列表")
    @PreAuthorize("hasPermission(null,'edoc:folder:query')")
    public ResponseEntity<Result> list(FolderVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Folder> queryWrapper = QueryGenerator.generateQueryWrapper(Folder.class, queryVO,sortInfo);
        List<Folder> list= folderService.list(queryWrapper);
        //转换vo
        List<FolderVO>  folderVOList=convert2VO(list);
        return ResultUtil.success(folderVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "文件夹-详情")
    @PreAuthorize("hasPermission(null,'edoc:folder:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Folder entity = folderService.query(id);
        FolderVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "文件夹-复制新增")
    @PreAuthorize("hasPermission(null,'edoc:folder:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        folderService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 获取子对象
     */
    @GetMapping("/{id}/getChildren")
    @SystemLog(value = "文件夹-获取子对象")
    @PreAuthorize("hasPermission(null,'edoc:folder:query')")
    public ResponseEntity<Result> getChildren(@PathVariable String id,String name,Boolean ignoreParent,SortInfo sortInfo) {
        List<PopDocument> list = folderService.getChildren(id,name ,ignoreParent, sortInfo);
        //转换为视图对象
        List<PopDocumentVO> voList=mapperFacade.mapAsList(list,PopDocumentVO.class);
        //转换字典及对象
        voList.stream().forEach(x->{
            x.setStatusName(dictionaryUtil.getNameByCode("DocumentStatus", x.getStatus()));
            if(StringUtils.isNotBlank(x.getLockId())) {
                x.setLockUser(userService.getNameById(x.getLockId()));
            }
        });
        return ResultUtil.success(voList);
    }



    /**
     * 更名
     */
    @ApiOperation(value = "更名")
    @PutMapping("/{id}/rename")
    @SystemLog(value = "文件夹-更名")
    @PreAuthorize("hasPermission(null,'edoc:folder:rename')")
    public ResponseEntity<Result> rename(@PathVariable String id,String name)  {
        folderService.rename(id,name);
        return ResultUtil.success();
    }

    /**
     * 复制
     */
    @ApiOperation(value = "复制")
    @PostMapping("/copy")
    @SystemLog(value = "文件夹-复制")
    @PreAuthorize("hasPermission(null,'edoc:folder:copy')")
    public ResponseEntity<Result> copy(String srcId,String targetId)  {
        folderService.copy(srcId,targetId);
        return ResultUtil.success();
    }
    /**
     * 移动
     */
    @ApiOperation(value = "移动")
    @PutMapping("/move")
    @SystemLog(value = "文件夹-移动")
    @PreAuthorize("hasPermission(null,'edoc:folder:move')")
    public ResponseEntity<Result> move(String srcId,String targetId,String retainPermission)  {
        folderService.move(srcId,targetId,retainPermission);
        return ResultUtil.success();
    }


    /**
     * 混合删除
     */
    @ApiOperation(value = "混合删除")
    @PostMapping("/mixRemove")
    @SystemLog(value = "文件夹-混合删除",logRequestParam = false)
    @AllowAll
    public ResponseEntity<Result> mixRemove(@RequestBody List<PopDocument> list) {

        folderService.mixRemove(list);
        return ResultUtil.success();
    }

    /**
     * 混合复制
     */
    @ApiOperation(value = "混合复制")
    @PostMapping("/mixCopy")
    @SystemLog(value = "文件夹-混合复制")
    @AllowAll
    public ResponseEntity<Result> mixCopy(@RequestBody MixPopDocumentVO vo) {

        List<PopDocument> list=mapperFacade.mapAsList(vo.getData(), PopDocument.class);
        folderService.mixCopy(list,vo.getTargetFolderId());
        return ResultUtil.success();
    }

    /**
     * 混合移动
     */
    @ApiOperation(value = "混合移动")
    @PutMapping("/mixMove")
    @SystemLog(value = "文件夹-混合移动")
    @AllowAll
    public ResponseEntity<Result> mixMove(@RequestBody MixPopDocumentVO vo) {

        List<PopDocument> list=mapperFacade.mapAsList(vo.getData(),PopDocument.class);
        folderService.mixMove(list,vo.getTargetFolderId(),vo.getRetainPermission());
        return ResultUtil.success();
    }


    //endregion

    //region 树操作
    /**
    * 获取树数据
    *
    * @return
    */
    @GetMapping("/tree")
    @PreAuthorize("hasPermission(null,'edoc:folder:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        //附加按照名称排序
        queryWrapper.lambda().orderByAsc(Folder::getName);
        List<Folder> list = folderService.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
   }

    /**
    * 转换为树视图对象
    */
    private TreeVO convert2TreeVO(Folder entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.getParentId());
        tree.setLabel(entity.getName());
        return tree;
    }

    //endregion
    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected FolderVO convert2VO(Folder entity){
        FolderVO vo=mapperFacade.map(entity,FolderVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<FolderVO> convert2VO(List<Folder> entityList) {
        List<FolderVO> voList = new ArrayList<>(entityList.size());

        // 获取 上级 集合
        List<String> folderList = entityList.stream().map(x -> x.getParentId()).collect(Collectors.toList());
        Map<String,String> folderNameMap = folderService.getNameMap(folderList);

        entityList.stream().forEach(x -> {
            FolderVO vo = convert2VO(x);
            // 设置 上级
            vo.setParentIdName(folderNameMap.get(x.getParentId()));
            voList.add(vo);
        });
        return voList;
    }


    private Folder convert2Entity(FolderVO vo){
        Folder entity=mapperFacade.map(vo,Folder.class);
        return entity;
    }

    //endregion
 }