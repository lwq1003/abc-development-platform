package tech.abc.edoc.edoc.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import tech.abc.edoc.edoc.entity.FolderPermission;
import tech.abc.edoc.edoc.service.FolderPermissionService;
import tech.abc.edoc.edoc.vo.FolderPermissionVO;
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
* 文件夹权限 前端控制器类
*
* @author wqliu
* @date 2024-02-04
*/
@RestController
@RequestMapping("/edoc/folderPermission")
@Slf4j
public class FolderPermissionController extends BaseController {
    @Autowired
    private FolderPermissionService folderPermissionService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        FolderPermission entity=folderPermissionService.init();
        FolderPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "文件夹权限-新增")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody FolderPermissionVO vo) {
        FolderPermission entity=convert2Entity(vo);
        folderPermissionService.add(entity);
        FolderPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "文件夹权限-修改")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody FolderPermissionVO vo) {
        FolderPermission entity=convert2Entity(vo);
        folderPermissionService.modify(entity);
        FolderPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "文件夹权限-删除")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        folderPermissionService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "文件夹权限-分页")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:query')")
    public ResponseEntity<Result> page(FolderPermissionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<FolderPermission> page = new Page<FolderPermission>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<FolderPermission> queryWrapper = QueryGenerator.generateQueryWrapper(FolderPermission.class,queryVO,sortInfo);

        //查询数据
        folderPermissionService.page(page, queryWrapper);
        //转换vo
        IPage<FolderPermissionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<FolderPermissionVO>  folderPermissionVOList=convert2VO(page.getRecords());
        pageVO.setRecords(folderPermissionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "文件夹权限-列表")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:query')")
    public ResponseEntity<Result> list(FolderPermissionVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<FolderPermission> queryWrapper = QueryGenerator.generateQueryWrapper(FolderPermission.class, queryVO,sortInfo);
        List<FolderPermission> list= folderPermissionService.list(queryWrapper);
        //转换vo
        List<FolderPermissionVO>  folderPermissionVOList=convert2VO(list);
        return ResultUtil.success(folderPermissionVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "文件夹权限-详情")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        FolderPermission entity = folderPermissionService.query(id);
        FolderPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "文件夹权限-复制新增")
    @PreAuthorize("hasPermission(null,'edoc:folderPermission:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        folderPermissionService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 授权
     */
    @ApiOperation(value = "授权")
    @PutMapping("/grantPermission")
    @SystemLog(value = "文档管理-授权")
    @PreAuthorize("hasPermission(null,'edoc:folder:grantPermission')")
    public ResponseEntity<Result> grantPermission(@Validated @RequestBody FolderPermissionVO vo) {
        FolderPermission entity = convert2Entity(vo);
        folderPermissionService.savePermission(entity,vo.getPermissionCodeList());
        return ResultUtil.success();
    }



    /**
     * 获取某文件夹对应的某授权类型的权限
     */
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}/getPermission")
    @PreAuthorize("hasPermission(null,'edoc:folder:grantPermission')")
    public ResponseEntity<Result> getPermission(@PathVariable("id") String folderId, String objectId,
                                                String objectType) {
        List<String> permissionCodeList = folderPermissionService.getFolderPermission(folderId, objectId, objectType);
        FolderPermissionVO vo = new FolderPermissionVO();
        vo.setFolderId(folderId);
        vo.setObjectType(objectType);
        vo.setObjectId(objectId);
        vo.setPermissionCodeList(permissionCodeList);
        return ResultUtil.success(vo);
    }


    /**
     * 获取当前用户对某文件夹的权限
     */
    @GetMapping("/getFolderPermissionForUser")
    @PreAuthorize("hasPermission(null,'edoc:folder:grantPermission')")
    public ResponseEntity<Result> getFolderPermissionForUser(String id) {
        List<String> permissionCodeList = folderPermissionService.getFolderPermission(id);
        return ResultUtil.success(permissionCodeList);
    }

    /**
     * 获取当前用户对某文档的权限
     */
    @GetMapping("/getDocumentPermissionForUser")
    @PreAuthorize("hasPermission(null,'edoc:folder:grantPermission')")
    public ResponseEntity<Result> getDocumentPermissionForUser(String id) {
        List<String> permissionCodeList = folderPermissionService.getDocumentPermission(id);
        return ResultUtil.success(permissionCodeList);
    }

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected FolderPermissionVO convert2VO(FolderPermission entity){
        FolderPermissionVO vo=mapperFacade.map(entity,FolderPermissionVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<FolderPermissionVO> convert2VO(List<FolderPermission> entityList) {
        List<FolderPermissionVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            FolderPermissionVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private FolderPermission convert2Entity(FolderPermissionVO vo){
        FolderPermission entity=mapperFacade.map(vo,FolderPermission.class);
        return entity;
    }

    //endregion
 }