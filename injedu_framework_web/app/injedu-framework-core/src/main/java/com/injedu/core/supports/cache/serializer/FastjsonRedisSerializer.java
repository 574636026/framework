package com.injedu.core.supports.cache.serializer;

import org.apache.commons.lang3.SerializationException;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * redis fastjson 系列化
 *
 * @author joy.zhou
 * @date 2016年8月17日
 * @version 1.0
 *
 */
public class FastjsonRedisSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		// 输出类型以供反序列化
		return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		return JSON.parse(bytes);
	}

}
