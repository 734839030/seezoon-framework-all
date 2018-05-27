package com.seezoon.framework.common.job;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.seezoon.framework.common.context.utils.NDCUtils;

/**
 * job 线程号
 * 
 * @author hdf 切入点语法 @see http://jinnianshilongnian.iteye.com/blog/1415606
 */
@Component
@Aspect
public class JobThreadAspect {
	private Logger logger = LoggerFactory.getLogger(JobThreadAspect.class);

	ThreadLocal<StopWatch> threadLocal = new ThreadLocal<>();

	/**
	 * com.seezoon.eagle.job.core.BaseJob 子类切入
	 * 
	 */
	@Pointcut("execution(* com.seezoon.framework.common.job.BaseJob+.*(com.dangdang.ddframe.job.api.ShardingContext,..))")
	private void anyMethod() {
	}// 定义一个切入点

	@Before("anyMethod()")
	public void before() {
		NDCUtils.push();
		StopWatch watch = new StopWatch();
		watch.start();
		threadLocal.set(watch);
	}

	@After("anyMethod()")
	public void after(JoinPoint point) {
		StopWatch watch = threadLocal.get();
		Object[] args = point.getArgs();
		String methodName = point.getSignature().getName();
		ShardingContext context = (ShardingContext) args[0];
		watch.stop();
		if (null != context) {
			logger.info("current job {}->{} completed used : {} ms", context.getJobName(), methodName,
					watch.getTotalTimeMillis());
		}
		threadLocal.remove();
	}

	@AfterThrowing("anyMethod()")
	public void doAfterThrow() {
		NDCUtils.clear();
	}

}
