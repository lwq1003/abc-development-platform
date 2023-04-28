package tech.abc.platform.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.AllowAuthenticated;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.system.entity.UserProfile;
import tech.abc.platform.system.service.UserProfileService;
import tech.abc.platform.system.vo.UserProfileVO;
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
 * 用户配置 前端控制器
 *
 * @author wqliu
 * @date 2023-03-08
 */
@RestController
@RequestMapping("/system/userProfile")
@Slf4j
public class UserProfileController extends BaseController {
    @Autowired
    private UserProfileService userProfileService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        UserProfile entity = userProfileService.init();
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "用户配置-新增")
    @PreAuthorize("hasPermission(null,'system:userProfile:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody UserProfileVO vo) {
        UserProfile entity = convert2Entity(vo);
        userProfileService.add(entity);
        UserProfileVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "用户配置-修改", logRequestParam = false)
    @PreAuthorize("hasPermission(null,'system:userProfile:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody UserProfileVO vo) {
        UserProfile entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        userProfileService.modify(entity);
        UserProfileVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "用户配置-删除")
    @PreAuthorize("hasPermission(null,'system:userProfile:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        userProfileService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "用户配置-分页")
    @PreAuthorize("hasPermission(null,'system:userProfile:query')")
    public ResponseEntity<Result> page(UserProfileVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<UserProfile> page = new Page<UserProfile>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<UserProfile> queryWrapper = QueryGenerator.generateQueryWrapper(UserProfile.class, queryVO, sortInfo);

        // 查询数据
        userProfileService.page(page, queryWrapper);
        // 转换vo
        IPage<UserProfileVO> pageVO = mapperFacade.map(page, IPage.class);
        List<UserProfileVO> userProfileVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            UserProfileVO vo = convert2VO(page.getRecords().get(i));
            userProfileVOList.add(vo);
        }
        pageVO.setRecords(userProfileVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "用户配置-列表")
    @PreAuthorize("hasPermission(null,'system:userProfile:query')")
    public ResponseEntity<Result> list(UserProfileVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<UserProfile> queryWrapper = QueryGenerator.generateQueryWrapper(UserProfile.class, queryVO, sortInfo);
        List<UserProfile> list = userProfileService.list(queryWrapper);
        // 转换vo
        List<UserProfileVO> userProfileVOList = mapperFacade.mapAsList(list, UserProfileVO.class);
        return ResultUtil.success(userProfileVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "用户配置-详情")
    @PreAuthorize("hasPermission(null,'system:userProfile:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        UserProfile entity = userProfileService.query(id);
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 获取配置，如不存在则初始化
     */
    @ApiOperation(value = "获取配置")
    @GetMapping("/")
    @SystemLog(value = "用户配置-获取")
    @AllowAuthenticated
    public ResponseEntity<Result> getOrInit() {
        UserProfile entity = userProfileService.getOrInit();
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    private UserProfileVO convert2VO(UserProfile entity) {
        UserProfileVO vo = mapperFacade.map(entity, UserProfileVO.class);
        return vo;
    }

    private UserProfile convert2Entity(UserProfileVO vo) {

        UserProfile entity = mapperFacade.map(vo, UserProfile.class);
        return entity;
    }

}
