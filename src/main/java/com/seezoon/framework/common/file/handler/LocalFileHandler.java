package com.seezoon.framework.common.file.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

/**
 * 本地文件处理
 * 
 * @author hdf 2018年4月15日
 */
public class LocalFileHandler implements FileHandler {

	private String storePath;

	public LocalFileHandler(String storePath) {
		super();
		Assert.hasLength(storePath, "存储路径为空");
		this.storePath = storePath;
	}

	@Override
	public void upload(String relativePath, InputStream in) throws IOException {
		Assert.hasLength(storePath, "存储路径为空");
		Assert.hasLength(relativePath, "相对路径为空");
		Assert.notNull(in, "上传文件为空");
		File dest = new File(storePath, relativePath);
		FileUtils.copyInputStreamToFile(in, dest);
	}

	@Override
	public InputStream download(String relativePath) throws FileNotFoundException {
		Assert.hasLength(relativePath, "相对路径为空");
		File file = new File(storePath, relativePath);
		FileInputStream in = new FileInputStream(file);
		return in;
	}

	@Override
	public void destroy() {
		// 云上传功能需要暴露销毁接口
	}


}
