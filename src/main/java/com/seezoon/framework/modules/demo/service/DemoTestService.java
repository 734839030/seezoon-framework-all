package com.seezoon.framework.modules.demo.service;

import org.springframework.stereotype.Service;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.demo.dao.DemoTestDao;
import com.seezoon.framework.modules.demo.entity.DemoTest;

@Service
public class DemoTestService extends CrudService<DemoTestDao, DemoTest>{
	
}
