package com.service.api.me.guava.service;

import com.service.api.me.guava.cache.CacheService;
import com.service.api.me.guava.db.DBUtil;
import com.service.api.me.guava.entity.User;

import java.sql.ResultSet;

public class UserService extends CacheService<Long, User> {

    public static UserService INSTANCE = new UserService();

    @Override
    public User load(Long id) throws Exception {
        System.out.println("query db");
        User user = null;
        DBUtil dbUtil = new DBUtil();
        ResultSet rs = dbUtil.select("user", "id, name", "where id = 1");
        while (rs.next()) {
            String username = rs.getString("name");
            user = new User(id, username);
        }
        return user;
    }
}
