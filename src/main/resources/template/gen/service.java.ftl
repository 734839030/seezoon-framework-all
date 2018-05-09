package com.seezoon.framework.modules.${moduleName}.service;

import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.${moduleName}.dao.${className}Dao;
import com.seezoon.framework.modules.${moduleName}.entity.${className};

@Service
public class ${className}Service extends CrudService<${className}Dao, ${className}>{

}
