<template>
  <el-collapse v-model="activeNames" @change="handleChange">
    <el-collapse-item
      v-for="category in templateData"
      :key="category.code"
      :name="category.code"
      class="m-3"
    >
      <template #title>
        <div class="m-3 font-bold font-black" style="font-size: 18px"> {{ category.name }}</div>
      </template>
      <el-button
        v-for="template in category.workflowTemplateVOList"
        :key="template.code"
        type="primary"
        class="m-3"
        @click="createFlow(template.code, template.name, template.processDefinitionId)"
        >{{ template.name }}</el-button
      >
    </el-collapse-item>
  </el-collapse>
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
