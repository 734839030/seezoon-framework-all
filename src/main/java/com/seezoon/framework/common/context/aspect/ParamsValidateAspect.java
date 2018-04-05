package com.seezoon.framework.common.context.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.seezoon.framework.common.context.exception.ExceptionCode;
import com.seezoon.framework.common.context.exception.ResponeException;

/**
 * 参数校验切面
 * 
 * @author hdf 2017年9月25日
 */
@Component
@Aspect
public class ParamsValidateAspect {

	@Pointcut("execution(* com.seezoon.framework.common.web.BaseController+.*(..))")
	private void anyMethod() {
	}

	@Around("anyMethod() && args(..)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		if (null != args && args.length > 0) {
			for (Object arg : args) {
				if (arg instanceof BindingResult) {
					BindingResult br = (BindingResult) arg;
					if (br.hasErrors()) {
						FieldError fieldError = br.getFieldErrors().get(0);
						throw new ResponeException(ExceptionCode.PARAM_INVALID, "参数校验错误",
								new Object[] { fieldError.getField(), fieldError.getDefaultMessage() });
					}
				}
			}
		}
		return point.proceed();
	}
}
