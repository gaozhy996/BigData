package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/11.22:35
 */
public class MethodInvokeData implements Serializable {
    /**
     * 代理接口
     */
    private Class<?> targetInterface;

    /**
     * 调用方法名
     */
    private String methodName;

    /**
     * 方法的参数列表
     */
    private Object[] args;

    /**
     * 调用方法的参数类型列表
     */
    private Class<?>[] parameterTypes;

    public MethodInvokeData() {
    }

    public MethodInvokeData(Class<?> targetInterface, String methodName, Object[] args, Class<?>[] parameterTypes) {
        this.targetInterface = targetInterface;
        this.methodName = methodName;
        this.args = args;
        this.parameterTypes = parameterTypes;
    }

    public Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
