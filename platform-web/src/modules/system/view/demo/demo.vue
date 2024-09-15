<script setup>
import { ref, unref } from 'vue'
import { EverrightFilter } from 'everright-filter'
import 'everright-filter/dist/style.css'
import { getCurrentInstance } from 'vue'
let { proxy } = getCurrentInstance()
const ERfilterRef = ref(null)
const lang = ref('zh-cn')
const handleListener = ({ type, data }) => {
  console.log(type, data)
}
const getOptions = async () => {
  return new Promise((resolve, reject) => {
    resolve({
      data: {
        options: [
          {
            label: '姓名',
            en_label: 'name',
            renderType: 'TEXT',
            operatorKey: 'Text',
            value: 'name',
            excludeOperator: {
              operator: ['one_of']
            }
          },
          {
            label: '年龄',
            en_label: 'age',
            renderType: 'NUMBER',
            operatorKey: 'Number',
            value: 'age'
          },
          {
            label: '性别',
            renderType: 'SELECT',
            operatorKey: 'Gender',
            value: 'gender'
          },
          {
            label: '部门',
            renderType: 'CASCADER',
            operatorKey: 'Collection',
            value: 'dept',
            multiple: true
          }
        ],
        operators: {
          Collection: [
            {
              label: '包含在……',
              value: 'in',
              style: 'noop'
            }
          ],
          Gender: [
            {
              label: '等于',
              en_label: 'Equal',
              style: 'noop'
            },
            {
              label: '不等于',
              value: 'not_equal',
              style: 'noop'
            }
          ],
          Text: [
            {
              label: '等于',
              en_label: 'Equal',
              style: 'noop'
            },
            {
              label: '等于其中之一',
              en_label: 'Equal to one of',
              value: 'one_of',
              style: 'tags'
            },
            {
              label: '不等于',
              en_label: 'Not equal',
              value: 'not_equal',
              style: 'noop'
            },
            {
              label: '包含',
              en_label: 'Contains',
              value: 'contains',
              style: 'noop'
            },
            {
              label: '不包含',
              en_label: 'Not contain',
              value: 'not_contain',
              style: 'noop'
            },
            {
              label: '为空',
              en_label: 'Empty',
              value: 'empty',
              style: 'none'
            },
            {
              label: '不为空',
              en_label: 'Not empty',
              value: 'not_empty',
              style: 'none'
            }
          ],
          Number: [
            {
              label: '等于',
              en_label: 'Equal',
              value: 'equal',
              style: 'noop'
            },
            {
              label: '不等于',
              en_label: 'Not equal',
              value: 'not_equal',
              style: 'noop'
            },
            {
              label: '大于',
              en_label: 'Greater than',
              value: 'greater_than',
              style: 'noop'
            },
            {
              label: '大于等于',
              en_label: 'Greater than or equal to',
              value: 'greater_than_equal',
              style: 'noop'
            },
            {
              label: '小于',
              en_label: 'Less than',
              value: 'less_than',
              style: 'noop'
            },
            {
              label: '小于等于',
              en_label: 'Less than or equal to',
              value: 'less_than_equal',
              style: 'noop'
            },
            {
              label: '区间',
              en_label: 'Between',
              value: 'between',
              style: 'range'
            },
            {
              label: '为空',
              en_label: 'Empty',
              value: 'empty',
              style: 'none'
            },
            {
              label: '不为空',
              en_label: 'Not empty',
              value: 'not_empty',
              style: 'none'
            }
          ]
        }
      }
    })
  })
}
const getConditions = async (params) => {
  const { property } = params
  if (property === 'dept') {
    return new Promise((resolve, reject) => {
      resolve({
        data: [
          // {
          // label: '一二三集团',
          // value: '1',
          // children: [
          //   {
          //     label: '北京分公司',
          //     value: 'bj',
          //     children: null
          //   },
          //   {
          //     label: '上海分公司',
          //     value: 'sh',
          //     children: null
          //   }
          // ]
          // }
        ]
      })
    })
  } else if (property === 'gender') {
    console.log(property)
    // 当 property 为 'gender' 时，返回相应的条件选项
    return new Promise((resolve, reject) => {
      resolve({
        data: [
          { label: '男', value: 'male' },
          { label: '女', value: 'female' }
        ]
      })
    })
  }
}
const getProps = async () => {
  return new Promise((resolve, reject) => {
    resolve({
      data: {}
    })
  })
}
const getPropValues = async (params) => {
  return new Promise((resolve, reject) => {
    resolve({
      data: {}
    })
  })
}

const getData = () => {
  const data = JSON.stringify(ERfilterRef.value.getData(), null, ' ')
  console.log(JSON.stringify(data))
  proxy.$api.entityconfig.entityModelProperty
    .getFullPropertyListForFilter('1641252670496907265')
    .then((res) => {
      // console.log(res.data)
    })
}

const setData = () => {
  unref(ERfilterRef).setData({
    filters: [{ conditions: [{ property: 'name', value: '111' }], logicalOperator: 'and' }],
    logicalOperator: 'and'
  })
}
</script>
<template>
  <div style="width: 100%">
    <el-button type="primary" @click="getData">获取数据</el-button>

    <el-button type="primary" @click="setData">设置数据</el-button>
    <EverrightFilter
      :lang="lang"
      @listener="handleListener"
      type="matrix"
      :getOptions="getOptions"
      :getConditions="getConditions"
      :getProps="getProps"
      :getPropValues="getPropValues"
      ref="ERfilterRef"
    />
  </div>
</template>
