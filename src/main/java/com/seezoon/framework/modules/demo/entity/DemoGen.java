package com.seezoon.framework.modules.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.seezoon.framework.common.file.beans.FileInfo;
import java.util.List;
/**
 * 生成案例
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-5-22 1:00:14
 */
public class DemoGen extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 文本
     */
    @NotNull
    @Length(min = 1, max = 50)
    private String inputText;
    /**
     * 下拉
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String inputSelect;
    /**
     * 单选
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String inputRadio;
    /**
     * 复选
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String inputCheckbox;
    /**
     * 文本域
     */
    @Length(max = 65535)
    private String inputTextarea;
    /**
     * 日期
     */
    @NotNull
    @JSONField(format="yyyy-MM-dd")
    private Date inputDate;
    /**
     * 整数
     */
    private Integer inputZhengshu;
    /**
     * 小数
     */
    private Double inputXiaoshu;
    /**
     * 富文本
     */
    @Length(max = 16777215)
    private String richText;
    /**
     * 图片
     */
    @NotNull
    @Length(min = 1, max = 255)
    private String image;
    private List<FileInfo> imageArray;
    public List<FileInfo> getImageArray(){
        return imageArray;
    }
    public void setImageArray(List<FileInfo> imageArray){
        this.imageArray = imageArray;
    }
    /**
     * 文件
     */
    @NotNull
    @Length(min = 1, max = 255)
    private String file;
    private List<FileInfo> fileArray;
    public List<FileInfo> getFileArray(){
        return fileArray;
    }
    public void setFileArray(List<FileInfo> fileArray){
        this.fileArray = fileArray;
    }
    public String getInputText(){
        return inputText;
    }
    public void setInputText(String inputText){
        this.inputText = inputText;
    }
    public String getInputSelect(){
        return inputSelect;
    }
    public void setInputSelect(String inputSelect){
        this.inputSelect = inputSelect;
    }
    public String getInputRadio(){
        return inputRadio;
    }
    public void setInputRadio(String inputRadio){
        this.inputRadio = inputRadio;
    }
    public String getInputCheckbox(){
        return inputCheckbox;
    }
    public void setInputCheckbox(String inputCheckbox){
        this.inputCheckbox = inputCheckbox;
    }
    public String getInputTextarea(){
        return inputTextarea;
    }
    public void setInputTextarea(String inputTextarea){
        this.inputTextarea = inputTextarea;
    }
    public Date getInputDate(){
        return inputDate;
    }
    public void setInputDate(Date inputDate){
        this.inputDate = inputDate;
    }
    public Integer getInputZhengshu(){
        return inputZhengshu;
    }
    public void setInputZhengshu(Integer inputZhengshu){
        this.inputZhengshu = inputZhengshu;
    }
    public Double getInputXiaoshu(){
        return inputXiaoshu;
    }
    public void setInputXiaoshu(Double inputXiaoshu){
        this.inputXiaoshu = inputXiaoshu;
    }
    public String getRichText(){
        return richText;
    }
    public void setRichText(String richText){
        this.richText = richText;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getFile(){
        return file;
    }
    public void setFile(String file){
        this.file = file;
    }
}