package tech.abc.platform.common.base;

import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.common.utils.DictionaryUtil;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 控制器基类
 *
 * @author wqliu
 * @date 2023-03-06
 */
public class BaseController {

    @Autowired
    protected MapperFacade mapperFacade;

    @Autowired
    protected QueryGenerator queryGenerator;

    @Autowired
    protected DictionaryUtil dictionaryUtil;

    @Autowired
    protected CacheUtil cacheUtil;


    /**
     * 分页
     *
     * @param binder
     */
    @InitBinder("pageInfo")
    public void initPageInfo(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page_");
    }

    /**
     * 排序
     *
     * @param binder
     */
    @InitBinder("sortInfo")
    public void initAdmin(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("sort_");
    }


}
