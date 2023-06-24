package tech.abc.platform.support.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
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
import tech.abc.platform.support.entity.SerialNo;
import tech.abc.platform.support.service.SerialNoService;
import tech.abc.platform.support.vo.SerialNoVO;
import tech.abc.platform.system.service.ModuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流水号 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/support/serialNo")
@Slf4j
public class SerialNoController extends BaseController {
    @Autowired
    private SerialNoService serialNoService;
    @Autowired
    private ModuleService moduleService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        SerialNo entity = serialNoService.init();
        SerialNoVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "流水号-新增")
    @PreAuthorize("hasPermission(null,'support:serialNo:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody SerialNoVO vo) {
        SerialNo entity = convert2Entity(vo);
        serialNoService.add(entity);
        SerialNoVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "流水号-修改")
    @PreAuthorize("hasPermission(null,'support:serialNo:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody SerialNoVO vo) {
        SerialNo entity = convert2Entity(vo);
        serialNoService.modify(entity);
        SerialNoVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "流水号-删除")
    @PreAuthorize("hasPermission(null,'support:serialNo:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        serialNoService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "流水号-分页")
    @PreAuthorize("hasPermission(null,'support:serialNo:query')")
    public ResponseEntity<Result> page(SerialNoVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<SerialNo> page = new Page<SerialNo>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<SerialNo> queryWrapper = QueryGenerator.generateQueryWrapper(SerialNo.class, queryVO, sortInfo);

        // 查询数据
        serialNoService.page(page, queryWrapper);
        // 转换vo
        IPage<SerialNoVO> pageVO = mapperFacade.map(page, IPage.class);
        List<SerialNoVO> serialNoVOList = convert2VO(page.getRecords());
        pageVO.setRecords(serialNoVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "流水号-列表")
    @PreAuthorize("hasPermission(null,'support:serialNo:query')")
    public ResponseEntity<Result> list(SerialNoVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<SerialNo> queryWrapper = QueryGenerator.generateQueryWrapper(SerialNo.class, queryVO, sortInfo);
        List<SerialNo> list = serialNoService.list(queryWrapper);
        // 转换vo
        List<SerialNoVO> serialNoVOList = convert2VO(list);
        return ResultUtil.success(serialNoVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "流水号-详情")
    @PreAuthorize("hasPermission(null,'support:serialNo:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        SerialNo entity = serialNoService.query(id);
        SerialNoVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "流水号-复制新增")
    @PreAuthorize("hasPermission(null,'support:serialNo:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        serialNoService.addByCopy(id);
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
    private SerialNoVO convert2VO(SerialNo entity) {
        SerialNoVO vo = mapperFacade.map(entity, SerialNoVO.class);
        vo.setResetStrategyName(dictionaryUtil.getNameByCode("SerialNoResetStrategy", entity.getResetStrategy()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<SerialNoVO> convert2VO(List<SerialNo> entityList) {
        List<SerialNoVO> voList = new ArrayList<>(entityList.size());

        // 获取 模块 集合
        List<String> moduleList = entityList.stream().map(x -> x.getModule()).collect(Collectors.toList());
        Map<String, String> moduleNameMap = moduleService.getNameMap(moduleList);
        entityList.stream().forEach(x -> {
            SerialNoVO vo = convert2VO(x);
            // 设置 模块
            vo.setModuleName(moduleNameMap.get(x.getModule()));
            voList.add(vo);
        });
        return voList;
    }


    private SerialNo convert2Entity(SerialNoVO vo) {
        SerialNo entity = mapperFacade.map(vo, SerialNo.class);
        return entity;
    }

    // endregion
}