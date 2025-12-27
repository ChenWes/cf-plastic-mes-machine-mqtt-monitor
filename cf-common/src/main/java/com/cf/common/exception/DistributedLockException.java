package com.cf.common.exception;

/**
 * 分布式锁异常
 *
 * @author maowf
 */
public class DistributedLockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private Object data;

    public DistributedLockException(String message) {
        this.message = message;
    }

    public DistributedLockException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public DistributedLockException(String message, Integer code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public DistributedLockException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
