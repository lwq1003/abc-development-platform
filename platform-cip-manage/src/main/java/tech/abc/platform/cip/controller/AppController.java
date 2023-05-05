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
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.vo.AppVO;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-02
 */
@RestController
@RequestMapping("/cip/app")
@Slf4j
public class AppController extends BaseController {
    @Autowired
    private AppService appService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        App entity = appService.init();
        AppVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "应用-新增")
    @PreAuthorize("hasPermission(null,'cip:app:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody AppVO vo) {
        App entity = convert2Entity(vo);
        appService.add(entity);
        AppVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "应用-修改")
    @PreAuthorize("hasPermission(null,'cip:app:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody AppVO vo) {
        App entity = convert2Entity(vo);
        appService.modify(entity);
        AppVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "应用-删除")
    @PreAuthorize("hasPermission(null,'cip:app:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        appService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "应用-分页")
    @PreAuthorize("hasPermission(null,'cip:app:query')")
    public ResponseEntity<Result> page(AppVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<App> page = new Page<App>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<App> queryWrapper = QueryGenerator.generateQueryWrapper(App.class, queryVO, sortInfo);

        // 查询数据
        appService.page(page, queryWrapper);
        // 转换vo
        IPage<AppVO> pageVO = mapperFacade.map(page, IPage.class);
        List<AppVO> appVOList = convert2VO(page.getRecords());
        pageVO.setRecords(appVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "应用-列表")
    @PreAuthorize("hasPermission(null,'cip:app:query')")
    public ResponseEntity<Result> list(AppVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<App> queryWrapper = QueryGenerator.generateQueryWrapper(App.class, queryVO, sortInfo);
        List<App> list = appService.list(queryWrapper);
        // 转换vo
        List<AppVO> appVOList = convert2VO(list);
        return ResultUtil.success(appVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "应用-详情")
    @PreAuthorize("hasPermission(null,'cip:app:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        App entity = appService.query(id);
        AppVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "应用-复制新增")
    @PreAuthorize("hasPermission(null,'cip:app:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        appService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "应用-启用")
    @PreAuthorize("hasPermission(null,'cip:app:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        appService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @PutMapping("/{id}/disable")
    @SystemLog(value = "应用-停用")
    @PreAuthorize("hasPermission(null,'cip:app:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        appService.disable(id);
        return ResultUtil.success();
    }

    /**
     * 管理员重置密钥
     */
    @PutMapping("/{id}/resetSecret")
    @SystemLog(value = "重置密码", logType = LogTypeEnum.AUDIT)
    @PreAuthorize("hasPermission(null,'cip:app:resetSecret')")
    public ResponseEntity<Result> resetPassword(@PathVariable String id) {
        appService.resetSecret(id);
        return ResultUtil.success();
    }


    // endregion

    // region 接口服务权限

    /**
     * 授予接口服务权限
     */
    @PutMapping("/{appId}/grantApiPermission")
    @SystemLog(value = "应用-授予接口服务权限")
    @PreAuthorize("hasPermission(null,'cip:app:grantApiPermission')")
    public ResponseEntity<Result> grantApiPermission(@PathVariable String appId,
                                                     @RequestBody(required = false) List<String> apiServiceIdList) {
        appService.grantApiPermission(appId, apiServiceIdList);
        return ResultUtil.success();
    }

    /**
     * 收回接口服务权限
     */
    @PutMapping("/{appId}/withdrawApiPermission")
    @SystemLog(value = "应用-收回接口服务权限")
    @PreAuthorize("hasPermission(null,'cip:app:withdrawApiPermission')")
    public ResponseEntity<Result> withdrawApiPermission(@PathVariable String appId, @RequestBody(required = false) List<String> apiServiceIdList) {
        appService.withdrawApiPermission(appId, apiServiceIdList);
        return ResultUtil.success();
    }


    // endregion

    // region 消息主题权限

    /**
     * 授予消息主题权限
     */
    @PutMapping("/{appId}/grantMessagePermission")
    @SystemLog(value = "应用-授予消息主题权限")
    @PreAuthorize("hasPermission(null,'cip:app:grantMessagePermission')")
    public ResponseEntity<Result> grantMessagePermission(@PathVariable String appId,
                                                         @RequestBody(required = false) List<String> messageTopicIdList) {
        appService.grantMessageTopicPermission(appId, messageTopicIdList);
        return ResultUtil.success();
    }

    /**
     * 收回消息主题权限
     */
    @PutMapping("/{appId}/withdrawMessagePermission")
    @SystemLog(value = "应用-收回消息主题权限")
    @PreAuthorize("hasPermission(null,'cip:app:withdrawMessagePermission')")
    public ResponseEntity<Result> withdrawMessagePermission(@PathVariable String appId, @RequestBody(required = false) List<String> messageTopicIdList) {
        appService.withdrawMessageTopicPermission(appId, messageTopicIdList);
        return ResultUtil.success();
    }


    // endregion


    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private AppVO convert2VO(App entity) {
        AppVO vo = mapperFacade.map(entity, AppVO.class);
        vo.setIntegrationModelName(dictionaryUtil.getNameByCode("IntegrationModel", entity.getIntegrationModel()));
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<AppVO> convert2VO(List<App> entityList) {
        List<AppVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            AppVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private App convert2Entity(AppVO vo) {
        App entity = mapperFacade.map(vo, App.class);
        return entity;
    }

    // endregion
}