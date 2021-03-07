package com.amir01.jredis;

import redis.clients.jedis.Jedis;

/**
 * RedisClient
 *
 * this class is an implementation for a simple client for
 * famous and popular NoSQL database, Redis. in this client
 * we use Jedis which is a popular client for redis in Java
 *
 * @author Amir01
 * @since 07 March 2021
 * @version 0.0.1
 */
public class RedisClient {

    private static RedisClient INSTANCE = null;
    private Jedis jedisInstance;

    /**
     * Setter of jedisInstance
     * @param jedisInstance instance of Jedis Client
     */
    public void setJedisInstance(Jedis jedisInstance) {
        this.jedisInstance = jedisInstance;
    }

    /**
     * Getter of jedisInstance
     * @return current instance of Jedis Client
     */
    public Jedis getJedisInstance() {
        return jedisInstance;
    }

    /**
     * Private Constructor (Implement Singleton)
     */
    private RedisClient() {

    }

    /**
     * public method to retrieve object of this class
     *
     * @param jedisInstance instance of Jedis Client
     * @return instance of RedisClient class
     */
    public static RedisClient getInstance(Jedis jedisInstance) {
        if(INSTANCE == null) {
            INSTANCE = new RedisClient();
            INSTANCE.setJedisInstance(jedisInstance);
        }
        return INSTANCE;
    }

}
