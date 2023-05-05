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
import tech.abc.platform.cip.entity.ApiService;
import tech.abc.platform.cip.service.ApiServiceService;
import tech.abc.platform.cip.vo.ApiServiceVO;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * API服务 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/cip/apiService")
@Slf4j
public class ApiServiceController extends BaseController {
    @Autowired
    private ApiServiceService apiServiceService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ApiService entity = apiServiceService.init();
        ApiServiceVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "API服务-新增")
    @PreAuthorize("hasPermission(null,'cip:apiService:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ApiServiceVO vo) {
        ApiService entity = convert2Entity(vo);
        apiServiceService.add(entity);
        ApiServiceVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "API服务-修改")
    @PreAuthorize("hasPermission(null,'cip:apiService:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ApiServiceVO vo) {
        ApiService entity = convert2Entity(vo);
        apiServiceService.modify(entity);
        ApiServiceVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "API服务-删除")
    @PreAuthorize("hasPermission(null,'cip:apiService:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        apiServiceService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "API服务-分页")
    @PreAuthorize("hasPermission(null,'cip:apiService:query')")
    public ResponseEntity<Result> page(ApiServiceVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<ApiService> page = new Page<ApiService>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<ApiService> queryWrapper = QueryGenerator.generateQueryWrapper(ApiService.class, queryVO, sortInfo);

        // 查询数据
        apiServiceService.page(page, queryWrapper);
        // 转换vo
        IPage<ApiServiceVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ApiServiceVO> apiServiceVOList = convert2VO(page.getRecords());
        pageVO.setRecords(apiServiceVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "API服务-列表")
    @PreAuthorize("hasPermission(null,'cip:apiService:query')")
    public ResponseEntity<Result> list(ApiServiceVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ApiService> queryWrapper = QueryGenerator.generateQueryWrapper(ApiService.class, queryVO, sortInfo);
        List<ApiService> list = apiServiceService.list(queryWrapper);
        // 转换vo
        List<ApiServiceVO> apiServiceVOList = convert2VO(list);
        return ResultUtil.success(apiServiceVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "API服务-详情")
    @PreAuthorize("hasPermission(null,'cip:apiService:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ApiService entity = apiServiceService.query(id);
        ApiServiceVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "API服务-复制新增")
    @PreAuthorize("hasPermission(null,'cip:apiService:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        apiServiceService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "接口服务-启用")
    @PreAuthorize("hasPermission(null,'cip:apiService:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        apiServiceService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "接口服务-停用")
    @PreAuthorize("hasPermission(null,'cip:apiService:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        apiServiceService.disable(id);
        return ResultUtil.success();
    }


    /**
     * 接口服务权限查询
     */
    @GetMapping("/queryApiPermission")
    @SystemLog(value = "接口服务-查询指定应用服务权限")
    @PreAuthorize("hasPermission(null,'cip:apiApp:grantApiPermission')")
    public ResponseEntity<Result> queryApiPermission(ApiServiceVO queryVO, SortInfo sortInfo) {
        // 暂存传入的参数
        String appId = queryVO.getAppId();
        String hasPermission = queryVO.getHasPermission();
        // 移除hasPermission参数，否则会构建sql语句，而该字段并不存在
        queryVO.setHasPermission(null);
        // 构造查询条件
        QueryWrapper<ApiService> queryWrapper = QueryGenerator.generateQueryWrapper(ApiService.class, queryVO, sortInfo);
        List<ApiService> list = apiServiceService.queryApiPermission(queryWrapper, appId, hasPermission);
        // 转换vo
        List<ApiServiceVO> apiServiceVOList = convert2VO(list);
        return ResultUtil.success(apiServiceVOList);
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private ApiServiceVO convert2VO(ApiService entity) {
        ApiServiceVO vo = mapperFacade.map(entity, ApiServiceVO.class);
        vo.setCategoryName(dictionaryUtil.getNameByCode("ApiServiceCategory", entity.getCategory()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<ApiServiceVO> convert2VO(List<ApiService> entityList) {
        List<ApiServiceVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ApiServiceVO vo = convert2VO(x);
            vo.setHasPermissionName(dictionaryUtil.getNameByCode("YesOrNo", vo.getHasPermission()));
            voList.add(vo);
        });
        return voList;
    }


    private ApiService convert2Entity(ApiServiceVO vo) {
        ApiService entity = mapperFacade.map(vo, ApiService.class);
        return entity;
    }

    // endregion
}