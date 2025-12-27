package com.cf.common.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.HttpStatus;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.AjaxDetailResult;
import com.cf.common.core.domain.AjaxResult;
import com.cf.common.core.domain.ListRes;
import com.cf.common.core.page.PageDomain;
import com.cf.common.core.page.TableDataInfo;
import com.cf.common.core.page.TableSupport;
import com.cf.common.core.token.ITokenService;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.ServletUtils;
import com.cf.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * web层通用数据处理
 *
 * @author WesChen
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected ITokenService tokenService;

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(String orderByColumnName) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageIndex = pageDomain.getPageIndex();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = pageDomain.getOrderBy();
        if (StringUtils.isEmpty(orderBy) && StringUtils.isNotEmpty(orderByColumnName)) {
            orderBy = orderByColumnName;
        }
        if (StringUtils.isNotNull(pageIndex) && StringUtils.isNotNull(pageSize)) {
            //有分页信息则进行分页和排序
            PageHelper.startPage(pageIndex, pageSize, orderBy);
        } else {
            //无分页信息则只进行排序
            PageHelper.orderBy(orderBy);
        }
    }

    protected void startPage(String orderByColumnName, PageDomain pageDomain) {
        Integer pageIndex = pageDomain.getPageIndex();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = pageDomain.getOrderBy();
        if (StringUtils.isEmpty(orderBy) && StringUtils.isNotEmpty(orderByColumnName)) {
            orderBy = orderByColumnName;
        }
        if (StringUtils.isNotNull(pageIndex) && StringUtils.isNotNull(pageSize)) {
            //有分页信息则进行分页和排序
            PageHelper.startPage(pageIndex, pageSize, orderBy);
        } else {
            //无分页信息则只进行排序
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 修改排序字段,用于处理模型字段与数据库结构字段不匹配时，传入正确的排序字段
     *
     * @param targetField      前端传过来的排序字段
     * @param replacementFiled 覆盖前端排序字段的 数据库字段
     * @return
     */
    protected PageDomain overrideOrderBy(String targetField, String replacementFiled) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isEmpty(targetField) || StringUtils.isEmpty(replacementFiled)) {
            return pageDomain;
        }
        // 获取请求中原始的排序数据
        String orderBy = ServletUtils.getParameter(TableSupport.ORDER_BY);
        if (StringUtils.isEmpty(orderBy)) {
            return pageDomain;
        }
        JSONObject jsonObj = JSON.parseObject(orderBy);
        Object val = jsonObj.get(targetField);
        if (ObjectUtils.isEmpty(val)) {
            return pageDomain;
        }
        jsonObj.remove(targetField);
        jsonObj.put(replacementFiled, val);
        pageDomain.setOrderBy(JSON.toJSONString(jsonObj));
        return pageDomain;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg(MessageUtils.message(MessageCode.OPERATION_SUCCESS));
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    protected TableDataInfo getDataTable(ListRes<?> listRes) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg(MessageUtils.message(MessageCode.OPERATION_SUCCESS));
        rspData.setRows(new ArrayList<>());
        rspData.setTotal(0);
        if (listRes != null) {
            rspData.setRows(listRes.getRecords());
            rspData.setTotal(listRes.getTotal());
        }
        return rspData;
    }
    
    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 返回正常結果
     *
     * @param rows
     * @return
     */
    protected AjaxDetailResult toAjaxWithDetail(int rows) {
        return rows > 0 ? AjaxDetailResult.success() : AjaxDetailResult.error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }
}
