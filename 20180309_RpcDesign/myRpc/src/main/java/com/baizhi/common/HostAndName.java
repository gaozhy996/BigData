package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/9.15:15
 */
public class HostAndName implements Serializable {

    private String hostname;
    private int port;

    public HostAndName(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
