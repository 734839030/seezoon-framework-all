package com.seezoon.framework.modules.system.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.context.exception.ServiceException;
import com.seezoon.framework.common.file.beans.FileInfo;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.common.utils.IdGen;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysFile;
import com.seezoon.framework.modules.system.service.FileService;

@RestController
@RequestMapping({"${admin.path}/file","${front.path}/file"})
public class FileController extends BaseController {

	private Pattern pattern = Pattern.compile("^image/.+$");

	@Autowired
	private FileService fileService;

	@PostMapping("/uploadFile.do")
	public ResponeModel uploadFile(@RequestParam MultipartFile file) throws IOException {
		return ResponeModel.ok(fileService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
				file.getInputStream()));
	}

	@PostMapping("/uploadBatchFile.do")
	public ResponeModel uploadBatchFile(@RequestParam MultipartFile[] files) throws IOException {
		List<FileInfo> fileInfos = Lists.newArrayList();
		for (MultipartFile file : files) {
			FileInfo fileInfo = fileService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					file.getInputStream());
			fileInfos.add(fileInfo);
		}
		return ResponeModel.ok(fileInfos);
	}

	@PostMapping("/uploadImage.do")
	public ResponeModel uploadImage(@RequestParam MultipartFile file) throws IOException {
		if (!pattern.matcher(file.getContentType()).matches()) {
			return ResponeModel.error(file.getOriginalFilename() + "不是图片类型");
		}
		return ResponeModel.ok(fileService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
				file.getInputStream()));
	}
	@PostMapping("/uploadBase64.do")
	public ResponeModel uploadBase64(String base64File) throws IOException {
		String[] contents = StringUtils.split(base64File, ":;,");
		if (contents.length < 4) {
			return ResponeModel.error("文件为空");
		}
		String contentType = contents[1];
		byte[] file = CodecUtils.base64Decode(contents[3]);
		return ResponeModel.ok(fileService.upload(IdGen.uuid() + "." +contentType.substring(contentType.lastIndexOf("/") + 1), contentType, (long)file.length,
				new ByteArrayInputStream(file)));
	}
	@PostMapping("/uploadBatchImage.do")
	public ResponeModel uploadBatchImage(@RequestParam MultipartFile[] files) throws IOException {
		for (MultipartFile file : files) {
			if (!pattern.matcher(file.getContentType()).matches()) {
				return ResponeModel.error(file.getOriginalFilename() + "不是图片类型");
			}
		}
		List<FileInfo> fileInfos = Lists.newArrayList();
		for (MultipartFile file : files) {
			FileInfo fileInfo = fileService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					file.getInputStream());
			fileInfos.add(fileInfo);
		}
		return ResponeModel.ok(fileInfos);
	}
	/**
	 * kindeditor 专用
	 * @param imgFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/k_upload_image.do")
	public Map<String,Object> umUpload(@RequestParam MultipartFile imgFile) throws IOException {
		FileInfo fileInfo = fileService.upload(imgFile.getOriginalFilename(), imgFile.getContentType(), imgFile.getSize(),
				imgFile.getInputStream());
		Map<String,Object> map = new HashMap<String,Object>();
		// 输出文件地址
		map.put("url",fileInfo.getFullUrl());
		// 状态
		map.put("error",0);
		return map;
	}
	
	@RequestMapping("/down.do")
	public void  down(@RequestParam  String relativePath,HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		SysFile sysFile = fileService.findByRelativePath(relativePath);
		Assert.notNull(sysFile,"文件不存在");
		InputStream in = fileService.download(relativePath);
		//第一步：设置响应类型
		//response.setContentType("application/force-download");//应用程序强制下载,实际测试不需要
		response.setContentType(sysFile.getContentType());
		response.setHeader("Content-Disposition", "attachment;filename="+ CodecUtils.urlEncode(sysFile.getName())); 
		response.setContentLength(in.available());
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		BufferedInputStream bin = new BufferedInputStream(in);
		//1M 一写
		byte[] buffer = new byte[1024 * 1024];
		int len = 0;
		while (-1 != (len = bin.read(buffer))) {
			bos.write(buffer, 0, len);
		}
		bos.flush();
		bos.close();
		bin.close();
	}
}
