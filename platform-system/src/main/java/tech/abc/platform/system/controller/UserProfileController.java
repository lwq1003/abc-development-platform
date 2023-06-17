package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 用户配置 前端控制器类
 *
 * @author wqliu
 * @date 2023-06-14
 */
@RestController
@RequestMapping("/system/userProfile")
@Slf4j
public class UserProfileController extends BaseController {
    @Autowired
    private UserProfileService userProfileService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        UserProfile entity = userProfileService.init();
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
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
    @PutMapping("/")
    @SystemLog(value = "用户配置-修改", logRequestParam = false)
    @PreAuthorize("hasPermission(null,'system:userProfile:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody UserProfileVO vo) {
        UserProfile entity = convert2Entity(vo);
        userProfileService.modify(entity);
        UserProfileVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
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
        List<UserProfileVO> userProfileVOList = convert2VO(page.getRecords());
        pageVO.setRecords(userProfileVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "用户配置-列表")
    @PreAuthorize("hasPermission(null,'system:userProfile:query')")
    public ResponseEntity<Result> list(UserProfileVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<UserProfile> queryWrapper = QueryGenerator.generateQueryWrapper(UserProfile.class, queryVO, sortInfo);
        List<UserProfile> list = userProfileService.list(queryWrapper);
        // 转换vo
        List<UserProfileVO> userProfileVOList = convert2VO(list);
        return ResultUtil.success(userProfileVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "用户配置-详情")
    @PreAuthorize("hasPermission(null,'system:userProfile:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        UserProfile entity = userProfileService.query(id);
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "用户配置-复制新增")
    @PreAuthorize("hasPermission(null,'system:userProfile:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        userProfileService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 获取配置，如不存在则初始化
     */
    @GetMapping("/")
    @SystemLog(value = "用户配置-获取")
    @AllowAuthenticated
    public ResponseEntity<Result> getOrInit() {
        UserProfile entity = userProfileService.getOrInit();
        UserProfileVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private UserProfileVO convert2VO(UserProfile entity) {
        UserProfileVO vo = mapperFacade.map(entity, UserProfileVO.class);
        vo.setLanguageName(dictionaryUtil.getNameByCode("Language", entity.getLanguage()));
        vo.setTimeZoneName(dictionaryUtil.getNameByCode("TimeZone", entity.getTimeZone()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<UserProfileVO> convert2VO(List<UserProfile> entityList) {
        List<UserProfileVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            UserProfileVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private UserProfile convert2Entity(UserProfileVO vo) {
        UserProfile entity = mapperFacade.map(vo, UserProfile.class);
        return entity;
    }

    // endregion
}