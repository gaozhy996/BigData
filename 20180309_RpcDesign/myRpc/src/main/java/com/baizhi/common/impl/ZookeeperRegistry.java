package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.Registry;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;

/**
 * zk注册中心
 * @author gaozhy
 * @date 2018/3/9.15:42
 */
public class ZookeeperRegistry implements Registry {

    private ZkClient zkClient;

    public ZookeeperRegistry(String serverAddress) {
        this.zkClient = new ZkClient(serverAddress);
    }


    @Override
    public void register(String interfaceName, HostAndName hostAndName) {
        String path = SERVICE_PREFIX+interfaceName+SERVICE_SUFFIX;
        // 创建服务持久节点
        if(!zkClient.exists(path)){
            zkClient.createPersistent(path,true);
        }
        String nodePath = path+"/"+hostAndName.getHostname()+":"+hostAndName.getPort();
        if(zkClient.exists(nodePath)){
            zkClient.delete(nodePath);
        }
        // 创建服务提供临时节点
        zkClient.createEphemeral(nodePath);
    }

    @Override
    public void subscribe(String interfaceName, final List<HostAndName> hostAndNames) {
        String path = SERVICE_PREFIX+interfaceName+SERVICE_SUFFIX;

        // 更新订阅信息
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                hostAndNames.clear();
                // 更新列表
                hostAndNames.addAll(udpateSubscribeMessage(list));
            }
        });
    }

    @Override
    public List<HostAndName> receive(String interfaceName) {
        String path = SERVICE_PREFIX+interfaceName+SERVICE_SUFFIX;
        List<String> children = zkClient.getChildren(path);
        return udpateSubscribeMessage(children);
    }

    @Override
    public void close() {
        zkClient.close();
    }

    public List<HostAndName> udpateSubscribeMessage(List<String> nodes){
        ArrayList<HostAndName> hostAndNames = new ArrayList<HostAndName>();
        for (String node : nodes) {
            String[] split = node.split(":");
            hostAndNames.add(new HostAndName(split[0],Integer.parseInt(split[1])));
        }
        return hostAndNames;
    }
}
