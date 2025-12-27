package com.cf.common.core.redis.type;

import java.io.Serializable;

/**
 * redis hash数据
 *
 * @author wilfMao
 * @date 2023/7/3 13:39
 */
public class Hash<K, HK, KV> implements Serializable {
    private K key;
    private HK field;
    private KV value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public HK getField() {
        return field;
    }

    public void setField(HK field) {
        this.field = field;
    }

    public KV getValue() {
        return value;
    }

    public void setValue(KV value) {
        this.value = value;
    }
}
