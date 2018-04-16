package com.seezoon.framework.modules.system.service;

import org.springframework.stereotype.Service;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysFileDao;
import com.seezoon.framework.modules.system.entity.SysFile;

@Service
public class SysFileService extends CrudService<SysFileDao, SysFile>{

}
