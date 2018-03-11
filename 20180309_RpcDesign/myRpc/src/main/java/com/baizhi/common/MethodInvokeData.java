package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/9.15:27
 */
public class MethodInvokeData implements Serializable {

    private Class targetInterface;

    private String methodName;

    /**
     * 参数类型
      */
    private Class<?>[] paramterTypes;

    /**
     * 参数列表
     */
    private Object[] args;

    public MethodInvokeData() {
    }

    public MethodInvokeData(Class targetInterface, String methodName, Class<?>[] paramterTypes, Object[] args) {
        this.targetInterface = targetInterface;
        this.methodName = methodName;
        this.paramterTypes = paramterTypes;
        this.args = args;
    }

    public Class getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamterTypes() {
        return paramterTypes;
    }

    public void setParamterTypes(Class<?>[] paramterTypes) {
        this.paramterTypes = paramterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
