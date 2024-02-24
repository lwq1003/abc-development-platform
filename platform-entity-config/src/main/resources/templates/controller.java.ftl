package ${package.Controller};


import org.springframework.web.bind.annotation.RestController;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
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
import tech.abc.${appCode}.${package.ModuleName}.entity.${entity};
import tech.abc.${appCode}.${package.ModuleName}.service.${entity}Service;
import tech.abc.${appCode}.${package.ModuleName}.vo.${entity}VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
<#if entityModelSelfReferenceFlag=="YES">
import tech.abc.platform.common.utils.TreeUtil;
import tech.abc.platform.common.vo.TreeVO;
</#if>

/**
* ${table.comment!} 前端控制器类
*
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?uncap_first}")
@Slf4j
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

<#if entityModelPropertyList?? && (entityModelPropertyList?size > 0)>
<#list entityModelPropertyList as item>
    <#if item.dataType=='ENTITY'>
        <#if item.entityCode!= entity >
    @Autowired
    private ${item.entityCode}Service ${item.entityCode?uncap_first}Service;
        </#if>
    <#elseif item.dataType=='USER_SINGLE'>
         <#if "user"!= entity?uncap_first >
    @Autowired
    private UserService userService;
         </#if>
    <#elseif item.dataType=='ORGANIZATION_SINGLE'>
         <#if "organization"!= entity?uncap_first >
    @Autowired
    private OrganizationService organizationService;
         </#if>
    </#if>
</#list>
</#if>

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ${entity} entity=${entity?uncap_first}Service.init();
        ${entity}VO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "${table.comment!}-新增")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ${entity}VO vo) {
        ${entity} entity=convert2Entity(vo);
        ${entity?uncap_first}Service.add(entity);
        ${entity}VO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "${table.comment!}-修改")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ${entity}VO vo) {
        ${entity} entity=convert2Entity(vo);
        ${entity?uncap_first}Service.modify(entity);
        ${entity}VO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "${table.comment!}-删除")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        ${entity?uncap_first}Service.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "${table.comment!}-分页")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:query')")
    public ResponseEntity<Result> page(${entity}VO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<${entity}> page = new Page<${entity}>(pageInfo.getPageNum(), pageInfo.getPageSize());

        <#if parentPropertyCode??>
        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.set${parentPropertyCode?cap_first}(null);
        }
       </#if>

        //构造查询条件
        QueryWrapper<${entity}> queryWrapper = QueryGenerator.generateQueryWrapper(${entity}.class,queryVO,sortInfo);

        //查询数据
        ${entity?uncap_first}Service.page(page, queryWrapper);
        //转换vo
        IPage<${entity}VO> pageVO = mapperFacade.map(page, IPage.class);
        List<${entity}VO>  ${entity?uncap_first}VOList=convert2VO(page.getRecords());
        pageVO.setRecords(${entity?uncap_first}VOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "${table.comment!}-列表")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:query')")
    public ResponseEntity<Result> list(${entity}VO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<${entity}> queryWrapper = QueryGenerator.generateQueryWrapper(${entity}.class, queryVO,sortInfo);
        List<${entity}> list= ${entity?uncap_first}Service.list(queryWrapper);
        //转换vo
        List<${entity}VO>  ${entity?uncap_first}VOList=convert2VO(list);
        return ResultUtil.success(${entity?uncap_first}VOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "${table.comment!}-详情")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ${entity} entity = ${entity?uncap_first}Service.query(id);
        ${entity}VO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "${table.comment!}-复制新增")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        ${entity?uncap_first}Service.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作

    //endregion

    <#if entityModelSelfReferenceFlag=="YES">
    //region 树操作
    /**
    * 获取树数据
    *
    * @return
    */
    @GetMapping("/tree")
    @PreAuthorize("hasPermission(null,'${package.ModuleName}:${entity?uncap_first}:query')")
    public ResponseEntity<Result> tree() {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        <#list entityModelPropertyList as item>
            <#if item.code=="status">
        queryWrapper.lambda().eq(${entity}::getStatus, StatusEnum.NORMAL.toString());
            </#if>
            <#if item.code=="orderNo">
        // 附加按照排序号排序
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
            </#if>
        </#list>
        List<${entity}> list = ${entity?uncap_first}Service.list(queryWrapper);
        // 转化成树结构数据
        List<TreeVO> treeList = list.stream().map(e -> convert2TreeVO(e)).collect(Collectors.toList());
        List<TreeVO> tree = TreeUtil.buildTree(treeList, TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID);
        return ResultUtil.success(tree);
   }

    /**
    * 转换为树视图对象
    */
    private TreeVO convert2TreeVO(${entity} entity) {
        TreeVO tree = new TreeVO();
        tree.setId(entity.getId());
        tree.setParentId(entity.get${parentPropertyCode?cap_first}());
        tree.setLabel(entity.get${mainPropertyCode?cap_first}());
        return tree;
    }

    //endregion
    </#if>
    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected ${entity}VO convert2VO(${entity} entity){
        ${entity}VO vo=mapperFacade.map(entity,${entity}VO.class);
        <#list entityModelPropertyList as item>
            <#if item.dataType=="DATA_DICTIONARY">
        vo.set${item.code?cap_first}Name(dictionaryUtil.getNameByCode("${item.dictionaryType}", entity.get${item.code?cap_first}()));
            </#if>
        </#list>
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<${entity}VO> convert2VO(List<${entity}> entityList) {
        List<${entity}VO> voList = new ArrayList<>(entityList.size());

<#if entityModelPropertyList?? && (entityModelPropertyList?size > 0)>
<#list entityModelPropertyList as item>
    <#if item.dataType=='ENTITY' >
        // 获取 ${item.name} 集合
        List<String> ${item.entityCode?uncap_first}List = entityList.stream().map(x -> x.get${item.code?cap_first}()).collect(Collectors.toList());
        Map<String,String> ${item.entityCode?uncap_first}NameMap = ${item.entityCode?uncap_first}Service.getNameMap(${item.entityCode?uncap_first}List);

    <#elseif item.dataType=='USER_SINGLE'>
        // 获取 ${item.name} 集合
        List<String> ${item.code}List = entityList.stream().map(x -> x.get${item.code?cap_first}()).collect(Collectors.toList());
        Map<String,String> ${item.code}NameMap = userService.getNameMap(${item.code}List);


    <#elseif item.dataType=='ORGANIZATION_SINGLE'>
        // 获取 ${item.name} 集合
        List<String> ${item.code}List = entityList.stream().map(x -> x.get${item.code?cap_first}()).collect(Collectors.toList());
        Map<String,String> ${item.code}NameMap = organizationService.getNameMap(${item.code}List);

    </#if>
</#list>
</#if>
        entityList.stream().forEach(x -> {
            ${entity}VO vo = convert2VO(x);
<#if entityModelPropertyList?? && (entityModelPropertyList?size > 0)>
<#list entityModelPropertyList as item>
    <#if item.dataType=='ENTITY' >
            // 设置 ${item.name}
            vo.set${item.code?cap_first}Name(${item.entityCode?uncap_first}NameMap.get(x.get${item.code?cap_first}()));
    <#elseif item.dataType=='USER_SINGLE' || item.dataType=='ORGANIZATION_SINGLE'>
            // 设置 ${item.name}
            vo.set${item.code?cap_first}Name(${item.code}NameMap.get(x.get${item.code?cap_first}()));
    </#if>
</#list>
</#if>
            voList.add(vo);
        });
        return voList;
    }


    private ${entity} convert2Entity(${entity}VO vo){
        ${entity} entity=mapperFacade.map(vo,${entity}.class);
        return entity;
    }

    //endregion
 }