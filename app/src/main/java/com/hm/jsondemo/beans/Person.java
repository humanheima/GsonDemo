package com.hm.jsondemo.beans;

/**
 * Created by dumingwei on 2020/4/2
 * <p>
 * Desc:
 */
public class Person {

    private String id;
    private String name;
    private String version;

    private int age;

    public Person() {
    }

    public Person(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", age=" + age +
                '}';
    }
}