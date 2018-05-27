package com.seezoon.framework.common.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseJob {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
}