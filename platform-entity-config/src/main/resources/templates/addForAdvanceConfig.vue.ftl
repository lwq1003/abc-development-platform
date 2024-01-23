<template>
    <Dialog title="新增" v-model="visible" width="800px">
        <form-create :rule="rule" v-model:api="fApi" :option="options" v-model="formValue" />
        <template #footer>
            <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
            <el-button @click="close">关闭</el-button>
        </template>
    </Dialog>
</template>

<script>
import {addMixin} from '@/mixin/addForAdvanceConfigMixin.js'
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
           //fc组件
           options:${modifyEntityView.advanceConfigOption},
           rule:${modifyEntityView.advanceConfigRule}
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
