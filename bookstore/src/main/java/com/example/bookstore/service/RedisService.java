package com.example.bookstore.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
@Service
public class RedisService {
	public RedisTemplate<String, Object> template;
	public ValueOperations<String, Object> value;
	public HashOperations<String, String, Object> hash;
	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		template=redisTemplate;
		value=redisTemplate.opsForValue();
		hash=redisTemplate.opsForHash();
	}

	
}
