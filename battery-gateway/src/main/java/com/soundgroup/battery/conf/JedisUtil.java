package com.soundgroup.battery.conf;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

public class JedisUtil {

    @Value("${redis.host}")
    private String redisHost;

    public  Jedis createJedis() {
        Jedis jedis = new Jedis(redisHost);
        return jedis;
    }

    public static Jedis createJedis(String host, int port) {
        Jedis jedis = new Jedis(host, port);
        return jedis;
    }

    public static Jedis createJedis(String host, int port, String passwrod) {
        Jedis jedis = new Jedis(host, port);

        if (!StringUtils.isNotBlank(passwrod))
            jedis.auth(passwrod);

        return jedis;
    }
}
