package tech.abc.platform.businessflow.controller;


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
import tech.abc.platform.businessflow.entity.Leave;
import tech.abc.platform.businessflow.service.LeaveService;
import tech.abc.platform.businessflow.vo.LeaveVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
* 请假单 前端控制器类
*
* @author wqliu
* @date 2023-07-03
*/
@RestController
@RequestMapping("/businessflow/leave")
@Slf4j
public class LeaveController extends BaseController {
    @Autowired
    private LeaveService leaveService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Leave entity=leaveService.init();
        LeaveVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "请假单-新增")
    @PreAuthorize("hasPermission(null,'businessflow:leave:add')")
    public ResponseEntity<Result> add(@RequestBody LeaveVO vo) {
        Leave entity=convert2Entity(vo);
        leaveService.add(entity);
        LeaveVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "请假单-修改")
    @PreAuthorize("hasPermission(null,'businessflow:leave:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody LeaveVO vo) {
        Leave entity=convert2Entity(vo);
        leaveService.modify(entity);
        LeaveVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "请假单-删除")
    @PreAuthorize("hasPermission(null,'businessflow:leave:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        leaveService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "请假单-分页")
    @PreAuthorize("hasPermission(null,'businessflow:leave:query')")
    public ResponseEntity<Result> page(LeaveVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Leave> page = new Page<Leave>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<Leave> queryWrapper = QueryGenerator.generateQueryWrapper(Leave.class,queryVO,sortInfo);

        //查询数据
        leaveService.page(page, queryWrapper);
        //转换vo
        IPage<LeaveVO> pageVO = mapperFacade.map(page, IPage.class);
        List<LeaveVO>  leaveVOList=convert2VO(page.getRecords());
        pageVO.setRecords(leaveVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "请假单-列表")
    @PreAuthorize("hasPermission(null,'businessflow:leave:query')")
    public ResponseEntity<Result> list(LeaveVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Leave> queryWrapper = QueryGenerator.generateQueryWrapper(Leave.class, queryVO,sortInfo);
        List<Leave> list= leaveService.list(queryWrapper);
        //转换vo
        List<LeaveVO>  leaveVOList=convert2VO(list);
        return ResultUtil.success(leaveVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "请假单-详情")
    @PreAuthorize("hasPermission(null,'businessflow:leave:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Leave entity = leaveService.query(id);
        LeaveVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "请假单-复制新增")
    @PreAuthorize("hasPermission(null,'businessflow:leave:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        leaveService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     *  根据单号获取单条数据
     */
    @ApiOperation(value = "根据单号获取单条数据")
    @GetMapping("/getByBillNo/{billNo}")
    @SystemLog(value = "请假申请-详情")
    @PreAuthorize("hasPermission(null,'businessflow:leave:query')")
    public ResponseEntity<Result> getByBillNo(@PathVariable String billNo) {
        Leave entity = leaveService.lambdaQuery().eq(Leave::getBillNo,billNo).one();
        LeaveVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private LeaveVO convert2VO(Leave entity){
        LeaveVO vo=mapperFacade.map(entity,LeaveVO.class);
        vo.setLeaveTypeName(dictionaryUtil.getNameByCode("LeaveType", entity.getLeaveType()));
        vo.setFlowStatusName(dictionaryUtil.getNameByCode("FlowStatus", entity.getFlowStatus()));

        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<LeaveVO> convert2VO(List<Leave> entityList) {
        List<LeaveVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            LeaveVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Leave convert2Entity(LeaveVO vo){
        Leave entity=mapperFacade.map(vo,Leave.class);
        return entity;
    }

    //endregion
 }