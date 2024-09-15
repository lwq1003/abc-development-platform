<template>
  <Dialog title="自定义查询" v-model="visible" fullscreen>
    <el-row>
      <el-col :span="24">
        查询方案<QueryPlanSelect
          @my-change="queryPlanChanged"
          :entity-model-code="entityModelCode"
          ref="queryPlanSelect"
          style="margin: auto 20px"
        ></QueryPlanSelect>

        <el-button type="primary" @click="modify">修改</el-button>
        <el-button type="primary" @click="remove">删除</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <EverrightFilter
          lang="zh-cn"
          type="matrix"
          :getOptions="getOptions"
          :getConditions="getConditions"
          ref="everrightFilter"
        />
      </el-col>
    </el-row>

    <template #footer>
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="primary" @click="saveAs">另存为</el-button>
      <el-button type="primary" @click="query">查询</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Dialog } from '@/components/abc/Dialog'
import { EverrightFilter } from 'everright-filter'
import 'everright-filter/dist/style.css'
import QueryPlanSelect from '@/modules/system/view/queryPlan/select.vue'
export default {
  components: { Dialog, EverrightFilter, QueryPlanSelect },
  data() {
    return {
      visible: false,
      //模型编码
      entityModelCode: '',
      // 属性与字典类型映射
      propertyDictionaryTypeMapping: {},
      // 属性与集合类型映射
      propertyCollectionTypeMapping: {},
      // 当前选中的查询方案
      currentQueryPlan: null
    }
  },
  methods: {
    // 修改方案名称
    modify() {
      if (this.currentQueryPlan == null) {
        this.$message.warning('请先选择要改名的查询方案')
        return
      }

      //获取查询方案内容，并做非空判断
      const content = JSON.stringify(this.$refs.everrightFilter.getData(), null, '\t')
      if (this.checkContentIsNull(content)) {
        return
      }
      // 输入新的方案名称
      ElMessageBox.prompt('请输入新的方案名称', '修改查询方案', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: this.currentQueryPlan.name,
        inputPattern: /.+/,
        inputErrorMessage: '不能为空'
      }).then(({ value }) => {
        this.currentQueryPlan.name = value
        this.currentQueryPlan.content = content
        this.$api.system.queryPlan.modify(this.currentQueryPlan).then((res) => {
          //刷新查询方案列表
          this.refreshQueryPlanList()
        })
      })
    },
    // 删除
    remove() {
      if (this.currentQueryPlan == null) {
        this.$message.warning('请先选择要删除的查询方案')
        return
      }
      this.$confirm('确定要删除该查询方案吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$api.system.queryPlan.remove(this.currentQueryPlan.id).then((res) => {
          // 刷新查询方案列表
          this.refreshQueryPlanList()
        })
      })
    },
    // 保存查询方案
    save() {
      //获取查询方案内容，并做非空判断
      const content = JSON.stringify(this.$refs.everrightFilter.getData(), null, '\t')
      if (this.checkContentIsNull(content)) {
        return
      }

      if (this.currentQueryPlan) {
        // 如当前查询方案不会空，更新数据
        this.currentQueryPlan.content = content
        this.$api.system.queryPlan.modify(this.currentQueryPlan)
      } else {
        // 如当前查询方案为空，新增数据
        ElMessageBox.prompt('请输入方案名称', '新增查询方案', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /.+/,
          inputErrorMessage: '不能为空'
        }).then(({ value }) => {
          const data = {
            name: value,
            entityModelCode: this.entityModelCode,
            content: content
          }
          this.$api.system.queryPlan.add(data).then((res) => {
            //刷新查询方案列表
            this.refreshQueryPlanList()
          })
        })
      }
    },
    //另存为
    saveAs() {
      //获取查询方案内容，并做非空判断
      const content = JSON.stringify(this.$refs.everrightFilter.getData(), null, '\t')
      if (this.checkContentIsNull(content)) {
        return
      }
      if (this.currentQueryPlan == null) {
        // 如当前查询方案为空，新增数据
        ElMessageBox.prompt('请输入方案名称', '新增查询方案', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /.+/,
          inputErrorMessage: '不能为空'
        }).then(({ value }) => {
          const data = {
            name: value,
            entityModelCode: this.entityModelCode,
            content: content
          }
          this.$api.system.queryPlan.add(data).then((res) => {
            //刷新查询方案列表
            this.refreshQueryPlanList()
          })
        })
      } else {
        // 如当前查询方案不为空，参照当前数据新增
        ElMessageBox.prompt('请输入方案名称', '查询方案另存为', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputValue: this.currentQueryPlan.name,
          inputPattern: /.+/,
          inputErrorMessage: '不能为空'
        }).then(({ value }) => {
          const data = {
            name: value,
            entityModelCode: this.entityModelCode,
            content: content
          }
          this.$api.system.queryPlan.add(data).then((res) => {
            //刷新查询方案列表
            this.refreshQueryPlanList()
          })
        })
      }
    },
    // 判断方案内容是否为空
    checkContentIsNull(content) {
      if (content == '{}') {
        this.$message.warning('请先添加至少一个查询条件')
        return true
      }
      return false
    },
    // 刷新查询方案列表
    refreshQueryPlanList() {
      this.$refs.queryPlanSelect.loadData()
    },
    // 查询方案变更
    queryPlanChanged(value, data) {
      // 保存为当前查询方案
      this.currentQueryPlan = data
      if (data) {
        //加载条件
        this.$refs.everrightFilter.setData(JSON.parse(data.content))
      } else {
        this.$refs.everrightFilter.clearData()
      }
    },

    close() {
      this.visible = false
    },
    query() {
      //获取查询方案内容，并做非空判断
      const content = JSON.stringify(this.$refs.everrightFilter.getData(), null, '\t')
      if (this.checkContentIsNull(content)) {
        return
      }
      this.$emit('confirm', content)
      this.close()
    },
    init(entityModelCode) {
      this.entityModelCode = entityModelCode
      this.visible = true
    },
    async getConditions(params) {
      // 优先根据缓存的对应关系，获取到属性对应的集合类型编码
      const collectionType = this.propertyCollectionTypeMapping[params.property]

      if (collectionType && collectionType.length > 0) {
        if (collectionType === 'Organization') {
          return new Promise((resolve, reject) => {
            this.$api.system.organization.cascader().then((res) => {
              // 注意，筛选器需要的数据结构是一个数组，后端返回的组织机构树是一个对象
              let cascederData = []
              cascederData.push(res.data)
              res.data = cascederData
              resolve(res)
            })
          })
        }
      } else {
        // 然后根据缓存的对应关系，获取到属性对应的数据字典类型编码
        const dictionaryType = this.propertyDictionaryTypeMapping[params.property]
        return new Promise((resolve, reject) => {
          this.$api.system.dictionaryType.getItem(dictionaryType).then((res) => {
            if (res.data.length > 0) {
              res.data = res.data.map((item) => {
                // 后端范围的value是id,code才是编码，因此需要转换
                return { label: item.label, value: item.code }
              })
            }
            resolve(res)
          })
        })
      }
    },
    async getOptions() {
      const options =
        await this.$api.entityconfig.entityModelProperty.getFullPropertyListForFilterByEntityModelCode(
          this.entityModelCode
        )

      let data = options.data

      //遍历，日期类型追加相关设置
      data.forEach((item) => {
        if (item.renderType == 'DATE') {
          item.includeOperator = {
            dateOperator: ['date']
          }
          item.datePanel = {
            excludeManuals: -1,
            excludeShortcuts: -1,
            pickerType: 'datetime'
          }
        }
      })

      // 缓存属性与集合类型映射
      this.propertyCollectionTypeMapping = data.reduce((acc, cur) => {
        acc[cur.value] = cur.collectionType
        return acc
      }, {})

      // 缓存属性与数据字典类型映射
      this.propertyDictionaryTypeMapping = data.reduce((acc, cur) => {
        acc[cur.value] = cur.dictionaryType
        return acc
      }, {})

      return new Promise((resolve, reject) => {
        resolve({
          data: {
            options: options.data,
            operators: {
              Text: [
                {
                  label: '等于',
                  value: 'EQ',
                  style: 'noop'
                },
                {
                  label: '模糊匹配',
                  value: 'LK',
                  style: 'noop'
                },

                {
                  label: '以…开始',
                  value: 'RL',
                  style: 'noop'
                },
                {
                  label: '以…结束',
                  value: 'LL',
                  style: 'noop'
                },
                {
                  label: '不等于',
                  value: 'NE',
                  style: 'noop'
                },
                {
                  label: '为空',
                  value: 'NL',
                  style: 'none'
                },
                {
                  label: '不为空',
                  value: 'NN',
                  style: 'none'
                }
              ],
              Number: [
                {
                  label: '等于',
                  value: 'EQ',
                  style: 'noop'
                },
                {
                  label: '大于',
                  value: 'GT',
                  style: 'noop'
                },
                {
                  label: '大于等于',
                  value: 'GE',
                  style: 'noop'
                },
                {
                  label: '小于',
                  value: 'LT',
                  style: 'noop'
                },
                {
                  label: '小于等于',
                  value: 'LE',
                  style: 'noop'
                },

                {
                  label: '不等于',
                  value: 'NE',
                  style: 'noop'
                },
                {
                  label: '为空',
                  value: 'NL',
                  style: 'none'
                },
                {
                  label: '不为空',
                  value: 'NN',
                  style: 'none'
                }
              ],
              DateTime: [
                {
                  label: '大于等于',
                  value: 'GE',
                  style: 'noop'
                },
                {
                  label: '小于等于',
                  value: 'LE',
                  style: 'noop'
                },
                {
                  label: '为空',
                  value: 'NL',
                  style: 'none'
                },
                {
                  label: '不为空',
                  value: 'NN',
                  style: 'none'
                }
              ],
              DataDictionary: [
                {
                  label: '等于',
                  value: 'EQ',
                  style: 'noop'
                },
                {
                  label: '不等于',
                  value: 'NQ',
                  style: 'noop'
                }
              ],
              Collection: [
                {
                  label: '包含在……',
                  value: 'IN',
                  style: 'noop'
                }
              ],
              Contain: [
                {
                  label: '包含',
                  value: 'CT',
                  style: 'noop'
                }
              ],
              User: [
                {
                  label: '等于',
                  value: 'EQ',
                  style: 'noop'
                }
              ]
            }
          }
        })
      })
    }
  }
}
</script>

<style></style>
