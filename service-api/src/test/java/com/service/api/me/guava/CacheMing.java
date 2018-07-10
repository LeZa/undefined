package com.service.api.me.guava;

import com.build.entity.Book;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.service.api.me.guava.entity.User;
import com.service.api.me.guava.service.UserService;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CacheMing {


    LoadingCache<String,Book> cache
              = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterAccess(30L, TimeUnit.MILLISECONDS)
                .build( createCacheLoader());


    public static CacheLoader<String,Book> createCacheLoader(){

        return  new CacheLoader<String, Book>() {
            @Override
            public Book load(String key) throws Exception {
                Book book = new Book();
                book.setId(123);
                book.setName("java");
                return book;
            }
        };
    }



    @Test
    public void test() {
        UserService userService = UserService.INSTANCE;
        User user = userService.get(1L);
        System.out.println(user.getName());
        user.setName("haha");
        System.out.println(user.getName());
        User user1 = userService.get(1L);
        System.out.println(user1.getName());
        // 删除缓存中数据
         /*  userService.remove(1L);
        User user2 = userService.get(1L);
        System.out.println(user2.getName());*/
    }

}
