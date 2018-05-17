package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.file.FileConfig;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysFile;
import com.seezoon.framework.modules.system.service.SysFileService;

@RestController
@RequestMapping("${admin.path}/sys/file")
public class SysFileController extends BaseController {

	@Autowired
	private SysFileService sysFileService;
	
	@RequiresPermissions("sys:file:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysFile sysFile,@RequestParam(required=false) Date startDate,@RequestParam(required=false) Date endDate) {
		sysFile.addProperty("startDate", startDate);
		sysFile.addProperty("endDate", endDate);
		PageInfo<SysFile> page = sysFileService.findByPage(sysFile, sysFile.getPage(), sysFile.getPageSize());
		for (SysFile file: page.getList()) {
			file.setFullUrl(FileConfig.getFullUrl(file.getRelativePath()));
		}
		return ResponeModel.ok(page);
	}
	
	@RequiresPermissions("sys:file:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysFileService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	
	
}
