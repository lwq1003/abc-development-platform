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
                        <${item.code?cap_first}Reference v-model="entityData.${item.code}" :${item.code}-param="${item.code}Param" <#if item
                        .readonlyFlag=="YES">:readonly='true'
                        </#if> />
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
import ${item.code?cap_first}Reference from '@/modules/${package.ModuleName}/view/${item.code}/${mainReferenceViewMap[item.code]}.vue'
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
       ${item.code?cap_first}Reference,
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
           <#list modifyViewPropertyList as item>
           <#if item.dataType=="ENTITY">
           // ${item.name}组件参数，用于传递数据
           ${item.code}Param: {},
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
