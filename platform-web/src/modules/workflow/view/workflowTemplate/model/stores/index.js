/*
 * @Date: 2022-08-25 14:13:11
 * @LastEditors: StavinLi 495727881@qq.com
 * @LastEditTime: 2023-05-24 15:00:32
 * @FilePath: /Workflow-Vue3/src/store/index.js
 */
import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
  state: () => ({
    isTried: false,
    // 发起节点配置可见性
    rootNodeConfigVisible: false,
    // 发起节点配置
    rootNodeConfig: {},
    // 办理节点配置可见性
    handleNodeConfigVisible: false,
    // 办理节点配置
    handleNodeConfig: {},
    // 条件节点配置可见性
    conditionNodeConfigVisible: false,
    // 条件节点配置
    conditionNodeConfig: {},
    // 工作流程定义
    processDefinitionId: ''
  }),
  actions: {
    setIsTried(payload) {
      this.isTried = payload
    },
    setRootNodeConfigVisible(showFlag) {
      this.rootNodeConfigVisible = showFlag
    },
    setRootNodeConfig(config) {
      this.rootNodeConfig = config
    },
    setHandleNodeConfigVisible(showFlag) {
      this.handleNodeConfigVisible = showFlag
    },
    setHandleNodeConfig(config) {
      this.handleNodeConfig = config
    },
    setConditionNodeConfigVisible(showFlag) {
      this.conditionNodeConfigVisible = showFlag
    },
    setConditionNodeConfig(config) {
      this.conditionNodeConfig = config
    },
    setProcessDefinitionId(processDefinitionId) {
      this.processDefinitionId = processDefinitionId
    }
  }
})
