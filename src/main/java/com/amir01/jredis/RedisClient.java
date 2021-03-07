package com.amir01.jredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

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
     * and initialize Jedis object automatically
     *
     * @return instance of RedisClient class
     */
    public static RedisClient getInstance() {

        if(INSTANCE == null) {
            INSTANCE = new RedisClient();
            INSTANCE.setJedisInstance(new Jedis("localhost"));
        }
        return INSTANCE;

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


    /**
     * check to see this key is exist or not
     * @param key key name
     * @return final existence status of input key
     */
    public boolean isExist(String key) {

        try {

            return this.jedisInstance.exists(key);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * create command to create new key in Redis server
     *
     * @param key key name
     * @param value value name
     * @return final status of operation
     */
    public boolean create(String key, String value) {

        try{

            String message = this.jedisInstance.set(key, value, new SetParams().nx());
            if(message != null) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * fetch command to get a key in Redis server
     *
     * @param key key name
     * @return value associated to input key
     */
    public String fetch(String key) {

        String value = "";

        try{

            value = this.jedisInstance.get(key);

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }


    /**
     * update command to update an existing key value pair
     * in Redis server
     * @param key key name
     * @param value value name
     * @return final status of operation
     */
    public boolean update(String key, String value) {

        try{

            String message = this.jedisInstance.set(key, value, new SetParams().xx());
            if(message != null) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


    /**
     * delete command to delete an existing key in Redis server
     * @param key key name
     * @return final status of operation
     */
    public boolean delete(String key) {

        try{

            long status = this.jedisInstance.del(key);
            if(status == 0) {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    /**
     * close jedisInstance
     */
    public void jedisClose() {
        this.jedisInstance.close();
    }

}
