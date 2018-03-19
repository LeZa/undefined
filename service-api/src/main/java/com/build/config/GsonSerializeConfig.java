package com.build.config;

import Entity.Author;
import Entity.Base;
import Entity.Book;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSerializeConfig implements JsonSerializer<Base> {
    @Override
    public JsonElement serialize(Base src, Type typeOfSrc, JsonSerializationContext context) {

        System.out.println(src);
        return null;
    }


    static class UserTypeAdapter extends TypeAdapter<Base>{

        @Override
        public void write(JsonWriter out, Base value) throws IOException {

        }

        @Override
        public Base read(JsonReader in) throws IOException {
            return null;
        }
    }


    public static void main( String[] sck ){

        Author author1 = new Author();
        author1.setId(1);
        author1.setName("author11");

        Author author2 = new Author();
        author2.setId(2);
        author2.setName("author22");

        Author author3 = new Author();
        author3.setId(3);
        author3.setName("author33");

        Book book1 = new Book();
        book1.setId(1);
        book1.setName("book11");

        Book book2 = new Book();
        book2.setId(2);
        book2.setName("book22");

        Book book3 = new Book();
        book3.setId(3);
        book3.setName("book33");


        List<Book> bookList = new ArrayList<Book>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        author1.setBookList(bookList);
        author2.setBookList(bookList);
        author3.setBookList(bookList);

        List<Author> authorList = new ArrayList<Author>();
        authorList.add(author1);
        authorList.add(author2);
        authorList.add(author2);

        book1.setAuthorList(authorList);
        book2.setAuthorList(authorList);
        book3.setAuthorList(authorList);


        Gson gson = new GsonBuilder().registerTypeAdapter(Base.class,new UserTypeAdapter() ).create();

        for( Author author:authorList ){
            System.out.println( gson.toJson(author));
        }






    }
}
