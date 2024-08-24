<template>
  <Dialog title="配置数据权限" v-model="visible" fullscreen>
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="100px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="模型标识" prop="modelId" v-show="false">
        <el-input v-model="entityData.modelId" />
      </el-form-item>
      <el-form-item label="表名" prop="tableName" v-show="false">
        <el-input v-model="entityData.tableName" />
      </el-form-item>
      <el-form-item label="配置">
        <EverrightFilter
          lang="zh-cn"
          @listener="handleListener"
          type="matrix"
          :getOptions="getOptions"
          :getConditions="getConditions"
          ref="everrightFilter"
        />
      </el-form-item>
      <el-form-item label="规则" prop="rule">
        <el-input v-model="entityData.rule" type="textarea" rows="10" />
      </el-form-item>
      <el-form-item label="sql片段" prop="sqlPart">
        <el-input v-model="entityData.sqlPart" type="textarea" rows="10" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="generateRule">生成规则</el-button>
      <el-button type="primary" @click="generateSqlPart">生成SQL</el-button>

      <el-button type="primary" @click="save" v-permission="pageCode + mode" v-show="mode != 'view'"
        >保存</el-button
      >
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { detailMixin } from '@/mixin/detailMixin.js'
import { EverrightFilter } from 'everright-filter'
import 'everright-filter/dist/style.css'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityModelDataPermission'
export default {
  name: ENTITY_TYPE + '-detail',
  components: { EverrightFilter },
  mixins: [detailMixin],
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
      },
      // 属性与字典类型映射
      propertyDictionaryTypeMapping: {},
      // 属性与集合类型映射
      propertyCollectionTypeMapping: {}
    }
  },
  methods: {
    getOrInit(id) {
      this.mode = 'modify'
      this.api.getOrInit(id).then((res) => {
        this.loadData(res.data)
      })
    },
    handleListener({ type, data }) {
      // if (type == 'triggerChange') {
      //   this.generateRule()
      // }
    },
    // 生成规则
    generateRule() {
      this.entityData.rule = JSON.stringify(this.$refs.everrightFilter.getData(), null, '\t')
    },
    // 生成SQL片段
    generateSqlPart() {
      // 先调用一次生成规则，避免配置调整了规则未同步
      this.generateRule()
      // 调用后端服务转换
      this.api.generateSqlPart(this.entityData.modelId, this.entityData.rule).then((res) => {
        this.entityData.sqlPart = res.data
      })
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
              // 虚拟当前用户部门节点
              const currentDepartment = { value: '{@CurrentDepartment@}', label: '当前用户部门' }
              cascederData.push(currentDepartment)
              cascederData.push(res.data)
              res.data = cascederData
              resolve(res)
            })
          })
        } else if (collectionType === 'UserGroup') {
          return new Promise((resolve, reject) => {
            this.$api.system.userGroup.cascader().then((res) => {
              // 注意，筛选器需要的数据结构是一个数组，后端返回的组织机构树是一个对象
              res.data = [res.data]
              resolve(res)
            })
          })
        } else if (collectionType === 'User') {
          return new Promise((resolve, reject) => {
            // 虚拟一个当前用户选项
            const data = [{ label: '当前用户', value: '{@CurrentUser@}' }]
            const res = { data }
            resolve(res)
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
      const options = await this.$api.entityconfig.entityModelProperty.getFullPropertyListForFilter(
        this.entityData.modelId
      )
      // 增加虚拟的条件：当前用户组
      const currentUserGroup = {
        label: '【当前用户组】',
        renderType: 'CASCADER',
        operatorKey: 'Contain',
        value: '{@CurrentUserGroup@}',
        collectionType: 'UserGroup',
        multiple: false
      }
      options.data.push(currentUserGroup)

      const data = options.data

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

      if (this.entityData.rule && this.entityData.rule !== '') {
        this.$refs.everrightFilter.setData(JSON.parse(this.entityData.rule))
      }

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
