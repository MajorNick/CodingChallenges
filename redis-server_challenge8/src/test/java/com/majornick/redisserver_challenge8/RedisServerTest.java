package com.majornick.redisserver_challenge8;

import org.junit.Test;
import redis.clients.jedis.JedisPooled;

public class RedisServerTest {

    @Test
    public void redisPongCommandTest() {
        try (JedisPooled jedis = new JedisPooled("localhost", 6379)) {
            System.out.println(jedis.set("nika", "baro"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}