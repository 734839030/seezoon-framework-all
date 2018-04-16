package com.seezoon.framework.common.file.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件处理
 * @author hdf
 * 2018年4月15日
 */
public interface FileHandler {

	/**
	 * 上传
	 * @param storePath
	 * @param relativePath
	 * @param in
	 * @throws IOException
	 */
	public void upload(String relativePath,InputStream in) throws IOException;
	/**
	 * 下载
	 * @param storePath
	 * @param relativePath
	 * @return
	 */
	public InputStream download(String relativePath) throws FileNotFoundException;
	
	public void destroy();
}
