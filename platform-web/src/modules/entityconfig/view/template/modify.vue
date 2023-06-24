<template>
  <Dialog title="修改" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="用户单选" prop="userSingle">
        <UserSingleSelect v-model="entityData.userSingle" />
      </el-form-item>
      <el-form-item label="组织机构单选" prop="organizationSingle">
        <OrganizationSingleSelect v-model="entityData.organizationSingle" />
      </el-form-item>
      <el-form-item label="用户" prop="entity">
        <UserReference v-model="entityData.entity" :user-param="userParam" />
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <IconPicker v-model="entityData.icon" />
      </el-form-item>
      <el-form-item label="流水号" prop="serialNo">
        <el-input v-model="entityData.serialNo" :readonly="true" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="日期" prop="date">
        <el-date-picker
          v-model="entityData.date"
          :value-format="$dateFormatter.getDatetimeFormat('DAY')"
          :type="$dateFormatter.getDatetimeType('DAY')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="时间" prop="time">
        <el-date-picker
          v-model="entityData.time"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="是否" prop="yesOrNo">
        <dictionary-radio-group v-model="entityData.yesOrNo" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="Status" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { modifyMixin } from '@/mixin/modifyMixin.js'
import UserReference from '@/modules/system/view/user/treeListReference.vue'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'template'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {
    UserReference
  },
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 用户组件参数，用于传递数据
      userParam: {},
      rules: {
        //前端验证规则
        userSingle: [{ required: true, message: '【用户单选】不能为空', trigger: 'blur' }],
        icon: [{ required: true, message: '【图标】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        yesOrNo: [{ required: true, message: '【是否】不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
