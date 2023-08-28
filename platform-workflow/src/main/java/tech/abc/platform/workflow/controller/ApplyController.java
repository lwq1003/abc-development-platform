package tech.abc.platform.workflow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.workflow.entity.ProcessInstance;
import tech.abc.platform.workflow.entity.ProcessTask;
import tech.abc.platform.workflow.service.ProcessInstanceService;
import tech.abc.platform.workflow.vo.ProcessInstanceVO;
import tech.abc.platform.workflow.vo.ProcessTaskVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的申请 控制器
 * @author wqliu
 * @date 2023-07-26
 */
@RestController
@RequestMapping("/workflow/apply")
@Slf4j
public class ApplyController extends BaseController {


    /**
     * 最大数量
     */
    public static final int MAX_COUNT = 20;

    @Autowired
    private ProcessInstanceService processInstanceService;


    /**
     * 我的申请查询
     */
    @GetMapping("/page")
    @SystemLog(value = "我的申请-分页查询")
    public ResponseEntity<Result> getTodo(ProcessInstanceVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<ProcessInstance> page = new Page<ProcessInstance>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<ProcessInstance> queryWrapper = QueryGenerator.generateQueryWrapper(ProcessInstance.class, queryVO,
                sortInfo);

        //查询数据
        processInstanceService.getApply(page, queryWrapper);


        //转换vo
        IPage<ProcessInstanceVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ProcessInstanceVO>  processInstanceVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ProcessInstanceVO vo = convert2VO(page.getRecords().get(i));
            processInstanceVOList.add(vo);
        }
        pageVO.setRecords(processInstanceVOList);        ;
        return ResultUtil.success(pageVO);
    }

    private ProcessInstanceVO convert2VO(ProcessInstance entity) {

        ProcessInstanceVO vo = mapperFacade.map(entity, ProcessInstanceVO.class);
        vo.setStateName(dictionaryUtil.getNameByCode("FlowStatus", entity.getState()));

        return vo;
    }

    /**
     * 获取组件数据
     */

    @GetMapping("/portlet")
    public ResponseEntity<Result> getPortletData(@RequestParam Map<String, String> map) {

        // 数量参数处理
        String countString = StringUtils.getDigits(map.get("count"));
        Integer count = Integer.parseInt(countString);
        // 如参数不正确，则设置为最大值
        if (count <= 0 || count > MAX_COUNT) {
            count=MAX_COUNT;
        }


        List<ProcessInstance> list = processInstanceService.getApplyPortletData(count);
        List<ProcessInstanceVO> voList = mapperFacade.mapAsList(list, ProcessInstanceVO.class);
        return ResultUtil.success(voList);
    }

}
