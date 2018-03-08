package com.baizhi.netty2;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/8.14:17
 */
public class User implements Serializable{

    private Integer id;
    private String name;

    public User(Integer i, String zs) {
        this.id = i;
        this.name = zs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
