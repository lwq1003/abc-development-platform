<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="搜索内容">
          <el-input v-model="queryCondition.searchText" />
        </el-form-item>
        <el-form-item style="float: right">
          <el-button
            :v-permission="pageCode + functionCode"
            type="primary"
            icon="Search"
            @click="query"
            >查询</el-button
          >
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>

    <el-card style="width: 100%" class="mb-10px mt-10px">
      <el-card v-for="item in searchData" :key="item.id" class="result">
        <a :title="item.title" @click="previewDocument(item.content)">
          <span
            class="title"
            v-html="item.highlightFields.name ? item.highlightFields.name[0] : item.name"
          ></span>
        </a>

        <div
          class="content"
          v-html="item.highlightFields.content ? item.highlightFields.content[0] : ''"
        ></div>
      </el-card>
      <div>
        <el-button
          v-show="hasMoreData"
          type="primary"
          link
          class="m-2 float-right"
          @click="loadMore"
          >加载更多……</el-button
        >
        <el-button
          v-show="!hasMoreData"
          type="primary"
          link
          style="color: grey"
          class="m-2 float-right"
          >已加载所有数据</el-button
        >
      </div>
    </el-card>

    <PreviewDocument ref="previewDocument" />
  </ContentWrap>
</template>

<script>
import PreviewDocument from '../document/preview.vue'
import CollapseTab from '@/components/abc/CollapseTab/index.vue'
import QueryButton from '@/components/abc/QueryButton/index.vue'
export default {
  components: { PreviewDocument, CollapseTab, QueryButton },
  provide() {
    return {
      query: this.query
    }
  },
  data() {
    return {
      queryCondition: {
        searchText: ''
      },
      // 搜索结果
      searchData: [],
      loading: false,
      // 游标
      scrollId: '',
      // 还有更多数据
      hasMoreData: true
    }
  },

  methods: {
    // 查询
    query() {
      if (!this.queryCondition.searchText || this.queryCondition.searchText.length === 0) {
        this.$message.warning('请输入查询关键词')
        return
      }
      this.scrollId = ''
      this.hasMoreData = true
      this.$api.edoc.fullTextSearch
        .query(this.queryCondition.searchText, this.scrollId)
        .then((res) => {
          this.searchData = res.data.data
          this.scrollId = res.data.scrollId
        })
    },
    // 加载更多数据
    loadMore() {
      if (this.queryCondition.searchText) {
        this.loading = true
        this.$api.edoc.fullTextSearch
          .query(this.queryCondition.searchText, this.scrollId)
          .then((res) => {
            if (res.data.data.length > 0) {
              this.searchData = this.searchData.concat(res.data.data)
            } else {
              this.hasMoreData = false
            }
            this.scrollId = res.data.scrollId
          })
          .finally(() => {
            this.loading = false
          })
      }
    },

    // 预览文档
    previewDocument(row) {
      this.$refs.previewDocument.show(row.id, row.name)
    }
  }
}
</script>

<style scoped>
.title {
  line-height: 30px;
  border-bottom: 1px dashed;
  width: 100%;
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-size: 16px;
  font-weight: bold;
}
.content {
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
}
</style>
