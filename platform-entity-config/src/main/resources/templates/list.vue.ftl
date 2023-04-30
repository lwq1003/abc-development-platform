<template>
    <ContentWrap>
        <CollapseTab>
            <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
                <!--查询条件区 -->
                <#list queryConditionList as item>
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
                                        multiple
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
                            <${item.code?cap_first}Reference v-model="queryCondition.${item.code}" :${item.code}-param="${item.code}Param" <#if item.readonlyFlag=="YES">:readonly='true'
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
        <div class="mb-10px mt-10px">
            <#list pageButtonList as pageButton>
                <el-button
                        <#if pageButton.permissionFlag=="YES">
                            v-permission="pageCode+'${pageButton.permissionCode}'"
                        </#if>
                        type="primary"
                        <#if pageButton.icon??>
                            icon="${pageButton.icon}"
                        </#if>
                        @click="${pageButton.code}"
                >${pageButton.name}</el-button>
            </#list>
            <#if pageMoreButtonList?? && (pageMoreButtonList?size > 0)>
                <el-dropdown class="ml-10px">
                    <el-button type="primary" icon="more">
                        更多
                        <el-icon class="el-icon--right"><arrow-down /></el-icon>
                    </el-button>
                    <template #dropdown>
                        <el-dropdown-menu>
                              <#list pageMoreButtonList as pageButton>
                            <el-dropdown-item>
                                <el-button text
                                        <#if pageButton.permissionFlag=="YES">
                                            v-permission="pageCode+'${pageButton.permissionCode}'"
                                        </#if>
                                        <#if pageButton.icon??>
                                            icon="${pageButton.icon}"
                                        </#if>
                                        @click="${pageButton.code}"
                                >${pageButton.name}</el-button>
                            </el-dropdown-item>
                              </#list>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </#if>
        </div>

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
                    @selection-change="selectionChange"
                    @row-dblclick="rowDoubleClick"
            >
                <el-table-column type="selection" width="55" />
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
                <#if rowButtonList?? && (rowButtonList?size > 0)>
                <el-table-column fixed="right" label="操作" width="320">
                    <template #default="scope">
                        <#list rowButtonList as rowButton>
                            <el-button
                                    <#if rowButton.permissionFlag=="YES">
                                        v-permission="pageCode+'${rowButton.permissionCode}'"
                                    </#if>
                                    type="primary"
                                    @click="${rowButton.code}(scope.row)"
                            >${rowButton.name}</el-button>
                        </#list>
                        <#if rowMoreButtonList?? && (rowMoreButtonList?size > 0)>
                            <el-dropdown class="ml-10px">
                                <el-button type="primary">
                                    更多
                                    <el-icon class="el-icon--right"><arrow-down /></el-icon>
                                </el-button>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                    <#list rowMoreButtonList as rowButton>
                                        <el-dropdown-item>
                                            <el-button text
                                                    <#if rowButton.permissionFlag=="YES">
                                                        v-permission="pageCode+'${rowButton.permissionCode}'"
                                                    </#if>
                                                    @click="${rowButton.code}(scope.row)"
                                            >${rowButton.name}</el-button>
                                        </el-dropdown-item>
                                    </#list>
                                </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </#if>
                    </template>
                </el-table-column>
                </#if>
            </el-table>
            <ListPager
                    :page-num="pageInfo.pageNum"
                    :page-size="pageInfo.pageSize"
                    :page-total="pageTotal"
            />
        </el-card>

        <AddPage ref="addPage" @refresh="refresh" />
        <ModifyPage ref="modifyPage" @refresh="refresh" />
        <ViewPage ref="viewPage" />
    </ContentWrap>
</template>

<script lang="ts">
    import {listMixin} from '@/mixin/listMixin.js'
    import { formatDate, formatTime } from '@/utils/TableColumnFormatter.js'
    import AddPage from './add.vue'
    import ModifyPage from './modify.vue'
    import ViewPage from './view.vue'
    <#list queryConditionList as item>
    <#if item.dataType=="ENTITY">
    import ${item.code?cap_first}Reference from '@/modules/${package.ModuleName}/view/${item.code}/${mainReferenceViewMap[item.code]}.vue'
    </#if>
    </#list>
    const MODULE_CODE = '${package.ModuleName}'
    const ENTITY_TYPE = '${entity?uncap_first}'
    export default {
        mixins: [listMixin],
        components:{
            AddPage,
            ModifyPage,
            ViewPage,
            <#list queryConditionList as item>
            <#if item.dataType=="ENTITY">
            ${item.code?cap_first}Reference,
            </#if>
            </#list>
        },
        data() {
            return {
                name: ENTITY_TYPE + '-list',
                entityType: ENTITY_TYPE,
                moduleCode: MODULE_CODE,
                // eslint-disable-next-line no-eval
                api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
                pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
                <#list queryConditionList as item>
                <#if item.dataType=="ENTITY">
                // ${item.name}组件参数，用于传递数据
                ${item.code}Param: {},
                </#if>
                </#list>
                columnList: [
                    <#list queryResultList as field>
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
                        , formatFunction: ${field.formatFunction}
                        </#if>
                        <#if field.sortableFlag=="YES" && field.dataType!="DATA_DICTIONARY">
                        , sortable: true
                        </#if>
                    }<#if field_has_next>, </#if>
                    </#list>
                ],
                queryCondition: {
                    //默认值处理
                    <#list queryConditionList as item>
                    <#if item.defaultValue??>
                    <#if item.dataType=="STRING" || item.dataType=="DATA_DICTIONARY">
                    ${item.code}: '${item.defaultValue}'<#if item_has_next>,</#if>
                    </#if>
                    </#if>
                    </#list>

                }
            }
        },

        methods: {
            <#if listEntityView.beforeInit?? && listEntityView.beforeInit!="">
            beforeInit(param){
                ${listEntityView.beforeInit}
            },
            </#if>
            <#if listEntityView.afterInit?? && listEntityView.afterInit!="">
            afterInit(param){
                ${listEntityView.afterInit}
            },
            </#if>
            <#if listEntityView.commonParamChange?? && listEntityView.commonParamChange!="">
            commonParamChange(param){
                ${listEntityView.commonParamChange}
            },
            </#if>
            <#if parentPropertyCode??>
            add() {
                this.$refs.addPage.init({ id: this.queryCondition.${parentPropertyCode}})
            },
            commonParamChange(param) {
                this.queryCondition. ${parentPropertyCode} = param.id
            },
            </#if>
            <#global pageMoreButtonFunctionExist=false,rowButtonFunctionExist=false,rowMoreButtonFunctionExist=false>
            <#list pageMoreButtonList as item>
            <#if item.content?? && item.content!="">
            <#global pageMoreButtonFunctionExist=true>
            <#break>
            </#if>
            </#list>
            <#list rowButtonList as item>
             <#if item.content?? && item.content!="">
            <#global rowButtonFunctionExist=true>
            <#break>
            </#if>
            </#list>
            <#list rowMoreButtonList as item>
             <#if item.content?? && item.content!="">
            <#global rowMoreButtonFunctionExist=true>
            <#break>
            </#if>
            </#list>
            <#list pageButtonList as pageButton>
            <#if pageButton.content?? && pageButton.content!="">
            ${pageButton.code}(){
                <#if pageButton.confirmFlag=="YES">
                this.$confirm('${pageButton.confirmMessage}', '确认', {
                    type: 'warning'
                }).then(() => {
                    ${pageButton.content}
                }).catch(() => {
                    this.$message.info('已取消')
                })
                <#else>
                ${pageButton.content}
                </#if>
            }<#if pageButton_has_next || pageMoreButtonFunctionExist || rowButtonFunctionExist || rowMoreButtonFunctionExist>,</#if>
            </#if>
            </#list>
            <#list pageMoreButtonList as pageButton>
            <#if pageButton.content?? && pageButton.content!="">
            ${pageButton.code}(){
                <#if pageButton.confirmFlag=="YES">
                this.$confirm('${pageButton.confirmMessage}', '确认', {
                    type: 'warning'
                }).then(() => {
                    ${pageButton.content}
                }).catch(() => {
                    this.$message.info('已取消')
                })
                <#else>
                ${pageButton.content}
                </#if>
            }<#if pageButton_has_next || rowButtonFunctionExist || rowMoreButtonFunctionExist>,</#if>
            </#if>
            </#list>
            <#list rowButtonList as rowButton>
            <#if  rowButton.content?? && rowButton.content!="">
            ${rowButton.code}(row){
                <#if rowButton.confirmFlag=="YES">
                this.$confirm('${rowButton.confirmMessage}', '确认', {
                    type: 'warning'
                }).then(() => {
                    ${rowButton.content}
                }).catch(() => {
                    this.$message.info('已取消')
                })
                <#else>
                ${rowButton.content}
                </#if>
            }<#if rowButton_has_next || rowMoreButtonFunctionExist>,</#if>
            </#if>
            </#list>
            <#list rowMoreButtonList as rowButton>
            <#if  rowButton.content?? && rowButton.content!="">
            ${rowButton.code}(row){
                <#if rowButton.confirmFlag=="YES">
                this.$confirm('${rowButton.confirmMessage}', '确认', {
                    type: 'warning'
                }).then(() => {
                    ${rowButton.content}
                }).catch(() => {
                    this.$message.info('已取消')
                })
                <#else>
                ${rowButton.content}
                </#if>
            }<#if rowButton_has_next>,</#if>
            </#if>
            </#list>
        }
    }
</script>
