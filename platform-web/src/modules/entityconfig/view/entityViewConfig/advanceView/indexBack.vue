<template>
  <form-create v-model="value" v-model:api="fApi" :rule="rule" :option="option" v-show="true" />
</template>

<script lang="ts">
import { markRaw } from 'vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
export default {
  components: { DictionarySelect },
  data() {
    return {
      //实例对象
      fApi: {},
      //表单数据
      value: {},
      //表单生成规则
      rule: [
        {
          type: 'input',
          field: 'goods_name',
          title: '商品名称'
        },
        {
          type: 'datePicker',
          field: 'created_at',
          title: '创建时间'
        },
        {
          type: 'DictionarySelect',
          component: markRaw(DictionarySelect),
          field: 'onSaleFlag',
          title: '是否在售',
          props: {
            code: 'YesOrNo'
          }
        }
      ],
      //组件参数配置
      option: {
        //表单提交事件
        onSubmit: function (formData) {
          alert(JSON.stringify(formData))
        }
      }
    }
  },

  mounted() {},
  methods: {}
}
</script>

<style></style>
