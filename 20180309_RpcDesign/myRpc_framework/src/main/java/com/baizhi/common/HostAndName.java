package com.baizhi.common;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/11.22:13
 */
public class HostAndName implements Serializable {

    private String hostName;
    private int port;

    public HostAndName(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "HostAndName{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
    }
}
