package tech.popsoft.cip.client.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.popsoft.cip.client.manage.entity.ApiMessageTopic;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;
import tech.popsoft.cip.client.manage.vo.ApiMessageTopicVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息主题 前端控制器
 *
 * @author wqliu
 * @date 2021-08-21
 */
@RestController
@RequestMapping("/cip/apiMessageTopic")
@Slf4j
public class ApiMessageTopicController extends BaseController {
    @Autowired
    private ApiMessageTopicService apiMessageTopicService;

    // region 基本操作

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ApiMessageTopic entity = apiMessageTopicService.init();
        ApiMessageTopicVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "消息主题-新增")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ApiMessageTopicVO vo) {
        ApiMessageTopic entity = convert2Entity(vo);
        apiMessageTopicService.add(entity);
        ApiMessageTopicVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "消息主题-修改")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ApiMessageTopicVO vo) {
        ApiMessageTopic entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        apiMessageTopicService.modify(entity);
        ApiMessageTopicVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "消息主题-删除")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        apiMessageTopicService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "消息主题-分页")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:query')")
    public ResponseEntity<Result> page(ApiMessageTopicVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<ApiMessageTopic> page = new Page<ApiMessageTopic>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<ApiMessageTopic> queryWrapper = QueryGenerator.generateQueryWrapper(ApiMessageTopic.class, queryVO, sortInfo);

        // 查询数据
        apiMessageTopicService.page(page, queryWrapper);
        // 转换vo
        IPage<ApiMessageTopicVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ApiMessageTopicVO> apiMessageTopicVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ApiMessageTopicVO vo = convert2VO(page.getRecords().get(i));
            apiMessageTopicVOList.add(vo);
        }
        pageVO.setRecords(apiMessageTopicVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "消息主题-列表")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:query')")
    public ResponseEntity<Result> list(ApiMessageTopicVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ApiMessageTopic> queryWrapper = QueryGenerator.generateQueryWrapper(ApiMessageTopic.class, queryVO, sortInfo);
        List<ApiMessageTopic> list = apiMessageTopicService.list(queryWrapper);
        // 转换vo
        List<ApiMessageTopicVO> apiMessageTopicVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ApiMessageTopicVO vo = convert2VO(list.get(i));
            apiMessageTopicVOList.add(vo);
        }
        return ResultUtil.success(apiMessageTopicVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "消息主题-详情")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ApiMessageTopic entity = apiMessageTopicService.query(id);
        ApiMessageTopicVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    // endregion

    // region 扩展操作

    /**
     * 启用
     */
    @ApiOperation(value = "启用")
    @PutMapping("/{id}/enable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @SystemLog(value = "消息主题-启用")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        apiMessageTopicService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/{id}/disable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @SystemLog(value = "消息主题-停用")
    @PreAuthorize("hasPermission(null,'cip:apiMessageTopic:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        apiMessageTopicService.disable(id);
        return ResultUtil.success();
    }


    // endregion

    // region 辅助操作

    private ApiMessageTopicVO convert2VO(ApiMessageTopic entity) {
        ApiMessageTopicVO vo = mapperFacade.map(entity, ApiMessageTopicVO.class);
        String statusName = dictionaryUtil.getNameByCode("Status", entity.getStatus());
        vo.setStatusName(statusName);
        String categoryName = dictionaryUtil.getNameByCode("ApiMessageTopicCategory", entity.getCategory());
        vo.setCategoryName(categoryName);

        if (StringUtils.isNotBlank(entity.getHasPermission())) {
            String hasPermission = dictionaryUtil.getNameByCode("YesOrNo", entity.getHasPermission());
            vo.setHasPermissionName(hasPermission);
        }

        if (StringUtils.isNotBlank(entity.getHasSubscription())) {
            String hasSubscription = dictionaryUtil.getNameByCode("YesOrNo", entity.getHasSubscription());
            vo.setHasSubscriptionName(hasSubscription);
        }
        return vo;
    }

    private ApiMessageTopic convert2Entity(ApiMessageTopicVO vo) {

        ApiMessageTopic entity = mapperFacade.map(vo, ApiMessageTopic.class);
        return entity;
    }

    // endregion
}
