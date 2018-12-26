package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2018/12/25.
 */

public class UserBean {

    /**
     * baiduOpenId :
     * basePath :
     * beThirdLogin :
     * beWriter : false
     * channelId : null
     * count : 0.0
     * createTime : 1545719606000
     * grade : 0
     * imgCode :
     * isThree : false
     * isVip : 0
     * newPwd :
     * openId :
     * qqOpenId :
     * recodeCode : 100
     * sessionId : session-1290cfa3eb094895a2999255888fe758
     * sinawebOpenId :
     * userAddress :
     * userEmail : 412815117@qq.com
     * userGender : null
     * userId : 302
     * userIntroduction :
     * userName : 李杰
     * userNickName : 李杰
     * userPassword :
     * userPhoto :
     * userTel :
     * wechatOpenId :
     */

    private String baiduOpenId;
    private String basePath;
    private String beThirdLogin;
    private String beWriter;
    private Object channelId;
    private double count;
    private long createTime;
    private int grade;
    private String imgCode;
    private String isThree;
    private int isVip;
    private String newPwd;
    private String openId;
    private String qqOpenId;
    private int recodeCode;
    private String sessionId;
    private String sinawebOpenId;
    private String userAddress;
    private String userEmail;
    private Object userGender;
    private int userId;
    private String userIntroduction;
    private String userName;
    private String userNickName;
    private String userPassword;
    private String userPhoto;
    private String userTel;
    private String wechatOpenId;

    public static UserBean objectFromData(String str) {

        return new Gson().fromJson(str, UserBean.class);
    }

    public static List<UserBean> arrayDataBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<UserBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }





    public String getBaiduOpenId() {
        return baiduOpenId;
    }

    public void setBaiduOpenId(String baiduOpenId) {
        this.baiduOpenId = baiduOpenId;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBeThirdLogin() {
        return beThirdLogin;
    }

    public void setBeThirdLogin(String beThirdLogin) {
        this.beThirdLogin = beThirdLogin;
    }

    public String getBeWriter() {
        return beWriter;
    }

    public void setBeWriter(String beWriter) {
        this.beWriter = beWriter;
    }

    public Object getChannelId() {
        return channelId;
    }

    public void setChannelId(Object channelId) {
        this.channelId = channelId;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getIsThree() {
        return isThree;
    }

    public void setIsThree(String isThree) {
        this.isThree = isThree;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public int getRecodeCode() {
        return recodeCode;
    }

    public void setRecodeCode(int recodeCode) {
        this.recodeCode = recodeCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSinawebOpenId() {
        return sinawebOpenId;
    }

    public void setSinawebOpenId(String sinawebOpenId) {
        this.sinawebOpenId = sinawebOpenId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Object getUserGender() {
        return userGender;
    }

    public void setUserGender(Object userGender) {
        this.userGender = userGender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "baiduOpenId='" + baiduOpenId + '\'' +
                ", basePath='" + basePath + '\'' +
                ", beThirdLogin='" + beThirdLogin + '\'' +
                ", beWriter='" + beWriter + '\'' +
                ", channelId=" + channelId +
                ", count=" + count +
                ", createTime=" + createTime +
                ", grade=" + grade +
                ", imgCode='" + imgCode + '\'' +
                ", isThree='" + isThree + '\'' +
                ", isVip=" + isVip +
                ", newPwd='" + newPwd + '\'' +
                ", openId='" + openId + '\'' +
                ", qqOpenId='" + qqOpenId + '\'' +
                ", recodeCode=" + recodeCode +
                ", sessionId='" + sessionId + '\'' +
                ", sinawebOpenId='" + sinawebOpenId + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userGender=" + userGender +
                ", userId=" + userId +
                ", userIntroduction='" + userIntroduction + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", userTel='" + userTel + '\'' +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                '}';
    }
}
