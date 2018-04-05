package com.seezoon.framework.common.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.common.entity.BaseEntity;
import com.seezoon.framework.common.utils.IdGen;

/**
 * 增删改查
 * 
 * @author hdf 2018年3月31日
 * @param <D>
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
public class CrudService<D extends CrudDao<T>, T extends BaseEntity<String>> extends BaseService {

	@Autowired
	protected D d;

	public int save(T t) {
		t.setCreateDate(new Date());
		t.setUpdateDate(t.getCreateDate());
		if (null == t.getId() || StringUtils.isEmpty(t.getId().toString())) {
			t.setId(IdGen.uuid());
		}
		return d.insert(t);
	}

	public int updateSelective(T t) {
		t.setUpdateDate(new Date());
		return d.updateByPrimaryKeySelective(t);
	}

	public int updateById(T t) {
		t.setUpdateDate(new Date());
		return d.updateByPrimaryKey(t);
	}

	@Transactional(readOnly = true)
	public T findById(Serializable id) {
		return d.selectByPrimaryKey(id);
	}

	public int deleteById(Serializable id) {
		return d.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	public List<T> findList(T t) {
		return d.findList(t);
	}

	/**
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @param count 是否统计总数 
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageInfo<T> findByPage(T t, int pageNum, int pageSize, boolean count) {
		PageHelper.startPage(pageNum, pageSize, count);
		List<T> list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}

	@Transactional(readOnly = true)
	public PageInfo<T> findByPage(T t, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize, Boolean.FALSE);
		List<T> list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
}
