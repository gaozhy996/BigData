package com.baizhi.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author gaozhy
 * @date 2018/3/9.15:31
 */
public class MethodInvokeDataWrap implements Serializable {

    private MethodInvokeData methodInvokeData;

    private Map<String,Object> attachments;

    public MethodInvokeDataWrap() {
    }

    public MethodInvokeDataWrap(MethodInvokeData methodInvokeData, Map<String, Object> attachments) {
        this.methodInvokeData = methodInvokeData;
        this.attachments = attachments;
    }

    public MethodInvokeData getMethodInvokeData() {
        return methodInvokeData;
    }

    public void setMethodInvokeData(MethodInvokeData methodInvokeData) {
        this.methodInvokeData = methodInvokeData;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "MethodInvokeDataWrap{" +
                "methodInvokeData=" + methodInvokeData +
                ", attachments=" + attachments +
                '}';
    }
}
