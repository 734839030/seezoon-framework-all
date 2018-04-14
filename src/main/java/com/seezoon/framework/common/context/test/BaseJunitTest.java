package com.seezoon.framework.common.context.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单元测试父类
 * 
 * @author hdf 2018年3月31日
 */
@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration(locations = {"classpath*:/spring-mvc.xml", "classpath*:spring-context*.xml" }) // 初始化core
@Transactional(rollbackFor=Exception.class)
@WebAppConfiguration
public class BaseJunitTest {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
}
