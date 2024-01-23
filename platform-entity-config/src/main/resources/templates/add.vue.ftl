<template>
    <Dialog title="新增" v-model="visible" width="500px">
        <el-form
                ref="form"
                :model="entityData"
                :rules="rules"
                label-width="120px"
                label-position="right"
                style="width: 90%; margin: 0px auto"
        >
            <!--表单区域 -->
     <#list addViewPropertyList as item>
                <#if item.dataType=="STRING">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                         <#if item.widgetType!="RICH_TEXT">
                         <el-input v-model="entityData.${item.code}" <#if item.readonlyFlag=="YES">:readonly='true' </#if>  <#if item
                         .widgetType=="TEXTAREA">type="textarea" rows="4" </#if> />
                         <#else>
                         <Editor v-model="entityData.${item.code}"  <#if item.readonlyFlag=="YES">:readonly='true' </#if>  />
                         </#if>
                    </el-form-item>
                <#elseif item.dataType=="DATETIME"  >
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <el-date-picker
                                v-model="entityData.${item.code}"
                                :value-format="$dateFormatter.getDatetimeFormat('${item.formatPattern}')"
                                :type="$dateFormatter.getDatetimeType('${item.formatPattern}')"
                                align="right"
                                unlink-panels
                                class="form-item"
                                <#if item.readonlyFlag=="YES">:readonly='true' </#if>
                        />
                    </el-form-item>
                <#elseif item.dataType=="DATA_DICTIONARY"  >
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <#if item.widgetType=="DROP_DOWN_LIST">
                            <dictionary-select
                                    v-model="entityData.${item.code}"
                                    code="${item.dictionaryType}"
                                    <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                            />
                        <#elseif item.widgetType=="CHECK_BOX_GROUP">
                            <dictionary-checkbox-group
                                    v-model="entityData.${item.code}"
                                    code="${item.dictionaryType}"
                                    <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                            />

                        <#elseif item.widgetType=="RADIO_BUTTON_GROUP">
                            <dictionary-radio-group
                                    v-model="entityData.${item.code}"
                                    code="${item.dictionaryType}"
                                    <#if item.readonlyFlag=="YES">readonly='readonly' </#if>
                            />
                        </#if>
                    </el-form-item>
                <#elseif item.dataType=="ENTITY">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                         <${item.entityCode}Reference v-model="entityData.${item.code}" :${item.entityCode?uncap_first}-param="${item.entityCode?uncap_first}Param" <#if
                            item.readonlyFlag=="YES">:readonly='true'
                            </#if> />
                    </el-form-item>
                <#elseif item.dataType=="ORGANIZATION_SINGLE">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <OrganizationSingleSelect v-model="entityData.${item.code}"  <#if item
                        .readonlyFlag=="YES">:readonly='true'
                                </#if> />
                    </el-form-item>
                <#elseif item.dataType=="ORGANIZATION_MULTIPLE">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <OrganizationMultipleSelect v-model="entityData.${item.code}"  <#if item
                        .readonlyFlag=="YES">:readonly='true'
                                </#if> />
                    </el-form-item>
                <#elseif item.dataType=="USER_SINGLE">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <UserSingleSelect v-model="entityData.${item.code}"  <#if item
                        .readonlyFlag=="YES">:readonly='true'
                        </#if> />
                    </el-form-item>
                <#elseif item.dataType=="ICON">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <IconPicker v-model="entityData.${item.code}"  <#if item
                        .readonlyFlag=="YES">:readonly='true'
                                </#if> />
                    </el-form-item>
                <#elseif item.dataType=="ATTACHMENT">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                         <#if item.widgetType=="MANAGE">
                             <AttachmentManager ref="attachmentManager" :entity-id="entityData.id" <#if item.readonlyFlag=="YES">:readonly='true'
                                     </#if> />
                         <#elseif item.widgetType=="UPLOAD">
                             <AttachmentUploader
                                     entity-type="${entity}"
                                     :entity-id="entityData.id"
                                     module-code="${package.ModuleName}"
                                     :show-success-files="false"
                                     @file-complete="fileComplete"
                             />
                         <#elseif item.widgetType=="VIEW">
                             <AttachmentViewer :entity-id="entityData.id" />
                         <#elseif item.widgetType=="MANAGE_AND_UPLOAD">
                             <AttachmentManagerAndUploader
                                     entity-type="${entity}"
                                     :entity-id="entityData.id"
                                     module-code="${package.ModuleName}"
                             />
                         </#if>
                    </el-form-item>
                <#elseif item.dataType=="SERIAL_NO">
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <el-input v-model="entityData.${item.code}" :readonly='true' />
                    </el-form-item>
                <#else>
                    <el-form-item label="${item.name}" prop="${item.code}"  <#if item.showFlag=="NO">v-show='false' <#elseif item
                    .showFlag=="CUSTOM">v-show="${item.showExpression}" </#if>>
                        <el-input v-model="entityData.${item.code}"
                                  <#if item.readonlyFlag=="YES">:readonly='true' </#if>
                        />
                    </el-form-item>
                </#if>
            </#list>
        </el-form>
        <template #footer>
            <el-button type="primary" @click="save" v-permission="pageCode + 'add'">保存</el-button>
            <el-button @click="close">关闭</el-button>
        </template>
    </Dialog>
</template>

<script>
import {addMixin} from '@/mixin/addMixin.js'
<#list addViewPropertyList as item>
<#if item.dataType=="ENTITY">
import ${item.entityCode}Reference from '@/modules/${item.moduleCode}/view/${item.entityCode?uncap_first}/${mainReferenceViewMap[item.entityCode]}.vue'
</#if>
</#list>
const MODULE_CODE = '${package.ModuleName}'
const ENTITY_TYPE = '${entity?uncap_first}'
export default {
   name: ENTITY_TYPE + '-add',
   mixins: [addMixin],
   components:{
       <#list addViewPropertyList as item>
       <#if item.dataType=="ENTITY">
       ${item.entityCode}Reference,
       </#if>
       </#list>
   },
   data() {
       return {
           entityType: ENTITY_TYPE,
           moduleCode: MODULE_CODE,
           // eslint-disable-next-line no-eval
           api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
           pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
           entityData: {},
           <#list addViewPropertyList as item>
           <#if item.dataType=="ENTITY">
           // ${item.name}组件参数，用于传递数据
           ${item.entityCode?uncap_first}Param: {},
           </#if>
           </#list>
           rules: {
               //前端验证规则
           <#list entityModelPropertyList as item>
           <#if item.nullFlag=="NO">
               ${item.code}: [{ required: true, message: '【${item.name}】不能为空', trigger: 'blur' }]<#if item_has_next>, </#if>
           </#if>
           </#list>
           }
       }
   },
   methods: {
       <#if parentPropertyCode??>
       afterInit(param) {
           this.entityData.${parentPropertyCode} = param.id
       },
       </#if>
       <#if addEntityView.beforeInit?? && addEntityView.beforeInit!="">
       beforeInit(param){
           ${addEntityView.beforeInit}
       },
       </#if>
       <#if addEntityView.afterInit?? && addEntityView.afterInit!="">
       afterInit(param){
           ${addEntityView.afterInit}
       },
       </#if>
       <#if addEntityView.validateData?? && addEntityView.validateData!="">
       validateData(){
           ${addEntityView.validateData}
       },
       </#if>
       <#if addEntityView.beforeSave?? && addEntityView.beforeSave!="">
       beforeSave(){
           ${addEntityView.beforeSave}
       },
       </#if>
       <#if addEntityView.afterSave?? && addEntityView.afterSave!="">
       afterSave(){
           ${addEntityView.afterSave}
       },
       </#if>
   }
}
</script>

<style></style>
