package com.cf.common.core.page;

import com.cf.common.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author WesChen
 */
public class TableSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_INDEX = "pageIndex";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY = "orderBy";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";


    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageIndex(ServletUtils.getParameterToInt(PAGE_INDEX));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderBy(ServletUtils.getParameter(ORDER_BY));

        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
