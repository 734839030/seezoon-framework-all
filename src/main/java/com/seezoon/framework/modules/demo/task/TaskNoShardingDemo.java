package com.seezoon.framework.modules.demo.task;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.seezoon.framework.common.job.BaseJob;

/**
 * 不分片作业demo
 * @author hdf
 * 2018年5月27日
 */
@Component
public class TaskNoShardingDemo extends BaseJob implements SimpleJob{

	@Override
	public void execute(ShardingContext shardingContext) {
		logger.info("不分片作业demo");
	}

}
