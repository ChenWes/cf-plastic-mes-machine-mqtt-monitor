package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 平台系统对象 platform_system
 *
 * @author wilfmao
 * @date 2024-10-31
 */
public class PlatformSystem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 系统ID
     */
    private Long systemId;

    /**
     * 系统编码
     */
    @Excel(name = "系统编码")
    private String systemCode;

    /**
     * 系统名称
     */
    @Excel(name = "系统名称")
    private String systemName;

    /**
     * 图标
     */
    @Excel(name = "图标")
    private String icon;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 标签,多个标签使用英文逗号分割
     */
    @Excel(name = "标签,多个标签使用英文逗号分割")
    private String tag;

    /**
     * 标签颜色
     */
    @Excel(name = "标签颜色")
    private String tagColor;

    /**
     * 系统URL
     */
    @Excel(name = "系统URL")
    private String url;

    /**
     * 链接的目标窗口或标签页
     */
    @Excel(name = "链接的目标窗口或标签页")
    private String hrefTarget;

    /**
     * 是否可用: 0 不可用 , 1 可用
     */
    @Excel(name = "是否可用: 0 不可用 , 1 可用")
    private Integer enableFlag;

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }

    public String getHrefTarget() {
        return hrefTarget;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("systemId", getSystemId())
                .append("systemCode", getSystemCode())
                .append("systemName", getSystemName())
                .append("icon", getIcon())
                .append("title", getTitle())
                .append("tag", getTag())
                .append("tagColor", getTagColor())
                .append("url", getUrl())
                .append("hrefTarget", getHrefTarget())
                .append("enableFlag", getEnableFlag())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
