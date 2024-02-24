package tech.abc.edoc.edoc.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.edoc.edoc.entity.PopSearchScrollHits;
import tech.abc.edoc.edoc.service.FullTextSearchService;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;

/**
 * 全文搜索 前端控制器
 * @author wqliu
 * @date 2021-04-01
 *
 */
@RestController
@RequestMapping("/edoc/fullTextSearch")
@Slf4j
public class FullTextSearchController extends BaseController {

    @Autowired
    private FullTextSearchService fullTextSearchService;


    //region 基本操作

    /**
     * 查询
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/query")
    @SystemLog(value = "全文搜索-查询")
    @PreAuthorize("hasPermission(null,'edoc:fullTextSearch:query')")
    public ResponseEntity<Result> list(String searchText, String scrollId) {

        PopSearchScrollHits list=fullTextSearchService.fullTextSearch(searchText,scrollId);
        return ResultUtil.success(list);

    }

    //endregion

    //region 辅助操作



    //endregion
}
