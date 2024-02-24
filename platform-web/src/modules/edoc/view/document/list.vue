<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <el-input v-model="queryCondition.name" />
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="queryCondition.ignoreParent">查询全部</el-checkbox>
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="Refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'remove'" type="primary" icon="delete" @click="mixRemove"
        >删除</el-button
      >
      <el-button v-permission="pageCode + 'upload'" type="primary" icon="Upload" @click="upload"
        >上传</el-button
      >
      <el-button
        v-permission="pageCode + 'copy'"
        type="primary"
        icon="CopyDocument"
        @click="mixCopy"
        >复制</el-button
      >
      <el-button v-permission="pageCode + 'move'" type="primary" icon="Position" @click="mixMove"
        >移动</el-button
      >
    </div>

    <el-card style="width: 100%">
      <div style="margin-top: 0; margin-bottom: 10px; float: right">
        <ColumnsController :value="columnList" :tableKey="tableKey" />
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        style="width: 100%"
        highlight-current-row
        border
        :default-sort="{ prop: sortInfo.sort_field, order: sortInfo.sort_sortType }"
        @row-contextmenu="tableContextMenuShow"
        @sort-change="sortChange"
        @current-change="rowChange"
        @selection-change="selectionChange"
        @row-dblclick="rowDoubleClick"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column
          v-for="(item, index) in showCols"
          :key="index"
          :label="item.label"
          :prop="item.prop"
          :show-overflow-tooltip="item.showOverflowTooltip"
          :width="item.width"
          :formatter="item.formatFunc"
          :sortable="item.sortable"
        />
        <el-table-column fixed="right" label="操作" width="130">
          <template #default="scope">
            <el-button
              v-if="scope.row.objectType === $constant.POP_DOCUMENT_TYPE_DOCUMENT"
              v-permission="pageCode + 'download'"
              type="primary"
              link
              @click="downloadDocument(scope.row)"
              >下载</el-button
            >
            <el-button
              v-if="scope.row.objectType === $constant.POP_DOCUMENT_TYPE_FOLDER"
              type="primary"
              link
              @click="viewFolder(scope.row)"
              >查看</el-button
            >
            <el-button type="primary" link @click="addFavorite(scope.row)">收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
      <ListPager
        v-show="false"
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>
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
    <!-- 文档右键菜单 -->
    <el-menu
      v-show="tableContextMenu.visible"
      ref="tableContextMenu"
      :style="{
        width: '150px',
        left: tableContextMenu.left + 'px',
        top: tableContextMenu.top + 'px',
        position: 'fixed',
        cursor: 'pointer',
        'z-index': 9999
      }"
      popper-append-to-body
      @mouseleave="tableContextMenu.visible = false"
      @select="tableContextMenuSelect"
    >
      <el-menu-item
        v-if="documentPermissionList.includes($constant.PREVIEW_DOCUMENT)"
        :index="$constant.PREVIEW_DOCUMENT"
      >
        <el-icon><View /></el-icon>
        <template #title>预览</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.DOWNLOAD_DOCUMENT)"
        :index="$constant.DOWNLOAD_DOCUMENT"
      >
        <el-icon><Download /></el-icon>
        <template #title>下载</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.RENAME_DOCUMENT)"
        :index="$constant.RENAME_DOCUMENT"
      >
        <el-icon><Edit /></el-icon>
        <template #title>更名</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.UPDATE_DOCUMENT)"
        :index="$constant.UPDATE_DOCUMENT"
      >
        <el-icon><Edit /></el-icon>
        <template #title>更新</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.SHARE_DOCUMENT)"
        :index="$constant.SHARE_DOCUMENT"
      >
        <el-icon><Share /></el-icon>
        <template #title>分享</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.LOCK_DOCUMENT)"
        :index="$constant.LOCK_DOCUMENT"
      >
        <el-icon><Lock /></el-icon>
        <template #title>锁定</template>
      </el-menu-item>

      <el-menu-item
        v-if="documentPermissionList.includes($constant.UNLOCK_DOCUMENT)"
        :index="$constant.UNLOCK_DOCUMENT"
      >
        <el-icon><Unlock /></el-icon>
        <template #title>解锁</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.REMOVE_DOCUMENT)"
        :index="$constant.REMOVE_DOCUMENT"
      >
        <el-icon><DocumentRemove /></el-icon>
        <template #title>删除</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.COPY_DOCUMENT)"
        :index="$constant.COPY_DOCUMENT"
      >
        <el-icon><CopyDocument /></el-icon>
        <template #title>复制</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.MOVE_DOCUMENT)"
        :index="$constant.MOVE_DOCUMENT"
      >
        <el-icon><Position /></el-icon>
        <template #title>移动</template>
      </el-menu-item>
      <el-menu-item
        v-if="documentPermissionList.includes($constant.VIEW_DOCUMENT_VERSION)"
        :index="$constant.VIEW_DOCUMENT_VERSION"
      >
        <el-icon><Flag /></el-icon>
        <template #title>查看版本</template>
      </el-menu-item>

      <el-menu-item v-if="documentPermissionList.length === 0" :index="$constant.NO_MENU_AVAILABLE">
        <el-icon><Close /></el-icon>
        <template #title>无可用菜单</template>
      </el-menu-item>
    </el-menu>
    <AddFolder ref="addFolder" @refresh="folderCreateCallback" />
    <RenameFolder ref="renameFolder" @refresh="folderRenameCallback" />
    <CopyFolder ref="copyFolder" @confirm="folderCopyCallback" />
    <MoveFolder ref="moveFolder" @confirm="folderMoveCallback" />
    <GrantPermissionByUserGroup ref="grantPermissionByUserGroup" />
    <GrantPermissionByOrganization ref="grantPermissionByOrganization" />
    <FileUploader ref="fileUploader" @refresh="loadData" />

    <PreviewDocument ref="previewDocument" />
    <RenameDocument ref="renameDocument" @refresh="loadData" />
    <UpdateDocument ref="updateDocument" @refresh="loadData" />
    <CopyDocumentPage ref="copyDocument" @confirm="copyDocumentCallback" />
    <MoveDocument ref="moveDocument" @confirm="moveDocumentCallback" />
    <ShareDocument ref="shareDocument" />
    <DocumentVersion ref="documentVersion" />
    <MixCopy ref="mixCopy" @confirm="mixCopyCallback" />
    <MixMove ref="mixMove" @confirm="mixMoveCallback" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import AddFolder from '../folder/add.vue'
import RenameFolder from '../folder/rename.vue'
import CopyFolder from '../folder/select.vue'
import MoveFolder from '../folder/selectWithPermissionOption.vue'
import GrantPermissionByUserGroup from '../folder/userGroupPermission.vue'
import GrantPermissionByOrganization from '../folder/organizationPermission.vue'
import FileUploader from '../document/uploader.vue'
import PreviewDocument from './preview.vue'
import RenameDocument from './rename.vue'
import UpdateDocument from './update.vue'
// 加page后缀是因为防止与图标组件命名冲突
import CopyDocumentPage from '../folder/select.vue'
import MoveDocument from '../folder/select.vue'
import ShareDocument from './share.vue'
import DocumentVersion from '../documentVersion/list.vue'

import MixCopy from '../folder/select.vue'
import MixMove from '../folder/selectWithPermissionOption.vue'

const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'document'
export default {
  components: {
    AddFolder,
    RenameFolder,
    CopyFolder,
    MoveFolder,
    GrantPermissionByUserGroup,
    GrantPermissionByOrganization,
    FileUploader,
    PreviewDocument,
    RenameDocument,
    UpdateDocument,
    CopyDocumentPage,
    MoveDocument,
    ShareDocument,
    DocumentVersion,
    MixCopy,
    MixMove
  },
  mixins: [listMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 排序信息
      sortInfo: {
        sort_field: 'id',
        sort_sortType: 'descending'
      },
      columnList: [
        {
          prop: 'name',
          label: '名称',
          width: '300px',
          show: true,
          showOverflowTooltip: true,
          sortable: 'custom'
        },
        { prop: 'type', label: '类型', show: true, width: '100px', sortable: 'custom' },
        { prop: 'size', label: '大小', show: true, width: '90px', sortable: 'custom' },
        { prop: 'readCount', label: '查看量', show: true, width: '100px', sortable: 'custom' },
        { prop: 'downloadCount', label: '下载量', show: true, width: '100px', sortable: 'custom' },
        { prop: 'uploadTime', label: '上传时间', show: true, width: '180px', sortable: 'custom' },
        { prop: 'statusName', label: '状态', show: true, width: '100px' },
        { prop: 'lockUser', label: '锁定人', show: true, width: '100px' },
        { prop: 'lockTime', label: '锁定时间', show: true, width: '180px', sortable: 'custom' }
      ],
      queryCondition: {
        //默认值处理
        name: '',
        ignoreParent: false
      },
      // 文件夹树右键菜单属性
      folderContextMenu: {
        // 是否可见
        visible: false,
        // 左边距
        left: 0,
        // 上边距
        top: 0
      },
      // 文档右键菜单属性
      tableContextMenu: {
        // 是否可见
        visible: false,
        // 左边距
        left: 0,
        // 上边距
        top: 0
      },
      // 权限项列表
      documentPermissionList: [],
      // 当前选中记录
      selectedRow: {}
    }
  },

  methods: {
    loadData() {
      return new Promise((resolve) => {
        if (this.parentId) {
          this.loading = true
          this.$api.edoc.folder
            .getChildren(
              this.parentId,
              this.queryCondition.name,
              this.queryCondition.ignoreParent,
              this.sortInfo
            )
            .then((res) => {
              this.tableData = res.data
              resolve()
            })
            .finally(() => {
              this.loading = false
              this.currentId = this.$constant.NO_ITEM_SELECTED
            })
        }
      })
    },
    commonParamChange(param) {
      this.parentId = param.id
      this.queryCondition.parentId = param.id
    },
    // 表格右键菜单
    tableContextMenuShow(row, column, mouseEvent) {
      mouseEvent.preventDefault()
      this.selectedRow = row
      this.currentId = row.id
      if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_FOLDER) {
        // 文件夹
        this.$api.edoc.folderPermission.getFolderPermissionForUser(row.id).then((res) => {
          this.setFolderContextMenu(res.data, mouseEvent)
        })
      } else if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_DOCUMENT) {
        // 文档
        this.parentId = row.parentId
        this.$api.edoc.folderPermission.getDocumentPermissionForUser(row.id).then((res) => {
          this.setTableContextMenu(res.data, mouseEvent)
        })
      }
    },
    // 设置表格右键菜单位置
    setTableContextMenu(data, mouseEvent) {
      this.documentPermissionList = data
      this.tableContextMenu.visible = true
      this.$nextTick(() => {
        this.tableContextMenu.left = mouseEvent.clientX - 10
        const menuHeight = this.$refs.tableContextMenu.$el.clientHeight
        const areaHeight = document.documentElement.clientHeight

        if (mouseEvent.clientY + menuHeight > areaHeight) {
          // 当鼠标点击的y坐标加上菜单高度超出区域高度时
          this.tableContextMenu.top = mouseEvent.clientY - menuHeight + 25
        } else {
          this.tableContextMenu.top = mouseEvent.clientY - 25
        }
      })
    },
    // 表格右键菜单命令
    tableContextMenuSelect(command) {
      // 隐藏右键菜单
      this.tableContextMenu.visible = false

      if (command === this.$constant.RENAME_DOCUMENT) {
        this.renameDocument(this.selectedRow)
      } else if (command === this.$constant.REMOVE_DOCUMENT) {
        this.removeDocument(this.selectedRow)
      } else if (command === this.$constant.COPY_DOCUMENT) {
        this.copyDocument()
      } else if (command === this.$constant.MOVE_DOCUMENT) {
        this.moveDocument()
      } else if (command === this.$constant.DOWNLOAD_DOCUMENT) {
        this.downloadDocument(this.selectedRow)
      } else if (command === this.$constant.PREVIEW_DOCUMENT) {
        this.previewDocument(this.selectedRow)
      } else if (command === this.$constant.LOCK_DOCUMENT) {
        this.lockDocument(this.selectedRow)
      } else if (command === this.$constant.UNLOCK_DOCUMENT) {
        this.unlockDocument(this.selectedRow)
      } else if (command === this.$constant.UPDATE_DOCUMENT) {
        this.updateDocument(this.selectedRow)
      } else if (command === this.$constant.VIEW_DOCUMENT_VERSION) {
        this.viewDocumentVersion(this.selectedRow)
      } else if (command === this.$constant.SHARE_DOCUMENT) {
        this.shareDocument(this.selectedRow)
      }
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
    // 预览
    previewDocument(row) {
      this.$refs.previewDocument.show(row.id, row.name)
    },
    // 下载
    downloadDocument(row) {
      this.api.download(row.id)
    },
    // 更名文档
    renameDocument(row) {
      this.$refs.renameDocument.init(row.id)
    },
    // 更新文档
    updateDocument(row) {
      if (row.status === 'LOCKED') {
        this.$confirm('该文档当前是锁定状态, 锁定人可能在更改，是否继续?', '确认', {
          type: 'warning'
        })
          .then(() => {
            this.$refs.updateDocument.show(row.id)
          })
          .catch(() => {
            this.$message.info('已取消')
          })
      } else {
        this.$refs.updateDocument.show(row.id)
      }
    },
    // 锁定文档
    lockDocument(row) {
      if (row.status === 'LOCKED') {
        this.$confirm('该文档当前是锁定状态, 是否将锁定人更新为您?', '确认', {
          type: 'warning'
        })
          .then(() => {
            this.api.lock(row.id).then(() => {
              this.loadData()
            })
          })
          .catch(() => {
            this.$message.info('已取消')
          })
      } else {
        this.$confirm('此操作将锁定该文档, 是否继续?', '确认', {
          type: 'warning'
        })
          .then(() => {
            this.api.lock(row.id).then(() => {
              this.loadData()
            })
          })
          .catch(() => {
            this.$message.info('已取消')
          })
      }
    },
    // 解锁文档
    unlockDocument(row) {
      if (row.status === 'LOCKED') {
        this.$confirm('该操作将解除对文档的锁定，是否继续?', '确认', {
          type: 'warning'
        })
          .then(() => {
            this.api.unlock(row.id).then(() => {
              this.loadData()
            })
          })
          .catch(() => {
            this.$message.info('已取消')
          })
      } else {
        this.$message.info('当前文档未锁定，无需解锁')
      }
    },
    // 移除文档
    removeDocument(row) {
      this.$confirm('此操作将删除该文档, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.remove(row.id).then(() => {
            this.loadData()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 复制文档
    copyDocument() {
      this.$refs.copyDocument.show(this.parentId)
    },
    // 复制文档后回调
    copyDocumentCallback(parentId) {
      this.api.copy(this.currentId, parentId).then(() => {
        this.loadData()
      })
    },
    // 移动文档
    moveDocument() {
      this.$refs.moveDocument.show(this.parentId)
    },
    // 移动文档后回调
    moveDocumentCallback(parentId) {
      this.api.move(this.currentId, parentId).then(() => {
        this.loadData()
      })
    },
    // 分享
    shareDocument(row) {
      this.$refs.shareDocument.show(row.id, row.name)
    },
    // 查看版本列表
    viewDocumentVersion(row) {
      this.$refs.documentVersion.show(row.id, row.name)
    },
    // 混合删除
    mixRemove() {
      if (!this.checkSelected()) {
        return
      }

      this.$confirm('此操作将删除选中记录, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.edoc.folder.mixRemove(this.multipleSelection).then(() => {
            this.loadData()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 混合复制
    mixCopy() {
      if (!this.checkSelected()) {
        return
      }
      this.$refs.mixCopy.show(this.parentId)
    },
    // 混合复制后回调
    mixCopyCallback(parentId) {
      this.$api.edoc.folder.mixCopy(this.multipleSelection, parentId).then(() => {
        this.loadData()
      })
    },
    // 混合移动
    mixMove() {
      if (!this.checkSelected()) {
        return
      }
      this.$refs.mixMove.show(this.parentId)
    },
    // 混合复制后回调
    mixMoveCallback(parentId, retainPermission) {
      this.$api.edoc.folder.mixMove(this.multipleSelection, parentId, retainPermission).then(() => {
        this.loadData()
      })
    },
    // 文件夹树右键菜单命令
    folderContextMenuSelect(command) {
      if (command === this.$constant.CREATE_FOLDER) {
        this.createFolder()
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
    // 新建文件夹
    createFolder() {
      this.$refs.addFolder.init({ id: this.currentId })
    },
    // 更名文件夹
    renameFolder() {
      this.$refs.renameFolder.init(this.currentId)
    },
    // 新建文件夹后回调
    folderCreateCallback() {
      this.$emit('refresh')
    },
    // 更名文件夹后回调
    folderRenameCallback(data) {
      this.$emit('refresh')
      this.loadData()
    },
    // 移除文件夹
    removeFolder() {
      this.$confirm('此操作将删除该文件夹下所有内容, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.edoc.folder.remove(this.currentId).then(() => {
            this.$emit('refresh')
            this.loadData()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 复制文件夹
    copyFolder() {
      this.$refs.copyFolder.show(this.currentId)
    },
    // 复制文件夹回调
    folderCopyCallback(parentId) {
      this.$api.edoc.folder.copy(this.currentId, parentId).then(() => {
        this.$emit('refresh')
      })
    },
    // 移动文件夹
    moveFolder() {
      this.$refs.moveFolder.show(this.currentId)
    },
    // 移动文件夹成功后回调
    folderMoveCallback(parentId, retainPermission) {
      this.$api.edoc.folder.move(this.currentId, parentId, retainPermission).then(() => {
        this.$emit('refresh')
      })
    },
    // 按用户组授权
    grantPermissionByUserGroup() {
      this.$refs.grantPermissionByUserGroup.show(this.currentId)
    },
    // 按组织机构授权
    grantPermissionByOrganization() {
      this.$refs.grantPermissionByOrganization.show(this.currentId)
    },
    // 显示上传——文件夹右键菜单触发
    uploadDocument() {
      const targetFolderId = this.currentId
      if (!targetFolderId) {
        this.$message.info('请选择目标文件夹')
        return
      }

      this.$refs.fileUploader.show(targetFolderId)
    },
    // 显示上传——页面按钮触发
    upload() {
      const targetFolderId = this.parentId
      if (!targetFolderId) {
        this.$message.info('请选择目标文件夹')
        return
      }
      this.$refs.fileUploader.show(targetFolderId)
    },
    // 添加收藏
    addFavorite(row) {
      this.$confirm('是否添加到收藏?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const data = { objectId: row.id, objectType: row.objectType }
          this.$api.edoc.documentFavorite.add(data)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 处理表格双击
    rowDoubleClick(row) {
      if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_FOLDER) {
        // 文件夹
        this.viewFolder(row)
      } else if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_DOCUMENT) {
        // 文档,预览
        this.previewDocument(row)
      }
    },
    viewFolder(row) {
      // 文件夹，切换树节点
      this.$emit('changeTreeNode', row.id)
    }
  }
}
</script>

<style scoped lang="less">
:deep(.el-menu-item) {
  --el-menu-item-height: 36px !important;
}
</style>
