package com.mytests.spring.testing.wiremock.test1.model;

/**
 * *
 * <p>Created by irina on 7/7/2022.</p>
 * <p>Project: spring-wiremock-test1</p>
 * *
 */
public class MyPOJO {
    String id;
    String name;
    Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MyPOJO(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyPOJO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
