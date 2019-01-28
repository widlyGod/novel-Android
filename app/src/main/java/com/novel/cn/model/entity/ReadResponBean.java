package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/25.
 */

public class ReadResponBean {


    /**
     * basePage : null
     * code : 1
     * data : {"chapterInfo":{"baseUrl":"/novel_a/novel","chapter":2,"chapterLevel":"","content":"\t\t\t\t\t\t\t\t\t第一章\t李乐传\n\n\t《斗破他的脑袋，忽然笑道：\u201c不早了，回去休息吧，明天，家族中有贵客，你可别失了礼。\u201d","\u201c贵客？谁啊？\u201d萧炎好奇的问道。"],"publishTime":"2018-12-18 15:20:03","status":1,"title":"李乐传","updateNum":3,"updateStatus":"","updateTime":"2018-12-18 15:16:56","uploadType":"commonFileService","url":"/784ea9dcd71148dd9e177715a0eac565/f26fd328649a4ece8969a0790ad47ba3.txt","volumeId":"882570f2901c4daf8d1b575ae74395e3","words":6286},"chapterInfos":[],"flag":true,"novelInfo":{"acrossPhoto":"","authType":0,"authorInfo":{"address":"阿朵莉切围殴","allWords":"","applyTime":"2018-11-08 11:13:58","approvalTime":"2018-11-08 11:13:58","area":"青龙满族自治县","basePath":"","city":"秦皇岛市","days":"","email":"123456@qq.com","id":10000003,"idCard":"430703199901234567","isSigned":0,"name":"神笔马良","novelNum":"","password":"4a6629303c679cfa6a5a81433743e52c","penName":"我是大作家","phoneNum":"13168686868","province":"河北省","qq":"123456","sex":"0","status":1,"user":null,"userId":234,"userIntroduction":"","userPhoto":""},"authorPenName":"","chapterInfoList":[],"chapterNum":null,"chapterUpdate":null,"chapterUpdateStatus":null,"clickNum":"","crazyTime":"","createTime":"2018-11-12 17:13:16","describe":"555555","haveVolumes":1,"id":"784ea9dcd71148dd9e177715a0eac565","imageList":[],"isChecked":null,"isCollection":"false","isFree":0,"isFreeLimit":"notLimit","isOver":0,"isRead":null,"isRecommend":null,"isSequence":1,"isSigned":0,"isSubscibe":"","newChapterInfo":null,"newVolumeInfo":null,"nextUpdateTime":"","novelSignInfo":null,"novelTypeName":"","oldIsOver":null,"oldPublishStatus":null,"pTypeId":"1","pTypeName":"","photoContent":"","publishStatus":1,"publishTime":"2018-11-12 17:13:16.0","readingChapterInfo":null,"readingVolumeInfo":null,"score":null,"scoreNum":null,"status":1,"title":"有卷连号","typeId":5,"updateNum":1,"updateTime":"2019-01-23 00:00:00","updateType":1,"uploadRole":null,"userId":"234","userScore":"","volumeInfos":[],"words":12572},"novelInfos":[],"novelPageInfo":null,"timingPublishInfo":null,"userSet":{"fontSize":"","fontStyle":"0","skinColor":"0"},"volumeInfo":{"chapterInfos":[],"chapterNum":null,"createTime":"2018-11-12 17:16:53","id":"882570f2901c4daf8d1b575ae74395e3","novelId":"784ea9dcd71148dd9e177715a0eac565","status":1,"title":"我是第一卷","updateTime":"2018-12-18 15:16:56","volume":1},"volumeInfos":[]}
     * message : 操作成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static ReadResponBean objectFromData(String str) {

        return new Gson().fromJson(str, ReadResponBean.class);
    }

    public static List<ReadResponBean> arrayReadResponBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ReadResponBean>>() {
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
         * chapterInfo : {"baseUrl":"/novel_a/novel","chapter":2,"chapterLevel":"","content":"\t\t\t\t\t\t\t\t\t第一章\t李乐传\n\n\t《斗破苍穹》前，或许还有可能吧，不过现在\u2026\u2026基本没半点机会\u2026\u2026\u201d虽然口中在安慰着父亲，不过萧炎心中却是自嘲的苦笑了起来。","同样非常清楚萧炎底细的萧战，也只得叹息着应了一声，他知道一年修炼四段斗之气有多困难，轻拍了拍他的脑袋，忽然笑道：\u201c不早了，回去休息吧，明天，家族中有贵客，你可别失了礼。\u201d","\u201c贵客？谁啊？\u201d萧炎好奇的问道。"],"publishTime":"2018-12-18 15:20:03","status":1,"title":"李乐传","updateNum":3,"updateStatus":"","updateTime":"2018-12-18 15:16:56","uploadType":"commonFileService","url":"/784ea9dcd71148dd9e177715a0eac565/f26fd328649a4ece8969a0790ad47ba3.txt","volumeId":"882570f2901c4daf8d1b575ae74395e3","words":6286}
         * chapterInfos : []
         * flag : true
         * novelInfo : {"acrossPhoto":"","authType":0,"authorInfo":{"address":"阿朵莉切围殴","allWords":"","applyTime":"2018-11-08 11:13:58","approvalTime":"2018-11-08 11:13:58","area":"青龙满族自治县","basePath":"","city":"秦皇岛市","days":"","email":"123456@qq.com","id":10000003,"idCard":"430703199901234567","isSigned":0,"name":"神笔马良","novelNum":"","password":"4a6629303c679cfa6a5a81433743e52c","penName":"我是大作家","phoneNum":"13168686868","province":"河北省","qq":"123456","sex":"0","status":1,"user":null,"userId":234,"userIntroduction":"","userPhoto":""},"authorPenName":"","chapterInfoList":[],"chapterNum":null,"chapterUpdate":null,"chapterUpdateStatus":null,"clickNum":"","crazyTime":"","createTime":"2018-11-12 17:13:16","describe":"555555","haveVolumes":1,"id":"784ea9dcd71148dd9e177715a0eac565","imageList":[],"isChecked":null,"isCollection":"false","isFree":0,"isFreeLimit":"notLimit","isOver":0,"isRead":null,"isRecommend":null,"isSequence":1,"isSigned":0,"isSubscibe":"","newChapterInfo":null,"newVolumeInfo":null,"nextUpdateTime":"","novelSignInfo":null,"novelTypeName":"","oldIsOver":null,"oldPublishStatus":null,"pTypeId":"1","pTypeName":"","photoContent":"","publishStatus":1,"publishTime":"2018-11-12 17:13:16.0","readingChapterInfo":null,"readingVolumeInfo":null,"score":null,"scoreNum":null,"status":1,"title":"有卷连号","typeId":5,"updateNum":1,"updateTime":"2019-01-23 00:00:00","updateType":1,"uploadRole":null,"userId":"234","userScore":"","volumeInfos":[],"words":12572}
         * novelInfos : []
         * novelPageInfo : null
         * timingPublishInfo : null
         * userSet : {"fontSize":"","fontStyle":"0","skinColor":"0"}
         * volumeInfo : {"chapterInfos":[],"chapterNum":null,"createTime":"2018-11-12 17:16:53","id":"882570f2901c4daf8d1b575ae74395e3","novelId":"784ea9dcd71148dd9e177715a0eac565","status":1,"title":"我是第一卷","updateTime":"2018-12-18 15:16:56","volume":1}
         * volumeInfos : []
         */

        private ChapterInfoBean chapterInfo;
        private boolean flag;
        private NovelInfoBean novelInfo;
        private Object novelPageInfo;
        private Object timingPublishInfo;
        private UserSetBean userSet;
        private VolumeInfoBean volumeInfo;
        private List<?> chapterInfos;
        private List<?> novelInfos;
        private List<?> volumeInfos;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public ChapterInfoBean getChapterInfo() {
            return chapterInfo;
        }

        public void setChapterInfo(ChapterInfoBean chapterInfo) {
            this.chapterInfo = chapterInfo;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public NovelInfoBean getNovelInfo() {
            return novelInfo;
        }

        public void setNovelInfo(NovelInfoBean novelInfo) {
            this.novelInfo = novelInfo;
        }

        public Object getNovelPageInfo() {
            return novelPageInfo;
        }

        public void setNovelPageInfo(Object novelPageInfo) {
            this.novelPageInfo = novelPageInfo;
        }

        public Object getTimingPublishInfo() {
            return timingPublishInfo;
        }

        public void setTimingPublishInfo(Object timingPublishInfo) {
            this.timingPublishInfo = timingPublishInfo;
        }

        public UserSetBean getUserSet() {
            return userSet;
        }

        public void setUserSet(UserSetBean userSet) {
            this.userSet = userSet;
        }

        public VolumeInfoBean getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfoBean volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public List<?> getChapterInfos() {
            return chapterInfos;
        }

        public void setChapterInfos(List<?> chapterInfos) {
            this.chapterInfos = chapterInfos;
        }

        public List<?> getNovelInfos() {
            return novelInfos;
        }

        public void setNovelInfos(List<?> novelInfos) {
            this.novelInfos = novelInfos;
        }

        public List<?> getVolumeInfos() {
            return volumeInfos;
        }

        public void setVolumeInfos(List<?> volumeInfos) {
            this.volumeInfos = volumeInfos;
        }

        public static class ChapterInfoBean {
            /**
             * baseUrl : /novel_a/novel
             * chapter : 2
             * chapterLevel :
             * content : 									第一章	李乐传
             * createTime : 2018-12-18 15:16:55
             * describe :
             * extChapterId : null
             * id : f26fd328649a4ece8969a0790ad47ba3
             * isFree : 0
             * isLocked : null
             * isSubscribed :
             * money : 36
             * novelId : 784ea9dcd71148dd9e177715a0eac565
             * paragraphs : ["第一章李乐传","《斗破苍穹》全集","作者：天蚕土豆","申明:本书由[大帝电子书][www.55660.com]自网络收集整理制作,仅供预览
             * publishTime : 2018-12-18 15:20:03
             * status : 1
             * title : 李乐传
             * updateNum : 3
             * updateStatus :
             * updateTime : 2018-12-18 15:16:56
             * uploadType : commonFileService
             * url : /784ea9dcd71148dd9e177715a0eac565/f26fd328649a4ece8969a0790ad47ba3.txt
             * volumeId : 882570f2901c4daf8d1b575ae74395e3
             * words : 6286
             */

            private String baseUrl;
            private int chapter;
            private String chapterLevel;
            private String content;
            private String createTime;
            private String describe;
            private Object extChapterId;
            private String id;
            private int isFree;
            private Object isLocked;
            private String isSubscribed;
            private int money;
            private String novelId;
            private String publishTime;
            private int status;
            private String title;
            private int updateNum;
            private String updateStatus;
            private String updateTime;
            private String uploadType;
            private String url;
            private String volumeId;
            private int words;
            private List<String> paragraphs;

            public static ChapterInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, ChapterInfoBean.class);
            }

            public static List<ChapterInfoBean> arrayChapterInfoBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ChapterInfoBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getBaseUrl() {
                return baseUrl;
            }

            public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
            }

            public int getChapter() {
                return chapter;
            }

            public void setChapter(int chapter) {
                this.chapter = chapter;
            }

            public String getChapterLevel() {
                return chapterLevel;
            }

            public void setChapterLevel(String chapterLevel) {
                this.chapterLevel = chapterLevel;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public Object getExtChapterId() {
                return extChapterId;
            }

            public void setExtChapterId(Object extChapterId) {
                this.extChapterId = extChapterId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIsFree() {
                return isFree;
            }

            public void setIsFree(int isFree) {
                this.isFree = isFree;
            }

            public Object getIsLocked() {
                return isLocked;
            }

            public void setIsLocked(Object isLocked) {
                this.isLocked = isLocked;
            }

            public String getIsSubscribed() {
                return isSubscribed;
            }

            public void setIsSubscribed(String isSubscribed) {
                this.isSubscribed = isSubscribed;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUpdateNum() {
                return updateNum;
            }

            public void setUpdateNum(int updateNum) {
                this.updateNum = updateNum;
            }

            public String getUpdateStatus() {
                return updateStatus;
            }

            public void setUpdateStatus(String updateStatus) {
                this.updateStatus = updateStatus;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUploadType() {
                return uploadType;
            }

            public void setUploadType(String uploadType) {
                this.uploadType = uploadType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getVolumeId() {
                return volumeId;
            }

            public void setVolumeId(String volumeId) {
                this.volumeId = volumeId;
            }

            public int getWords() {
                return words;
            }

            public void setWords(int words) {
                this.words = words;
            }

            public List<String> getParagraphs() {
                return paragraphs;
            }

            public void setParagraphs(List<String> paragraphs) {
                this.paragraphs = paragraphs;
            }
        }

        public static class NovelInfoBean {
            /**
             * acrossPhoto :
             * authType : 0
             * authorInfo : {"address":"阿朵莉切围殴","allWords":"","applyTime":"2018-11-08 11:13:58","approvalTime":"2018-11-08 11:13:58","area":"青龙满族自治县","basePath":"","city":"秦皇岛市","days":"","email":"123456@qq.com","id":10000003,"idCard":"430703199901234567","isSigned":0,"name":"神笔马良","novelNum":"","password":"4a6629303c679cfa6a5a81433743e52c","penName":"我是大作家","phoneNum":"13168686868","province":"河北省","qq":"123456","sex":"0","status":1,"user":null,"userId":234,"userIntroduction":"","userPhoto":""}
             * authorPenName :
             * chapterInfoList : []
             * chapterNum : null
             * chapterUpdate : null
             * chapterUpdateStatus : null
             * clickNum :
             * crazyTime :
             * createTime : 2018-11-12 17:13:16
             * describe : 555555
             * haveVolumes : 1
             * id : 784ea9dcd71148dd9e177715a0eac565
             * imageList : []
             * isChecked : null
             * isCollection : false
             * isFree : 0
             * isFreeLimit : notLimit
             * isOver : 0
             * isRead : null
             * isRecommend : null
             * isSequence : 1
             * isSigned : 0
             * isSubscibe :
             * newChapterInfo : null
             * newVolumeInfo : null
             * nextUpdateTime :
             * novelSignInfo : null
             * novelTypeName :
             * oldIsOver : null
             * oldPublishStatus : null
             * pTypeId : 1
             * pTypeName :
             * photoContent :
             * publishStatus : 1
             * publishTime : 2018-11-12 17:13:16.0
             * readingChapterInfo : null
             * readingVolumeInfo : null
             * score : null
             * scoreNum : null
             * status : 1
             * title : 有卷连号
             * typeId : 5
             * updateNum : 1
             * updateTime : 2019-01-23 00:00:00
             * updateType : 1
             * uploadRole : null
             * userId : 234
             * userScore :
             * volumeInfos : []
             * words : 12572
             */

            private String acrossPhoto;
            private int authType;
            private AuthorInfoBean authorInfo;
            private String authorPenName;
            private Object chapterNum;
            private Object chapterUpdate;
            private Object chapterUpdateStatus;
            private String clickNum;
            private String crazyTime;
            private String createTime;
            private String describe;
            private int haveVolumes;
            private String id;
            private Object isChecked;
            private String isCollection;
            private int isFree;
            private String isFreeLimit;
            private int isOver;
            private Object isRead;
            private Object isRecommend;
            private int isSequence;
            private int isSigned;
            private String isSubscibe;
            private Object newChapterInfo;
            private Object newVolumeInfo;
            private String nextUpdateTime;
            private Object novelSignInfo;
            private String novelTypeName;
            private Object oldIsOver;
            private Object oldPublishStatus;
            private String pTypeId;
            private String pTypeName;
            private String photoContent;
            private int publishStatus;
            private String publishTime;
            private Object readingChapterInfo;
            private Object readingVolumeInfo;
            private Object score;
            private Object scoreNum;
            private int status;
            private String title;
            private int typeId;
            private int updateNum;
            private String updateTime;
            private int updateType;
            private Object uploadRole;
            private String userId;
            private String userScore;
            private int words;
            private List<?> chapterInfoList;
            private List<?> imageList;
            private List<?> volumeInfos;

            public static NovelInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, NovelInfoBean.class);
            }

            public static List<NovelInfoBean> arrayNovelInfoBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<NovelInfoBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getAcrossPhoto() {
                return acrossPhoto;
            }

            public void setAcrossPhoto(String acrossPhoto) {
                this.acrossPhoto = acrossPhoto;
            }

            public int getAuthType() {
                return authType;
            }

            public void setAuthType(int authType) {
                this.authType = authType;
            }

            public AuthorInfoBean getAuthorInfo() {
                return authorInfo;
            }

            public void setAuthorInfo(AuthorInfoBean authorInfo) {
                this.authorInfo = authorInfo;
            }

            public String getAuthorPenName() {
                return authorPenName;
            }

            public void setAuthorPenName(String authorPenName) {
                this.authorPenName = authorPenName;
            }

            public Object getChapterNum() {
                return chapterNum;
            }

            public void setChapterNum(Object chapterNum) {
                this.chapterNum = chapterNum;
            }

            public Object getChapterUpdate() {
                return chapterUpdate;
            }

            public void setChapterUpdate(Object chapterUpdate) {
                this.chapterUpdate = chapterUpdate;
            }

            public Object getChapterUpdateStatus() {
                return chapterUpdateStatus;
            }

            public void setChapterUpdateStatus(Object chapterUpdateStatus) {
                this.chapterUpdateStatus = chapterUpdateStatus;
            }

            public String getClickNum() {
                return clickNum;
            }

            public void setClickNum(String clickNum) {
                this.clickNum = clickNum;
            }

            public String getCrazyTime() {
                return crazyTime;
            }

            public void setCrazyTime(String crazyTime) {
                this.crazyTime = crazyTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getHaveVolumes() {
                return haveVolumes;
            }

            public void setHaveVolumes(int haveVolumes) {
                this.haveVolumes = haveVolumes;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getIsChecked() {
                return isChecked;
            }

            public void setIsChecked(Object isChecked) {
                this.isChecked = isChecked;
            }

            public String getIsCollection() {
                return isCollection;
            }

            public void setIsCollection(String isCollection) {
                this.isCollection = isCollection;
            }

            public int getIsFree() {
                return isFree;
            }

            public void setIsFree(int isFree) {
                this.isFree = isFree;
            }

            public String getIsFreeLimit() {
                return isFreeLimit;
            }

            public void setIsFreeLimit(String isFreeLimit) {
                this.isFreeLimit = isFreeLimit;
            }

            public int getIsOver() {
                return isOver;
            }

            public void setIsOver(int isOver) {
                this.isOver = isOver;
            }

            public Object getIsRead() {
                return isRead;
            }

            public void setIsRead(Object isRead) {
                this.isRead = isRead;
            }

            public Object getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(Object isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getIsSequence() {
                return isSequence;
            }

            public void setIsSequence(int isSequence) {
                this.isSequence = isSequence;
            }

            public int getIsSigned() {
                return isSigned;
            }

            public void setIsSigned(int isSigned) {
                this.isSigned = isSigned;
            }

            public String getIsSubscibe() {
                return isSubscibe;
            }

            public void setIsSubscibe(String isSubscibe) {
                this.isSubscibe = isSubscibe;
            }

            public Object getNewChapterInfo() {
                return newChapterInfo;
            }

            public void setNewChapterInfo(Object newChapterInfo) {
                this.newChapterInfo = newChapterInfo;
            }

            public Object getNewVolumeInfo() {
                return newVolumeInfo;
            }

            public void setNewVolumeInfo(Object newVolumeInfo) {
                this.newVolumeInfo = newVolumeInfo;
            }

            public String getNextUpdateTime() {
                return nextUpdateTime;
            }

            public void setNextUpdateTime(String nextUpdateTime) {
                this.nextUpdateTime = nextUpdateTime;
            }

            public Object getNovelSignInfo() {
                return novelSignInfo;
            }

            public void setNovelSignInfo(Object novelSignInfo) {
                this.novelSignInfo = novelSignInfo;
            }

            public String getNovelTypeName() {
                return novelTypeName;
            }

            public void setNovelTypeName(String novelTypeName) {
                this.novelTypeName = novelTypeName;
            }

            public Object getOldIsOver() {
                return oldIsOver;
            }

            public void setOldIsOver(Object oldIsOver) {
                this.oldIsOver = oldIsOver;
            }

            public Object getOldPublishStatus() {
                return oldPublishStatus;
            }

            public void setOldPublishStatus(Object oldPublishStatus) {
                this.oldPublishStatus = oldPublishStatus;
            }

            public String getPTypeId() {
                return pTypeId;
            }

            public void setPTypeId(String pTypeId) {
                this.pTypeId = pTypeId;
            }

            public String getPTypeName() {
                return pTypeName;
            }

            public void setPTypeName(String pTypeName) {
                this.pTypeName = pTypeName;
            }

            public String getPhotoContent() {
                return photoContent;
            }

            public void setPhotoContent(String photoContent) {
                this.photoContent = photoContent;
            }

            public int getPublishStatus() {
                return publishStatus;
            }

            public void setPublishStatus(int publishStatus) {
                this.publishStatus = publishStatus;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public Object getReadingChapterInfo() {
                return readingChapterInfo;
            }

            public void setReadingChapterInfo(Object readingChapterInfo) {
                this.readingChapterInfo = readingChapterInfo;
            }

            public Object getReadingVolumeInfo() {
                return readingVolumeInfo;
            }

            public void setReadingVolumeInfo(Object readingVolumeInfo) {
                this.readingVolumeInfo = readingVolumeInfo;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getUpdateNum() {
                return updateNum;
            }

            public void setUpdateNum(int updateNum) {
                this.updateNum = updateNum;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getUpdateType() {
                return updateType;
            }

            public void setUpdateType(int updateType) {
                this.updateType = updateType;
            }

            public Object getUploadRole() {
                return uploadRole;
            }

            public void setUploadRole(Object uploadRole) {
                this.uploadRole = uploadRole;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserScore() {
                return userScore;
            }

            public void setUserScore(String userScore) {
                this.userScore = userScore;
            }

            public int getWords() {
                return words;
            }

            public void setWords(int words) {
                this.words = words;
            }

            public List<?> getChapterInfoList() {
                return chapterInfoList;
            }

            public void setChapterInfoList(List<?> chapterInfoList) {
                this.chapterInfoList = chapterInfoList;
            }

            public List<?> getImageList() {
                return imageList;
            }

            public void setImageList(List<?> imageList) {
                this.imageList = imageList;
            }

            public List<?> getVolumeInfos() {
                return volumeInfos;
            }

            public void setVolumeInfos(List<?> volumeInfos) {
                this.volumeInfos = volumeInfos;
            }

            public static class AuthorInfoBean {
                /**
                 * address : 阿朵莉切围殴
                 * allWords :
                 * applyTime : 2018-11-08 11:13:58
                 * approvalTime : 2018-11-08 11:13:58
                 * area : 青龙满族自治县
                 * basePath :
                 * city : 秦皇岛市
                 * days :
                 * email : 123456@qq.com
                 * id : 10000003
                 * idCard : 430703199901234567
                 * isSigned : 0
                 * name : 神笔马良
                 * novelNum :
                 * password : 4a6629303c679cfa6a5a81433743e52c
                 * penName : 我是大作家
                 * phoneNum : 13168686868
                 * province : 河北省
                 * qq : 123456
                 * sex : 0
                 * status : 1
                 * user : null
                 * userId : 234
                 * userIntroduction :
                 * userPhoto :
                 */

                private String address;
                private String allWords;
                private String applyTime;
                private String approvalTime;
                private String area;
                private String basePath;
                private String city;
                private String days;
                private String email;
                private int id;
                private String idCard;
                private int isSigned;
                private String name;
                private String novelNum;
                private String password;
                private String penName;
                private String phoneNum;
                private String province;
                private String qq;
                private String sex;
                private int status;
                private Object user;
                private int userId;
                private String userIntroduction;
                private String userPhoto;

                public static AuthorInfoBean objectFromData(String str) {

                    return new Gson().fromJson(str, AuthorInfoBean.class);
                }

                public static List<AuthorInfoBean> arrayAuthorInfoBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<AuthorInfoBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAllWords() {
                    return allWords;
                }

                public void setAllWords(String allWords) {
                    this.allWords = allWords;
                }

                public String getApplyTime() {
                    return applyTime;
                }

                public void setApplyTime(String applyTime) {
                    this.applyTime = applyTime;
                }

                public String getApprovalTime() {
                    return approvalTime;
                }

                public void setApprovalTime(String approvalTime) {
                    this.approvalTime = approvalTime;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getBasePath() {
                    return basePath;
                }

                public void setBasePath(String basePath) {
                    this.basePath = basePath;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getDays() {
                    return days;
                }

                public void setDays(String days) {
                    this.days = days;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIdCard() {
                    return idCard;
                }

                public void setIdCard(String idCard) {
                    this.idCard = idCard;
                }

                public int getIsSigned() {
                    return isSigned;
                }

                public void setIsSigned(int isSigned) {
                    this.isSigned = isSigned;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNovelNum() {
                    return novelNum;
                }

                public void setNovelNum(String novelNum) {
                    this.novelNum = novelNum;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getPenName() {
                    return penName;
                }

                public void setPenName(String penName) {
                    this.penName = penName;
                }

                public String getPhoneNum() {
                    return phoneNum;
                }

                public void setPhoneNum(String phoneNum) {
                    this.phoneNum = phoneNum;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getQq() {
                    return qq;
                }

                public void setQq(String qq) {
                    this.qq = qq;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
                    this.user = user;
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

                public String getUserPhoto() {
                    return userPhoto;
                }

                public void setUserPhoto(String userPhoto) {
                    this.userPhoto = userPhoto;
                }
            }
        }

        public static class UserSetBean {
            /**
             * fontSize :
             * fontStyle : 0
             * skinColor : 0
             */

            private String fontSize;
            private String fontStyle;
            private String skinColor;

            public static UserSetBean objectFromData(String str) {

                return new Gson().fromJson(str, UserSetBean.class);
            }

            public static List<UserSetBean> arrayUserSetBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<UserSetBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getFontSize() {
                return fontSize;
            }

            public void setFontSize(String fontSize) {
                this.fontSize = fontSize;
            }

            public String getFontStyle() {
                return fontStyle;
            }

            public void setFontStyle(String fontStyle) {
                this.fontStyle = fontStyle;
            }

            public String getSkinColor() {
                return skinColor;
            }

            public void setSkinColor(String skinColor) {
                this.skinColor = skinColor;
            }
        }

        public static class VolumeInfoBean {
            /**
             * chapterInfos : []
             * chapterNum : null
             * createTime : 2018-11-12 17:16:53
             * id : 882570f2901c4daf8d1b575ae74395e3
             * novelId : 784ea9dcd71148dd9e177715a0eac565
             * status : 1
             * title : 我是第一卷
             * updateTime : 2018-12-18 15:16:56
             * volume : 1
             */

            private Object chapterNum;
            private String createTime;
            private String id;
            private String novelId;
            private int status;
            private String title;
            private String updateTime;
            private int volume;
            private List<?> chapterInfos;

            public static VolumeInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, VolumeInfoBean.class);
            }

            public static List<VolumeInfoBean> arrayVolumeInfoBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<VolumeInfoBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public Object getChapterNum() {
                return chapterNum;
            }

            public void setChapterNum(Object chapterNum) {
                this.chapterNum = chapterNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
            }

            public List<?> getChapterInfos() {
                return chapterInfos;
            }

            public void setChapterInfos(List<?> chapterInfos) {
                this.chapterInfos = chapterInfos;
            }
        }
    }
}
