package com.service.api.me.gson;

import Entity.Author;
import Entity.Book;
import com.build.gson.net.UserTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GsonJunit {

    @Test
    public void typeAdapterTest() {

 /*       NetUser user = new NetUser("怪盗kidou", 24);
        user.setEmail("ikidou@example.com");

        Gson gson = new GsonBuilder()
                //为User注册TypeAdapter
                .registerTypeAdapter(NetUser.class, new UserTypeAdapter())
                .create();
        System.out.println(gson.toJson(user));*/

        Author author = new Author();
        author.setId(1);
        author.setName("sck");
        Gson gson = new GsonBuilder()
                //为User注册TypeAdapter
                .registerTypeAdapter(Author.class, new UserTypeAdapter())
                .create();
        System.out.println(gson.toJson(author));

    }

    @Test
    public void simpleTest(){
        List<Book> bookList = new ArrayList<Book>();
        Author author = new Author();
        author.setId(1);
        author.setName("sck");

        Author author1 = new Author();
        author1.setId(2);
        author1.setName("ssl");

        Author author2 = new Author();
        author2.setId(3);
        author2.setName("Uoxixk.P");

        List<Author> authorList =  new ArrayList<Author>();
        authorList.add(author);
        authorList.add(author1);
        authorList.add(author2);

        Book book = new Book();
        book.setId(1);
        book.setName("Thinking in java");


        Book book1 = new Book();
        book1.setId(2);
        book1.setName("efective java");


        Book book2 = new Book();
        book2.setId(3);
        book2.setName("Design pattern");


        bookList.add(book);
        bookList.add(book1);
        bookList.add(book2);

        author.setBookList( bookList );
        author1.setBookList( bookList );
        author2.setBookList( bookList );

        book.setAuthorList(authorList);
        book1.setAuthorList(authorList);
        book2.setAuthorList(authorList);

        List<String> sdek = new ArrayList<String>();

        StringBuilder sb = new StringBuilder(1024).append("[");
        Gson gson = new GsonBuilder()
                //为User注册TypeAdapter
                .registerTypeAdapter(Book.class, new UserTypeAdapter())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        for(Book bookFor : bookList ){
            sb.append(
                    gson.toJson(bookFor).replaceAll("\\\\","")).append(",");
        }
        String sbVal = sb.substring(0,sb.length()-1);
        sbVal = sbVal+"]";
        System.out.println( sbVal );
  /*      sdek.add(gson.toJson(book));
        sdek.add(gson.toJson(book1));
        sdek.add(gson.toJson(book2));
        Gson gsonLast = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        System.out.println( gsonLast.toJson( sdek ));*/

    }
}
