package com.seezoon.framework.modules.${moduleName}.web;

import java.io.Serializable;
<#if hasRichText>
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
</#if>
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.${moduleName}.entity.${className};
import com.seezoon.framework.modules.${moduleName}.service.${className}Service;

/**
 * ${menuName}controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf ${.now}
 */
@RestController
@RequestMapping("${r'${admin.path}'}/${moduleName}/${functionName}")
public class ${className}Controller extends BaseController {

	@Autowired
	private ${className}Service ${className?uncap_first}Service;

	@RequiresPermissions("${moduleName}:${functionName}:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(${className} ${className?uncap_first}) {
		PageInfo<${className}> page = ${className?uncap_first}Service.findByPage(${className?uncap_first}, ${className?uncap_first}.getPage(), ${className?uncap_first}.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("${moduleName}:${functionName}:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		${className} ${className?uncap_first} = ${className?uncap_first}Service.findById(id);
		//富文本处理
		<#list columnInfos as columnInfo>
		<#if columnInfo.inputType! == "richtext">
        if (null != ${className?uncap_first}) {
		    if (StringUtils.isNotEmpty(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}())) {
				${className?uncap_first}.set${columnInfo.javaFieldName?cap_first}(StringEscapeUtils.unescapeHtml4(${className?uncap_first}.get${columnInfo.javaFieldName?cap_first}()));
			}
		}
        </#if>
   		</#list>
		return ResponeModel.ok(${className?uncap_first});
	}
	@RequiresPermissions("${moduleName}:${functionName}:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated ${className} ${className?uncap_first}, BindingResult bindingResult) {
		int cnt = ${className?uncap_first}Service.save(${className?uncap_first});
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("${moduleName}:${functionName}:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated ${className} ${className?uncap_first}, BindingResult bindingResult) {
		int cnt = ${className?uncap_first}Service.updateSelective(${className?uncap_first});
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("${moduleName}:${functionName}:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = ${className?uncap_first}Service.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
