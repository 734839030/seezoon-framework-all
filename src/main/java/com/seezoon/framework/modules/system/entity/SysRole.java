package com.seezoon.framework.modules.system.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;

public class SysRole extends BaseEntity<String>{

    /**
     * 角色名称
     */
	@NotNull
	@Length(min=1,max=50)
    private String name;

    /**
     * 数据范围
     */
	@NotNull
	@Length(min=1,max=1)
	@Pattern(regexp="0|1|2|3")
    private String dataScope;
	
	/**下列字段为业务字典**/
	
	public List<String> menuIds;
    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getDataScope() {
        return dataScope;
    }

    
    public void setDataScope(String dataScope) {
        this.dataScope = dataScope == null ? null : dataScope.trim();
    }


	public List<String> getMenuIds() {
		return menuIds;
	}


	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

}