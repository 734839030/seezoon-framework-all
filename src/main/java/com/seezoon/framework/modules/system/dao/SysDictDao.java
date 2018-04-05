package com.seezoon.framework.modules.system.dao;

import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysDict;

public interface SysDictDao extends CrudDao<SysDict>{
    public List<String> findTypes();
}