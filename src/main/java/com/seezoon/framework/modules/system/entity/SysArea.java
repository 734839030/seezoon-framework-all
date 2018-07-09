package com.seezoon.framework.modules.system.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
/**
 * 区域表
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-7-9 21:56:46
 */
public class SysArea extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @NotNull
    @Length(min = 1, max = 100)
    private String name;
    /**
     * 父id 根节点为0
     */
    @NotNull
    @Length(min = 1, max = 32)
    private String parentId;
    /**
     * 排序
     */
    @NotNull
    private Integer sort;
    /**
     * 区域类型1省，2市，3区、县
     */
    @NotNull
    @Length(min = 1, max = 1)
    private String type;
    /**
     * 是否启用1:是；0:否
     */
    @NotNull
    @Length(min = 1, max = 1)
    private String status;
    
    public static final String AREA_TYPE_PROVINCE = "1";
    public static final String AREA_TYPE_CITY = "2";
    public static final String AREA_TYPE_COUNTY = "3";
    public static final String ROOT_ID = "0";

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getParentId(){
        return parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    public Integer getSort(){
        return sort;
    }
    public void setSort(Integer sort){
        this.sort = sort;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}