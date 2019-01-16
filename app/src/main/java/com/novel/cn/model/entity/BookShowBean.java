package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/15.
 */

public class BookShowBean {


    /**
     * basePage : null
     * code : 1
     * data : {"total":145,"WEEK":[{"basePath":"/novel_b/novel","chapter":"2","chapterId":"a2e752ae1f7b445889e9a427e1b2838f","chapterIsFree":"0","chapterMoney":"9","chapterSubTitle":"90","chapterVolume":"0","chapterVolumeId":"e85fc1b8bc3549fbbf9de18335c2d340","createTime":1543285399000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"哈哈","novelId":"106837f7748746ec816ae2816850d268","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/106837f7748746ec816ae2816850d268/novel_img_1543285384217_0.jpg","novelTitle":"xiaosa测试1","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1547445079000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"6","chapterId":"993926fe26dc4e40b4fba481523242ff","chapterIsFree":"0","chapterMoney":"9","chapterSubTitle":"90","chapterVolume":"0","chapterVolumeId":"ebb906f9915d44ee9feeb3ff662fb5d2","createTime":1547029545000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"","novelId":"4c42e35a427b4ad3a00652c5f9f4ad7e","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4c42e35a427b4ad3a00652c5f9f4ad7e/novel_img_1547029540085_0.jpg","novelTitle":"禅道","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1547452882000,"userId":"234","writer":"我是大作家"},{"basePath":"/novel_a/novel","chapter":"2","chapterId":"cbc4d4b7eaf1404ab9b176c8162acbf1","chapterIsFree":"0","chapterMoney":"12","chapterSubTitle":"test001","chapterVolume":"0","chapterVolumeId":"3790321edc154f0c85bc406c6270fc65","createTime":1541655802000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"作者创建独家首发无卷小说001","novelId":"7e01e72401d9443d8416f3bb8020070c","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelTitle":"作者创建独家首发无卷小说001","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541664274000,"userId":"235","writer":"tianx"},{"basePath":"/novel_a/novel","chapter":"3","chapterId":"9b240497a29f425d9cda9b569b698b4c","chapterIsFree":"0","chapterMoney":"15","chapterSubTitle":"第003章 客人","chapterVolume":"0","chapterVolumeId":"1ee8fbbf74864dd99b22ba85a4798388","createTime":1541656832000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"管理员创建独家首发免费小说001","novelId":"4b82610b3aa14b699005f6559a0c8081","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelTitle":"管理员创建独家首发免费小说001","novelType":"军事","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1543285338000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"4","chapterId":"42121aa1f6244326b85cfa8a4c52f6ff","chapterIsFree":"0","chapterMoney":"60","chapterSubTitle":"10000","chapterVolume":"0","chapterVolumeId":"1368be6256a6403da1c0da45e828fde0","createTime":1541656204000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"那年夏天","novelId":"e1a32c8a358e49f8ae9c576864cc5797","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/e1a32c8a358e49f8ae9c576864cc5797/e9f25b8025b8480d881591fbbbf281a5.jpg","novelTitle":"驻站1","novelType":"历史","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541656225000,"userId":"232","writer":"老铁瓜1"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"588b349316c8474f9eba129b3470d050","chapterIsFree":"0","chapterMoney":"30","chapterSubTitle":"5k字符","chapterVolume":"0","chapterVolumeId":"d8be452df9df47c5b0813090908d48ac","createTime":1541656483000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"管理员创建驻站作品小说001","novelId":"80bd05450ceb4233b90450fb351a8e7e","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelTitle":"管理员创建驻站作品小说001","novelType":"武侠","rankWay":null,"score":9,"scoreNum":1,"type":"","updateTime":1541656497000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"79d5303d21024392a3435bdc3c4a988a","chapterIsFree":"0","chapterMoney":"30","chapterSubTitle":"5k字符","chapterVolume":"4","chapterVolumeId":"e850751c029a480b9b221aae4fc3d674","createTime":1541655848000,"haveVolumes":1,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"驻站有卷小说001","novelId":"bc35cf06340b498cb1e638b551616fbe","novelParentType":"女生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/bc35cf06340b498cb1e638b551616fbe/2af1b94cf0e34c7faf64a8bf75faaf8d.jpg","novelTitle":"驻站有卷小说001","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541664729000,"userId":"235","writer":"tianx"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"23664fb388084a8a8460aefc9302a4ae","chapterIsFree":"0","chapterMoney":"6","chapterSubTitle":"test001","chapterVolume":"0","chapterVolumeId":"093fb0c147db441ba5079eb918fd0bd8","createTime":1542076598000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"dfsdf","novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/novel_img_1542076587368_0.jpg","novelTitle":"test1113","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1542076626000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"","chapterId":"","chapterIsFree":"","chapterMoney":"","chapterSubTitle":"","chapterVolume":"","chapterVolumeId":"","createTime":1543888807000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"test","novelId":"b1586554cc764fb388d17d708ed834a2","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/b1586554cc764fb388d17d708ed834a2/novel_img_1543888797072_0.jpg","novelTitle":"testqqq","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1543888807000,"userId":"231","writer":"甜心01"},{"basePath":"","chapter":"","chapterId":"","chapterIsFree":"","chapterMoney":"","chapterSubTitle":"","chapterVolume":"","chapterVolumeId":"","createTime":1545733971000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"","novelId":"240cf5c688d045cc94e4793ba2fc7d7f","novelParentType":"男生频道","novelPhoto":"","novelTitle":"12345","novelType":"武侠","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1545733971000,"userId":"234","writer":"我是大作家"}]}
     * message : 操作成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static BookShowBean objectFromData(String str) {

        return new Gson().fromJson(str, BookShowBean.class);
    }

    public static List<BookShowBean> arrayBookShowBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BookShowBean>>() {
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
         * total : 145
         * WEEK : [{"basePath":"/novel_b/novel","chapter":"2","chapterId":"a2e752ae1f7b445889e9a427e1b2838f","chapterIsFree":"0","chapterMoney":"9","chapterSubTitle":"90","chapterVolume":"0","chapterVolumeId":"e85fc1b8bc3549fbbf9de18335c2d340","createTime":1543285399000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"哈哈","novelId":"106837f7748746ec816ae2816850d268","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/106837f7748746ec816ae2816850d268/novel_img_1543285384217_0.jpg","novelTitle":"xiaosa测试1","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1547445079000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"6","chapterId":"993926fe26dc4e40b4fba481523242ff","chapterIsFree":"0","chapterMoney":"9","chapterSubTitle":"90","chapterVolume":"0","chapterVolumeId":"ebb906f9915d44ee9feeb3ff662fb5d2","createTime":1547029545000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"","novelId":"4c42e35a427b4ad3a00652c5f9f4ad7e","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4c42e35a427b4ad3a00652c5f9f4ad7e/novel_img_1547029540085_0.jpg","novelTitle":"禅道","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1547452882000,"userId":"234","writer":"我是大作家"},{"basePath":"/novel_a/novel","chapter":"2","chapterId":"cbc4d4b7eaf1404ab9b176c8162acbf1","chapterIsFree":"0","chapterMoney":"12","chapterSubTitle":"test001","chapterVolume":"0","chapterVolumeId":"3790321edc154f0c85bc406c6270fc65","createTime":1541655802000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"作者创建独家首发无卷小说001","novelId":"7e01e72401d9443d8416f3bb8020070c","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelTitle":"作者创建独家首发无卷小说001","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541664274000,"userId":"235","writer":"tianx"},{"basePath":"/novel_a/novel","chapter":"3","chapterId":"9b240497a29f425d9cda9b569b698b4c","chapterIsFree":"0","chapterMoney":"15","chapterSubTitle":"第003章 客人","chapterVolume":"0","chapterVolumeId":"1ee8fbbf74864dd99b22ba85a4798388","createTime":1541656832000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"管理员创建独家首发免费小说001","novelId":"4b82610b3aa14b699005f6559a0c8081","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelTitle":"管理员创建独家首发免费小说001","novelType":"军事","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1543285338000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"4","chapterId":"42121aa1f6244326b85cfa8a4c52f6ff","chapterIsFree":"0","chapterMoney":"60","chapterSubTitle":"10000","chapterVolume":"0","chapterVolumeId":"1368be6256a6403da1c0da45e828fde0","createTime":1541656204000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"那年夏天","novelId":"e1a32c8a358e49f8ae9c576864cc5797","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/e1a32c8a358e49f8ae9c576864cc5797/e9f25b8025b8480d881591fbbbf281a5.jpg","novelTitle":"驻站1","novelType":"历史","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541656225000,"userId":"232","writer":"老铁瓜1"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"588b349316c8474f9eba129b3470d050","chapterIsFree":"0","chapterMoney":"30","chapterSubTitle":"5k字符","chapterVolume":"0","chapterVolumeId":"d8be452df9df47c5b0813090908d48ac","createTime":1541656483000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"管理员创建驻站作品小说001","novelId":"80bd05450ceb4233b90450fb351a8e7e","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelTitle":"管理员创建驻站作品小说001","novelType":"武侠","rankWay":null,"score":9,"scoreNum":1,"type":"","updateTime":1541656497000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"79d5303d21024392a3435bdc3c4a988a","chapterIsFree":"0","chapterMoney":"30","chapterSubTitle":"5k字符","chapterVolume":"4","chapterVolumeId":"e850751c029a480b9b221aae4fc3d674","createTime":1541655848000,"haveVolumes":1,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"驻站有卷小说001","novelId":"bc35cf06340b498cb1e638b551616fbe","novelParentType":"女生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/bc35cf06340b498cb1e638b551616fbe/2af1b94cf0e34c7faf64a8bf75faaf8d.jpg","novelTitle":"驻站有卷小说001","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1541664729000,"userId":"235","writer":"tianx"},{"basePath":"/novel_b/novel","chapter":"1","chapterId":"23664fb388084a8a8460aefc9302a4ae","chapterIsFree":"0","chapterMoney":"6","chapterSubTitle":"test001","chapterVolume":"0","chapterVolumeId":"093fb0c147db441ba5079eb918fd0bd8","createTime":1542076598000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"dfsdf","novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/novel_img_1542076587368_0.jpg","novelTitle":"test1113","novelType":"灵异","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1542076626000,"userId":"231","writer":"甜心01"},{"basePath":"/novel_a/novel","chapter":"","chapterId":"","chapterIsFree":"","chapterMoney":"","chapterSubTitle":"","chapterVolume":"","chapterVolumeId":"","createTime":1543888807000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"test","novelId":"b1586554cc764fb388d17d708ed834a2","novelParentType":"男生频道","novelPhoto":"http://59.110.124.41:80/novel_a/novel/b1586554cc764fb388d17d708ed834a2/novel_img_1543888797072_0.jpg","novelTitle":"testqqq","novelType":"科幻","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1543888807000,"userId":"231","writer":"甜心01"},{"basePath":"","chapter":"","chapterId":"","chapterIsFree":"","chapterMoney":"","chapterSubTitle":"","chapterVolume":"","chapterVolumeId":"","createTime":1545733971000,"haveVolumes":0,"isFree":"0","isFreeLimit":"notLimit","isOver":"0","novelDescribe":"","novelId":"240cf5c688d045cc94e4793ba2fc7d7f","novelParentType":"男生频道","novelPhoto":"","novelTitle":"12345","novelType":"武侠","rankWay":null,"score":null,"scoreNum":null,"type":"","updateTime":1545733971000,"userId":"234","writer":"我是大作家"}]
         */

        private int total;
        private List<WEEKBean> WEEK;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<WEEKBean> getWEEK() {
            return WEEK;
        }

        public void setWEEK(List<WEEKBean> WEEK) {
            this.WEEK = WEEK;
        }

        public static class WEEKBean {
            /**
             * basePath : /novel_b/novel
             * chapter : 2
             * chapterId : a2e752ae1f7b445889e9a427e1b2838f
             * chapterIsFree : 0
             * chapterMoney : 9
             * chapterSubTitle : 90
             * chapterVolume : 0
             * chapterVolumeId : e85fc1b8bc3549fbbf9de18335c2d340
             * createTime : 1543285399000
             * haveVolumes : 0
             * isFree : 0
             * isFreeLimit : notLimit
             * isOver : 0
             * novelDescribe : 哈哈
             * novelId : 106837f7748746ec816ae2816850d268
             * novelParentType : 男生频道
             * novelPhoto : http://59.110.124.41:80/novel_b/novel/106837f7748746ec816ae2816850d268/novel_img_1543285384217_0.jpg
             * novelTitle : xiaosa测试1
             * novelType : 灵异
             * rankWay : null
             * score : null
             * scoreNum : null
             * type :
             * updateTime : 1547445079000
             * userId : 231
             * writer : 甜心01
             */

            private String isCollection;

            private String basePath;
            private String chapter;
            private String chapterId;
            private String chapterIsFree;
            private String chapterMoney;
            private String chapterSubTitle;
            private String chapterVolume;
            private String chapterVolumeId;
            private long createTime;
            private int haveVolumes;
            private String isFree;
            private String isFreeLimit;
            private String isOver;
            private String novelDescribe;
            private String novelId;
            private String novelParentType;
            private String novelPhoto;
            private String novelTitle;
            private String novelType;
            private Object rankWay;
            private Object score;
            private Object scoreNum;
            private String type;
            private long updateTime;
            private String userId;
            private String writer;

            public static WEEKBean objectFromData(String str) {

                return new Gson().fromJson(str, WEEKBean.class);
            }

            public static List<WEEKBean> arrayWEEKBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<WEEKBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }


            public String isCollection() {
                return isCollection;
            }

            public void setCollection(String collection) {
                isCollection = collection;
            }

            public String getBasePath() {
                return basePath;
            }

            public void setBasePath(String basePath) {
                this.basePath = basePath;
            }

            public String getChapter() {
                return chapter;
            }

            public void setChapter(String chapter) {
                this.chapter = chapter;
            }

            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterIsFree() {
                return chapterIsFree;
            }

            public void setChapterIsFree(String chapterIsFree) {
                this.chapterIsFree = chapterIsFree;
            }

            public String getChapterMoney() {
                return chapterMoney;
            }

            public void setChapterMoney(String chapterMoney) {
                this.chapterMoney = chapterMoney;
            }

            public String getChapterSubTitle() {
                return chapterSubTitle;
            }

            public void setChapterSubTitle(String chapterSubTitle) {
                this.chapterSubTitle = chapterSubTitle;
            }

            public String getChapterVolume() {
                return chapterVolume;
            }

            public void setChapterVolume(String chapterVolume) {
                this.chapterVolume = chapterVolume;
            }

            public String getChapterVolumeId() {
                return chapterVolumeId;
            }

            public void setChapterVolumeId(String chapterVolumeId) {
                this.chapterVolumeId = chapterVolumeId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getHaveVolumes() {
                return haveVolumes;
            }

            public void setHaveVolumes(int haveVolumes) {
                this.haveVolumes = haveVolumes;
            }

            public String getIsFree() {
                return isFree;
            }

            public void setIsFree(String isFree) {
                this.isFree = isFree;
            }

            public String getIsFreeLimit() {
                return isFreeLimit;
            }

            public void setIsFreeLimit(String isFreeLimit) {
                this.isFreeLimit = isFreeLimit;
            }

            public String getIsOver() {
                return isOver;
            }

            public void setIsOver(String isOver) {
                this.isOver = isOver;
            }

            public String getNovelDescribe() {
                return novelDescribe;
            }

            public void setNovelDescribe(String novelDescribe) {
                this.novelDescribe = novelDescribe;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelParentType() {
                return novelParentType;
            }

            public void setNovelParentType(String novelParentType) {
                this.novelParentType = novelParentType;
            }

            public String getNovelPhoto() {
                return novelPhoto;
            }

            public void setNovelPhoto(String novelPhoto) {
                this.novelPhoto = novelPhoto;
            }

            public String getNovelTitle() {
                return novelTitle;
            }

            public void setNovelTitle(String novelTitle) {
                this.novelTitle = novelTitle;
            }

            public String getNovelType() {
                return novelType;
            }

            public void setNovelType(String novelType) {
                this.novelType = novelType;
            }

            public Object getRankWay() {
                return rankWay;
            }

            public void setRankWay(Object rankWay) {
                this.rankWay = rankWay;
            }

            public Object getScore() {
                return score;
            }

            public void setScore(Object score) {
                this.score = score;
            }

            public Object getScoreNum() {
                return scoreNum;
            }

            public void setScoreNum(Object scoreNum) {
                this.scoreNum = scoreNum;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getWriter() {
                return writer;
            }

            public void setWriter(String writer) {
                this.writer = writer;
            }
        }
    }
}
