package com.build.gson.net;

import java.io.Serializable;

public class User
    implements Serializable{

    private static final long serialVersionUID = -5890716215258498488L;

    private Integer id;

    private String name;

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
}
