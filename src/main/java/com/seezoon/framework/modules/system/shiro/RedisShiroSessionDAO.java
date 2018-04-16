package com.seezoon.framework.modules.system.shiro;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * shiro session dao
 * @author hdf
 * 2018年4月15日
 */
@Component
public class RedisShiroSessionDAO extends EnterpriseCacheSessionDAO {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${admin.session.key.preffix}")
    private String redisSessionKeyPreffix;
    @Value("${admin.session.timeout}")
    private Integer sessionTimeout;
    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        final String key = redisSessionKeyPreffix + sessionId.toString();
        setShiroSession(key, session);
        return sessionId;
    }

    //获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if(session == null){
            final String key = redisSessionKeyPreffix + sessionId.toString();
            session = getShiroSession(key);
        }
        return session;
    }

    //更新session
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        final String key = redisSessionKeyPreffix + session.getId().toString();
        setShiroSession(key, session);
    }

    //删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        final String key = redisSessionKeyPreffix + session.getId().toString();
        redisTemplate.delete(key);
    }

    private Session getShiroSession(String key) {
        return (Session)redisTemplate.opsForValue().get(key);
    }

    private void setShiroSession(String key, Session session){
        redisTemplate.opsForValue().set(key, session, sessionTimeout, TimeUnit.MILLISECONDS);
    }

}
