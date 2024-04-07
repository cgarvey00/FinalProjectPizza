//package com.finalprojectcoffee.utils;
//
//import redis.clients.jedis.Jedis;
//
//public class RedisUtil {
//
//    private static final String REDIS_HOST = "localhost";
//    private static final int REDIS_PORT = 6868;
//
//    private Jedis jedis;
//
//    public RedisUtil(){
//        this.jedis = new Jedis(REDIS_HOST, REDIS_PORT);
//    }
//
//    public String getFromCache(String key){
//        return jedis.get(key);
//    }
//
//    public void saveToCache(String key, String value, int expireTime){
//        jedis.setex(key, expireTime, value);
//    }
//
//    public void removeFromCache(String key){
//        jedis.del(key);
//    }
//}
