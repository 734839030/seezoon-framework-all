package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysParamDao;
import com.seezoon.framework.modules.system.entity.SysParam;

@Service
public class SysParamService extends CrudService<SysParamDao, SysParam>{

	public SysParam findByParamKey(String paramKey) {
		Assert.hasLength("paramKey","paramKey 不能为空");
		SysParam sysParam = new SysParam();
		sysParam.setParamKey(paramKey);
		List<SysParam> list = this.findList(sysParam);
		return list.isEmpty()?null:list.get(0);
	}
}
