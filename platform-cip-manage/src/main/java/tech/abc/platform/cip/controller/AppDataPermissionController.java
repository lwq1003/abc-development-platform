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
import tech.abc.platform.cip.entity.AppDataPermission;
import tech.abc.platform.cip.service.AppDataPermissionService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.vo.AppDataPermissionVO;
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
 * 应用数据权限 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/appDataPermission")
@Slf4j
public class AppDataPermissionController extends BaseController {
    @Autowired
    private AppDataPermissionService appDataPermissionService;
    @Autowired
    private AppService appService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        AppDataPermission entity = appDataPermissionService.init();
        AppDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "应用数据权限-新增")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody AppDataPermissionVO vo) {
        AppDataPermission entity = convert2Entity(vo);
        appDataPermissionService.add(entity);
        AppDataPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "应用数据权限-修改")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody AppDataPermissionVO vo) {
        AppDataPermission entity = convert2Entity(vo);
        appDataPermissionService.modify(entity);
        AppDataPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "应用数据权限-删除")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        appDataPermissionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "应用数据权限-分页")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:query')")
    public ResponseEntity<Result> page(AppDataPermissionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<AppDataPermission> page = new Page<AppDataPermission>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<AppDataPermission> queryWrapper = QueryGenerator.generateQueryWrapper(AppDataPermission.class, queryVO, sortInfo);

        // 查询数据
        appDataPermissionService.page(page, queryWrapper);
        // 转换vo
        IPage<AppDataPermissionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<AppDataPermissionVO> appDataPermissionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(appDataPermissionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "应用数据权限-列表")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:query')")
    public ResponseEntity<Result> list(AppDataPermissionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<AppDataPermission> queryWrapper = QueryGenerator.generateQueryWrapper(AppDataPermission.class, queryVO, sortInfo);
        List<AppDataPermission> list = appDataPermissionService.list(queryWrapper);
        // 转换vo
        List<AppDataPermissionVO> appDataPermissionVOList = convert2VO(list);
        return ResultUtil.success(appDataPermissionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "应用数据权限-详情")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        AppDataPermission entity = appDataPermissionService.query(id);
        AppDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "应用数据权限-复制新增")
    @PreAuthorize("hasPermission(null,'cip:appDataPermission:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        appDataPermissionService.addByCopy(id);
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
    private AppDataPermissionVO convert2VO(AppDataPermission entity) {
        AppDataPermissionVO vo = mapperFacade.map(entity, AppDataPermissionVO.class);
        vo.setRoleCodeName(dictionaryUtil.getNameByCode("DataRole", entity.getRoleCode()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<AppDataPermissionVO> convert2VO(List<AppDataPermission> entityList) {
        List<AppDataPermissionVO> voList = new ArrayList<>(entityList.size());

        // 获取 应用 集合
        List<String> appList = entityList.stream().map(x -> x.getApp()).collect(Collectors.toList());
        Map<String, String> appNameMap = appService.getNameMap(appList);
        entityList.stream().forEach(x -> {
            AppDataPermissionVO vo = convert2VO(x);
            // 设置 应用
            vo.setAppName(appNameMap.get(x.getApp()));
            voList.add(vo);
        });
        return voList;
    }


    private AppDataPermission convert2Entity(AppDataPermissionVO vo) {
        AppDataPermission entity = mapperFacade.map(vo, AppDataPermission.class);
        return entity;
    }

    // endregion
}