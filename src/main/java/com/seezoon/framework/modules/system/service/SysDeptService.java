package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysDeptDao;
import com.seezoon.framework.modules.system.entity.SysDept;

@Service
public class SysDeptService extends CrudService<SysDeptDao, SysDept>{

	public List<SysDept> findByParentIds(String parentIds){
		Assert.hasLength(parentIds,"parentIds 不能为空");
		SysDept sysDept = new SysDept();
		sysDept.setSortField("sort");
		sysDept.setDirection(Constants.ASC);
		sysDept.setParentIds(parentIds);
		return this.findList(sysDept);
	}
	@Override
	public int deleteById(Serializable id) {
		//删除下级部门
		SysDept sysDept = this.findById(id);
		Assert.notNull(sysDept,"部门不能为空");
		List<SysDept> list = this.findByParentIds(sysDept.getParentIds() + sysDept.getId());
		for (SysDept sDept : list) {
			super.deleteById(sDept.getId());
			this.d.deleteRoleDeptByDeptId(sDept.getId());
		}
		this.d.deleteRoleDeptByDeptId(id);
		return super.deleteById(id);
	}
}
