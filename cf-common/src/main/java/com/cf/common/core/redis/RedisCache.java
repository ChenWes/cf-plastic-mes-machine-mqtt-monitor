package com.cf.common.core.redis;

import com.cf.common.core.redis.type.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author WesChen
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> long setCacheSet(final String key, final Set<T> dataSet) {
        Long count = redisTemplate.opsForSet().add(key, dataSet);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKeys Hash键
     * @return Hash中的对象
     */
    public Long delCacheMapValue(final String key, final String... hKeys) {
        return redisTemplate.opsForHash().delete(key, hKeys);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 扫描 redis中的键名 , redis keys 查询键名会有性能问题，建议使用scan
     *
     * @param key 匹配键 : 支持表达式
     *            如 包含 *key*
     *            前缀: key*
     *            后缀: *key
     * @return
     */
    public Set<String> scan(String key) {
        return (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keys = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(key).build());
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return keys;
        });
    }

    /**
     * 检查是否有该Key
     *
     * @param key
     * @return
     */
    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 使用管道批量操作
     * pipeline可以提升批量写入性能，但是建议每次管道里命令条数不能太多，以免造成客户端响应时间变久，网络传输阻塞。
     * 因此可以对大的批量写入进行拆分，分批来批量插入
     *
     * @param map
     * @param expireSeconds
     */
    public void executePipelined(Map<Object, Object> map, Long expireSeconds) {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                map.forEach((key, value) -> {
                    connection.set(stringSerializer.serialize(key), valueSerializer.serialize(value),
                            Expiration.seconds(expireSeconds), RedisStringCommands.SetOption.UPSERT);
                });
                return null;
            }
        });
    }

    /**
     * redis hash 批量插入操作
     *
     * @param key
     * @param map
     * @param expireSeconds
     */
    public void executePipelined(String key, Map<String, ? extends Object> map, Long expireSeconds) {
        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        RedisSerializer hashHeySerializer = redisTemplate.getHashKeySerializer();
        RedisSerializer valueSerializer = redisTemplate.getHashValueSerializer();
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            map.forEach((field, value) -> {
                connection.hSet(keySerializer.serialize(key), hashHeySerializer.serialize(field),
                        valueSerializer.serialize(value));
                ///connection.pExpire(keySerializer.serialize(key),expireSeconds);
            });
            return null;
        });
    }

    /**
     * redis hash 管道批量插入操作
     *
     * @param hashes
     */
    public void executePipelined(List<? extends Hash> hashes) {
        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        RedisSerializer hashHeySerializer = redisTemplate.getHashKeySerializer();
        RedisSerializer valueSerializer = redisTemplate.getHashValueSerializer();
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            hashes.forEach((hash) -> {
                connection.hSet(keySerializer.serialize(hash.getKey()), hashHeySerializer.serialize(hash.getField()),
                        valueSerializer.serialize(hash.getValue()));

            });
            return null;
        });
    }

    /**
     * 管道批量删除
     *
     * @param keys
     */
    public void executeDelPipelined(List<String> keys) {
        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            keys.forEach((key) -> {
                connection.del(keySerializer.serialize(key));
            });
            return null;
        });
    }


    /**
     * 自增操作
     *
     * @param key
     * @param liveTime
     * @return
     */
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {
            //初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }
        return increment;
    }
}
