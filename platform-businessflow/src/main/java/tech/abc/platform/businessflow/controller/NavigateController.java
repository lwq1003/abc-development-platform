package tech.abc.platform.businessflow.controller;


import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.CascaderItemVO;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.system.entity.DictionaryItem;
import tech.abc.platform.system.service.DictionaryTypeService;
import tech.abc.platform.workflow.entity.WorkflowTemplate;
import tech.abc.platform.workflow.service.WorkflowTemplateService;

import tech.abc.platform.workflow.vo.WorkflowTemplateVO;
import tech.abc.platform.workflow.vo.WorkflowTemplateCategoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 业务流程导航 控制器
 *
 * @author wqliu
 * @date 2023-07-04
 */
@RestController
@RequestMapping("/businessflow/navigate")
public class NavigateController extends BaseController {


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;



    @Autowired
    private WorkflowTemplateService flowTemplateService;

    /**
     * 获取流程模板及其分类
     */
    @GetMapping("/")
    @SystemLog(value = "流程一览-查询数据")
    @PreAuthorize("hasPermission(null,'businessflow:navigate:query')")
    public ResponseEntity<Result> get() {


        //获取当前用户拥有启动权限流程权限接口
        List<WorkflowTemplate> flowTemplateList = flowTemplateService.listByPermission();

        // 为空无需继续处理，直接返回即可
        if (CollectionUtils.isEmpty(flowTemplateList)) {
            return ResultUtil.success();
        }

        // 获取分类
        List<DictionaryItem> categoryList = dictionaryTypeService.getItem("ProcessDefinitionCategory");


        // 组装数据
        List<WorkflowTemplateCategoryVO> data = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            WorkflowTemplateCategoryVO category = new WorkflowTemplateCategoryVO();
            String categoryCode = categoryList.get(i).getCode();
            category.setCode(categoryCode);
            category.setName(categoryList.get(i).getName());
            List<WorkflowTemplate> childTemplateList = flowTemplateList.stream()
                    .filter(x -> x.getCategory().equals(categoryCode)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(childTemplateList)) {
                List<WorkflowTemplateVO> childList =mapperFacade.mapAsList(childTemplateList, WorkflowTemplateVO.class);
                category.setWorkflowTemplateVOList(childList);
                // 只有存在业务流程时才添加分类
                data.add(category);
            }
        }
        return ResultUtil.success(data);
    }


    /**
     * 获取流程分类及流程 级联框
     */
    @GetMapping("/cascader")
    @SystemLog(value = "流程一览-获取级联数据")
    public ResponseEntity<Result> cascader() {


        //获取当前用户拥有启动权限流程权限接口
        List<WorkflowTemplate> flowTemplateList = flowTemplateService.listByPermission();

        // 为空无需继续处理，直接返回即可
        if (CollectionUtils.isEmpty(flowTemplateList)) {
            return ResultUtil.success();
        }

        // 获取分类
        List<DictionaryItem> categoryList = dictionaryTypeService.getItem("ProcessDefinitionCategory");


        // 组装数据
        List<CascaderItemVO> data = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            CascaderItemVO category = new CascaderItemVO();
            String categoryCode = categoryList.get(i).getCode();
            category.setValue(categoryCode);
            category.setLabel(categoryList.get(i).getName());
            List<WorkflowTemplate> childTemplateList = flowTemplateList.stream()
                    .filter(x -> x.getCategory().equals(categoryCode)).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(childTemplateList)) {
                List<CascaderItemVO> childList = new ArrayList<>();
                for (int j = 0; j < childTemplateList.size(); j++) {
                    CascaderItemVO template = new CascaderItemVO();
                    template.setValue(childTemplateList.get(j).getCode());
                    template.setLabel(childTemplateList.get(j).getName());
                    childList.add(template);
                }
                category.setChildren(childList);
                // 只有存在业务流程时才添加分类
                data.add(category);
            }
        }
        return ResultUtil.success(data);
    }

}
