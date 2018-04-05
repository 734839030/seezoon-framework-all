package com.seezoon.framework.modules.system.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;

public class SysDict extends BaseEntity<String>{

    /**
     * 字典类型
     */
	@NotNull
	@Length(min=1,max=50)
    private String type;

    /**
     * 编码
     */
	@NotNull
	@Length(min=1,max=50)
    private String code;

    /**
     * 名称
     */
	@NotNull
	@Length(min=1,max=50)
    private String name;

    /**
     * 排序
     */
	@NotNull
    private Integer sort;

    /**
     * 是否启用1：是，0：否
     */
	@NotNull
	@Pattern(regexp="1|0")
    private String status;
    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    
    public String getCode() {
        return code;
    }

    
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public Integer getSort() {
        return sort;
    }

    
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}