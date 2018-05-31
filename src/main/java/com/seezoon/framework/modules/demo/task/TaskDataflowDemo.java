package com.seezoon.framework.modules.demo.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.seezoon.framework.common.job.BaseJob;

/**
 * 数据流job
 * 
 * Dataflow类型用于处理数据流，需实现DataflowJob接口。该接口提供2个方法可供覆盖，分别用于抓取(fetchData)和处理(processData)数据。
 * 通过参数streaming-process	是否流式处理数据默认	false	
	如果流式处理数据, 则fetchData不返回空结果将持续执行作业
	如果非流式处理数据, 则处理数据完成后完成当次作业结束
 * @author hdf
 *
 */
@Component
public class TaskDataflowDemo extends BaseJob implements DataflowJob<String>{

	@Override
	public List<String> fetchData(ShardingContext shardingContext) {
		List<String> list = new ArrayList<>();
		list.add("hello  job");
		return list;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<String> data) {
		logger.debug("processData:" + JSON.toJSONString(data));
	}

}
