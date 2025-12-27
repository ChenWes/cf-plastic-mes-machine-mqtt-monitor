package com.cf.common.core.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.StringUtils;
import com.cf.common.utils.sql.SqlUtil;

/**
 * 分页数据
 *
 * @author WesChen
 */
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageIndex;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderBy;

    /** 是否分页 */
    private boolean pagination;


    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderBy))
        {
            return "";
        }
        JSONObject orderByObj= JSON.parseObject(StringUtils.toUnderlineCase(orderBy));
        String orderByStr="";
        for(String key:orderByObj.keySet())
        {
            String sort=orderByObj.getString(key);
            if(sort!=null && sort.toLowerCase().contains("asc"))
            {
                sort="asc";
            }
            else if(sort!=null && sort.toLowerCase().contains("desc"))
            {
                sort="desc";
            }
            else
            {
                sort="";
            }
            if(StringUtils.isNotEmpty(sort)) {
                orderByStr += orderByStr.length() > 0 ? (", " + key + " " + sort) :( key + " " + sort);
            }
            orderByStr = SqlUtil.escapeOrderBySql(orderByStr);
        }
        return orderByStr;


    }

    public boolean getPagination()
    {
        return pagination;
    }

    public void setPagination(boolean pagination)
    {
        this.pagination = pagination;
    }

    public Integer getPageIndex()
    {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


}
