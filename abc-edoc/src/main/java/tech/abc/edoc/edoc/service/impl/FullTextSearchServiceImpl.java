package tech.abc.edoc.edoc.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.edoc.edoc.entity.PopSearchScrollHits;
import tech.abc.edoc.edoc.enums.DocumentPermissionItemEnum;
import tech.abc.edoc.edoc.esrepository.DocumentRepository;
import tech.abc.edoc.edoc.service.FolderPermissionService;
import tech.abc.edoc.edoc.service.FullTextSearchService;


import java.util.ArrayList;
import java.util.List;

/**
 * @author wqliu
 * @date 2021-4-6 11:00
 **/
@Slf4j
@Service
public class FullTextSearchServiceImpl implements FullTextSearchService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Autowired
    private FolderPermissionService folderPermissionService;

    @Override
    public PopSearchScrollHits fullTextSearch(String searchText, String scrollId) {
        //参数定义
        //页面大小
        int pageSize = 10;
        //页码
        int pageNo = 0;

        //返回结果
        PopSearchScrollHits result=new PopSearchScrollHits();
        List<SearchHit<Document>> data = new ArrayList<>();

        while (data.size() < pageSize) {
            //获取单页数据
            SearchScrollHits<Document> searchHits = getPageData(searchText, pageSize, pageNo, scrollId);
            //未查询到数据后退出循环
            if (!searchHits.hasSearchHits()) {
                break;
            }
            //更新游标
            scrollId = searchHits.getScrollId();
            //将有权限的文档放入结果列表
            handlePermissionFilter(data, searchHits);
        }

        result.setData(data);
        result.setScrollId(scrollId);
        log.info(JSON.toJSONString(result));
        return result;
    }

    private void handlePermissionFilter(List<SearchHit<Document>> data, SearchScrollHits<Document> searchHits) {

        searchHits.getSearchHits().stream().forEach(x -> {
            String folderId = x.getContent().getParentId();
            if (folderPermissionService.checkPermissionFlag(folderId,
                    DocumentPermissionItemEnum.PREVIEW_DOCUMENT.name())) {
                data.add(x);
            }
        });

    }

    private SearchScrollHits<Document> getPageData(String searchText, int pageSize, int pageNo, String scrollId) {
        //限制检索耗时，单位毫秒
        long millis = 5000;
        // 索引库
        IndexCoordinates index = IndexCoordinates.of("document");


        if (StringUtils.isBlank(scrollId)) {
            //首次查询，构造查询条件
            // 分页条件
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
            // 高亮条件
            HighlightBuilder highlightBuilder = getHighlightBuilder("name", "content");
            // 排序条件
            FieldSortBuilder sortBuilder = SortBuilders.fieldSort("id.keyword").order(SortOrder.DESC);

            MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(searchText, "name", "content");
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withHighlightBuilder(highlightBuilder)
                    .withPageable(pageRequest)
                    .withSort(sortBuilder).build();
            return esTemplate.searchScrollStart(millis, searchQuery, Document.class, index);

        } else {
            //非首次查询
            return esTemplate.searchScrollContinue(scrollId, millis, Document.class, index);
        }


    }


    /**
     * 设置高亮字段
     *
     * @param fields
     * @return
     */
    private HighlightBuilder getHighlightBuilder(String... fields) {
        //生成高亮查询器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String field : fields) {
            //高亮查询字段
            highlightBuilder.field(field);
        }
        //如果要多个字段高亮,这项要为false
        highlightBuilder.requireFieldMatch(false);
        //高亮设置
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        // 一段 fragment 包含多少个字符
        highlightBuilder.fragmentSize(250);
        // 返回结果最多可以包含几段不连续的文字。默认是5。
        highlightBuilder.numOfFragments(1);
        //即使字段中没有关键字命中，也可以返回一段文字，该参数表示从开始多少个字符被返回
        highlightBuilder.noMatchSize(100);

        return highlightBuilder;
    }


}
