<template>
  <ContentWrap>
    <el-tree
      ref="tree"
      class="aside-tree"
      :data="treeData"
      node-key="id"
      highlight-current
      :default-expanded-keys="cacheTreeExpandedKeys"
      @node-contextmenu="folderContextMenuShow"
      @current-change="handleTreeSelectChange"
      @node-expand="handleNodeExpand"
      @node-collapse="handleNodeCollapse"
    >
      <template #default="{ node, data }">
        <span class="el-tree-node__label">
          <el-icon v-if="node.expanded"><FolderOpened /></el-icon>
          <el-icon v-else><Folder /></el-icon>
          {{ data.label }}
        </span>
      </template>
    </el-tree>

    <!-- 文件夹树右键菜单 -->
    <el-menu
      v-show="folderContextMenu.visible"
      ref="folderContextMenu"
      :style="{
        width: '150px',
        left: folderContextMenu.left + 'px',
        top: folderContextMenu.top + 'px',
        position: 'fixed',
        cursor: 'pointer',
        'z-index': 9999
      }"
      popper-append-to-body
      @mouseleave="folderContextMenu.visible = false"
      @select="folderContextMenuSelect"
    >
      <el-menu-item
        v-if="documentPermissionList.includes($constant.CREATE_FOLDER)"
        :index="$constant.CREATE_FOLDER"
      >
        <el-icon><FolderAdd /></el-icon>
        <template #title>创建</template>
      </el-menu-item>

      <el-menu-item
        v-if="documentPermissionList.includes($constant.RENAME_FOLDER)"
        :index="$constant.RENAME_FOLDER"
      >
        <el-icon><Edit /></el-icon>
        <template #title>更名</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.REMOVE_FOLDER)"
        :index="$constant.REMOVE_FOLDER"
      >
        <el-icon><FolderRemove /></el-icon>
        <template #title>删除</template>
      </el-menu-item>

      <el-menu-item
        v-if="documentPermissionList.includes($constant.COPY_FOLDER)"
        :index="$constant.COPY_FOLDER"
      >
        <el-icon><CopyDocument /></el-icon>
        <template #title>复制到…</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.MOVE_FOLDER)"
        :index="$constant.MOVE_FOLDER"
      >
        <el-icon><Position /></el-icon>
        <template #title>移动到…</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.GRANT_PERMISSION_BY_USER_GROUP)"
        :index="$constant.GRANT_PERMISSION_BY_USER_GROUP"
      >
        <el-icon><Setting /></el-icon>
        <template #title>按用户组授权</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.GRANT_PERMISSION_BY_ORGANIZATION)"
        :index="$constant.GRANT_PERMISSION_BY_ORGANIZATION"
      >
        <el-icon><Setting /></el-icon>
        <template #title>按组织授权</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.UPLOAD_DOCUMENT)"
        :index="$constant.UPLOAD_DOCUMENT"
      >
        <el-icon><Upload /></el-icon>
        <template #title>上传文档</template>
      </el-menu-item>
      <el-menu-item v-if="documentPermissionList.length === 0" :index="$constant.NO_MENU_AVAILABLE">
        <el-icon><Close /></el-icon>
        <template #title>无可用菜单</template>
      </el-menu-item>
    </el-menu>
    <AddFolder ref="addFolder" @refresh="folderCreateCallback" />
    <RenaneFolder ref="RenaneFolder" @refresh="folderRenameCallback" />
    <CopyFolder ref="copyFolder" @confirm="folderCopyCallback" />
    <MoveFolder ref="moveFolder" @confirm="folderMoveCallback" />
    <GrantPermissionByUserGroup ref="grantPermissionByUserGroup" />
    <GrantPermissionByOrganization ref="grantPermissionByOrganization" />
    <FileUploader ref="fileUploader" @refresh="load" />
  </ContentWrap>
</template>

<script>
import { treeMixin } from '@/mixin/treeMixin.js'
import AddFolder from './add.vue'
import RenaneFolder from './rename.vue'
import CopyFolder from './select.vue'
import MoveFolder from './selectWithPermissionOption.vue'
import GrantPermissionByUserGroup from './userGroupPermission.vue'
import GrantPermissionByOrganization from './organizationPermission.vue'
import FileUploader from '../document/uploader.vue'
const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'folder'
export default {
  name: ENTITY_TYPE + '-tree',
  components: {
    AddFolder,
    RenaneFolder,
    CopyFolder,
    MoveFolder,
    GrantPermissionByUserGroup,
    GrantPermissionByOrganization,
    FileUploader
  },
  mixins: [treeMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 文件夹树右键菜单属性
      folderContextMenu: {
        // 是否可见
        visible: false,
        // 左边距
        left: 0,
        // 上边距
        top: 0
      },
      parentId: '',
      parentName: '',
      // 权限项列表
      documentPermissionList: [],
      // 当前选中记录
      selectedRow: {}
    }
  },
  methods: {
    // 文件夹树右键菜单显示
    folderContextMenuShow(mouseEvent, data) {
      // 设置右键点击的树节点为当前树的选中节点，后续操作如更名是取当前节点，不设置将会产生错位问题
      this.$refs.tree.setCurrentKey(data.id)
      this.parentId = data.id

      // 动态获取权限
      this.$api.edoc.folderPermission.getFolderPermissionForUser(data.id).then((res) => {
        this.setFolderContextMenu(res.data, mouseEvent)
      })
    },
    // 设置文件夹右键菜单位置
    setFolderContextMenu(data, mouseEvent) {
      this.documentPermissionList = data
      this.folderContextMenu.visible = true
      this.$nextTick(() => {
        this.folderContextMenu.left = mouseEvent.clientX - 10
        const menuHeight = this.$refs.folderContextMenu.$el.clientHeight
        const areaHeight = document.documentElement.clientHeight

        if (mouseEvent.clientY + menuHeight > areaHeight) {
          // 当鼠标点击的y坐标加上菜单高度超出区域高度时
          this.folderContextMenu.top = mouseEvent.clientY - menuHeight + 25
        } else {
          this.folderContextMenu.top = mouseEvent.clientY - 25
        }
      })
    },
    // 文件夹树右键菜单命令
    folderContextMenuSelect(command) {
      if (command === this.$constant.CREATE_FOLDER) {
        this.addFolder()
      } else if (command === this.$constant.RENAME_FOLDER) {
        this.renameFolder()
      } else if (command === this.$constant.REMOVE_FOLDER) {
        this.removeFolder()
      } else if (command === this.$constant.COPY_FOLDER) {
        this.copyFolder()
      } else if (command === this.$constant.MOVE_FOLDER) {
        this.moveFolder()
      } else if (command === this.$constant.GRANT_PERMISSION_BY_USER_GROUP) {
        this.grantPermissionByUserGroup()
      } else if (command === this.$constant.GRANT_PERMISSION_BY_ORGANIZATION) {
        this.grantPermissionByOrganization()
      } else if (command === this.$constant.UPLOAD_DOCUMENT) {
        this.uploadDocument()
      }
      // 隐藏右键菜单
      this.folderContextMenu.visible = false
    },
    // 刷新树
    refresh() {
      this.load()
    },
    // 新建文件夹
    addFolder() {
      this.$refs.addFolder.init({ id: this.currentId })
    },
    // 更名文件夹
    renameFolder() {
      this.$refs.modifyPage.init(this.currentId)
    },
    // 新建文件夹后回调
    folderCreateCallback(data) {
      // 构造树节点
      const node = { id: data.id, label: data.name }
      // 添加到树
      this.$refs.tree.append(node, this.$refs.tree.getCurrentNode())
    },
    // 更名文件夹后回调
    folderRenameCallback(data) {
      this.load()
    },
    // 移除文件夹
    removeFolder() {
      this.$confirm('此操作将删除该文件夹下所有内容, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const node = this.$refs.tree.getCurrentNode()
          this.$api.edoc.folder.remove(node.id).then(() => {
            // 删除完成后从树上移除节点
            this.$refs.tree.remove(node)
            // 设置被删除节点的上级为当前节点
            this.$refs.tree.setCurrentKey(node.parentId)
            this.parentId = node.parentId
            this.load()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 复制文件夹
    copyFolder() {
      this.$refs.copyFolder.show(this.$refs.tree.getCurrentKey())
    },
    // 复制文件夹回调
    folderCopyCallback(parentId) {
      this.$api.edoc.folder.copy(this.currentId, parentId).then(() => {
        this.load()
      })
    },
    // 移动文件夹
    moveFolder() {
      this.$refs.moveFolder.show(this.$refs.tree.getCurrentKey())
    },
    // 移动文件夹成功后回调
    folderMoveCallback(parentId, retainPermission) {
      this.$api.edoc.folder.move(this.currentId, parentId, retainPermission).then(() => {
        this.load()
      })
    },
    // 按用户组授权
    grantPermissionByUserGroup() {
      this.$refs.grantPermissionByUserGroup.show(this.$refs.tree.getCurrentNode())
    },
    // 按组织机构授权
    grantPermissionByOrganization() {
      this.$refs.grantPermissionByOrganization.show(this.$refs.tree.getCurrentNode())
    },
    // 显示上传
    uploadDocument() {
      const targetFolderId = this.$refs.tree.getCurrentKey()
      if (!targetFolderId) {
        this.$message.info('请选择目标文件夹')
        return
      }

      this.$refs.fileUploader.show(targetFolderId)
    }
  }
}
</script>

<style scoped lang="less">
:deep(.el-menu-item) {
  --el-menu-item-height: 36px !important;
}
</style>
