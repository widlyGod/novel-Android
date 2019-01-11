package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/11.
 */

public class PersonDataBean {


    /**
     * basePage : null
     * code : 1
     * data : {"diamonds":"0","shareCount":"0","monthTickets":"0","userLevel":"0","userIntro":"","recommendTickets":"0","msgCount":"7","userName":"Jackie1","moneys":"9100","userId":"308"}
     * message : 查询成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static PersonDataBean objectFromData(String str) {

        return new Gson().fromJson(str, PersonDataBean.class);
    }

    public static List<PersonDataBean> arrayPersonDataBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<PersonDataBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getBasePage() {
        return basePage;
    }

    public void setBasePage(Object basePage) {
        this.basePage = basePage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * diamonds : 0
         * shareCount : 0
         * monthTickets : 0
         * userLevel : 0
         * userIntro :
         * recommendTickets : 0
         * msgCount : 7
         * userName : Jackie1
         * moneys : 9100
         * userId : 308
         */

        private String diamonds;
        private String shareCount;
        private String monthTickets;
        private String userLevel;
        private String userIntro;
        private String recommendTickets;
        private String msgCount;
        private String userName;
        private String moneys;
        private String userId;
        private String userPhoto;



        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getDiamonds() {
            return diamonds;
        }

        public void setDiamonds(String diamonds) {
            this.diamonds = diamonds;
        }

        public String getShareCount() {
            return shareCount;
        }

        public void setShareCount(String shareCount) {
            this.shareCount = shareCount;
        }

        public String getMonthTickets() {
            return monthTickets;
        }

        public void setMonthTickets(String monthTickets) {
            this.monthTickets = monthTickets;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public String getUserIntro() {
            return userIntro;
        }

        public void setUserIntro(String userIntro) {
            this.userIntro = userIntro;
        }

        public String getRecommendTickets() {
            return recommendTickets;
        }

        public void setRecommendTickets(String recommendTickets) {
            this.recommendTickets = recommendTickets;
        }

        public String getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(String msgCount) {
            this.msgCount = msgCount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMoneys() {
            return moneys;
        }

        public void setMoneys(String moneys) {
            this.moneys = moneys;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }
    }
}
