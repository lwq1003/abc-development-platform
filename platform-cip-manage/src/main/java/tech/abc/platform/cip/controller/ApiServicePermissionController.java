package tech.abc.platform.cip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.cip.entity.ApiServicePermission;
import tech.abc.platform.cip.service.ApiServicePermissionService;
import tech.abc.platform.cip.service.ApiServiceService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.vo.ApiServicePermissionVO;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API服务权限 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/apiServicePermission")
@Slf4j
public class ApiServicePermissionController extends BaseController {
    @Autowired
    private ApiServicePermissionService apiServicePermissionService;
    @Autowired
    private AppService appService;
    @Autowired
    private ApiServiceService apiServiceService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ApiServicePermission entity = apiServicePermissionService.init();
        ApiServicePermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "API服务权限-新增")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ApiServicePermissionVO vo) {
        ApiServicePermission entity = convert2Entity(vo);
        apiServicePermissionService.add(entity);
        ApiServicePermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "API服务权限-修改")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ApiServicePermissionVO vo) {
        ApiServicePermission entity = convert2Entity(vo);
        apiServicePermissionService.modify(entity);
        ApiServicePermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "API服务权限-删除")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        apiServicePermissionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "API服务权限-分页")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:query')")
    public ResponseEntity<Result> page(ApiServicePermissionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ApiServicePermission> page = new Page<ApiServicePermission>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<ApiServicePermission> queryWrapper = QueryGenerator.generateQueryWrapper(ApiServicePermission.class, queryVO, sortInfo);

        // 查询数据
        apiServicePermissionService.page(page, queryWrapper);
        // 转换vo
        IPage<ApiServicePermissionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ApiServicePermissionVO> apiServicePermissionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(apiServicePermissionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "API服务权限-列表")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:query')")
    public ResponseEntity<Result> list(ApiServicePermissionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ApiServicePermission> queryWrapper = QueryGenerator.generateQueryWrapper(ApiServicePermission.class, queryVO, sortInfo);
        List<ApiServicePermission> list = apiServicePermissionService.list(queryWrapper);
        // 转换vo
        List<ApiServicePermissionVO> apiServicePermissionVOList = convert2VO(list);
        return ResultUtil.success(apiServicePermissionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "API服务权限-详情")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ApiServicePermission entity = apiServicePermissionService.query(id);
        ApiServicePermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "API服务权限-复制新增")
    @PreAuthorize("hasPermission(null,'cip:apiServicePermission:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        apiServicePermissionService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private ApiServicePermissionVO convert2VO(ApiServicePermission entity) {
        ApiServicePermissionVO vo = mapperFacade.map(entity, ApiServicePermissionVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ApiServicePermissionVO> convert2VO(List<ApiServicePermission> entityList) {
        List<ApiServicePermissionVO> voList = new ArrayList<>(entityList.size());

        // 获取 应用 集合
        List<String> appList = entityList.stream().map(x -> x.getApp()).collect(Collectors.toList());
        Map<String, String> appNameMap = appService.getNameMap(appList);
        // 获取 API服务 集合
        List<String> apiServiceList = entityList.stream().map(x -> x.getApiService()).collect(Collectors.toList());
        Map<String, String> apiServiceNameMap = apiServiceService.getNameMap(apiServiceList);
        entityList.stream().forEach(x -> {
            ApiServicePermissionVO vo = convert2VO(x);
            // 设置 应用
            vo.setAppName(appNameMap.get(x.getApp()));
            // 设置 API服务
            vo.setApiServiceName(apiServiceNameMap.get(x.getApiService()));
            voList.add(vo);
        });
        return voList;
    }


    private ApiServicePermission convert2Entity(ApiServicePermissionVO vo) {
        ApiServicePermission entity = mapperFacade.map(vo, ApiServicePermission.class);
        return entity;
    }

    // endregion
}