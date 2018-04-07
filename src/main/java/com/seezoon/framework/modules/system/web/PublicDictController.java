package com.seezoon.framework.modules.system.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysDict;
import com.seezoon.framework.modules.system.service.SysDictService;

/**
 * 公共字典数据
 * 
 * @author hdf 2018年4月5日
 */
@RestController
@RequestMapping("/public/dict")
public class PublicDictController extends BaseController {

	@Autowired
	private SysDictService sysDictService;

	@RequestMapping("/getDictsByType.do")
	public ResponeModel getDictsByType(@RequestParam String type) {
		List<SysDict> list = sysDictService.findByType(type);
		List<Map<String,String>> resList = Lists.newArrayList();
		for (SysDict sysDict:list) {
			Map<String,String> dictMap = Maps.newLinkedHashMap();
			dictMap.put("code", sysDict.getCode());
			dictMap.put("name", sysDict.getName());
			dictMap.put("status", sysDict.getStatus());
			resList.add(dictMap);
		}
		return ResponeModel.ok(resList);
	}
}
