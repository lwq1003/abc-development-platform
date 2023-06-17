package tech.abc.platform.support.controller;


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
import tech.abc.platform.support.entity.NoticeReceiver;
import tech.abc.platform.support.service.NoticeReceiverService;
import tech.abc.platform.support.vo.NoticeReceiverVO;
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
* 通知接收 前端控制器类
*
* @author wqliu
* @date 2023-06-15
*/
@RestController
@RequestMapping("/support/noticeReceiver")
@Slf4j
public class NoticeReceiverController extends BaseController {
    @Autowired
    private NoticeReceiverService noticeReceiverService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        NoticeReceiver entity=noticeReceiverService.init();
        NoticeReceiverVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "通知接收-新增")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody NoticeReceiverVO vo) {
        NoticeReceiver entity=convert2Entity(vo);
        noticeReceiverService.add(entity);
        NoticeReceiverVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "通知接收-修改")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody NoticeReceiverVO vo) {
        NoticeReceiver entity=convert2Entity(vo);
        noticeReceiverService.modify(entity);
        NoticeReceiverVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "通知接收-删除")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        noticeReceiverService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "通知接收-分页")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:query')")
    public ResponseEntity<Result> page(NoticeReceiverVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<NoticeReceiver> page = new Page<NoticeReceiver>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<NoticeReceiver> queryWrapper = QueryGenerator.generateQueryWrapper(NoticeReceiver.class,queryVO,sortInfo);

        //查询数据
        noticeReceiverService.page(page, queryWrapper);
        //转换vo
        IPage<NoticeReceiverVO> pageVO = mapperFacade.map(page, IPage.class);
        List<NoticeReceiverVO>  noticeReceiverVOList=convert2VO(page.getRecords());
        pageVO.setRecords(noticeReceiverVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "通知接收-列表")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:query')")
    public ResponseEntity<Result> list(NoticeReceiverVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<NoticeReceiver> queryWrapper = QueryGenerator.generateQueryWrapper(NoticeReceiver.class, queryVO,sortInfo);
        List<NoticeReceiver> list= noticeReceiverService.list(queryWrapper);
        //转换vo
        List<NoticeReceiverVO>  noticeReceiverVOList=convert2VO(list);
        return ResultUtil.success(noticeReceiverVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "通知接收-详情")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        NoticeReceiver entity = noticeReceiverService.query(id);
        NoticeReceiverVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "通知接收-复制新增")
    @PreAuthorize("hasPermission(null,'support:noticeReceiver:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        noticeReceiverService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private NoticeReceiverVO convert2VO(NoticeReceiver entity){
        NoticeReceiverVO vo=mapperFacade.map(entity,NoticeReceiverVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<NoticeReceiverVO> convert2VO(List<NoticeReceiver> entityList) {
        List<NoticeReceiverVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            NoticeReceiverVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private NoticeReceiver convert2Entity(NoticeReceiverVO vo){
        NoticeReceiver entity=mapperFacade.map(vo,NoticeReceiver.class);
        return entity;
    }

    //endregion
 }