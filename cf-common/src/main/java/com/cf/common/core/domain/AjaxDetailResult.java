package com.cf.common.core.domain;


import com.cf.common.constant.HttpStatus;
import com.cf.common.constant.MessageCode;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import lombok.Data;

@Data
public class AjaxDetailResult extends AjaxResult {

    //明细
    public static final String DATA_DETAIL_TAG = "data_detail";


    public AjaxDetailResult(int code, String msg, Object dataDetail) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);

        if (StringUtils.isNotNull(dataDetail)) {
            super.put(DATA_DETAIL_TAG, dataDetail);
        }
    }

    public static AjaxDetailResult success() {
        return AjaxDetailResult.success(MessageUtils.message(MessageCode.OPERATION_SUCCESS));
    }

    public static AjaxDetailResult success(String msg) {
        return AjaxDetailResult.success(msg, null);
    }

    public static AjaxDetailResult success(Object dataDetail) {
        return AjaxDetailResult.success(MessageUtils.message(MessageCode.OPERATION_SUCCESS), dataDetail);
    }

    public static AjaxDetailResult success(String msg, Object dataDetail) {
        return new AjaxDetailResult(HttpStatus.SUCCESS, msg, dataDetail);
    }


    public static AjaxDetailResult error() {
        return AjaxDetailResult.error(MessageUtils.message(MessageCode.OPERATION_ERROR));
    }

    public static AjaxDetailResult error(String msg) {
        return AjaxDetailResult.error(msg, null);
    }

    public static AjaxDetailResult error(Object dataDetail) {
        return AjaxDetailResult.error(MessageUtils.message(MessageCode.OPERATION_ERROR), dataDetail);
    }

    public static AjaxDetailResult error(String msg, Object dataDetail) {
        return new AjaxDetailResult(HttpStatus.ERROR, msg, dataDetail);
    }

    public static AjaxDetailResult error(int code, String msg, Object dataDetail) {
        return new AjaxDetailResult(code, msg, dataDetail);
    }

}
