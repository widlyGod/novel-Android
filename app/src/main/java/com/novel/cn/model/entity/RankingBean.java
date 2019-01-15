package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/15.
 */

public class RankingBean {


    /**
     * basePage : null
     * code : 1
     * data : [{"code":"ALL","name":"综合榜"},{"code":"RECOMMEND","name":"推荐榜"},{"code":"CLICK","name":"点击榜"},{"code":"SUBANDCOL","name":"订阅收藏榜"},{"code":"FINISH","name":"完结榜"},{"code":"UPDATE","name":"连载榜"},{"code":"NEW","name":"新书榜"},{"code":"DIAMOND","name":"钻石榜"},{"code":"READING","name":"捧场榜"},{"code":"MONTHLY","name":"月票榜"}]
     * message : 操作成功
     * success : true
     */

    private Object basePage;
    private String code;
    private String message;
    private boolean success;
    private List<DataBean> data;

    public static RankingBean objectFromData(String str) {

        return new Gson().fromJson(str, RankingBean.class);
    }

    public static List<RankingBean> arrayRankingBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankingBean>>() {
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : ALL
         * name : 综合榜
         */
        private boolean isClck;
        private String code;
        private String name;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }


        public boolean isClck() {
            return isClck;
        }

        public void setClck(boolean clck) {
            isClck = clck;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
