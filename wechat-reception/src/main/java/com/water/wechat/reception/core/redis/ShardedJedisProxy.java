package com.water.wechat.reception.core.redis;

import redis.clients.jedis.ShardedJedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by huangguoping.
 */
public class ShardedJedisProxy implements InvocationHandler {
    RedisCacheManager redisCacheManager;

    public ShardedJedisProxy(RedisCacheManager redisCacheManager){
        this.redisCacheManager = redisCacheManager;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ShardedJedis jedis = redisCacheManager.getJedis();
        try {
            return method.invoke(jedis, args);
        }catch (Exception e){
            throw e;
        }
        finally {
            redisCacheManager.returnResource(jedis);
        }
    }
}
