package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/9.
 */

public class QueryUpayBean {


    /**
     * basePage : null
     * code : 1
     * data : {"diamondNumber":0,"diamondValidity":null,"goldNumber":200,"isSubscibe":null,"monthRecommendNumber":0,"monthRecommendValidity":"","prendarGoldNumber":0,"recargaGoldNumber":200,"recommendNumber":0,"userId":308}
     * message : 查询个人账户信息成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static QueryUpayBean objectFromData(String str) {

        return new Gson().fromJson(str, QueryUpayBean.class);
    }

    public static List<QueryUpayBean> arrayQueryUpayBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<QueryUpayBean>>() {
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
         * diamondNumber : 0
         * diamondValidity : null
         * goldNumber : 200.0
         * isSubscibe : null
         * monthRecommendNumber : 0
         * monthRecommendValidity :
         * prendarGoldNumber : 0.0
         * recargaGoldNumber : 200.0
         * recommendNumber : 0
         * userId : 308
         */

        private int diamondNumber;
        private Object diamondValidity;
        private double goldNumber;
        private Object isSubscibe;
        private int monthRecommendNumber;
        private String monthRecommendValidity;
        private double prendarGoldNumber;
        private double recargaGoldNumber;
        private int recommendNumber;
        private int userId;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getDiamondNumber() {
            return diamondNumber;
        }

        public void setDiamondNumber(int diamondNumber) {
            this.diamondNumber = diamondNumber;
        }

        public Object getDiamondValidity() {
            return diamondValidity;
        }

        public void setDiamondValidity(Object diamondValidity) {
            this.diamondValidity = diamondValidity;
        }

        public double getGoldNumber() {
            return goldNumber;
        }

        public void setGoldNumber(double goldNumber) {
            this.goldNumber = goldNumber;
        }

        public Object getIsSubscibe() {
            return isSubscibe;
        }

        public void setIsSubscibe(Object isSubscibe) {
            this.isSubscibe = isSubscibe;
        }

        public int getMonthRecommendNumber() {
            return monthRecommendNumber;
        }

        public void setMonthRecommendNumber(int monthRecommendNumber) {
            this.monthRecommendNumber = monthRecommendNumber;
        }

        public String getMonthRecommendValidity() {
            return monthRecommendValidity;
        }

        public void setMonthRecommendValidity(String monthRecommendValidity) {
            this.monthRecommendValidity = monthRecommendValidity;
        }

        public double getPrendarGoldNumber() {
            return prendarGoldNumber;
        }

        public void setPrendarGoldNumber(double prendarGoldNumber) {
            this.prendarGoldNumber = prendarGoldNumber;
        }

        public double getRecargaGoldNumber() {
            return recargaGoldNumber;
        }

        public void setRecargaGoldNumber(double recargaGoldNumber) {
            this.recargaGoldNumber = recargaGoldNumber;
        }

        public int getRecommendNumber() {
            return recommendNumber;
        }

        public void setRecommendNumber(int recommendNumber) {
            this.recommendNumber = recommendNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
