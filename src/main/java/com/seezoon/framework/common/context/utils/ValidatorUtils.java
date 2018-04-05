package com.seezoon.framework.common.context.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import com.seezoon.framework.common.context.exception.ExceptionCode;
import com.seezoon.framework.common.context.exception.ResponeException;

/**
 * 校验工具类
 * 
 * @author hdf 2018年3月31日
 */
public class ValidatorUtils {

	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * 
	 * @param object
	 *            待校验对象
	 * @param groups
	 *            待校验的组
	 */
	public static void validateEntity(Object object, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator()
					.next();
			throw new ResponeException(ExceptionCode.PARAM_INVALID, constraint.getMessage());
		}
	}

}
