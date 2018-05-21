package com.seezoon.framework.modules.system.utils;

/**
 * 代码生成枚举
 * 
 * @author hdf 2018年4月28日
 */
public class GenEnum {

	public enum QueryType {
		
		EQ("="),
		NEQ("!="),
		GET(">="),
		GT(">"),
		GLT("<="),
		LT("<"),
		BETWEEN("between"),
		LIKE("like"),
		LEFTLIKE("left like"),
		RIGHTLIKE("right like");
		private String value;

		private QueryType(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}
	/**
	 * 输入框类型
	 */
	public enum InputType {
		/**
		 * 文本框
		 */
		TEXT("text"),
		/**
		 * 下拉
		 */
		SELECT("select"),
		/**
		 * 隐藏域
		 */
		HIDDEN("hidden"),
		/**
		 * 整数
		 */
		ZHENGSHU("zhengshu"),
		/**
		 * 小数
		 */
		XIAOSHU("xiaoshu"),
		/**
		 * 复选框
		 */
		CHECKBOX("checkbox"),
		/**
		 * 单选框
		 */
		RADIO("radio"),
		/**
		 * 时间选择器
		 */
		DATE("date"),
		/**
		 * 文本域
		 */
		TEXTAREA("textarea"),
		/**
		 * 富文本
		 */
		RICHTEXT("richtext"),
		/**
		 * 图片上传
		 */
		PICTURE("picture"),
		/**
		 * 文件上传
		 */
		FILE("file");

		private String value;

		private InputType(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}
}
