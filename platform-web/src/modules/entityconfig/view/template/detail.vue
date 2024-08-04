<template>
  <Dialog :title="title" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="时分秒" prop="time1">
        <el-time-picker
          v-model="entityData.time1"
          :value-format="$dateFormatter.getTimeFormat('SECOND')"
          :format="$dateFormatter.getTimeFormat('SECOND')"
          align="right"
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="时分" prop="time2">
        <el-time-picker
          v-model="entityData.time2"
          :value-format="$dateFormatter.getTimeFormat('MINITE')"
          :format="$dateFormatter.getTimeFormat('MINITE')"
          align="right"
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="时" prop="time3">
        <el-time-picker
          v-model="entityData.time3"
          :value-format="$dateFormatter.getTimeFormat('HOUR')"
          :format="$dateFormatter.getTimeFormat('HOUR')"
          align="right"
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="年月日" prop="date1">
        <el-date-picker
          v-model="entityData.date1"
          :value-format="$dateFormatter.getDatetimeFormat('DAY')"
          :type="$dateFormatter.getDatetimeType('DAY')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="年月日时分" prop="day2">
        <el-date-picker
          v-model="entityData.day2"
          :value-format="$dateFormatter.getDatetimeFormat('MINITE')"
          :type="$dateFormatter.getDatetimeType('MINITE')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="年月日时分秒" prop="day3">
        <el-date-picker
          v-model="entityData.day3"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="长文本" prop="content">
        <el-input v-model="entityData.content" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + mode" v-show="mode != 'view'"
        >保存</el-button
      >
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { detailMixin } from '@/mixin/detailMixin.js'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'template'
export default {
  name: ENTITY_TYPE + '-detail',
  mixins: [detailMixin],
  components: {},
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      rules: {
        //前端验证规则
      }
    }
  },
  methods: {}
}
</script>

<style></style>
