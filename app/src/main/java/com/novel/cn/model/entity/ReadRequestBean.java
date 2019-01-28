package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/25.
 */

public class ReadRequestBean {


    /**
     * chapterInfo : {"id":"0f7cd473773e421d9c8d1489d7ed8f1b","uniqueId":"1545097950184","channelId":-1}
     */

    private ChapterInfoBean chapterInfo;

    public static ReadRequestBean objectFromData(String str) {

        return new Gson().fromJson(str, ReadRequestBean.class);
    }

    public static List<ReadRequestBean> arrayReadRequestBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ReadRequestBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public ChapterInfoBean getChapterInfo() {
        return chapterInfo;
    }

    public void setChapterInfo(ChapterInfoBean chapterInfo) {
        this.chapterInfo = chapterInfo;
    }

    public static class ChapterInfoBean {
        /**
         * id : 0f7cd473773e421d9c8d1489d7ed8f1b
         * uniqueId : 1545097950184
         * channelId : -1
         */

        private String id;
        private String uniqueId;
        private int channelId;

        public static ChapterInfoBean objectFromData(String str) {

            return new Gson().fromJson(str, ChapterInfoBean.class);
        }

        public static List<ChapterInfoBean> arrayChapterInfoBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ChapterInfoBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }
    }
}
