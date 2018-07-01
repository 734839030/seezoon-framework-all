package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.seezoon.framework.common.context.support.SpringContextHolder;
import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.front.wechat.service.PayResultHandler;
import com.seezoon.framework.modules.system.entity.SysRole;

public class SysRoleServiceTest extends BaseJunitTest{

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, Long>  valueOperations;
	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;
	@Test
	public void t5() {
		//RedisAtomicLong counter = new RedisAtomicLong("keylong1", redisTemplate.getConnectionFactory());  
		redisTemplate.opsForValue().set("dsd", 111);
		System.out.println(redisTemplate.opsForValue().get("dsd").getClass().getName());
	}
	@Test
	public void t2() {
		Long increment = valueOperations.increment("key", 1);
		System.out.println(increment);
	}
	@Test
	public void t4() {
		Long increment = valueOperations.get("login_cnt_test001");
		System.out.println(increment);
	}
	@Test
	public  void t1() {
		System.out.println(sysUserService.encryptPwd("123456", "rNBdNtjuefmwLGzXjHoN"));
	}
	@Test
	public void testSaveSysRole() {
		for (int i = 0;i<10000;i++) {
			SysRole sysRole = new SysRole();
			sysRole.setDataScope("1");
			sysRole.setName("role" + RandomStringUtils.randomAscii(5));
			sysRoleService.save(sysRole);
		}
	}
	@Test
	public void t6() {
		Long expire = valueOperations.getOperations().getExpire("sdsada",TimeUnit.SECONDS);
		System.out.println(expire);
	}
	@Test
	public void t7() {
		PayResultHandler bean = SpringContextHolder.getBean("demoPayHandler", PayResultHandler.class);
		System.out.println(bean.handle(null));
	}

}
