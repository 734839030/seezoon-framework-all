package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysDictDao;
import com.seezoon.framework.modules.system.entity.SysDict;

@Service
public class SysDictService extends CrudService<SysDictDao, SysDict> {

	public List<SysDict> findByType(String type) {
		Assert.hasLength(type, "type 不能为空");
		SysDict sysDict = new SysDict();
		sysDict.setType(type);
		sysDict.setSortField("sort");
		sysDict.setDirection(Constants.ASC);
		return this.findList(sysDict);
	}

	public List<String> findTypes() {
		return this.d.findTypes();
	}
}
