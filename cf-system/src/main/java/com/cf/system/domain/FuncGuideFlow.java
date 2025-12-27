package com.cf.system.domain;

import com.cf.common.annotation.Excel;
import com.cf.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 功能指引流程图对象 func_guide_flow
 *
 * @author wilfmao
 * @date 2024-11-21
 */
public class FuncGuideFlow extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long guideFlowId;

    /**
     * 功能指引编码
     */
    @Excel(name = "功能指引编码")
    private String funcGuideCode;

    /**
     * 功能指引名称
     */
    @Excel(name = "功能指引名称")
    private String funcGuideName;

    /**
     * 流程配置JSON
     */
    @Excel(name = "流程配置JSON")
    private String flowJson;

    /**
     * 设置信息JSON
     */
    @Excel(name = "设置信息JSON")
    private String settingsJson;

    /**
     * 语言标识
     */
    @Excel(name = "语言标识")
    private String lang;

    public void setGuideFlowId(Long guideFlowId) {
        this.guideFlowId = guideFlowId;
    }

    public Long getGuideFlowId() {
        return guideFlowId;
    }

    public void setFuncGuideCode(String funcGuideCode) {
        this.funcGuideCode = funcGuideCode;
    }

    public String getFuncGuideCode() {
        return funcGuideCode;
    }

    public void setFuncGuideName(String funcGuideName) {
        this.funcGuideName = funcGuideName;
    }

    public String getFuncGuideName() {
        return funcGuideName;
    }

    public void setFlowJson(String flowJson) {
        this.flowJson = flowJson;
    }

    public String getFlowJson() {
        return flowJson;
    }

    public void setSettingsJson(String settingsJson) {
        this.settingsJson = settingsJson;
    }

    public String getSettingsJson() {
        return settingsJson;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("guideFlowId", getGuideFlowId())
                .append("funcGuideCode", getFuncGuideCode())
                .append("funcGuideName", getFuncGuideName())
                .append("flowJson", getFlowJson())
                .append("settingsJson", getSettingsJson())
                .append("lang", getLang())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
