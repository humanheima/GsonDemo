package com.hm.jsondemo.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dumingwei on 2020/5/24
 * <p>
 * Desc:
 */
public class UserSimple {

    @SerializedName(value = "name", alternate = {"fullName"})

    String name;
    String email;
    int age;
    boolean isDeveloper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public void setDeveloper(boolean developer) {
        isDeveloper = developer;
    }
}