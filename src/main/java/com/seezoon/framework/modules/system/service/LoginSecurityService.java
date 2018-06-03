package com.seezoon.framework.modules.system.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.BaseService;

@Service
public class LoginSecurityService extends BaseService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	private String LOCK_PREFIX = "login_cnt_";

	public void unLock(String loginName) {
		Assert.hasLength(loginName, "loginName 为空");
		redisTemplate.delete(LOCK_PREFIX + loginName);
	}

	public Long getLoginFailCount(String loginName) {
		try {
			// spring data increment 有bug 故采用这种方式
			RedisAtomicLong counter = new RedisAtomicLong(LOCK_PREFIX + loginName,
					redisTemplate.getConnectionFactory());
			return counter.get();
		} finally {
			redisTemplate.expire(LOCK_PREFIX + loginName, 24, TimeUnit.HOURS);
		}
	}

	public Long incrementLoginFailTimes(String loginName) {
		try {
			RedisAtomicLong counter = new RedisAtomicLong(LOCK_PREFIX + loginName,
					redisTemplate.getConnectionFactory());
			return counter.incrementAndGet();
		} finally {
			redisTemplate.expire(LOCK_PREFIX + loginName, 24, TimeUnit.HOURS);
		}
	}

	public void clearLoginFailTimes(String loginName) {
		redisTemplate.delete(LOCK_PREFIX + loginName);
	}
}
