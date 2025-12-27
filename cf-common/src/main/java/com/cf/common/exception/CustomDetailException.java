package com.cf.common.exception;

import java.util.List;

/**
 * 自定义异常
 *
 * @author WesChen
 */
public class CustomDetailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private List<String> messageList;


    public CustomDetailException(String message, List<String> messageList) {
        this.message = message;
        this.messageList = messageList;
    }

    public CustomDetailException(String message) {
        this.message = message;
    }

    public CustomDetailException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public CustomDetailException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }


    public List<String> getMessageList() {
        return messageList;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
