package com.baizhi.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author gaozhy
 * @date 2018/3/9.15:36
 */
public class ResultWrap implements Serializable {

    private Result result;
    private Map<String,Object> attachments;

    public ResultWrap() {
    }

    public ResultWrap(Result result, Map<String, Object> attachments) {
        this.result = result;
        this.attachments = attachments;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "ResultWrap{" +
                "result=" + result +
                ", attachments=" + attachments +
                '}';
    }
}
