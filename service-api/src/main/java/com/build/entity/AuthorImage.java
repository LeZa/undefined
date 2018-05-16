package com.build.entity;

import java.io.Serializable;

public class AuthorImage
    implements Serializable{

    private static final long serialVersionUID = 9179805390991090133L;

    private Integer id;

    private Integer author_id;

    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
