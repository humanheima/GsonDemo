package com.hm.jsondemo.beans;

import com.alibaba.fastjson.annotation.JSONField;

public class User {

    private Long id;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    @JSONField(deserialize = false)
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}