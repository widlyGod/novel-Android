package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/29.
 */

public class ReadChapterBean {


    /**
     * basePage : null
     * code : 1
     * data : {"novelInfo":{"acrossPhoto":"","authType":null,"authorInfo":null,"authorPenName":"","chapterInfoList":[],"chapterNum":null,"chapterUpdate":null,"chapterUpdateStatus":null,"clickNum":"","crazyTime":"","createTime":"","describe":"","haveVolumes":0,"id":"80bd05450ceb4233b90450fb351a8e7e","imageList":[],"isChecked":null,"isCollection":"","isFree":null,"isFreeLimit":"","isOver":null,"isRead":null,"isRecommend":null,"isSequence":null,"isSigned":null,"isSubscibe":"false","newChapterInfo":null,"newVolumeInfo":null,"nextUpdateTime":"","novelSignInfo":null,"novelTypeName":"","oldIsOver":null,"oldPublishStatus":null,"pTypeId":"","pTypeName":"","photoContent":"","publishStatus":null,"publishTime":"","readingChapterInfo":null,"readingVolumeInfo":null,"score":null,"scoreNum":null,"status":null,"title":"","typeId":null,"updateNum":null,"updateTime":"","updateType":null,"uploadRole":null,"userId":"","userScore":"","volumeInfos":[{"chapterInfos":[{"baseUrl":"","chapter":1,"chapterLevel":"","content":"","createTime":"","describe":"","extChapterId":null,"id":"588b349316c8474f9eba129b3470d050","isFree":0,"isLocked":false,"isSubscribed":"false","money":30,"novelId":"80bd05450ceb4233b90450fb351a8e7e","paragraphs":[],"publishTime":"","status":1,"title":"5k字符","updateNum":3,"updateStatus":"","updateTime":"","uploadType":"","url":"","volumeId":"d8be452df9df47c5b0813090908d48ac","words":5000}],"chapterNum":null,"createTime":"","id":"d8be452df9df47c5b0813090908d48ac","novelId":"80bd05450ceb4233b90450fb351a8e7e","status":1,"title":"无卷","updateTime":"","volume":0}],"words":null}}
     * message : 操作成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static ReadChapterBean objectFromData(String str) {

        return new Gson().fromJson(str, ReadChapterBean.class);
    }

    public static List<ReadChapterBean> arrayReadChapterBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ReadChapterBean>>() {
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
         * novelInfo : {"acrossPhoto":"","authType":null,"authorInfo":null,"authorPenName":"","chapterInfoList":[],"chapterNum":null,"chapterUpdate":null,"chapterUpdateStatus":null,"clickNum":"","crazyTime":"","createTime":"","describe":"","haveVolumes":0,"id":"80bd05450ceb4233b90450fb351a8e7e","imageList":[],"isChecked":null,"isCollection":"","isFree":null,"isFreeLimit":"","isOver":null,"isRead":null,"isRecommend":null,"isSequence":null,"isSigned":null,"isSubscibe":"false","newChapterInfo":null,"newVolumeInfo":null,"nextUpdateTime":"","novelSignInfo":null,"novelTypeName":"","oldIsOver":null,"oldPublishStatus":null,"pTypeId":"","pTypeName":"","photoContent":"","publishStatus":null,"publishTime":"","readingChapterInfo":null,"readingVolumeInfo":null,"score":null,"scoreNum":null,"status":null,"title":"","typeId":null,"updateNum":null,"updateTime":"","updateType":null,"uploadRole":null,"userId":"","userScore":"","volumeInfos":[{"chapterInfos":[{"baseUrl":"","chapter":1,"chapterLevel":"","content":"","createTime":"","describe":"","extChapterId":null,"id":"588b349316c8474f9eba129b3470d050","isFree":0,"isLocked":false,"isSubscribed":"false","money":30,"novelId":"80bd05450ceb4233b90450fb351a8e7e","paragraphs":[],"publishTime":"","status":1,"title":"5k字符","updateNum":3,"updateStatus":"","updateTime":"","uploadType":"","url":"","volumeId":"d8be452df9df47c5b0813090908d48ac","words":5000}],"chapterNum":null,"createTime":"","id":"d8be452df9df47c5b0813090908d48ac","novelId":"80bd05450ceb4233b90450fb351a8e7e","status":1,"title":"无卷","updateTime":"","volume":0}],"words":null}
         */

        private NovelInfoBean novelInfo;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public NovelInfoBean getNovelInfo() {
            return novelInfo;
        }

        public void setNovelInfo(NovelInfoBean novelInfo) {
            this.novelInfo = novelInfo;
        }

        public static class NovelInfoBean {
            /**
             * acrossPhoto :
             * authType : null
             * authorInfo : null
             * authorPenName :
             * chapterInfoList : []
             * chapterNum : null
             * chapterUpdate : null
             * chapterUpdateStatus : null
             * clickNum :
             * crazyTime :
             * createTime :
             * describe :
             * haveVolumes : 0
             * id : 80bd05450ceb4233b90450fb351a8e7e
             * imageList : []
             * isChecked : null
             * isCollection :
             * isFree : null
             * isFreeLimit :
             * isOver : null
             * isRead : null
             * isRecommend : null
             * isSequence : null
             * isSigned : null
             * isSubscibe : false
             * newChapterInfo : null
             * newVolumeInfo : null
             * nextUpdateTime :
             * novelSignInfo : null
             * novelTypeName :
             * oldIsOver : null
             * oldPublishStatus : null
             * pTypeId :
             * pTypeName :
             * photoContent :
             * publishStatus : null
             * publishTime :
             * readingChapterInfo : null
             * readingVolumeInfo : null
             * score : null
             * scoreNum : null
             * status : null
             * title :
             * typeId : null
             * updateNum : null
             * updateTime :
             * updateType : null
             * uploadRole : null
             * userId :
             * userScore :
             * volumeInfos : [{"chapterInfos":[{"baseUrl":"","chapter":1,"chapterLevel":"","content":"","createTime":"","describe":"","extChapterId":null,"id":"588b349316c8474f9eba129b3470d050","isFree":0,"isLocked":false,"isSubscribed":"false","money":30,"novelId":"80bd05450ceb4233b90450fb351a8e7e","paragraphs":[],"publishTime":"","status":1,"title":"5k字符","updateNum":3,"updateStatus":"","updateTime":"","uploadType":"","url":"","volumeId":"d8be452df9df47c5b0813090908d48ac","words":5000}],"chapterNum":null,"createTime":"","id":"d8be452df9df47c5b0813090908d48ac","novelId":"80bd05450ceb4233b90450fb351a8e7e","status":1,"title":"无卷","updateTime":"","volume":0}]
             * words : null
             */

            private String acrossPhoto;
            private Object authType;
            private Object authorInfo;
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
            private Object isFree;
            private String isFreeLimit;
            private Object isOver;
            private Object isRead;
            private Object isRecommend;
            private Object isSequence;
            private Object isSigned;
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
            private Object publishStatus;
            private String publishTime;
            private Object readingChapterInfo;
            private Object readingVolumeInfo;
            private Object score;
            private Object scoreNum;
            private Object status;
            private String title;
            private Object typeId;
            private Object updateNum;
            private String updateTime;
            private Object updateType;
            private Object uploadRole;
            private String userId;
            private String userScore;
            private Object words;
            private List<?> chapterInfoList;
            private List<?> imageList;
            private List<VolumeInfosBean> volumeInfos;

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

            public Object getAuthType() {
                return authType;
            }

            public void setAuthType(Object authType) {
                this.authType = authType;
            }

            public Object getAuthorInfo() {
                return authorInfo;
            }

            public void setAuthorInfo(Object authorInfo) {
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

            public Object getIsFree() {
                return isFree;
            }

            public void setIsFree(Object isFree) {
                this.isFree = isFree;
            }

            public String getIsFreeLimit() {
                return isFreeLimit;
            }

            public void setIsFreeLimit(String isFreeLimit) {
                this.isFreeLimit = isFreeLimit;
            }

            public Object getIsOver() {
                return isOver;
            }

            public void setIsOver(Object isOver) {
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

            public Object getIsSequence() {
                return isSequence;
            }

            public void setIsSequence(Object isSequence) {
                this.isSequence = isSequence;
            }

            public Object getIsSigned() {
                return isSigned;
            }

            public void setIsSigned(Object isSigned) {
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

            public Object getPublishStatus() {
                return publishStatus;
            }

            public void setPublishStatus(Object publishStatus) {
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

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getTypeId() {
                return typeId;
            }

            public void setTypeId(Object typeId) {
                this.typeId = typeId;
            }

            public Object getUpdateNum() {
                return updateNum;
            }

            public void setUpdateNum(Object updateNum) {
                this.updateNum = updateNum;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getUpdateType() {
                return updateType;
            }

            public void setUpdateType(Object updateType) {
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

            public Object getWords() {
                return words;
            }

            public void setWords(Object words) {
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

            public List<VolumeInfosBean> getVolumeInfos() {
                return volumeInfos;
            }

            public void setVolumeInfos(List<VolumeInfosBean> volumeInfos) {
                this.volumeInfos = volumeInfos;
            }

            public static class VolumeInfosBean {
                /**
                 * chapterInfos : [{"baseUrl":"","chapter":1,"chapterLevel":"","content":"","createTime":"","describe":"","extChapterId":null,"id":"588b349316c8474f9eba129b3470d050","isFree":0,"isLocked":false,"isSubscribed":"false","money":30,"novelId":"80bd05450ceb4233b90450fb351a8e7e","paragraphs":[],"publishTime":"","status":1,"title":"5k字符","updateNum":3,"updateStatus":"","updateTime":"","uploadType":"","url":"","volumeId":"d8be452df9df47c5b0813090908d48ac","words":5000}]
                 * chapterNum : null
                 * createTime :
                 * id : d8be452df9df47c5b0813090908d48ac
                 * novelId : 80bd05450ceb4233b90450fb351a8e7e
                 * status : 1
                 * title : 无卷
                 * updateTime :
                 * volume : 0
                 */

                private Object chapterNum;
                private String createTime;
                private String id;
                private String novelId;
                private int status;
                private String title;
                private String updateTime;
                private int volume;
                private List<ChapterInfosBean> chapterInfos;

                public static VolumeInfosBean objectFromData(String str) {
                    return new Gson().fromJson(str, VolumeInfosBean.class);
                }

                public static List<VolumeInfosBean> arrayVolumeInfosBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<VolumeInfosBean>>() {
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

                public List<ChapterInfosBean> getChapterInfos() {
                    return chapterInfos;
                }

                public void setChapterInfos(List<ChapterInfosBean> chapterInfos) {
                    this.chapterInfos = chapterInfos;
                }

                public static class ChapterInfosBean {
                    /**
                     * baseUrl :
                     * chapter : 1
                     * chapterLevel :
                     * content :
                     * createTime :
                     * describe :
                     * extChapterId : null
                     * id : 588b349316c8474f9eba129b3470d050
                     * isFree : 0
                     * isLocked : false
                     * isSubscribed : false
                     * money : 30
                     * novelId : 80bd05450ceb4233b90450fb351a8e7e
                     * paragraphs : []
                     * publishTime :
                     * status : 1
                     * title : 5k字符
                     * updateNum : 3
                     * updateStatus :
                     * updateTime :
                     * uploadType :
                     * url :
                     * volumeId : d8be452df9df47c5b0813090908d48ac
                     * words : 5000
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
                    private boolean isLocked;
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
                    private List<?> paragraphs;

                    public static ChapterInfosBean objectFromData(String str) {

                        return new Gson().fromJson(str, ChapterInfosBean.class);
                    }

                    public static List<ChapterInfosBean> arrayChapterInfosBeanFromData(String str) {

                        Type listType = new TypeToken<ArrayList<ChapterInfosBean>>() {
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

                    public boolean isIsLocked() {
                        return isLocked;
                    }

                    public void setIsLocked(boolean isLocked) {
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

                    public List<?> getParagraphs() {
                        return paragraphs;
                    }

                    public void setParagraphs(List<?> paragraphs) {
                        this.paragraphs = paragraphs;
                    }
                }
            }
        }
    }
}
