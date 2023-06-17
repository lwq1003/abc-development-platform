<template>
  <div class="w-full">
    <el-input disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="桌面设置" v-model="visible" fullscreen>
      <el-row :gutter="10">
        <el-col :span="24">
          <div style="display: flex; justify-content: center" class="mb-3 mt-0">
            <span style="width: 100px">桌面模板</span>
            <DesktopTemplate style="width: 230px" @my-change="desktopTemplateChange" />
            <el-button type="primary" @click="apply">应用模板</el-button>
            <el-button type="primary" @click="save">保存</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="10" type="flex">
        <el-col :span="4">
          <el-menu default-active="2" class="el-menu-vertical-demo">
            <el-sub-menu
              v-for="category in categoryList"
              :key="category.code"
              :index="category.code"
            >
              <template #title>
                <span>{{ category.label }}</span>
              </template>
              <el-menu-item v-for="(portlet, index) in category.portletList" :key="index">
                <span
                  @drag="drag($event, portlet)"
                  @dragend="dragend($event, portlet)"
                  draggable="true"
                  unselectable="on"
                  >{{ portlet.name }}</span
                >
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-col>

        <el-col :span="20">
          <div id="content">
            <grid-layout
              ref="gridlayout"
              v-model:layout="layout"
              :col-num="12"
              :row-height="30"
              :is-draggable="true"
              :is-resizable="true"
              :vertical-compact="true"
              :use-css-transforms="true"
              style="height: 700px"
            >
              <grid-item
                ref="gridItem"
                :key="item.i"
                v-for="item in layout"
                :x="item.x"
                :y="item.y"
                :w="item.w"
                :h="item.h"
                :i="item.i"
              >
                <PortletContainer
                  :id="item.i"
                  v-model:config="item.config"
                  @remove="remove"
                  :toolbarFlag="true"
                />
              </grid-item>
            </grid-layout>
          </div>
        </el-col>
      </el-row>
    </Dialog>
  </div>
</template>

<script>
import PortletContainer from '@/components/abc/PortletContainer/index.vue'
import DesktopTemplate from '@/modules/support/view/desktopTemplate/reference.vue'
import { Dialog } from '@/components/abc/Dialog'
import { GridLayout, GridItem } from 'vue-grid-layout'
import { uuid } from '@/utils'
let mouseXY = { x: null, y: null }
let DragPos = { x: null, y: null, w: 4, h: 6, i: null }
export default {
  components: {
    GridLayout,
    GridItem,
    PortletContainer,
    Dialog,
    DesktopTemplate
  },
  props: {
    modelValue: {
      type: String
    }
  },
  data() {
    return {
      visible: false,
      categoryList: [],
      templateConfig: [],
      layout: []
    }
  },
  mounted() {
    document.addEventListener(
      'dragover',
      function (e) {
        mouseXY.x = e.clientX
        mouseXY.y = e.clientY
      },
      false
    )
  },
  methods: {
    init() {
      // 获取类别
      this.$api.system.dictionaryType.getItem('PortletCategory').then((res) => {
        const categoryArray = res.data
        // 获取组件
        this.$api.support.portlet.getPortletList().then((resPortlet) => {
          const data = resPortlet.data
          categoryArray.forEach((category) => {
            category.portletList = data.filter((x) => x.category == category.code)
          })
          this.categoryList = categoryArray
        })
      })
      // 初始化桌面
      if (this.modelValue) {
        this.layout = JSON.parse(this.modelValue)
      }
      this.visible = true
    },
    drag(e, portlet) {
      const w = portlet.width
      const h = portlet.height
      const portletCode = portlet.code
      DragPos.w = w
      DragPos.h = h
      let parentRect = document.getElementById('content').getBoundingClientRect()
      let mouseInGrid = false
      if (
        mouseXY.x > parentRect.left &&
        mouseXY.x < parentRect.right &&
        mouseXY.y > parentRect.top &&
        mouseXY.y < parentRect.bottom
      ) {
        mouseInGrid = true
      }
      if (mouseInGrid === true && this.layout.findIndex((item) => item.i === portletCode) === -1) {
        this.layout.push({
          x: (this.layout.length * 2) % (this.colNum || 12),
          y: this.layout.length + (this.colNum || 12),
          w: w,
          h: h,
          i: portletCode
        })
      }
      let index = this.layout.findIndex((item) => item.i === portletCode)

      if (index !== -1) {
        try {
          this.$refs.gridlayout.$refs[this.layout.length].$refs.item.style.display = 'none'
        } catch {}

        let el = this.$refs.gridItem[index]
        // el.dragging = { top: mouseXY.y - parentRect.top, left: mouseXY.x - parentRect.left }
        let new_pos = el.calcXY(mouseXY.y - parentRect.top, mouseXY.x - parentRect.left)

        if (mouseInGrid === true) {
          this.$refs.gridlayout.dragEvent('dragstart', portletCode, new_pos.x, new_pos.y, h, w)
          DragPos.i = String(index)
          DragPos.x = this.layout[index].x
          DragPos.y = this.layout[index].y
        }
        if (mouseInGrid === false) {
          this.$refs.gridlayout.dragEvent('dragend', portletCode, new_pos.x, new_pos.y, h, w)
          this.layout = this.layout.filter((obj) => obj.i !== portletCode)
        }
      }
    },
    dragend(e, portlet) {
      const w = portlet.width
      const h = portlet.height
      const portletCode = portlet.code

      // 精简组件信息，避免过多存储
      let paramList = []
      if (portlet.paramList) {
        paramList = portlet.paramList.map((x) => {
          return {
            name: x.name,
            code: x.code,
            value: x.value
          }
        })
      }
      const portletConfig = {
        name: portlet.name,
        code: portlet.code,
        paramList: paramList
      }

      let parentRect = document.getElementById('content').getBoundingClientRect()
      let mouseInGrid = false
      if (
        mouseXY.x > parentRect.left &&
        mouseXY.x < parentRect.right &&
        mouseXY.y > parentRect.top &&
        mouseXY.y < parentRect.bottom
      ) {
        mouseInGrid = true
      }
      if (mouseInGrid === true) {
        this.$refs.gridlayout.dragEvent('dragend', portletCode, DragPos.x, DragPos.y, h, w)
        this.layout = this.layout.filter((obj) => obj.i !== portletCode)

        this.layout.push({
          x: DragPos.x,
          y: DragPos.y,
          w: w,
          h: h,
          i: uuid(),
          config: portletConfig
        })
      }
    },
    remove(id) {
      this.$confirm('此操作将移除组件, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.layout = this.layout.filter((obj) => obj.i !== id)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    save() {
      this.$emit('update:modelValue', JSON.stringify(this.layout))
      this.visible = false
    },
    desktopTemplateChange(id, entity) {
      if (entity) {
        this.templateConfig = JSON.parse(entity.config)
      } else {
        this.templateConfig = []
      }
    },
    // 应用模板配置
    apply() {
      if (this.templateConfig.length == 0) {
        this.$message.warning('请先选择桌面模板')
        return
      }
      if (this.layout.length == 0) {
        this.layout = this.templateConfig
      } else {
        this.$confirm('此操作将使用模板进行初始化，原配置将被替换, 是否继续?', '确认', {
          type: 'warning'
        })
          .then(() => {
            this.layout = this.templateConfig
          })
          .catch(() => {
            this.$message.info('已取消')
          })
      }
    },
    // 清空配置
    clear() {
      this.$confirm('此操作将清空桌面配置, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$emit('update:modelValue', '[]')
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    }
  }
}
</script>

<style scoped>
.droppable-element {
  width: 150px;
  text-align: center;
  background: gray;
  border: 1px solid black;
  margin: 10px 0;
  padding: 10px;
}
.vue-grid-layout {
  background: #eee;
}
.vue-grid-item:not(.vue-grid-placeholder) {
  background: #ccc;
  border: 1px solid black;
}
.vue-grid-item .resizing {
  opacity: 0.9;
}
.vue-grid-item .static {
  background: #cce;
}
.vue-grid-item .text {
  font-size: 24px;
  text-align: center;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  height: 100%;
  width: 100%;
}
.vue-grid-item .no-drag {
  height: 100%;
  width: 100%;
}
.vue-grid-item .minMax {
  font-size: 12px;
}
.vue-grid-item .add {
  cursor: pointer;
}
.vue-draggable-handle {
  position: absolute;
  width: 20px;
  height: 20px;
  top: 0;
  left: 0;
  background: url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='10' height='10'><circle cx='5' cy='5' r='5' fill='#999999'/></svg>")
    no-repeat;
  background-position: bottom right;
  padding: 0 8px 8px 0;
  background-repeat: no-repeat;
  background-origin: content-box;
  box-sizing: border-box;
  cursor: pointer;
}
.layoutJSON {
  background: #ddd;
  border: 1px solid black;
  margin-top: 10px;
  padding: 10px;
}
.layoutJSON {
  background: #ddd;
  border: 1px solid black;
  margin-top: 10px;
  padding: 10px;
}
.columns {
  -moz-columns: 120px;
  -webkit-columns: 120px;
  columns: 120px;
}
:deep(.el-dialog__body) {
  padding: 10px;
}
</style>
