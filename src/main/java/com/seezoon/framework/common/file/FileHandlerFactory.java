package com.seezoon.framework.common.file;

import org.apache.commons.lang3.StringUtils;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.file.handler.AliFileFileHandler;
import com.seezoon.framework.common.file.handler.FileHandler;
import com.seezoon.framework.common.file.handler.LocalFileHandler;

public class FileHandlerFactory {

	private static FileHandler fileHandler = null;

	// 文件配置
	private static FileConfig fileConfig = new FileConfig();

	public static FileHandler getHandler() {
		if (null != fileHandler) {
			return fileHandler;
		}
		String fileStorage = fileConfig.getFileStorage();
		if (Constants.FileStorage.LOCAL.getValue().equals(fileStorage)) {// 本地
			fileHandler = new LocalFileHandler(fileConfig.getLocalStorePath());
		} else if (Constants.FileStorage.ALIYUN.getValue().equals(fileStorage)) {// 阿里云
			fileHandler = new AliFileFileHandler(fileConfig.getAliyunEndpoint(), fileConfig.getAliyunAccessKeyId(),
					fileConfig.getAliyunAccessKeySecret(), fileConfig.getAliyunBucket());
		} else {
			throw new RuntimeException("请配置文件存储模块");
		}
		return fileHandler;
	}

	public static String getFullUrl(String relativePath) {
		if (StringUtils.isEmpty(relativePath)) {
			return null;
		}
		String fileStorage = fileConfig.getFileStorage();
		String urlPrefix = "";
		if (Constants.FileStorage.LOCAL.getValue().equals(fileStorage)) {// 本地
			urlPrefix = fileConfig.getLocalUrlPrefix();
		} else if (Constants.FileStorage.ALIYUN.getValue().equals(fileStorage)) {// 阿里云
			urlPrefix = fileConfig.getAliyunUrlPrefix();
		} else {
			throw new RuntimeException("请配置文件存储模块");
		}
		return urlPrefix + relativePath;
	}
}
