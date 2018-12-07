package com.bbt.cache.service.impl;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/28 15:10
 */
public class InventoryCacheManager {


    private static String formateKey(String keyPrefix, String businessKey, Object id) {
        return keyPrefix + businessKey + "_" + id;
    }

    /**
     * 从缓存中获取剩余库存
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @return
     */
    public static Integer getInventoryFromCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {
        String value = stringRedisTemplate.opsForValue().get(formateKey(keyPrefix, businessKey, id));
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Integer inventory = Integer.valueOf(value);
        return inventory < 0 ? 0 : inventory;
    }

    /**
     * 更新缓存中的库存
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @param inventory
     */
    public static void setInventoryToCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id, Integer inventory) {
        RedisSerializer<String> stringSerializer = stringRedisTemplate.getStringSerializer();
        byte[] serializeKey = stringSerializer.serialize(formateKey(keyPrefix, businessKey, id));
        byte[] serializeValue = stringSerializer.serialize(String.valueOf(inventory));
        RedisConnection connection = RedisConnectionUtils.getConnection(stringRedisTemplate.getConnectionFactory());
        //RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
        connection.set(serializeKey, serializeValue, Expiration.seconds(60 * 10), RedisStringCommands.SetOption.ifAbsent());
    }

    /**
     * 缓存中的库存量减一
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @return 减完之后的值
     */
    public static Integer decreaseInventoryToCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {

        Long count = stringRedisTemplate.boundValueOps(formateKey(keyPrefix, businessKey, id)).increment(-1);

        return count.intValue();
    }

    /**
     * 缓存中的库存量加一
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @return 加完之后的值
     */
    public static Integer increaseInventoryToCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {

        Long count = stringRedisTemplate.boundValueOps(formateKey(keyPrefix, businessKey, id)).increment(1);

        return count.intValue();
    }

    /**
     * 从缓存中清除库存信息
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     */
    public static void clearInventoryFromCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {
        stringRedisTemplate.delete(formateKey(keyPrefix, businessKey, id));
    }
}
