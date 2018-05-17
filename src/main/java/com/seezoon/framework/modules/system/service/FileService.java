package com.seezoon.framework.modules.system.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.file.FileConfig;
import com.seezoon.framework.common.file.FileHandlerFactory;
import com.seezoon.framework.common.file.beans.FileInfo;
import com.seezoon.framework.common.file.handler.FileHandler;
import com.seezoon.framework.common.service.BaseService;
import com.seezoon.framework.common.utils.DateUtils;
import com.seezoon.framework.modules.system.entity.SysFile;

/**
 * 文件服务
 * 
 * @author hdf 2018年4月15日
 */
@Service
public class FileService extends BaseService implements DisposableBean{

	private FileHandler fileHandler = FileHandlerFactory.getHandler();
	
	@Autowired
	private SysFileService sysFileService;

	public void upload(String relativePath, InputStream in) throws IOException {
		fileHandler.upload(relativePath, in);
	}

	public FileInfo upload(String originalFilename, String contentType, Long size, InputStream in) throws IOException {
		FileInfo fileInfo = new FileInfo();
		// 新命名
		String fileId = createFileId();
		String newName = rename(fileId, originalFilename);
		String relativePath = createRelativeDirectory() + newName;
		fileHandler.upload(relativePath, in);
		// 入库
		SysFile sysFile = new SysFile();
		sysFile.setName(originalFilename);
		sysFile.setId(fileId);
		sysFile.setContentType(contentType);
		sysFile.setFileSize(size);
		sysFile.setRelativePath(relativePath);
		sysFileService.save(sysFile);
		fileInfo.setFullUrl(FileConfig.getFullUrl(relativePath));
		fileInfo.setOriginalFilename(originalFilename);
		fileInfo.setRelativePath(relativePath);
		return fileInfo;
	}

	public SysFile findByRelativePath(String relativePath) {
		Assert.hasLength(relativePath,"相对路径为空");
		//取出文件id
		String fileId = relativePath.substring(relativePath.lastIndexOf("/") + 1, relativePath.lastIndexOf("."));
		SysFile sysFile = sysFileService.findById(fileId);
		return sysFile;
	}
	public InputStream download(String relativePath) throws FileNotFoundException {
		return fileHandler.download(relativePath);
	}

	/**
	 * 生成文件id
	 * 
	 * @return
	 */
	private String createFileId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成新的文件名
	 * 
	 * @param fileName
	 * @return
	 */
	private String rename(String fileId, String originalFileName) {
		return fileId + getFilesuffix(originalFileName);
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 *            包含后缀
	 * @return
	 */
	private String getFilesuffix(String fileName) {
		String suffix = "";
		int lastIndex = -1;
		if (-1 != (lastIndex = fileName.lastIndexOf("."))) {
			suffix = fileName.substring(lastIndex);
		}
		return suffix;
	}

	/**
	 * 生成相对目录
	 * 
	 * @return
	 */
	private  String createRelativeDirectory() {
		Date date = new Date();
		String y = DateUtils.formatDate(date, "yyyy");
		String m = DateUtils.formatDate(date, "MM");
		String d = DateUtils.formatDate(date, "dd");
		return "/" + y + "/" + m + "/" + d + "/";
	}

	@Override
	public void destroy() throws Exception {
		fileHandler.destroy();
	}
}
