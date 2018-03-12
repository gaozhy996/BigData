package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/11.22:42
 */
public class Result implements Serializable {

    /**
     * 返回值
     */
    private Object returnValue;

    /**
     * 异常信息
     */
    private Exception exception;

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
