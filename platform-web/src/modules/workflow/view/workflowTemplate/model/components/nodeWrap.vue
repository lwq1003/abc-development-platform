<!-- eslint-disable vue/no-mutating-props -->
<!--
 * @Date: 2022-09-21 14:41:53
 * @LastEditors: StavinLi 495727881@qq.com
 * @LastEditTime: 2023-05-24 15:20:24
 * @FilePath: /Workflow-Vue3/src/components/nodeWrap.vue
-->
<template>
  <!-- 发起环节 -->
  <div class="node-wrap" v-if="modelValue.type == 'ROOT'">
    <div
      class="node-wrap-box"
      :class="'start-node' + (isTried && modelValue.error ? 'active error' : '')"
    >
      <div class="title" :style="`background: rgb(87, 106, 149);`">
        <span v-if="modelValue.type == 'ROOT'">{{ modelValue.name }}</span>
      </div>
      <div class="content" @click="setRootNode">
        <div class="text"> 发起环节 </div>
        <i class="anticon anticon-right arrow"></i>
      </div>
      <div class="error_tip" v-if="isTried && modelValue.error">
        <i class="anticon anticon-exclamation-circle"></i>
      </div>
    </div>
    <addNode v-model:childNodeP="modelValue.child" />
  </div>
  <!-- 办理环节  -->
  <div class="node-wrap" v-else-if="modelValue.type == 'HANDLE'">
    <div class="node-wrap-box" :class="isTried && modelValue.error ? 'active error' : ''">
      <div class="title" :style="`background: rgb(255, 148, 62);`">
        <input
          v-if="isInput"
          type="text"
          class="ant-input editable-title-input"
          @blur="blurEvent()"
          @focus="$event.currentTarget.select()"
          v-focus
          v-model="modelValue.name"
          :placeholder="defaultText"
        />
        <span v-else class="editable-title" @click="clickEvent()">{{ modelValue.name }}</span>
        <i class="anticon anticon-close close" @click="delNode"></i>
      </div>
      <div class="content" @click="setHandleNode">
        <div class="text">
          <span
            class="placeholder"
            v-if="!modelValue.config.personConfig || !modelValue.config.personConfig.userGroupName"
            >待配置</span
          >
          <template v-else> {{ modelValue.config.personConfig.userGroupName }}</template>
        </div>
        <i class="anticon anticon-right arrow"></i>
      </div>
      <div class="error_tip" v-if="isTried && modelValue.error">
        <i class="anticon anticon-exclamation-circle"></i>
      </div>
    </div>
    <addNode v-model:childNodeP="modelValue.child" />
  </div>
  <!-- 路由节点 -->
  <div class="branch-wrap" v-else-if="modelValue.type == 'INCLUSIVE_GATEWAY'">
    <div class="branch-box-wrap">
      <div class="branch-box">
        <button class="add-branch" @click="addCondition">添加条件</button>
        <div class="col-box" v-for="(item, index) in modelValue.branchList" :key="index">
          <div class="condition-node">
            <div class="condition-node-box">
              <div class="auto-judge" :class="isTried && item.error ? 'error active' : ''">
                <div class="sort-left" v-if="index != 0" @click="arrTransfer(index, -1)">&lt;</div>
                <div class="title-wrapper">
                  <input
                    v-if="isInputList[index]"
                    type="text"
                    class="ant-input editable-title-input"
                    @blur="blurEvent(index)"
                    @focus="$event.currentTarget.select()"
                    v-focus
                    v-model="item.name"
                  />
                  <span v-else class="editable-title" @click="clickEvent(index)">{{
                    item.name
                  }}</span>
                  <i class="anticon anticon-close close" @click="removeCondition(index)"></i>
                </div>
                <div
                  class="sort-right"
                  v-if="index != modelValue.branchList.length - 1"
                  @click="arrTransfer(index)"
                  >&gt;</div
                >
                <div class="content" @click="setConditionNode(item)">
                  <span class="placeholder" v-if="!item.config.expression">未设置</span>
                  {{ item.config.expression }}
                </div>
                <div class="error_tip" v-if="isTried && item.error">
                  <i class="anticon anticon-exclamation-circle"></i>
                </div>
              </div>
              <addNode v-model:childNodeP="item.child" />
            </div>
          </div>
          <nodeWrap v-if="item.child" v-model:modelValue="item.child" />
          <template v-if="index == 0">
            <div class="top-left-cover-line"></div>
            <div class="bottom-left-cover-line"></div>
          </template>
          <template v-if="index == modelValue.branchList.length - 1">
            <div class="top-right-cover-line"></div>
            <div class="bottom-right-cover-line"></div>
          </template>
        </div>
      </div>
      <addNode v-model:childNodeP="modelValue.child" />
    </div>
  </div>
  <nodeWrap v-if="modelValue.child" v-model:modelValue="modelValue.child" />
</template>
<script setup>
import { onMounted, ref, watch, getCurrentInstance, computed } from 'vue'
import addNode from './addNode.vue'
import $func from '../utils/index'
import { useStore } from '../stores/index'
import { placeholderList } from '../utils/const'
let _uid = getCurrentInstance().uid

let props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({})
  }
})

let defaultText = computed(() => {
  return placeholderList[props.modelValue.type]
})

let isInputList = ref([])
let isInput = ref(false)
const resetConditionNodesErr = () => {
  for (var i = 0; i < props.modelValue.branchList.length; i++) {
    // eslint-disable-next-line vue/no-mutating-props
    props.modelValue.branchList[i].error =
      $func.conditionStr(props.modelValue, i) == '请设置条件' &&
      i != props.modelValue.branchList.length - 1
  }
}
onMounted(() => {
  if (props.modelValue.type == 'HANDLE') {
    // eslint-disable-next-line vue/no-mutating-props
    props.modelValue.error = !$func.setApproverStr(props.modelValue)
  }
  // else if (props.modelValue.type == 2) {
  //   // eslint-disable-next-line vue/no-mutating-props
  //   props.modelValue.error = !$func.copyerStr(props.modelValue)
  // } else if (props.modelValue.type == 4) {
  //   resetConditionNodesErr()
  // }
})
let emits = defineEmits(['update:modelValue'])
let store = useStore()
let {
  setRootNodeConfigVisible,
  setRootNodeConfig,
  setHandleNodeConfigVisible,
  setHandleNodeConfig,
  setConditionNodeConfigVisible,
  setConditionNodeConfig
} = store
let isTried = computed(() => store.isTried)

const clickEvent = (index) => {
  if (index || index === 0) {
    isInputList.value[index] = true
  } else {
    isInput.value = true
  }
}
const blurEvent = (index) => {
  if (index || index === 0) {
    isInputList.value[index] = false
    // eslint-disable-next-line vue/no-mutating-props
    props.modelValue.branchList[index].name = props.modelValue.branchList[index].name || '条件'
  } else {
    isInput.value = false
    // eslint-disable-next-line vue/no-mutating-props
    props.modelValue.name = props.modelValue.name || defaultText
  }
}
const delNode = () => {
  emits('update:modelValue', props.modelValue.child)
}
const addCondition = () => {
  let len = props.modelValue.branchList.length + 1
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.branchList.push({
    name: '条件' + len,
    type: 'CONDITION',
    child: null
  })
  resetConditionNodesErr()
  emits('update:modelValue', props.modelValue)
}
const removeCondition = (index) => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.branchList.splice(index, 1)
  props.modelValue.branchList.map((item, index) => {
    item.name = `条件${index + 1}`
  })
  resetConditionNodesErr()
  emits('update:modelValue', props.modelValue)
  if (props.modelValue.branchList.length == 1) {
    if (props.modelValue.child) {
      if (props.modelValue.branchList[0].child) {
        reData(props.modelValue.branchList[0].child, props.modelValue.child)
      } else {
        // eslint-disable-next-line vue/no-mutating-props
        props.modelValue.branchList[0].child = props.modelValue.child
      }
    }
    emits('update:modelValue', props.modelValue.branchList[0].child)
  }
}
const reData = (data, addData) => {
  if (!data.child) {
    data.child = addData
  } else {
    reData(data.child, addData)
  }
}

const arrTransfer = (index, type = 1) => {
  //向左-1,向右1
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.branchList[index] = props.modelValue.branchList.splice(
    index + type,
    1,
    props.modelValue.branchList[index]
  )[0]
  props.modelValue.branchList.map((item, index) => {
    item.priorityLevel = index + 1
  })
  resetConditionNodesErr()
  emits('update:modelValue', props.modelValue)
}

// 设置发起环节配置
const setRootNode = () => {
  setRootNodeConfigVisible(true)
  const nodeConfig = {
    config: props.modelValue.config,
    flag: false,
    componentId: _uid,
    id: props.modelValue.id
  }

  setRootNodeConfig(nodeConfig)
}
let rootNodeConfig = computed(() => store.rootNodeConfig)
watch(
  rootNodeConfig,
  (myConfig) => {
    if (myConfig.flag && myConfig.componentId === _uid) {
      const modelValue = Object.assign(props.modelValue, { config: myConfig.config })
      emits('update:modelValue', modelValue)
    }
  },
  { deep: true }
)

// 设置办理环节配置
const setHandleNode = () => {
  setHandleNodeConfigVisible(true)
  const handleNodeConfig = {
    config: props.modelValue.config,
    flag: false,
    componentId: _uid,
    id: props.modelValue.id
  }
  setHandleNodeConfig(handleNodeConfig)
}

let handleNodeConfig = computed(() => store.handleNodeConfig)
watch(
  handleNodeConfig,
  (myConfig) => {
    if (myConfig.flag && myConfig.componentId === _uid) {
      const modelValue = Object.assign(props.modelValue, { config: myConfig.config })
      emits('update:modelValue', modelValue)
    }
  },
  { deep: true }
)

// 设置条件节点配置
const setConditionNode = (condition) => {
  setConditionNodeConfigVisible(true)
  const conditionNodeConfig = {
    ...condition.config,
    flag: false,
    componentId: _uid,
    nodeId: condition.id
  }
  setConditionNodeConfig(conditionNodeConfig)
}

let conditionNodeConfig = computed(() => store.conditionNodeConfig)
watch(
  conditionNodeConfig,
  (myConfig) => {
    if (myConfig.flag && myConfig.componentId === _uid) {
      // 只保留必要属性，移除辅助使用的componentId和flag
      const conditionNodeConfig = {
        expression: myConfig.expression
      }
      // 获取条件节点标识
      const conditionNodeId = myConfig.nodeId
      let newModelValue = props.modelValue

      newModelValue.branchList.forEach((element) => {
        if (element.id === conditionNodeId) {
          element.config = conditionNodeConfig
          return
        }
      })

      emits('update:modelValue', newModelValue)
    }
  },
  { deep: true }
)
</script>
<style scoped>
@import '../css/workflow.css';
.error_tip {
  position: absolute;
  top: 0px;
  right: 0px;
  transform: translate(150%, 0px);
  font-size: 24px;
}

.promoter_person .el-dialog__body {
  padding: 10px 20px 14px 20px;
}

.selected_list {
  margin-bottom: 20px;
  line-height: 30px;
}

.selected_list span {
  margin-right: 10px;
  padding: 3px 6px 3px 9px;
  line-height: 12px;
  white-space: nowrap;
  border-radius: 2px;
  border: 1px solid rgba(220, 220, 220, 1);
}

.selected_list img {
  margin-left: 5px;
  width: 7px;
  height: 7px;
  cursor: pointer;
}
</style>
