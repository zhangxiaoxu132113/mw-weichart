package com.water.wechat.reception.core.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by huangguoping on 15/4/28.
 */

public interface RedisCacheManager extends CacheManager {
    public ShardedJedis getJedis();
    public void returnResource(ShardedJedis jedis);
}
