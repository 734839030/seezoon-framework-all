package com.seezoon.framework.common.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seezoon.framework.common.entity.BaseEntity;

/**
 * 当需要自动拥有增删改查功能时候继承
 * 
 * 不推荐联合主键,支持字符和整数主键
 * 
 * @author hdf 2017年8月30日
 * @param <M>
 *            mapper
 * @param <T>
 *            entity
 */
public interface CrudDao<T extends BaseEntity<?>> extends BaseDao {

	public int insert(T t);
	/**
	 * 关键业务场景需要备份表
	 * @param t
	 * @return
	 */
	public int insertBak(T t);
	public int updateByPrimaryKeySelective(T t);

	public int updateByPrimaryKey(T t);

	public T selectByPrimaryKey(Serializable id);

	public int deleteByPrimaryKey(@Param("id") Serializable id,@Param("dsf") String dsf);

	public List<T> findList(T t);
}
