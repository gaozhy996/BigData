package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.Registry;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于zk的注册中心
 *
 * @author gaozhy
 * @date 2018/3/11.22:33
 */
public class ZookeeperRegistry implements Registry {

    private ZkClient zkClient;

    public ZookeeperRegistry(String zkServerAddress) {
        this.zkClient = new ZkClient(zkServerAddress);
    }

    @Override
    public void registerService(String targetInterfaceName, HostAndName hostAndName) {
        String path = SERVICE_PREFIX + "/" + targetInterfaceName + SERVICE_SUFFIX;
        if (!zkClient.exists(path)) {
            // 如果服务第一次注册，需创建服务命名目录
            zkClient.createPersistent(path, true);
        }
        zkClient.createEphemeral(path + "/" + hostAndName.getHostName() + ":" + hostAndName.getPort());
    }

    @Override
    public void subscribleService(String targetInterfaceName, final List<HostAndName> existingHostAndNames) {
        String path = SERVICE_PREFIX + "/" + targetInterfaceName + SERVICE_SUFFIX;
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            // 使用新的服务提供列表 替换原有的服务提供列表
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                 existingHostAndNames.clear();

                 existingHostAndNames.addAll(updateServiceProviderList(list));
            }
        });
    }

    @Override
    public List<HostAndName> reciveService(String targetInterfaceName) {
        String path = SERVICE_PREFIX + "/" + targetInterfaceName + SERVICE_SUFFIX;
        List<String> children = zkClient.getChildren(path);
        return updateServiceProviderList(children);
    }

    /**
     * 更新服务提供列表
     */
    public List<HostAndName> updateServiceProviderList(List<String> nodes){

        List<HostAndName> list = new ArrayList<HostAndName>();
        for (String hostAndName : nodes) {
            list.add(new HostAndName(hostAndName.split(":")[0],Integer.parseInt(hostAndName.split(":")[1])));
        }
        return list;
    }

    @Override
    public void close() {
        zkClient.close();
    }
}
