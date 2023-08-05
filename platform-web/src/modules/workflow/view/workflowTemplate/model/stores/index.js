/*
 * @Date: 2022-08-25 14:13:11
 * @LastEditors: StavinLi 495727881@qq.com
 * @LastEditTime: 2023-05-24 15:00:32
 * @FilePath: /Workflow-Vue3/src/store/index.js
 */
import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
  state: () => ({
    tableId: '',
    isTried: false,

    approverDrawer: false,
    approverConfig1: {},
    copyerDrawer: false,
    copyerConfig1: {},
    conditionDrawer: false,
    conditionsConfig1: {
      conditionNodes: []
    },
    // 办理节点配置可见性
    handleNodeConfigVisible: false,
    // 办理节点配置
    handleNodeConfig: {},
    // 条件节点配置可见性
    conditionNodeConfigVisible: false,
    // 条件节点配置
    conditionNodeConfig: {}
  }),
  actions: {
    setTableId(payload) {
      this.tableId = payload
    },
    setIsTried(payload) {
      this.isTried = payload
    },

    setApprover(payload) {
      this.approverDrawer = payload
    },
    setApproverConfig(payload) {
      this.approverConfig1 = payload
    },
    setCopyer(payload) {
      this.copyerDrawer = payload
    },
    setCopyerConfig(payload) {
      this.copyerConfig1 = payload
    },
    setCondition(payload) {
      this.conditionDrawer = payload
    },
    setConditionsConfig(payload) {
      this.conditionsConfig1 = payload
    },
    // 已确认
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
    }
  }
})
