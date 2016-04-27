package com.lj.kernel.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis工具
 *
 * @author : TinyZ.
 * @required Jedis-2.8 library
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public final class RedisUtil {

    private JedisPool jedisPool;

    public RedisUtil(String host, int port, String password, int database) {
        // Jedis连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(64);
        this.jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT, "".equals(password) ? null : password, database);
        // 支持集群

    }

    // Normal

    public Long ttl(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.ttl(key);
        }
    }

    public String type(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.type(key);
        }
    }

    public Set<String> keys(String pattern) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.keys(pattern);
        }
    }

    // String

    public void set(String key, String value) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.set(key, value);
        }
    }

    public void setex(String key, String value, int expire) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.setex(key, expire, value);
        }
    }

    public String get(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.get(key);
        }
    }

    public Long del(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.del(key);
        }
    }

    public Long del(String... keys) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.del(keys);
        }
    }

    public Long incrBy(String key, long value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.incrBy(key, value);
        }
    }

    public Double incrBy(String key, double value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.incrByFloat(key, value);
        }
    }

    // List

    public Long listLeftPush(String key, String... string) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lpush(key, string);
        }
    }

    public Long listRightPush(String key, String... string) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.rpush(key, string);
        }
    }

    public String listLeftPop(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lpop(key);
        }
    }

    public String listRightPop(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.rpop(key);
        }
    }

    public Long listLen(String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.llen(key);
        }
    }

    public String listSet(String key, long index, String value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lset(key, index, value);
        }
    }

    public String listIndex(String key, long index) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lindex(key, index);
        }
    }

    public String listTrim(String key, long start, long end) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.ltrim(key, start, end);
        }
    }

    public Long listRem(String key, long count, String value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lrem(key, count, value);
        }
    }

    public List<String> listRange(String key, long start, long end) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.lrange(key, start, end);
        }
    }

    // Hash

    public String hashGet(String hash, String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hget(hash, key);
        }
    }

    public void hashSet(String hash, String key, String string) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.hset(hash, key, string);
        }
    }

    public Long hashLen(String hash) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hlen(hash);
        }
    }

    public Long hashDel(String hash, String... keys) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hdel(hash, keys);
        }
    }

    public Set<String> hashKeys(String hash) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hkeys(hash);
        }
    }

    public List<String> hashVals(String hash) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hvals(hash);
        }
    }

    public Map<String, String> hashGetAll(String hash) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hgetAll(hash);
        }
    }

    public Boolean hashExists(String hash, String key) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hexists(hash, key);
        }
    }

    public String hashMset(String hash, Map<String, String> fields) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hmset(hash, fields);
        }
    }

    public Long hashIncrby(String hash, String key, long value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hincrBy(hash, key, value);
        }
    }

    public Double hashIncrbyFloat(String hash, String key, double value) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hincrByFloat(hash, key, value);
        }
    }

    public List<String> hashMset(String hash, String... keys) {
        try (Jedis var1 = jedisPool.getResource()) {
            return var1.hmget(hash, keys);
        }
    }

    // Json Object  => Dependency fast-JSON library

    public <T> T objGet(String key, Class<T> clz) {
        try (Jedis var1 = jedisPool.getResource()) {
            String json = var1.get(key);
            if (json == null) {
                return null;
            }
            try {
                return JSONObject.parseObject(json, clz);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void objSet(String key, Object obj) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.set(key, JSON.toJSONString(obj));
        }
    }

    public void objSetEx(String key, Object obj, int expire) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.setex(key, expire, JSON.toJSONString(obj));
        }
    }

    public void objHashSet(String hash, String key, Object obj) {
        try (Jedis var1 = jedisPool.getResource()) {
            var1.hset(hash, key, JSON.toJSONString(obj));
        }
    }

    public <T> T objHashGet(String hash, String key, Class<T> clz) {
        try (Jedis var1 = jedisPool.getResource()) {
            String json = var1.hget(hash, key);
            if (json == null) {
                return null;
            }
            try {
                return JSONObject.parseObject(json, clz);
            } catch (Exception e) {
                return null;
            }
        }
    }


}
