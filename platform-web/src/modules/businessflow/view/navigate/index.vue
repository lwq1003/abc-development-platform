<template>
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange">
      <el-collapse-item
        v-for="category in templateData"
        :key="category.code"
        :title="category.name"
        :name="category.code"
        style="margin: 10px; padding: 5px; font-size: large"
      >
        <!-- TODO:布局美观性待调整 -->
        <el-button
          v-for="template in category.workflowTemplateVOList"
          :key="template.code"
          type="primary"
          @click="createFlow(template.code, template.name, template.processDefinitionId)"
          >{{ template.name }}</el-button
        >
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script lang="ts">
export default {
  name: 'FlowNavigate',
  data() {
    return {
      activeNames: [],
      // 流程模板数据
      templateData: []
    }
  },
  mounted() {
    this.$api.businessflow.navigate.get().then((res) => {
      this.templateData = res.data
      const nameArray = this.templateData.map((item) => {
        return item.code
      })
      this.activeNames = nameArray
    })
  },
  methods: {
    handleChange(val) {
      console.log(val)
    },
    createFlow(processDefinitionKey, processDefinitionName, processDefinitionId) {
      this.$router.push({
        name: 'flowCreate',
        query: { processDefinitionKey, processDefinitionName, processDefinitionId }
      })
    }
  }
}
</script>

<style scoped></style>
