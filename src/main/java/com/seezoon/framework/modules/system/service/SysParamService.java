package com.seezoon.framework.modules.system.service;

import org.springframework.stereotype.Service;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysParamDao;
import com.seezoon.framework.modules.system.entity.SysParam;

@Service
public class SysParamService extends CrudService<SysParamDao, SysParam>{

}
