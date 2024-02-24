package tech.abc.edoc.edoc.service;


import tech.abc.edoc.edoc.entity.PopSearchScrollHits;

/**
 * 全文搜索 服务类
 * @author wqliu
 * @date 2021-04-06
 *
 */
public interface FullTextSearchService {


    /**
     * 全文搜索
     * @param searchText 搜索内容
     * @param scrollId  游标
     * @return 搜索结果列表
     */
    PopSearchScrollHits fullTextSearch(String searchText, String scrollId);
}
