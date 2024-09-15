package tech.abc.platform.entityconfig.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;
import tech.abc.platform.entityconfig.enums.EntityModelPropertyTypeEnum;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.vo.EntityModelPropertyForFilterVO;
import tech.abc.platform.entityconfig.vo.EntityModelPropertyVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体模型属性 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/entityconfig/entityModelProperty")
@Slf4j
public class EntityModelPropertyController extends BaseController {
    @Autowired
    private EntityModelPropertyService entityModelPropertyService;


    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        EntityModelProperty entity = entityModelPropertyService.init();
        EntityModelPropertyVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "实体模型属性-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody EntityModelPropertyVO vo) {
        EntityModelProperty entity = convert2Entity(vo);
        entityModelPropertyService.add(entity);
        EntityModelPropertyVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "实体模型属性-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityModelPropertyVO vo) {
        EntityModelProperty entity = convert2Entity(vo);
        entityModelPropertyService.modify(entity);
        EntityModelPropertyVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "实体模型属性-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        entityModelPropertyService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "实体模型属性-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:query')")
    public ResponseEntity<Result> page(EntityModelPropertyVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<EntityModelProperty> page = new Page<EntityModelProperty>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<EntityModelProperty> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModelProperty.class, queryVO, sortInfo);

        // 查询数据
        entityModelPropertyService.page(page, queryWrapper);
        // 转换vo
        IPage<EntityModelPropertyVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityModelPropertyVO> entityModelPropertyVOList = convert2VO(page.getRecords());
        pageVO.setRecords(entityModelPropertyVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "实体模型属性-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:query')")
    public ResponseEntity<Result> list(EntityModelPropertyVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<EntityModelProperty> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModelProperty.class, queryVO, sortInfo);
        List<EntityModelProperty> list = entityModelPropertyService.list(queryWrapper);
        // 转换vo
        List<EntityModelPropertyVO> entityModelPropertyVOList = convert2VO(list);
        return ResultUtil.success(entityModelPropertyVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "实体模型属性-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        EntityModelProperty entity = entityModelPropertyService.query(id);
        EntityModelPropertyVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "实体模型属性-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        entityModelPropertyService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 获取完整属性列表
     *
     * @param entityModelId 实体模型id
     * @return {@link ResponseEntity}<{@link Result}>
     */
    @GetMapping("/{entityModelId}/list")
    @SystemLog(value = "实体模型属性-完整列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:query')")
    public ResponseEntity<Result> getFullPropertyList(@PathVariable String entityModelId) {
        // 构造查询条件

        List<EntityModelProperty> list = entityModelPropertyService.getFullPropertyByEntityModelId(entityModelId);
        // 转换vo
        List<EntityModelPropertyVO> entityModelPropertyVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EntityModelPropertyVO vo = convert2VO(list.get(i));
            entityModelPropertyVOList.add(vo);
        }
        return ResultUtil.success(entityModelPropertyVOList);
    }


    /**
     * 获取完整属性列表，转换为数据筛选器需要的格式，用于数据权限配置
     * 只读取数据库存储的属性
     *
     * @param entityModelId 实体模型id
     * @return {@link ResponseEntity}<{@link Result}>
     */
    @GetMapping("/{entityModelId}/getFullPropertyListForFilter")
    @SystemLog(value = "实体模型属性-完整列表，转换为过滤器格式")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:query')")
    public ResponseEntity<Result> getFullPropertyListForFilter(@PathVariable String entityModelId) {
        // 构造查询条件

        List<EntityModelProperty> list = entityModelPropertyService.getFullPropertyByEntityModelId(entityModelId);
        // 转换vo
        List<EntityModelPropertyForFilterVO> entityModelPropertyVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 获取属性定义
            EntityModelProperty entityModelProperty = list.get(i);
            // 非库表存储属性直接跳过
            if (entityModelProperty.getDatabaseStoreFlag().equals(YesOrNoEnum.NO.name())) {
                continue;
            }

            // 获取平台展现属性的控件类型
            String dataType = entityModelProperty.getDataType();
            String widgetType = entityModelProperty.getWidgetType();
            String[] numberDataType = {"INTEGER", "LONG", "DOUBLE", "DECIMAL"};
            String renderType = "TEXT";
            String operatorKey = "Text";
            String collectionType = "";
            Boolean multiple = false;
            switch (widgetType) {
                case "TEXT":
                    if (ArrayUtils.contains(numberDataType, dataType)) {
                        renderType = "NUMBER";
                        operatorKey = "Number";
                    } else {
                        renderType = "TEXT";
                        operatorKey = "Text";
                    }
                    break;
                case "TEXTAREA":
                case "RICH_TEXT":
                    renderType = "TEXT";
                    break;
                case "DROP_DOWN_LIST":
                case "RADIO_BUTTON_GROUP":
                    renderType = "SELECT";
                    operatorKey = "DataDictionary";
                    break;

                // 组织机构
                case "ORGANIZATION_SINGLE_SELECT":
                    renderType = "CASCADER";
                    operatorKey = "Collection";
                    collectionType = "Organization";
                    multiple = true;
                    break;
                // 用户
                case "USER_SINGLE_SELECT":
                    renderType = "SELECT";
                    operatorKey = "User";
                    collectionType = "User";
                    break;

                default:
                    renderType = "";
            }
            if (StringUtils.isNotBlank(renderType)) {
                EntityModelPropertyForFilterVO vo = new EntityModelPropertyForFilterVO();
                vo.setLabel(entityModelProperty.getName());
                vo.setValue(entityModelProperty.getCode());
                vo.setRenderType(renderType);
                vo.setOperatorKey(operatorKey);
                // 数据字典，需指定字典类型
                if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.DATA_DICTIONARY.name())) {
                    vo.setDictionaryType(entityModelProperty.getDictionaryType());
                }
                // 组织机构，需指定集合类型和是否多选
                if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.ORGANIZATION_SINGLE.name())) {
                    vo.setCollectionType(collectionType);
                    vo.setMultiple(multiple);
                }

                // 用户
                if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.USER_SINGLE.name())) {
                    vo.setCollectionType(collectionType);

                }
                entityModelPropertyVOList.add(vo);
            }

        }
        return ResultUtil.success(entityModelPropertyVOList);
    }


    /**
     * 获取完整属性列表，转换为自定义查询需要的格式
     * 只读取数据库存储的属性
     *
     * @param entityModelId 实体模型id
     * @return {@link ResponseEntity}<{@link Result}>
     */
    @GetMapping("/getFullPropertyListForFilterByEntityModelCode/{entityModelCode}")
    @SystemLog(value = "实体模型属性-完整列表，转换为筛选器格式")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelProperty:query')")
    public ResponseEntity<Result> getFullPropertyListForFilterByEntityModelCode(@PathVariable String entityModelCode) {
        // 构造查询条件

        List<EntityModelProperty> list = entityModelPropertyService.getFullPropertyByEntityModelCode(entityModelCode);
        // 转换vo
        List<EntityModelPropertyForFilterVO> entityModelPropertyVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 获取属性定义
            EntityModelProperty entityModelProperty = list.get(i);
            // 非库表存储属性直接跳过
            if (entityModelProperty.getDatabaseStoreFlag().equals(YesOrNoEnum.NO.name())) {
                continue;
            }

            // 获取平台展现属性的控件类型
            String dataType = entityModelProperty.getDataType();
            String widgetType = entityModelProperty.getWidgetType();
            String[] numberDataType = {"INTEGER", "LONG", "DOUBLE", "DECIMAL"};
            String renderType = "TEXT";
            String operatorKey = "Text";
            String collectionType = "";
            Boolean multiple = false;
            switch (widgetType) {
                case "TEXT":
                    if (ArrayUtils.contains(numberDataType, dataType)) {
                        renderType = "NUMBER";
                        operatorKey = "Number";
                    } else {
                        renderType = "TEXT";
                        operatorKey = "Text";
                    }
                    break;
                case "TEXTAREA":
                case "RICH_TEXT":
                    renderType = "TEXT";
                    break;
                case "DROP_DOWN_LIST":
                case "RADIO_BUTTON_GROUP":
                    renderType = "SELECT";
                    operatorKey = "DataDictionary";
                    break;
                case "DATETIME":
                    renderType = "DATE";
                    operatorKey = "DateTime";
                    break;
                // 组织机构
                case "ORGANIZATION_SINGLE_SELECT":
                    renderType = "CASCADER";
                    operatorKey = "Collection";
                    collectionType = "Organization";
                    multiple = true;
                    break;

                default:
                    renderType = "";
            }


            if (StringUtils.isNotBlank(renderType)) {
                EntityModelPropertyForFilterVO vo = new EntityModelPropertyForFilterVO();
                vo.setLabel(entityModelProperty.getName());
                vo.setValue(entityModelProperty.getCode());
                vo.setRenderType(renderType);
                vo.setOperatorKey(operatorKey);


                // 数据字典，需指定字典类型
                if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.DATA_DICTIONARY.name())) {
                    vo.setDictionaryType(entityModelProperty.getDictionaryType());
                }
                // 组织机构，需指定集合类型和是否多选
                if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.ORGANIZATION_SINGLE.name())) {
                    vo.setCollectionType(collectionType);
                    vo.setMultiple(multiple);
                }

                entityModelPropertyVOList.add(vo);
            }

        }
        return ResultUtil.success(entityModelPropertyVOList);
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private EntityModelPropertyVO convert2VO(EntityModelProperty entity) {
        EntityModelPropertyVO vo = mapperFacade.map(entity, EntityModelPropertyVO.class);
        vo.setDataTypeName(dictionaryUtil.getNameByCode("EntityModelPropertyType", entity.getDataType()));
        vo.setNullFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getNullFlag()));
        vo.setUniqueFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getUniqueFlag()));
        vo.setMainFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getMainFlag()));
        vo.setParentPropertyFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getParentPropertyFlag()));
        vo.setDatabaseStoreFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getDatabaseStoreFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<EntityModelPropertyVO> convert2VO(List<EntityModelProperty> entityList) {
        List<EntityModelPropertyVO> voList = new ArrayList<>(entityList.size());
        entityList.stream().forEach(x -> {
            EntityModelPropertyVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private EntityModelProperty convert2Entity(EntityModelPropertyVO vo) {
        EntityModelProperty entity = mapperFacade.map(vo, EntityModelProperty.class);
        return entity;
    }

    // endregion
}