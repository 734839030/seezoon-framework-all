package com.seezoon.framework.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
public class BaseService {

		/**
		 * 日志对象
		 */
		protected Logger logger = LoggerFactory.getLogger(getClass());
		
}
