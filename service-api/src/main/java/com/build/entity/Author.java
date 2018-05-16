package com.build.entity;

import com.build.config.JsonKey;

import java.io.Serializable;
import java.util.List;

public class Author extends Base
    implements Serializable{


    private static final long serialVersionUID = -365418097942221990L;

    private Integer id;

    private String name;

    private List<Book> bookList;

    public Author(){};


    public Author(Integer id, String name, List<Book> bookList) {
        this.id = id;
        this.name = name;
        this.bookList = bookList;
    }

    @JsonKey(value="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonKey(value="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonKey(value="book",isCollection = true)
    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
