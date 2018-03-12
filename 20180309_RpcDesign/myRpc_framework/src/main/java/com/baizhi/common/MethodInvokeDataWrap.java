package com.baizhi.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author gaozhy
 * @date 2018/3/11.22:20
 */
public class MethodInvokeDataWrap implements Serializable {

    /**
     * 请求数据
     */
    private MethodInvokeData methodInvokeData;

    /**
     * 附件信息
     */
    private Map<String,Object> attachment;

    /**
     * 结果信息
     */
    private ResultWrap resultWrap;

    public MethodInvokeData getMethodInvokeData() {
        return methodInvokeData;
    }

    public void setMethodInvokeData(MethodInvokeData methodInvokeData) {
        this.methodInvokeData = methodInvokeData;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public ResultWrap getResultWrap() {
        return resultWrap;
    }

    public void setResultWrap(ResultWrap resultWrap) {
        this.resultWrap = resultWrap;
    }
}
