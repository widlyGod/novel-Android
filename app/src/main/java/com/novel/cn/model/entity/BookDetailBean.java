package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/18.
 */

public class BookDetailBean {


    /**
     * basePage : null
     * code : 1
     * data : {"novelInfo":{"readingVolumeInfo":{"volume":1,"volumeTitle":"我是第一卷","readingChapterInfo":{"chapter":1,"isFree":0,"money":36,"chapterId":"6d3ddaebd959496cb624e37d4bba841b","isLocked":"false","chapterTitle":"第一章"},"volumeId":"882570f2901c4daf8d1b575ae74395e3"},"novelPhoto":"http://59.110.124.41:80/novel_a/novel/784ea9dcd71148dd9e177715a0eac565/novel_img_1542013957535_0.jpg","isCollection":"false","isRead":false,"novelWords":"12572","novelType":"武侠","novelTitle":"有卷连号","authorId":"234","novelIntro":"555555","novelId":"784ea9dcd71148dd9e177715a0eac565","isSubscibe":"false","novelAuthor":"我是大作家","isOver":"0","haveVolumes":"1","clickNum":"0"},"newChapter":{"chapter":"2","publishTime":"2018-12-18 15:20","isFree":"0","money":"36","id":"f26fd328649a4ece8969a0790ad47ba3","title":"李乐传"},"gifts":[{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"1","rank":"2","difference":"1","type":0},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"0","rank":"65","difference":"0","type":1},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"0","rank":"70","difference":"0","type":2},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"2","difference":"2","rank":null,"type":3}],"fansRewards":[{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"admin"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"500","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"1","rewardType":"推荐票","fansName":"编辑123"}],"comment":{"comments":[],"totalCount":0},"editorRecommend":[{"novelId":"784ea9dcd71148dd9e177715a0eac565","novelPhoto":"http://59.110.124.41:80/novel_a/novel/784ea9dcd71148dd9e177715a0eac565/novel_img_1542013957535_0.jpg","novelTitle":"有卷连号"},{"novelId":"7e01e72401d9443d8416f3bb8020070c","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelTitle":"作者创建独家首发无卷小说001"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelTitle":"管理员创建驻站作品小说001"},{"novelId":"2c62f4d9fca546428daabdd2d954a406","novelPhoto":"http://59.110.124.41:80/novel_a/novel/2c62f4d9fca546428daabdd2d954a406/c8a389bb8f854d088b33007f14703dab.jpg","novelTitle":"有卷驻站"},{"novelId":"4b82610b3aa14b699005f6559a0c8081","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelTitle":"管理员创建独家首发免费小说001"}]}
     * message : 查询成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static BookDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, BookDetailBean.class);
    }

    public static List<BookDetailBean> arrayBookDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BookDetailBean>>() {
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
         * novelInfo : {"readingVolumeInfo":{"volume":1,"volumeTitle":"我是第一卷","readingChapterInfo":{"chapter":1,"isFree":0,"money":36,"chapterId":"6d3ddaebd959496cb624e37d4bba841b","isLocked":"false","chapterTitle":"第一章"},"volumeId":"882570f2901c4daf8d1b575ae74395e3"},"novelPhoto":"http://59.110.124.41:80/novel_a/novel/784ea9dcd71148dd9e177715a0eac565/novel_img_1542013957535_0.jpg","isCollection":"false","isRead":false,"novelWords":"12572","novelType":"武侠","novelTitle":"有卷连号","authorId":"234","novelIntro":"555555","novelId":"784ea9dcd71148dd9e177715a0eac565","isSubscibe":"false","novelAuthor":"我是大作家","isOver":"0","haveVolumes":"1","clickNum":"0"}
         * newChapter : {"chapter":"2","publishTime":"2018-12-18 15:20","isFree":"0","money":"36","id":"f26fd328649a4ece8969a0790ad47ba3","title":"李乐传"}
         * gifts : [{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"1","rank":"2","difference":"1","type":0},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"0","rank":"65","difference":"0","type":1},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"0","rank":"70","difference":"0","type":2},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"2","difference":"2","rank":null,"type":3}]
         * fansRewards : [{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"admin"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"500","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"10","rewardType":"阅读币","fansName":"编辑123"},{"novelId":"784ea9dcd71148dd9e177715a0eac565","rewardCount":"1","rewardType":"推荐票","fansName":"编辑123"}]
         * comment : {"comments":[],"totalCount":0}
         * editorRecommend : [{"novelId":"784ea9dcd71148dd9e177715a0eac565","novelPhoto":"http://59.110.124.41:80/novel_a/novel/784ea9dcd71148dd9e177715a0eac565/novel_img_1542013957535_0.jpg","novelTitle":"有卷连号"},{"novelId":"7e01e72401d9443d8416f3bb8020070c","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelTitle":"作者创建独家首发无卷小说001"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelTitle":"管理员创建驻站作品小说001"},{"novelId":"2c62f4d9fca546428daabdd2d954a406","novelPhoto":"http://59.110.124.41:80/novel_a/novel/2c62f4d9fca546428daabdd2d954a406/c8a389bb8f854d088b33007f14703dab.jpg","novelTitle":"有卷驻站"},{"novelId":"4b82610b3aa14b699005f6559a0c8081","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelTitle":"管理员创建独家首发免费小说001"}]
         */

        private NovelInfoBean novelInfo;
        private NewChapterBean newChapter;
        private CommentBean comment;
        private List<GiftsBean> gifts;
        private List<FansRewardsBean> fansRewards;
        private List<HomeReturnBean.DataBean.EditorRecommendBean> editorRecommend;

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

        public NewChapterBean getNewChapter() {
            return newChapter;
        }

        public void setNewChapter(NewChapterBean newChapter) {
            this.newChapter = newChapter;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public List<GiftsBean> getGifts() {
            return gifts;
        }

        public void setGifts(List<GiftsBean> gifts) {
            this.gifts = gifts;
        }

        public List<FansRewardsBean> getFansRewards() {
            return fansRewards;
        }

        public void setFansRewards(List<FansRewardsBean> fansRewards) {
            this.fansRewards = fansRewards;
        }

        public List<HomeReturnBean.DataBean.EditorRecommendBean> getEditorRecommend() {
            return editorRecommend;
        }

        public void setEditorRecommend(List<HomeReturnBean.DataBean.EditorRecommendBean> editorRecommend) {
            this.editorRecommend = editorRecommend;
        }

        public static class NovelInfoBean {
            /**
             * readingVolumeInfo : {"volume":1,"volumeTitle":"我是第一卷","readingChapterInfo":{"chapter":1,"isFree":0,"money":36,"chapterId":"6d3ddaebd959496cb624e37d4bba841b","isLocked":"false","chapterTitle":"第一章"},"volumeId":"882570f2901c4daf8d1b575ae74395e3"}
             * novelPhoto : http://59.110.124.41:80/novel_a/novel/784ea9dcd71148dd9e177715a0eac565/novel_img_1542013957535_0.jpg
             * isCollection : false
             * isRead : false
             * novelWords : 12572
             * novelType : 武侠
             * novelTitle : 有卷连号
             * authorId : 234
             * novelIntro : 555555
             * novelId : 784ea9dcd71148dd9e177715a0eac565
             * isSubscibe : false
             * novelAuthor : 我是大作家
             * isOver : 0
             * haveVolumes : 1
             * clickNum : 0
             */

            private ReadingVolumeInfoBean readingVolumeInfo;
            private String novelPhoto;
            private String isCollection;
            private boolean isRead;
            private String novelWords;
            private String novelType;
            private String novelTitle;
            private String authorId;
            private String novelIntro;
            private String novelId;
            private String isSubscibe;
            private String novelAuthor;
            private String isOver;
            private String haveVolumes;
            private String clickNum;

            public static NovelInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, NovelInfoBean.class);
            }

            public static List<NovelInfoBean> arrayNovelInfoBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<NovelInfoBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public ReadingVolumeInfoBean getReadingVolumeInfo() {
                return readingVolumeInfo;
            }

            public void setReadingVolumeInfo(ReadingVolumeInfoBean readingVolumeInfo) {
                this.readingVolumeInfo = readingVolumeInfo;
            }

            public String getNovelPhoto() {
                return novelPhoto;
            }

            public void setNovelPhoto(String novelPhoto) {
                this.novelPhoto = novelPhoto;
            }

            public String getIsCollection() {
                return isCollection;
            }

            public void setIsCollection(String isCollection) {
                this.isCollection = isCollection;
            }

            public boolean isIsRead() {
                return isRead;
            }

            public void setIsRead(boolean isRead) {
                this.isRead = isRead;
            }

            public String getNovelWords() {
                return novelWords;
            }

            public void setNovelWords(String novelWords) {
                this.novelWords = novelWords;
            }

            public String getNovelType() {
                return novelType;
            }

            public void setNovelType(String novelType) {
                this.novelType = novelType;
            }

            public String getNovelTitle() {
                return novelTitle;
            }

            public void setNovelTitle(String novelTitle) {
                this.novelTitle = novelTitle;
            }

            public String getAuthorId() {
                return authorId;
            }

            public void setAuthorId(String authorId) {
                this.authorId = authorId;
            }

            public String getNovelIntro() {
                return novelIntro;
            }

            public void setNovelIntro(String novelIntro) {
                this.novelIntro = novelIntro;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getIsSubscibe() {
                return isSubscibe;
            }

            public void setIsSubscibe(String isSubscibe) {
                this.isSubscibe = isSubscibe;
            }

            public String getNovelAuthor() {
                return novelAuthor;
            }

            public void setNovelAuthor(String novelAuthor) {
                this.novelAuthor = novelAuthor;
            }

            public String getIsOver() {
                return isOver;
            }

            public void setIsOver(String isOver) {
                this.isOver = isOver;
            }

            public String getHaveVolumes() {
                return haveVolumes;
            }

            public void setHaveVolumes(String haveVolumes) {
                this.haveVolumes = haveVolumes;
            }

            public String getClickNum() {
                return clickNum;
            }

            public void setClickNum(String clickNum) {
                this.clickNum = clickNum;
            }

            public static class ReadingVolumeInfoBean {
                /**
                 * volume : 1
                 * volumeTitle : 我是第一卷
                 * readingChapterInfo : {"chapter":1,"isFree":0,"money":36,"chapterId":"6d3ddaebd959496cb624e37d4bba841b","isLocked":"false","chapterTitle":"第一章"}
                 * volumeId : 882570f2901c4daf8d1b575ae74395e3
                 */

                private int volume;
                private String volumeTitle;
                private ReadingChapterInfoBean readingChapterInfo;
                private String volumeId;

                public static ReadingVolumeInfoBean objectFromData(String str) {

                    return new Gson().fromJson(str, ReadingVolumeInfoBean.class);
                }

                public static List<ReadingVolumeInfoBean> arrayReadingVolumeInfoBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<ReadingVolumeInfoBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }

                public String getVolumeTitle() {
                    return volumeTitle;
                }

                public void setVolumeTitle(String volumeTitle) {
                    this.volumeTitle = volumeTitle;
                }

                public ReadingChapterInfoBean getReadingChapterInfo() {
                    return readingChapterInfo;
                }

                public void setReadingChapterInfo(ReadingChapterInfoBean readingChapterInfo) {
                    this.readingChapterInfo = readingChapterInfo;
                }

                public String getVolumeId() {
                    return volumeId;
                }

                public void setVolumeId(String volumeId) {
                    this.volumeId = volumeId;
                }

                public static class ReadingChapterInfoBean {
                    /**
                     * chapter : 1
                     * isFree : 0
                     * money : 36
                     * chapterId : 6d3ddaebd959496cb624e37d4bba841b
                     * isLocked : false
                     * chapterTitle : 第一章
                     */

                    private int chapter;
                    private int isFree;
                    private int money;
                    private String chapterId;
                    private String isLocked;
                    private String chapterTitle;

                    public static ReadingChapterInfoBean objectFromData(String str) {

                        return new Gson().fromJson(str, ReadingChapterInfoBean.class);
                    }

                    public static List<ReadingChapterInfoBean> arrayReadingChapterInfoBeanFromData(String str) {

                        Type listType = new TypeToken<ArrayList<ReadingChapterInfoBean>>() {
                        }.getType();

                        return new Gson().fromJson(str, listType);
                    }

                    public int getChapter() {
                        return chapter;
                    }

                    public void setChapter(int chapter) {
                        this.chapter = chapter;
                    }

                    public int getIsFree() {
                        return isFree;
                    }

                    public void setIsFree(int isFree) {
                        this.isFree = isFree;
                    }

                    public int getMoney() {
                        return money;
                    }

                    public void setMoney(int money) {
                        this.money = money;
                    }

                    public String getChapterId() {
                        return chapterId;
                    }

                    public void setChapterId(String chapterId) {
                        this.chapterId = chapterId;
                    }

                    public String getIsLocked() {
                        return isLocked;
                    }

                    public void setIsLocked(String isLocked) {
                        this.isLocked = isLocked;
                    }

                    public String getChapterTitle() {
                        return chapterTitle;
                    }

                    public void setChapterTitle(String chapterTitle) {
                        this.chapterTitle = chapterTitle;
                    }
                }
            }
        }

        public static class NewChapterBean {
            /**
             * chapter : 2
             * publishTime : 2018-12-18 15:20
             * isFree : 0
             * money : 36
             * id : f26fd328649a4ece8969a0790ad47ba3
             * title : 李乐传
             */

            private String chapter;
            private String publishTime;
            private String isFree;
            private String money;
            private String id;
            private String title;

            public static NewChapterBean objectFromData(String str) {

                return new Gson().fromJson(str, NewChapterBean.class);
            }

            public static List<NewChapterBean> arrayNewChapterBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<NewChapterBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getChapter() {
                return chapter;
            }

            public void setChapter(String chapter) {
                this.chapter = chapter;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public String getIsFree() {
                return isFree;
            }

            public void setIsFree(String isFree) {
                this.isFree = isFree;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class CommentBean {
            /**
             * comments : []
             * totalCount : 0
             */

            private int totalCount;
            private List<Object> comments;

            public static CommentBean objectFromData(String str) {

                return new Gson().fromJson(str, CommentBean.class);
            }

            public static List<CommentBean> arrayCommentBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<CommentBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<Object> getComments() {
                return comments;
            }

            public void setComments(List<Object> comments) {
                this.comments = comments;
            }
        }

        public static class GiftsBean {
            /**
             * novelId : 784ea9dcd71148dd9e177715a0eac565
             * rewardCount : 1
             * rank : 2
             * difference : 1
             * type : 0
             */

            private String novelId;
            private String rewardCount;
            private String rank;
            private String difference;
            private int type;

            public static GiftsBean objectFromData(String str) {

                return new Gson().fromJson(str, GiftsBean.class);
            }

            public static List<GiftsBean> arrayGiftsBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<GiftsBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getRewardCount() {
                return rewardCount;
            }

            public void setRewardCount(String rewardCount) {
                this.rewardCount = rewardCount;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getDifference() {
                return difference;
            }

            public void setDifference(String difference) {
                this.difference = difference;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class FansRewardsBean {
            /**
             * novelId : 784ea9dcd71148dd9e177715a0eac565
             * rewardCount : 10
             * rewardType : 阅读币
             * fansName : admin
             */

            private String novelId;
            private String rewardCount;
            private String rewardType;
            private String fansName;

            public static FansRewardsBean objectFromData(String str) {

                return new Gson().fromJson(str, FansRewardsBean.class);
            }

            public static List<FansRewardsBean> arrayFansRewardsBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<FansRewardsBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getRewardCount() {
                return rewardCount;
            }

            public void setRewardCount(String rewardCount) {
                this.rewardCount = rewardCount;
            }

            public String getRewardType() {
                return rewardType;
            }

            public void setRewardType(String rewardType) {
                this.rewardType = rewardType;
            }

            public String getFansName() {
                return fansName;
            }

            public void setFansName(String fansName) {
                this.fansName = fansName;
            }
        }

    }
}
