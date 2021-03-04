package com.amir01.jredis;

import redis.clients.jedis.Jedis;

public class Main {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println("value: " + value);
        jedis.close();

    }

}
