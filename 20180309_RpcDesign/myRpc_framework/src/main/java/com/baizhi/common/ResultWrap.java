package com.baizhi.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author gaozhy
 * @date 2018/3/11.22:21
 */
public class ResultWrap implements Serializable {

    private Result result;
    private Map<String,Object> attachment;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
