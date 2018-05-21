package com.seezoon.framework.modules.demo.web;

import java.io.Serializable;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.demo.entity.DemoGen;
import com.seezoon.framework.modules.demo.service.DemoGenService;
import java.util.ArrayList;
import java.util.List;
import com.seezoon.framework.modules.system.entity.SysFile;
import com.seezoon.framework.modules.system.service.SysFileService;
import com.seezoon.framework.common.file.FileConfig;
import com.seezoon.framework.common.file.beans.FileInfo;
import com.seezoon.framework.common.Constants;

/**
 * 生成案例controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-5-22 1:00:15
 */
@RestController
@RequestMapping("${admin.path}/demo/gen")
public class DemoGenController extends BaseController {

	@Autowired
	private DemoGenService demoGenService;
	@Autowired
	private SysFileService sysFileService;

	@RequiresPermissions("demo:gen:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(DemoGen demoGen) {
		PageInfo<DemoGen> page = demoGenService.findByPage(demoGen, demoGen.getPage(), demoGen.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("demo:gen:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		DemoGen demoGen = demoGenService.findById(id);
		//富文本处理
        if (null != demoGen) {
		    if (StringUtils.isNotEmpty(demoGen.getRichText())) {
				demoGen.setRichText(StringEscapeUtils.unescapeHtml4(demoGen.getRichText()));
			}
		}
        	 //文件处理
        	if (StringUtils.isNotEmpty(demoGen.getImage())) {
        		String[] images = StringUtils.split(demoGen.getImage(), Constants.SEPARATOR);
        		List<FileInfo> imageArray = new ArrayList<>();
	        	for (String path :images) {
	        		SysFile sysFile = sysFileService.findById(FileConfig.getFileId(path));
	        		if (null != sysFile) {
	        			imageArray.add(new FileInfo(FileConfig.getFullUrl(path),path,sysFile.getName()));
	        		}
	        	}
	        	demoGen.setImageArray(imageArray);
        }
        	 //文件处理
        	if (StringUtils.isNotEmpty(demoGen.getFile())) {
        		String[] files = StringUtils.split(demoGen.getFile(), Constants.SEPARATOR);
        		List<FileInfo> fileArray = new ArrayList<>();
	        	for (String path :files) {
	        		SysFile sysFile = sysFileService.findById(FileConfig.getFileId(path));
	        		if (null != sysFile) {
	        			fileArray.add(new FileInfo(FileConfig.getFullUrl(path),path,sysFile.getName()));
	        		}
	        	}
	        	demoGen.setFileArray(fileArray);
        }
		return ResponeModel.ok(demoGen);
	}
	@RequiresPermissions("demo:gen:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated DemoGen demoGen, BindingResult bindingResult) {
		int cnt = demoGenService.save(demoGen);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("demo:gen:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated DemoGen demoGen, BindingResult bindingResult) {
		int cnt = demoGenService.updateSelective(demoGen);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("demo:gen:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = demoGenService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
