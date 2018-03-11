package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/9.15:33
 */
public class Result implements Serializable {

    private Object returnValue;

    private Exception e;

    public Result() {
    }

    public Result(Object returnValue, Exception e) {
        this.returnValue = returnValue;
        this.e = e;
    }
    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
