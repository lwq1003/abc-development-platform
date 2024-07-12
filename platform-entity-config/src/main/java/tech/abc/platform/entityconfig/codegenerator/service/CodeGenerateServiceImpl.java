package tech.abc.platform.entityconfig.codegenerator.service;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.base.BaseEntity;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.codegenerator.constant.FieldConstant;
import tech.abc.platform.entityconfig.codegenerator.entity.TableFieldInfo;
import tech.abc.platform.entityconfig.codegenerator.extention.MyFreemarkerTemplateEngine;
import tech.abc.platform.entityconfig.constant.EntityConfigConstant;
import tech.abc.platform.entityconfig.entity.*;
import tech.abc.platform.entityconfig.enums.EntityModelPropertyTypeEnum;
import tech.abc.platform.entityconfig.enums.EntityViewTypeEnum;
import tech.abc.platform.entityconfig.enums.ModelCodeEnum;
import tech.abc.platform.entityconfig.enums.ViewButtonTypeEnum;
import tech.abc.platform.entityconfig.exception.EntityException;
import tech.abc.platform.entityconfig.exception.EntityModelException;
import tech.abc.platform.entityconfig.service.*;
import tech.abc.platform.system.entity.Module;
import tech.abc.platform.system.service.ModuleService;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 代码生成服务实现
 *
 * @author wqliu
 * @date 2022-11-1
 */
@Service
@Slf4j
public class CodeGenerateServiceImpl implements CodeGenerateService {


    /**
     * 排序号属性编码
     */
    public static final String ORDER_NO = "orderNo";
    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityModelService entityModelService;

    @Autowired
    private EntityModelPropertyService entityModelPropertyService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private ViewButtonService viewButtonService;

    @Autowired
    private ViewQueryConditionService viewQueryConditionService;

    @Autowired
    private ViewQueryResultService viewQueryResultService;

    @Autowired
    private ViewPropertyService viewPropertyService;


    /**
     * 视图文件夹名称
     */
    private String viewFolderName = "";


    @Override
    public void generateCode(String entityCode) {


        // 获取实体配置信息
        Entity entity = entityService.getByCode(entityCode);

        // 获取模型列表
        List<EntityModel> entityModelList = entityModelService.getByEntityId(entity.getId());
        // 未找到模型，抛出异常
        if (CollectionUtils.isEmpty(entityModelList)) {
            throw new CustomException(EntityException.MODEL_NOT_FOUND, entity.getName());
        }

        for (EntityModel entityModel : entityModelList) {
            // 创建代码生成器对象
            AutoGenerator codeGenerator = getCodegGenerator();

            // 获取模块配置信息
            Module module = moduleService.query(entity.getModule());


            // 配置全局信息
            configGlobal(codeGenerator, entity.getAuthor());

            // 配置包
            configPackage(codeGenerator, module.getPackagePath(), module.getCode());

            // 配置模板
            configTemplate(codeGenerator);


            // 配置注入
            configInjection(codeGenerator, entity, module.getApp().toLowerCase(), module.getCode(), entityModel);

            // 策略配置
            configStrategy(codeGenerator, entity.getCode(), module.getAbbreviation(), entityModel);

            // 使用Freemarker替代默认的Velocity模板引擎
            MyFreemarkerTemplateEngine freemarkerTemplateEngine = new MyFreemarkerTemplateEngine();

            // 生成代码
            codeGenerator.execute(freemarkerTemplateEngine);
        }

    }


    @Override
    public void generateTable(String entityCode) {
        // 获取实体配置信息
        Entity entity = entityService.getByCode(entityCode);
        // 获取模块配置信息
        Module module = moduleService.query(entity.getModule());


        // 获取模型列表
        List<EntityModel> entityModelList = entityModelService.getByEntityId(entity.getId());
        // 未找到模型，抛出异常
        if (CollectionUtils.isEmpty(entityModelList)) {
            throw new CustomException(EntityException.MODEL_NOT_FOUND, entity.getName());
        }
        // 遍历列表
        entityModelList.stream().forEach((entityModel) -> {

            // 获取自身标识
            String entityModelId = entityModel.getId();
            // 获取库表名称
            String tableName = generateTableName(entityModel.getCode(), module.getAbbreviation());
            // 获取库表备注
            String tableComment = entityModel.getName();
            // 获取父级模型属性列表
            List<EntityModelProperty> parentModelPropertyList = new ArrayList<>();
            // 循环向上查找父级模型
            while (true) {
                entityModel = entityModelService.query(entityModel.getParentModel());
                // 获取父级模型属性列表
                List<EntityModelProperty> currentPropertyList = entityModelPropertyService.getDatabaseStoreListByEntityModelId(entityModel.getId());
                CollectionUtils.addAll(parentModelPropertyList, currentPropertyList.iterator());

                // 找到顶层节点标识模型停止
                if (entityModel.getId().equals(EntityConfigConstant.ID_MODEL_ID)) {
                    break;
                }

            }
            ;

            // 获取实体模型属性列表,非库表存储字段忽略
            List<EntityModelProperty> entityModelPropertyList = entityModelPropertyService.getDatabaseStoreListByEntityModelId(entityModelId);
            CollectionUtils.addAll(entityModelPropertyList, parentModelPropertyList.iterator());
            // 将实体模型属性转换为库表字段信息
            List<TableFieldInfo> tableFieldInfoList = convertModelPropertyData(entityModelPropertyList);

            entityModelService.createTable(tableName, tableComment, tableFieldInfoList);

        });


    }

    private List<TableFieldInfo> convertModelPropertyData(List<EntityModelProperty> entityModelPropertyList) {
        List<TableFieldInfo> tableFieldInfoList = new ArrayList<>();
        entityModelPropertyList.stream().forEach(modelProperty -> {

            TableFieldInfo tableFieldInfo = new TableFieldInfo();
            String fieldCode = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelProperty.getCode());
            tableFieldInfo.setCode(fieldCode);
            tableFieldInfo.setDataType(generateDatabaseDataType(modelProperty));

            String nullFlag = "";
            if (modelProperty.getNullFlag().equals(YesOrNoEnum.NO.name())) {
                nullFlag = "not null";
            }
            tableFieldInfo.setNullFlag(nullFlag);
            tableFieldInfo.setName(modelProperty.getName());

            // 库表默认值设定，实际不再使用
            String defaultValue = "";
            if (StringUtils.isNotBlank(modelProperty.getDefaultValue())) {
                defaultValue = "DEFAULT '" + modelProperty.getDefaultValue() + "'";
            }
            tableFieldInfo.setDefaultValue(defaultValue);

            tableFieldInfoList.add(tableFieldInfo);
        });
        return tableFieldInfoList;
    }

    /**
     * 生成库表的数据类型
     *
     * @param modelProperty 模型属性
     * @return {@link String}
     */
    private String generateDatabaseDataType(EntityModelProperty modelProperty) {
        String result = StringUtils.EMPTY;
        Integer maxLength = modelProperty.getMaxLength();
        Integer decimalLength = modelProperty.getDecimalLength();
        EntityModelPropertyTypeEnum dataType = EnumUtils.getEnum(EntityModelPropertyTypeEnum.class, modelProperty.getDataType());
        switch (dataType) {
            case STRING:
                result = "VARCHAR(" + maxLength + ")";
                break;
            case INTEGER:
                result = "INT";
                break;
            case LONG:
                result = "BIGINT";
                break;
            case DOUBLE:
                result = "DOUBLE";
                break;
            case DATETIME:
                result = "DATETIME";
                break;
            case DECIMAL:
                result = "DECIMAL(" + maxLength + "," + decimalLength + ")";
                break;
            default:
                result = "VARCHAR(" + maxLength + ")";
        }

        return result;

    }

    private void configStrategy(AutoGenerator codeGenerator, String entityCode, String moduleAbbreviation, EntityModel entityModel) {

        String tablePrefix = moduleAbbreviation + "_";


        String tableName = generateTableName(entityModel.getCode(), moduleAbbreviation);

        StrategyConfig.Builder builder = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude(tableName)
                .addTablePrefix(tablePrefix);

        // 配置实体策略
        configEntityStrategy(builder);

        // 配置Mapper策略
        configMapperStrategy(builder);


        // 配置服务策略
        configServiceStrategy(builder);


        // 配置控制器策略
        configControllerStrategy(builder);


        StrategyConfig strategyConfig = builder.build();
        codeGenerator.strategy(strategyConfig);
    }

    @NotNull
    private static String generateTableName(String entityCode, String moduleAbbreviation) {

        return moduleAbbreviation.toLowerCase() + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityCode);
    }


    private void configEntityStrategy(StrategyConfig.Builder strategyConfigBuilder) {
        com.baomidou.mybatisplus.generator.config.builder.Entity.Builder builder = strategyConfigBuilder.entityBuilder();
        builder.superClass(BaseEntity.class)
                .enableFileOverride()
                .disableSerialVersionUID()
                .enableLombok()
                .enableTableFieldAnnotation()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addTableFills(getAutoFillField())
                .addSuperEntityColumns("id,create_id,update_id,create_time,update_time,version,delete_flag")
                .idType(IdType.ASSIGN_ID)
                .build();

    }

    private void configMapperStrategy(StrategyConfig.Builder strategyConfigBuilder) {
        Mapper.Builder builder = strategyConfigBuilder.mapperBuilder();
        builder.enableFileOverride()
                // .enableBaseResultMap()
                // .enableBaseColumnList()
                .build();

    }

    private void configServiceStrategy(StrategyConfig.Builder strategyConfigBuilder) {
        com.baomidou.mybatisplus.generator.config.builder.Service.Builder builder = strategyConfigBuilder.serviceBuilder();
        builder.superServiceClass(BaseService.class)
                .superServiceImplClass(BaseServiceImpl.class)
                .enableFileOverride()
                .formatServiceFileName("%sService")

                .build();

    }

    private void configControllerStrategy(StrategyConfig.Builder strategyConfigBuilder) {
        Controller.Builder builder = strategyConfigBuilder.controllerBuilder();
        builder.superClass(BaseController.class)
                .enableFileOverride()
                .enableRestStyle()
                .build();

    }


    /**
     * 获取自动填充字段
     *
     * @return 自动填充字段列表
     */
    private List<IFill> getAutoFillField() {
        // 自定义需要填充的字段
        List<IFill> tableFillList = new ArrayList<IFill>();
        // 创建时间
        IFill createField = new Column(FieldConstant.CREATE_TIME, FieldFill.INSERT);
        tableFillList.add(createField);
        // 更新时间

        IFill modifiedField = new Column(FieldConstant.UPDATE_TIME, FieldFill.INSERT_UPDATE);
        tableFillList.add(modifiedField);

        // 创建人
        IFill creatorField = new Column(FieldConstant.CREATE_ID, FieldFill.INSERT);
        tableFillList.add(creatorField);

        // 更新人
        IFill updaterField = new Column(FieldConstant.UPDATE_ID, FieldFill.INSERT_UPDATE);
        tableFillList.add(updaterField);

        // 版本号，插入时设置默认值
        IFill versionField = new Column(FieldConstant.VERSION, FieldFill.INSERT);
        tableFillList.add(versionField);

        // 删除标志位，插入时设置默认值
        IFill deleteFlagField = new Column(FieldConstant.DELETE_FLAG, FieldFill.INSERT);
        tableFillList.add(deleteFlagField);

        return tableFillList;
    }

    // region 自定义模板部分

    /**
     * 生成视图对象
     *
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateVO(Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {

        // 注入自定义变量
        // vo是否使用lombok模型
        customKeyValue.put("voLombokModel", true);


        // 自定义视图对象模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("VO.java")
                .templatePath("/templates/vo.java.ftl")
                .enableFileOverride()
                .packageName("vo")
                .build();

        builder.customFile(templateFile);
    }


    /**
     * 生成列表视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateListView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {
        // 视图对象放入自定义键值map
        customKeyValue.put("listEntityView", entityView);

        // 获取按钮配置
        List<ViewButton> pageButtonList = viewButtonService.listByViewAndType(entityView.getId(), ViewButtonTypeEnum.LIST_PAGE.name(),
                YesOrNoEnum.NO.name());
        customKeyValue.put("pageButtonList", pageButtonList);

        List<ViewButton> pageMoreButtonList = viewButtonService.listByViewAndType(entityView.getId(), ViewButtonTypeEnum.LIST_PAGE.name(),
                YesOrNoEnum.YES.name());
        customKeyValue.put("pageMoreButtonList", pageMoreButtonList);

        List<ViewButton> rowButtonList = viewButtonService.listByViewAndType(entityView.getId(), ViewButtonTypeEnum.LIST_ROW.name(),
                YesOrNoEnum.NO.name());
        customKeyValue.put("rowButtonList", rowButtonList);

        List<ViewButton> rowMoreButtonList = viewButtonService.listByViewAndType(entityView.getId(), ViewButtonTypeEnum.LIST_ROW.name(),
                YesOrNoEnum.YES.name());
        customKeyValue.put("rowMoreButtonList", rowMoreButtonList);
        // 获取查询条件配置
        List<ViewQueryCondition> queryConditionList = viewQueryConditionService.listByView(entityView.getId());
        customKeyValue.put("queryConditionList", queryConditionList);
        // 查询条件中如有日期和数值类，则在vo中需生成对应的属性BeginForQuery和EndForQuery
        Set<ViewQueryCondition> voRangeSet = queryConditionList.stream().filter(y -> {
                    EntityModelPropertyTypeEnum dataTypeEnum = EnumUtils.getEnum(EntityModelPropertyTypeEnum.class, y.getDataType());
                    if (dataTypeEnum.equals(EntityModelPropertyTypeEnum.INTEGER) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.LONG) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DOUBLE) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DECIMAL) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DATETIME)) {
                        return true;

                    } else {
                        return false;
                    }
                }

        ).collect(Collectors.toSet());
        customKeyValue.put("voRangeSet", voRangeSet);


        // 获取查询结果配置
        List<ViewQueryResult> queryResultList = viewQueryResultService.listByView(entityView.getId());
        customKeyValue.put("queryResultList", queryResultList);

        // 查询结果中有设置格式化方法
        if (queryResultList.stream().anyMatch(x -> StringUtils.isNotBlank(x.getFormatFunction()))) {
            customKeyValue.put("existFormatMethod", YesOrNoEnum.YES.name());
        } else {
            customKeyValue.put("existFormatMethod", YesOrNoEnum.NO.name());
        }

        // 自定义列表视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("list.vue")
                .templatePath("/templates/list.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();

        builder.customFile(templateFile);
    }

    /**
     * 生成新增视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateAddView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {

        // 视图对象放入自定义键值map
        customKeyValue.put("addEntityView", entityView);
        // 新增视图存在标识位
        customKeyValue.put("addViewFlag", YesOrNoEnum.YES.name());
        // 模板路径
        String templatePath = "";
        if (entityView.getEnableAdvanceConfig().equals(YesOrNoEnum.NO.name())) {
            // 标准配置模式
            templatePath = "/templates/add.vue.ftl";
        } else {
            // 高级配置模式
            templatePath = "/templates/addForAdvanceConfig.vue.ftl";
        }

        // 获取视图属性配置
        List<ViewProperty> viewPropertyList = viewPropertyService.listByView(entityView.getId());
        customKeyValue.put("addViewPropertyList", viewPropertyList);


        // 自定义新增视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("add.vue")
                .templatePath(templatePath)
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    /**
     * 生成修改视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateModifyView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {
        // 视图对象放入自定义键值map
        customKeyValue.put("modifyEntityView", entityView);
        // 修改视图存在标识位
        customKeyValue.put("modifyViewFlag", YesOrNoEnum.YES.name());
        // 模板路径
        String templatePath = "";
        if (entityView.getEnableAdvanceConfig().equals(YesOrNoEnum.NO.name())) {
            // 标准配置模式
            templatePath = "/templates/modify.vue.ftl";
        } else {
            // 高级配置模式
            templatePath = "/templates/modifyForAdvanceConfig.vue.ftl";
        }
        // 获取视图属性配置
        List<ViewProperty> viewPropertyList = viewPropertyService.listByView(entityView.getId());
        customKeyValue.put("modifyViewPropertyList", viewPropertyList);
        // 自定义编辑视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("modify.vue")
                .templatePath(templatePath)
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }


    /**
     * 生成查看视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateViewView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {
        // 视图对象放入自定义键值map
        customKeyValue.put("viewEntityView", entityView);
        // 查看视图存在标识位
        customKeyValue.put("viewViewFlag", YesOrNoEnum.YES.name());
        // 模板路径
        String templatePath = "";
        if (entityView.getEnableAdvanceConfig().equals(YesOrNoEnum.NO.name())) {
            // 标准配置模式
            templatePath = "/templates/view.vue.ftl";
        } else {
            // 高级配置模式
            templatePath = "/templates/viewForAdvanceConfig.vue.ftl";
        }

        // 获取视图属性配置
        List<ViewProperty> viewPropertyList = viewPropertyService.listByView(entityView.getId());
        customKeyValue.put("viewViewPropertyList", viewPropertyList);


        // 自定义查看视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("view.vue")
                .templatePath(templatePath)
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    /**
     * 生成参照视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateReferenceView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {

        // 视图对象放入自定义键值map
        customKeyValue.put("referenceEntityView", entityView);
        // 获取查询条件配置
        List<ViewQueryCondition> queryConditionList = viewQueryConditionService.listByView(entityView.getId());
        customKeyValue.put("queryConditionListReference", queryConditionList);
        // 查询条件中如有日期和数值类，则在vo中需生成对应的属性BeginForQuery和EndForQuery
        Set<ViewQueryCondition> voRangeSet = queryConditionList.stream().filter(y -> {
                    EntityModelPropertyTypeEnum dataTypeEnum = EnumUtils.getEnum(EntityModelPropertyTypeEnum.class, y.getDataType());
                    if (dataTypeEnum.equals(EntityModelPropertyTypeEnum.INTEGER) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.LONG) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DOUBLE) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DECIMAL) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DATETIME)) {
                        return true;

                    } else {
                        return false;
                    }
                }

        ).collect(Collectors.toSet());
        // 合并列表视图和参照视图中的范围查询条件，生成变量，用于设置vo对象中的范围查询属性
        Object voRangeSetObject = customKeyValue.get("voRangeSet");
        if (voRangeSetObject != null) {
            Set<ViewQueryCondition> voRangeCollection = (Set<ViewQueryCondition>) voRangeSetObject;
            voRangeCollection.addAll(voRangeSet);
            customKeyValue.put("voRangeSet", voRangeCollection);
        } else {
            customKeyValue.put("voRangeSet", voRangeSet);
        }

        // 获取查询结果配置
        List<ViewQueryResult> queryResultList = viewQueryResultService.listByView(entityView.getId());
        customKeyValue.put("queryResultListReference", queryResultList);


        // 自定义参照视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("reference.vue")
                .templatePath("/templates/reference.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();

        builder.customFile(templateFile);
    }

    /**
     * 生成树表参照视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateTreeListReferenceView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {

        // 视图对象放入自定义键值map
        customKeyValue.put("treeListReferenceEntityView", entityView);
        // 获取查询条件配置
        List<ViewQueryCondition> queryConditionList = viewQueryConditionService.listByView(entityView.getId());
        customKeyValue.put("queryConditionListTreeListReference", queryConditionList);
        // 查询条件中如有日期和数值类，则在vo中需生成对应的属性BeginForQuery和EndForQuery
        Set<ViewQueryCondition> voRangeSet = queryConditionList.stream().filter(y -> {
                    EntityModelPropertyTypeEnum dataTypeEnum = EnumUtils.getEnum(EntityModelPropertyTypeEnum.class, y.getDataType());
                    if (dataTypeEnum.equals(EntityModelPropertyTypeEnum.INTEGER) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.LONG) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DOUBLE) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DECIMAL) ||
                            dataTypeEnum.equals(EntityModelPropertyTypeEnum.DATETIME)) {
                        return true;

                    } else {
                        return false;
                    }
                }

        ).collect(Collectors.toSet());
        // 合并列表视图和参照视图中的范围查询条件，生成变量，用于设置vo对象中的范围查询属性
        Object voRangeSetObject = customKeyValue.get("voRangeSet");
        if (voRangeSetObject != null) {
            Set<ViewQueryCondition> voRangeCollection = (Set<ViewQueryCondition>) voRangeSetObject;
            voRangeCollection.addAll(voRangeSet);
            customKeyValue.put("voRangeSet", voRangeCollection);
        } else {
            customKeyValue.put("voRangeSet", voRangeSet);
        }

        // 获取查询结果配置
        List<ViewQueryResult> queryResultList = viewQueryResultService.listByView(entityView.getId());
        customKeyValue.put("queryResultListTreeListReference", queryResultList);


        // 自定义参照视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("treeListReference.vue")
                .templatePath("/templates/treeListReference.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();

        builder.customFile(templateFile);
    }


    /**
     * 生成树视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateTreeView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {


        // 自定义树视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("tree.vue")
                .templatePath("/templates/tree.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    /**
     * 生成树表视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateTreeListView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {

        // 视图对象放入自定义键值map
        customKeyValue.put("treeListEntityView", entityView);
        // 自定义树表视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("treeList.vue")
                .templatePath("/templates/treeList.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    /**
     * 生成树参照视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateTreeReferenceView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {


        // 自定义树视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("treeReference.vue")
                .templatePath("/templates/treeReference.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    /**
     * 生成树多选参照视图
     *
     * @param entityView     实体视图
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void generateTreeMultipleReferenceView(EntityView entityView, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {


        // 自定义树视图模板
        CustomFile templateFile = new CustomFile.Builder()
                .fileName("treeMultipleReference.vue")
                .templatePath("/templates/treeMultipleReference.vue.ftl")
                .enableFileOverride()
                .packageName(viewFolderName)
                .build();
        builder.customFile(templateFile);

    }

    //#endregion


    private void configInjection(AutoGenerator codeGenerator, Entity entity, String appCode, String moduleCode, EntityModel entityModel) {


        // 设置自定义变量
        Map<String, Object> customKeyValue = new HashMap<>(5);
        // 公共变量处理
        // 注入应用编码
        customKeyValue.put("appCode", appCode);
        // 设置实体和视图模型基类变量
        String baseEntity = StringUtils.EMPTY;
        String baseVO = StringUtils.EMPTY;
        EntityModel baseModel = entityModelService.getById(entityModel.getParentModel());
        String baseModelCode = baseModel.getCode();
        ModelCodeEnum modelCodeEnum = EnumUtils.getEnum(ModelCodeEnum.class, baseModelCode);
        if (modelCodeEnum == null) {
            throw new CustomException(EntityModelException.PARENT_MODE_NOT_CONFIG, baseModelCode);
        }
        switch (modelCodeEnum) {
            case ID_MODEL:
                // 标识模型
                baseEntity = "BaseIdEntity";
                baseVO = "BaseIdVO";
                break;
            case MAP_MODEL:
                // 映射模型
                baseEntity = "BaseMapEntity";
                baseVO = "BaseMapVO";
                break;
            case BUSINESS_MODEL:
                // 业务模型
                baseEntity = "BaseEntity";
                baseVO = "BaseVO";
                break;
            case BUSINESS_TENANT_MODEL:
                // 业务租户模型
                baseEntity = "BaseTenantEntity";
                baseVO = "BaseTenantVO";
                break;
            case FLOW_BILL:
                // 流程表单
                baseEntity = "BaseFlowBill";
                baseVO = "BaseFlowBillVO";
                break;
            default:
                break;

        }
        // 注入实体基类
        customKeyValue.put("baseEntity", baseEntity);
        // 注入视图对象基类
        customKeyValue.put("baseVO", baseVO);


        // 设置视图文件夹名称
        viewFolderName = "0view/" + moduleCode + "/" + StringUtils.uncapitalize(entity.getCode());
        InjectionConfig.Builder builder = new InjectionConfig.Builder();

        // 生成视图对象
        generateVO(customKeyValue, builder);

        // 视图处理
        // 设置实体模型是否为自关联变量
        customKeyValue.put("entityModelSelfReferenceFlag", entityModel.getSelfReferenceFlag());

        // 获取实体模型属性列表
        List<EntityModelProperty> entityModelPropertyList = entityModelPropertyService.getByEntityModelId(entityModel.getId());
        customKeyValue.put("entityModelPropertyList", entityModelPropertyList);

        // 获取库存储的实体模型属性列表
        List<EntityModelProperty> databaseStoreEntityModelPropertyList =
                entityModelPropertyService.getDatabaseStoreListByEntityModelId(entityModel.getId());
        customKeyValue.put("databaseStoreEntityModelPropertyList", databaseStoreEntityModelPropertyList);


        // 获取非库存储的实体模型属性列表
        List<EntityModelProperty> noDatabaseStoreEntityModelPropertyList = entityModelPropertyService.getNoDatabaseStoreListByEntityModelId(entityModel.getId());
        customKeyValue.put("noDatabaseStoreEntityModelPropertyList", noDatabaseStoreEntityModelPropertyList);

        // 默认设置不存在排序属性orderNo
        customKeyValue.put("existOrderNo", YesOrNoEnum.NO.name());
        entityModelPropertyList.forEach(x -> {
            // 设置主属性编码
            if (x.getMainFlag().equals(YesOrNoEnum.YES.name())) {
                customKeyValue.put("mainPropertyCode", x.getCode());
            }
            // 设置上级属性编码
            if (x.getParentPropertyFlag().equals(YesOrNoEnum.YES.name())) {
                customKeyValue.put("parentPropertyCode", x.getCode());
            }

            // 属性中有orderNo属性
            if (ORDER_NO.equals(x.getCode())) {
                customKeyValue.put("existOrderNo", YesOrNoEnum.YES.name());
            }


            // 设置关联实体主参照视图
            if (x.getDataType().equals(EntityModelPropertyTypeEnum.ENTITY.name())) {
                String entityCode = StringUtils.capitalize(x.getEntityCode());
                String mainReferenceViewCode = entityViewService.getMainReferenceViewCode(entityCode);
                Object mainReferenceViewMap = customKeyValue.get("mainReferenceViewMap");
                Map<String, String> mainReferenceView = new HashMap<>(5);
                if (mainReferenceViewMap != null) {
                    mainReferenceView = (HashMap<String, String>) mainReferenceViewMap;
                }
                mainReferenceView.put(x.getEntityCode(), mainReferenceViewCode);
                customKeyValue.put("mainReferenceViewMap", mainReferenceView);
                return;
            }
        });

        handleView(entityModel, customKeyValue, builder);

        InjectionConfig injectionConfig = builder
                // 自定义变量注入
                .customMap(customKeyValue)
                .build();
        codeGenerator.injection(injectionConfig);
    }

    /**
     * 处理视图
     *
     * @param entityModel    实体模型
     * @param customKeyValue 自定义键值
     * @param builder        构建器
     */
    private void handleView(EntityModel entityModel, Map<String, Object> customKeyValue, InjectionConfig.Builder builder) {
        // 获取所有视图
        List<EntityView> entityViewList = entityViewService.getByModelId(entityModel.getId());
        // 获取主视图
        Optional<EntityView> mainView = entityViewList.stream().filter(x -> x.getMainViewFlag().equals(YesOrNoEnum.YES.name())).findFirst();
        if (mainView.isPresent()) {
            customKeyValue.put("mainViewCode", mainView.get().getCode());
        } else {
            // 此处不抛出异常是因为部分关系对应表无视图，设置为空，避免代码生成时报错
            customKeyValue.put("mainViewCode", "");
        }


        // 遍历视图
        entityViewList.stream().forEach(entityView -> {
            EntityViewTypeEnum entityViewTypeEnum = EnumUtils.getEnum(EntityViewTypeEnum.class, entityView.getEntityViewType(), EntityViewTypeEnum.LIST);
            switch (entityViewTypeEnum) {
                case ADD:
                    // 新增视图
                    generateAddView(entityView, customKeyValue, builder);
                    break;
                case MODIFY:
                    // 修改视图
                    generateModifyView(entityView, customKeyValue, builder);
                    break;
                case VIEW:
                    // 查看视图
                    generateViewView(entityView, customKeyValue, builder);
                    break;
                case LIST:
                    // 列表视图
                    generateListView(entityView, customKeyValue, builder);
                    break;
                case REFERENCE:
                    // 参照视图
                    generateReferenceView(entityView, customKeyValue, builder);
                    break;
                case TREE:
                    // 树视图
                    generateTreeView(entityView, customKeyValue, builder);
                    break;
                case TREE_LIST:
                    // 树表视图
                    generateTreeListView(entityView, customKeyValue, builder);
                    break;
                case TREE_LIST_REFERENCE:
                    // 树表参照视图
                    generateTreeListReferenceView(entityView, customKeyValue, builder);
                    break;
                case TREE_REFERENCE:
                    // 树视图
                    generateTreeReferenceView(entityView, customKeyValue, builder);
                    break;
                case TREE_MULTIPLE_REFERENCE:
                    // 树多选参照视图
                    generateTreeMultipleReferenceView(entityView, customKeyValue, builder);
                    break;
                default:
                    break;
            }

        });
    }

    private void configTemplate(AutoGenerator codeGenerator) {
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .controller("/templates/controller.java")
                .build();
        codeGenerator.template(templateConfig);
    }

    private void configPackage(AutoGenerator codeGenerator, String parentPackage, String moduleCode) {
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent(parentPackage)
                .moduleName(moduleCode)
                // 实体类的包名
                .entity("entity")
                // 服务类的包名
                .service("service")
                // 服务实现类的包名
                .serviceImpl("service.impl")
                // Mybatis的mapper接口包名
                .mapper("mapper")
                // Mybatis的xml包名
                .xml("mapper")
                // 控制器类的包名
                .controller("controller")
                .build();
        codeGenerator.packageInfo(packageConfig);
    }

    /**
     * 配置全局信息
     *
     * @param codeGenerator 代码生成器
     */
    private void configGlobal(AutoGenerator codeGenerator, String author) {
        // 定义输出路径
        String outputDir = System.getProperty("user.dir") + "/output";
        log.info("代码输出路径：{}", outputDir);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                // 指定输出路径
                .outputDir(outputDir)
                // 生成代码结束后，禁止自动打开目录
                .disableOpenDir()
                // 设置代码编写人
                .author(author)
                // 启用Swagger
                .enableSwagger()
                // 定义日期类型为java8的LocalDateTime
                .dateType(DateType.TIME_PACK)
                // 设置注释日期格式
                .commentDate("yyyy-MM-dd")
                .build();

        codeGenerator.global(globalConfig);
    }

    @NotNull
    private AutoGenerator getCodegGenerator() {

        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(dataSource)
                .databaseQueryClass(SQLQuery.class)
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();
        // 实例化代码生成器
        AutoGenerator codeGenerator = new AutoGenerator(dataSourceConfig);
        return codeGenerator;
    }
}
