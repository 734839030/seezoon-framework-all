package com.seezoon.framework.modules.wechat.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 用户信息
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-6-18 10:29:20
 */
public class WechatUserInfo extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 昵称
     */
    @Length(max = 100)
    private String nickname;
    /**
     * 性别(值为1时是男性，值为2时是女性，值为0时是未知)
     */
    @Length(max = 1)
    private String sex;
    /**
     * 国家
     */
    @Length(max = 64)
    private String country;
    /**
     * 省份
     */
    @Length(max = 64)
    private String province;
    /**
     * 城市
     */
    @Length(max = 64)
    private String city;
    /**
     * 图像
     */
    @Length(max = 500)
    private String headImgUrl;
    /**
     * 是否关注
     */
    @Length(max = 2)
    private String subscribe;
    /**
     * 关注时间
     */
    private Date subscribeTime;
    /**
     * 关注场景
     */
    @Length(max = 100)
    private String subscribeScene;
    /**
     * openid
     */
    @NotNull
    @Length(min = 1, max = 64)
    private String openid;
    /**
     * unionid
     */
    @Length(max = 100)
    private String unionid;
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getProvince(){
        return province;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getHeadImgUrl(){
        return headImgUrl;
    }
    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }
    public String getSubscribe(){
        return subscribe;
    }
    public void setSubscribe(String subscribe){
        this.subscribe = subscribe;
    }
    public Date getSubscribeTime(){
        return subscribeTime;
    }
    public void setSubscribeTime(Date subscribeTime){
        this.subscribeTime = subscribeTime;
    }
    public String getSubscribeScene(){
        return subscribeScene;
    }
    public void setSubscribeScene(String subscribeScene){
        this.subscribeScene = subscribeScene;
    }
    public String getOpenid(){
        return openid;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }
    public String getUnionid(){
        return unionid;
    }
    public void setUnionid(String unionid){
        this.unionid = unionid;
    }
}