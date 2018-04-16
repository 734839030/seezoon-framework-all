package com.seezoon.framework.common;

public class Constants {

	public static final String YES = "1";
	public static final String NO = "0";
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final String SEPARATOR = ",";
	/**
	 * 系统超级管理员
	 */
	public static final String SUPER_ADMIN_ID = "1";
	
	 /**
     * 文件存储介质
     */
    public enum FileStorage {
        /**
         * 本地
         */
        LOCAL("local"),
        /**
         * 阿里云
         */
        ALIYUN("aliyun");

        private String value;

        private FileStorage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
