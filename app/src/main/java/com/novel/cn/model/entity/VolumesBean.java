package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/21.
 */

public class VolumesBean {


    /**
     * basePage : null
     * code : 1
     * data : [{"volume":"1","title":"第一卷","chapterNum":"2"},{"volume":"2","title":"第二卷","chapterNum":"4"},{"volume":"3","title":"第三卷","chapterNum":"5"},{"volume":"4","title":"new章节","chapterNum":"8"},{"volume":"5","title":"移到后创建新卷","chapterNum":"242"},{"volume":"6","title":"newCreatenew84","chapterNum":"74"}]
     * message : 查询成功！
     * success : true
     */

    private Object basePage;
    private String code;
    private String message;
    private boolean success;
    private List<DataBean> data;

    public static VolumesBean objectFromData(String str) {

        return new Gson().fromJson(str, VolumesBean.class);
    }

    public static List<VolumesBean> arrayVolumesBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<VolumesBean>>() {
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
         * volume : 1
         * title : 第一卷
         * chapterNum : 2
         */

        private String volume;
        private String title;
        private String chapterNum;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getChapterNum() {
            return chapterNum;
        }

        public void setChapterNum(String chapterNum) {
            this.chapterNum = chapterNum;
        }
    }
}
