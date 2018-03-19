package com.build.gson.net;

import Entity.Base;
import Entity.BaseInterface;
import com.build.config.JsonKey;

import java.io.Serializable;

public class NetUser extends Base
    implements Serializable{

    private String name;

    private Integer age;

    private String email;


    public NetUser(){};

    public NetUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @JsonKey(value = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonKey(value="age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonKey(value = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
