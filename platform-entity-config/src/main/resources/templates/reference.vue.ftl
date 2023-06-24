<template>
    <div class="w-full">
        <el-input v-model="displayName" disabled style="width: 152px" />
        <el-button-group>
            <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
            <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
        </el-button-group>
        <Dialog title="${table.comment!}选择" v-model="visible" class="w-150">
            <CollapseTab>
                <el-form :inline="true" :model="queryCondition" label-width="80px" @keyup.enter="query">
                    <!--查询条件区 -->
                    <#list queryConditionListReference as item>
                    <#--根据数据类型处理-->
                        <#if item.dataType=="STRING">
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <QueryText v-model="queryCondition.${item.code}" type="${item.matchRule}"
                                        <#if item.readonlyFlag=="YES">
                                            :readonly='true'
                                        </#if>
                                >
                                </QueryText>
                            </el-form-item>
                        <#elseif item.dataType=="DATETIME">
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <el-date-picker
                                        v-model="queryCondition.${item.code}BeginForQuery"
                                        <#if item.formatPattern??>
                                            :value-format="dateFormatter.getDatetimeFormat('${item.formatPattern}')"
                                            :type="dateFormatter.getDatetimeType('${item.formatPattern}')"
                                        </#if>
                                        align="right"
                                        unlink-panels
                                        placeholder="开始"

                                        <#if item.readonlyFlag=="YES">
                                            :readonly='true'
                                        </#if>
                                />
                            </el-form-item>
                            <el-form-item label="至" <#if item.showFlag=="NO">v-show="false" </#if>>
                                <el-date-picker
                                        v-model="queryCondition.${item.code}EndForQuery"
                                        <#if item.formatPattern??>
                                            :value-format="dateFormatter.getDatetimeFormat('${item.formatPattern}')"
                                            :type="dateFormatter.getDatetimeType('${item.formatPattern}')"
                                        </#if>
                                        align="right"
                                        unlink-panels
                                        placeholder="结束"

                                        <#if item.readonlyFlag=="YES">
                                            :readonly='true'
                                        </#if>
                                />
                            </el-form-item>

                        <#elseif item.dataType=="INTEGER" || item.dataType=="LONG" || item.dataType=="DOUBLE" || item.dataType=="DECIMAL" >
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <el-input v-model="queryCondition.${item.code}BeginForQuery"
                                          <#if item.readonlyFlag=="YES">:readonly='true' </#if>
                                />

                            </el-form-item>
                            <el-form-item label="至" <#if item.showFlag=="NO">v-show="false" </#if>>
                                <el-input v-model="queryCondition.${item.code}EndForQuery"
                                          <#if item.readonlyFlag=="YES">:readonly='true' </#if>
                                />
                            </el-form-item>

                        <#elseif item.dataType=="DATA_DICTIONARY">
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <#if item.widgetType=="DROP_DOWN_LIST">
                                    <dictionary-select
                                            v-model="queryCondition.${item.code}"
                                            code="${item.dictionaryType}"
                                            <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                                    />

                                <#elseif item.widgetType=="CHECK_BOX_GROUP">
                                    <dictionary-checkbox-group
                                            v-model="queryCondition.${item.code}"
                                            code="${item.dictionaryType}"
                                            <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                                    />

                                <#elseif item.widgetType=="RADIO_BUTTON_GROUP">
                                    <dictionary-radio-group
                                            v-model="queryCondition.${item.code}"
                                            code="${item.dictionaryType}"
                                            <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                                    />
                                </#if>
                            </el-form-item>
                        <#elseif item.dataType=="ENTITY">
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <${item.entityCode}Reference v-model="queryCondition.${item.code}" :${item.entityCode?uncap_first}-param="${item.entityCode?uncap_first}Param" <#if
                                item.readonlyFlag=="YES">:readonly='true'
                                </#if> />
                            </el-form-item>
                        <#else>
                            <el-form-item label="${item.name}"  <#if item.showFlag=="NO">v-show="false" </#if>>
                                <QueryText v-model="queryCondition.${item.code}" type="${item.matchRule}"
                                        <#if item.readonlyFlag=="YES">
                                            :readonly='true'
                                        </#if>
                                >
                                </QueryText>
                            </el-form-item>
                        </#if>
                    </#list>
                    <#if parentPropertyCode??>
                    <el-form-item>
                        <el-checkbox v-model="queryCondition.ignoreParent">查询全部</el-checkbox>
                    </el-form-item>
                    </#if>
                    <el-form-item style="float: right">
                        <QueryButton :page-code="pageCode" />
                    </el-form-item>
                    <div class="clearfix"></div>
                </el-form>
            </CollapseTab>
            <el-card style="width: 100%">
                <div style="margin-top: 0; margin-bottom: 10px; float: right">
                    <ColumnsController :value="columnList" :tableKey="tableKey" />
                </div>
                <el-table
                        v-loading="loading"
                        :data="tableData"
                        style="width: 100%"
                        highlight-current-row
                        border
                        @sort-change="sortChange"
                        @current-change="rowChange"
                >
                    <el-table-column
                            v-for="(item, index) in showCols"
                            :key="index"
                            :label="item.label"
                            :prop="item.prop"
                            :show-overflow-tooltip="item.showOverflowTooltip"
                            :width="item.width"
                            :formatter="item.formatFunc"
                            :sortable="item.sortable"
                    />
                </el-table>
                <ListPager
                        :page-num="pageInfo.pageNum"
                        :page-size="pageInfo.pageSize"
                        :page-total="pageTotal"
                />
            </el-card>
            <template #footer>
                <el-button type="primary" @click="confirm">确定</el-button>
                <el-button @click="close">关闭</el-button>
            </template>
        </Dialog>
    </div>
</template>

<script>
    import { referenceMixin } from '@/mixin/referenceMixin.js'
    <#list queryConditionListReference as item>
    <#if item.dataType=="ENTITY">
    import ${item.entityCode}Reference from '@/modules/${item.moduleCode}/view/${item.entityCode?uncap_first}/${mainReferenceViewMap[item.entityCode]}.vue'
    </#if>
    </#list>
    const MODULE_CODE = '${package.ModuleName}'
    const ENTITY_TYPE = '${entity?uncap_first}'
    export default {
        name: ENTITY_TYPE + '-reference',
        components: {
            <#list queryConditionListReference as item>
            <#if item.dataType=="ENTITY">
            ${item.entityCode}Reference,
            </#if>
            </#list>
        },
        mixins: [referenceMixin],
        props: {
            ${entity?uncap_first}Param: {
                type: Object,
                required: false
            }
        },
        data() {
            return {
                entityType: ENTITY_TYPE,
                moduleCode: MODULE_CODE,
                // eslint-disable-next-line no-eval
                api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
                pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
                <#list queryConditionListReference as item>
                <#if item.dataType=="ENTITY">
                // ${item.name}组件参数，用于传递数据
               ${item.entityCode?uncap_first}Param: {},
                </#if>
                </#list>
                <#if existOrderNo=="NO">
                // 排序信息
                sortInfo: {
                    sort_field: 'id',
                    sort_sortType: 'descending'
                },
                </#if>
                columnList: [
                    <#list queryResultListReference as field>
                    {
                        <#if field.dataType=="DATA_DICTIONARY" || field.dataType=="ENTITY">
                        prop: '${field.code}Name',
                        <#else>
                        prop: '${field.code}',
                        </#if>
                        label: '${field.name}'
                        <#if field.showFlag=="YES">
                        , show: true
                        <#else>
                        , show: false
                        </#if>
                        <#if field.showOverflowTooltipFlag=="YES">
                        , showOverflowTooltip: true
                        <#else>
                        , showOverflowTooltip: false
                        </#if>
                        <#if field.width??>
                        , width: '${field.width}'
                        </#if>
                        <#if field.formatFunction??>
                        , formatFunction: '${field.formatFunction}'
                        </#if>
                        <#if field.sortableFlag=="YES" && field.dataType!="DATA_DICTIONARY">
                        , sortable: true
                        </#if>
                    }<#if field_has_next>, </#if>
                    </#list>
                ],
                queryCondition: {
                    //默认值处理
                    <#list queryConditionListReference as item>
                    <#if item.defaultValue??>
                    <#if item.dataType=="STRING" || item.dataType=="DATA_DICTIONARY">
                    ${item.code}: '${item.defaultValue}'<#if item_has_next>,</#if>
                    </#if>
                    </#if>
                    </#list>

                },
                // 名称键值
                nameKey: '${mainPropertyCode}'
            }
        },
        methods: {
            <#if referenceEntityView.beforeInit?? && referenceEntityView.beforeInit!="">
            beforeInit(param){
                ${referenceEntityView.beforeInit}
            },
            </#if>
            <#if referenceEntityView.afterInit?? && referenceEntityView.afterInit!="">
            afterInit(param){
                ${referenceEntityView.afterInit}
            },
            </#if>
            <#if referenceEntityView.commonParamChange?? && referenceEntityView.commonParamChange!="">
            commonParamChange(param){
                ${referenceEntityView.commonParamChange}
            },
            </#if>
        }
    }
</script>

<style></style>