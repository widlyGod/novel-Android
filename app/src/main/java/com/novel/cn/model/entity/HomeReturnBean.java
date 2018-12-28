package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2018/12/28.
 */

public class HomeReturnBean {


    /**
     * basePage : null
     * code : 1
     * data : {"headRecommend":[{"novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelPhoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/bef18de92c8c4d7e93a268a72c6e0fcf.png","novelTitle":"test1113"},{"novelId":"2f0b62a919f041bc862a7022f6161be0","novelPhoto":"http://59.110.124.41:80/novel_b/novel/2f0b62a919f041bc862a7022f6161be0/0c1aafbb61e54fc499141a8388e352bd.png","novelTitle":"test00"},{"novelId":"c3e68d9d8d9f467a892e4ea0b4e98ed6","novelPhoto":"http://59.110.124.41:80/novel_a/novel/c3e68d9d8d9f467a892e4ea0b4e98ed6/d532b134d99a43b99bb7ea45328cc273.png","novelTitle":"test11132"},{"novelId":"2982353c29be41e19625ff2faf3a6728","novelPhoto":"http://59.110.124.41:80/novel_a/novel/2982353c29be41e19625ff2faf3a6728/12f3069ca152469e86394b5916b1c5d9.png","novelTitle":"甜心"},{"novelId":"7a553d07dbb1478c864507d349044f31","novelPhoto":"http://59.110.124.41:80/novel_b/novel/7a553d07dbb1478c864507d349044f31/3f416d76834c474c810267aea982526b.png","novelTitle":"wwwuuu"}],"hotRecommend":[{"novelId":"7d2f9ce60b254b22977f1d979abe3c5b","novelTitle":"买断管理员在线发布章节"},{"novelId":"b4caef9f9e4f4181b985684b521422c6","novelTitle":"创建的一本书"},{"novelId":"08690de2591a46a89632b519e7b011c3","novelTitle":"管理员创建的书籍"},{"novelId":"2c62f4d9fca546428daabdd2d954a406","novelTitle":"有卷驻站"},{"novelId":"f083b88051e34c9188d8f35f6a00a789","novelTitle":"超级书籍3"}],"editorRecommend":[{"novelId":"fd1dc45252b6493bb9be18133be42164","novelPhoto":"http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg","novelTitle":"有卷小说002"},{"novelId":"7e01e72401d9443d8416f3bb8020070c","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelTitle":"作者创建独家首发无卷小说001"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelTitle":"管理员创建驻站作品小说001"},{"novelId":"2c62f4d9fca546428daabdd2d954a406","novelPhoto":"http://59.110.124.41:80/novel_a/novel/2c62f4d9fca546428daabdd2d954a406/c8a389bb8f854d088b33007f14703dab.jpg","novelTitle":"有卷驻站"},{"novelId":"4b82610b3aa14b699005f6559a0c8081","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelTitle":"管理员创建独家首发免费小说001"}],"bestAuthorZone":[{"novelId":"d9485330914348038a5f1d347bc84ab3","novelAuthor":"我是大作家","novelPhoto":"http://59.110.124.41:80/novel_a/novel/d9485330914348038a5f1d347bc84ab3/b0d853b5c0964ad7a7a878430f8838f2.jpg","novelType":"武侠","novelTitle":"无卷","novelIntro":"我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介"},{"novelId":"fb1a8d0b20ac4fdfb86c870e0561bc29","novelAuthor":"tianx","novelPhoto":"http://59.110.124.41:80/novel_b/novel/fb1a8d0b20ac4fdfb86c870e0561bc29/517de32d30554fab873232eae2619b4c.png","novelType":"科幻","novelTitle":"作者创建独家首发有卷小说001","novelIntro":"作者创建独家首发有卷小说001"},{"novelId":"17d1cb767f344e5293037b778a0fd268","novelAuthor":"小猫咪","novelPhoto":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelType":"科幻","novelTitle":"作者创建已完结小说001","novelIntro":"作者创建已完结小说001"},{"novelId":"1ac176d5d48a43e5ab30de44c8bf8ca3","novelAuthor":"老铁瓜1","novelPhoto":"http://59.110.124.41:80/novel_b/novel/1ac176d5d48a43e5ab30de44c8bf8ca3/e12d9fabdd094af29727ae25153c3838.jpg","novelType":"社会","novelTitle":"驻站11","novelIntro":"备用驻站纯章"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelType":"武侠","novelTitle":"管理员创建驻站作品小说001","novelIntro":"管理员创建驻站作品小说001"},{"novelId":"44c8592d48e545e591dd9c804c2d6962","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_a/novel/44c8592d48e545e591dd9c804c2d6962/f00034d1b17d4d23bbaa9b920d5b4126.png","novelType":"军事","novelTitle":"管理员创建驻站作品有卷小说001","novelIntro":"管理员创建驻站作品有卷小说001"},{"novelId":"8d1e0a8ca9204494b5770cfca8d6debc","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_b/novel/8d1e0a8ca9204494b5770cfca8d6debc/95a3ed2f26b742a9bdb714abdafe3512.png","novelType":"历史","novelTitle":"管理员创建独家首发无卷小说001","novelIntro":"管理员创建独家首发无卷小说001"},{"novelId":"ae41ae955920463e97c03a1df6318ea9","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_a/novel/ae41ae955920463e97c03a1df6318ea9/b2aa78f4b1774328af835f0b30f4345b.png","novelType":"军事","novelTitle":"管理员创建独家首发有卷小说001","novelIntro":"管理员创建独家首发有卷小说001"},{"novelId":"5b5619e4738e404e8ae3962c8c6fa48b","novelAuthor":"老铁瓜1","novelPhoto":"http://59.110.124.41:80/novel_a/novel/5b5619e4738e404e8ae3962c8c6fa48b/ac012d7f492c4b8c845f716a638dc3cd.jpg","novelType":"古言","novelTitle":"独家1","novelIntro":"独家纯章"},{"novelId":"e148bf8bde994d198d2ae27e627cfd79","novelAuthor":"老铁瓜1","novelPhoto":"http://59.110.124.41:80/novel_b/novel/e148bf8bde994d198d2ae27e627cfd79/b4a5899e24204c93b64163dc87981a4d.jpg","novelType":"现言","novelTitle":"独家2","novelIntro":"独家卷章"},{"novelId":"69591839f3b146fa8af4374a104a0da7","novelAuthor":"我是大作家","novelPhoto":"http://59.110.124.41:80/novel_b/novel/69591839f3b146fa8af4374a104a0da7/45db79db132c41fd85865e5c8f277e7b.jpg","novelType":"武侠","novelTitle":"无卷免费","novelIntro":"我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介"}],"freeRecommend":[{"novelId":"7e01e72401d9443d8416f3bb8020070c","novelAuthor":"tianx","novelPhoto":"http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg","novelType":"科幻","novelTitle":"作者创建独家首发无卷小说001","novelIntro":"作者创建独家首发无卷小说001"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelType":"武侠","novelTitle":"管理员创建驻站作品小说001","novelIntro":"管理员创建驻站作品小说001"},{"novelId":"4b82610b3aa14b699005f6559a0c8081","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_a/novel/4b82610b3aa14b699005f6559a0c8081/1328648741ac49cf9951fc9f02e54a26.png","novelType":"军事","novelTitle":"管理员创建独家首发免费小说001","novelIntro":"管理员创建独家首发免费小说001"},{"novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/novel_img_1542076587368_0.jpg","novelType":"灵异","novelTitle":"test1113","novelIntro":"dfsdf"}],"bestNovelRecommend":[{"novelId":"bc35cf06340b498cb1e638b551616fbe","novelAuthor":"tianx","novelPhoto":"http://59.110.124.41:80/novel_b/novel/bc35cf06340b498cb1e638b551616fbe/2af1b94cf0e34c7faf64a8bf75faaf8d.jpg","novelType":"科幻","novelTitle":"驻站有卷小说001","novelIntro":"驻站有卷小说001"},{"novelId":"e1a32c8a358e49f8ae9c576864cc5797","novelAuthor":"老铁瓜1","novelPhoto":"http://59.110.124.41:80/novel_a/novel/e1a32c8a358e49f8ae9c576864cc5797/e9f25b8025b8480d881591fbbbf281a5.jpg","novelType":"历史","novelTitle":"驻站1","novelIntro":"那年夏天"},{"novelId":"80bd05450ceb4233b90450fb351a8e7e","novelAuthor":"甜心01","novelPhoto":"http://59.110.124.41:80/novel_b/novel/80bd05450ceb4233b90450fb351a8e7e/d6ebeee535084179af3eb07a3f1d1ba8.png","novelType":"武侠","novelTitle":"管理员创建驻站作品小说001","novelIntro":"管理员创建驻站作品小说001"},{"novelId":"69591839f3b146fa8af4374a104a0da7","novelAuthor":"我是大作家","novelPhoto":"http://59.110.124.41:80/novel_b/novel/69591839f3b146fa8af4374a104a0da7/45db79db132c41fd85865e5c8f277e7b.jpg","novelType":"武侠","novelTitle":"无卷免费","novelIntro":"我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介"}],"recentUpdate":[{"chapter":"14","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"88d287f5953442d7b971286d0811ce11","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"骄傲杨"},{"chapter":"13","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"5e4bb3e62c2d4e529349cb0f29924342","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"武宣"},{"chapter":"12","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"5bfcf96f505c47e9b5fecd11d906ab9e","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"卡达"},{"chapter":"11","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"6dbb201422c14eca822ceef1e53305bc","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"就有"},{"chapter":"10","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"afcdda92d4d14689a6571b46bdd6db25","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"神将"},{"chapter":"9","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"66b35ba3ecc042498dd1321d79cbc3d5","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"天赐"},{"chapter":"8","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"0bc1723fd5f448f491fedfe82d700101","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"神人"},{"chapter":"7","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"c9146e2ea70a414fb0b4af67964e80f8","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"元芳"},{"chapter":"6","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"2e2368ab59da44f2bfc9d48e6f7430c8","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"疾风"},{"chapter":"5","novelId":"6df3bce9e22c4f1da6ec2e128dfd4f17","chapterId":"15436a3142734a369db5886f8c6d873a","novelType":"历史","novelTitle":"订阅有卷1","chapterTitle":"三板斧"}]}
     * message : 查询成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static HomeReturnBean objectFromData(String str) {

        return new Gson().fromJson(str, HomeReturnBean.class);
    }

    public static List<HomeReturnBean> arrayHomeReturnBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<HomeReturnBean>>() {
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

        private List<HeadRecommendBean> headRecommend;
        private List<HotRecommendBean> hotRecommend;
        private List<EditorRecommendBean> editorRecommend;
        private List<BestAuthorZoneBean> bestAuthorZone;
        private List<FreeRecommendBean> freeRecommend;
        private List<BestNovelRecommendBean> bestNovelRecommend;
        private List<RecentUpdateBean> recentUpdate;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public List<HeadRecommendBean> getHeadRecommend() {
            return headRecommend;
        }

        public void setHeadRecommend(List<HeadRecommendBean> headRecommend) {
            this.headRecommend = headRecommend;
        }

        public List<HotRecommendBean> getHotRecommend() {
            return hotRecommend;
        }

        public void setHotRecommend(List<HotRecommendBean> hotRecommend) {
            this.hotRecommend = hotRecommend;
        }

        public List<EditorRecommendBean> getEditorRecommend() {
            return editorRecommend;
        }

        public void setEditorRecommend(List<EditorRecommendBean> editorRecommend) {
            this.editorRecommend = editorRecommend;
        }

        public List<BestAuthorZoneBean> getBestAuthorZone() {
            return bestAuthorZone;
        }

        public void setBestAuthorZone(List<BestAuthorZoneBean> bestAuthorZone) {
            this.bestAuthorZone = bestAuthorZone;
        }

        public List<FreeRecommendBean> getFreeRecommend() {
            return freeRecommend;
        }

        public void setFreeRecommend(List<FreeRecommendBean> freeRecommend) {
            this.freeRecommend = freeRecommend;
        }

        public List<BestNovelRecommendBean> getBestNovelRecommend() {
            return bestNovelRecommend;
        }

        public void setBestNovelRecommend(List<BestNovelRecommendBean> bestNovelRecommend) {
            this.bestNovelRecommend = bestNovelRecommend;
        }

        public List<RecentUpdateBean> getRecentUpdate() {
            return recentUpdate;
        }

        public void setRecentUpdate(List<RecentUpdateBean> recentUpdate) {
            this.recentUpdate = recentUpdate;
        }

        public static class HeadRecommendBean {
            /**
             * novelId : ce006ab1e5e8423e8c9de91aa12c2d34
             * novelPhoto : http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/bef18de92c8c4d7e93a268a72c6e0fcf.png
             * novelTitle : test1113
             */

            private String novelId;
            private String novelPhoto;
            private String novelTitle;

            public static HeadRecommendBean objectFromData(String str) {

                return new Gson().fromJson(str, HeadRecommendBean.class);
            }

            public static List<HeadRecommendBean> arrayHeadRecommendBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<HeadRecommendBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
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
        }

        public static class HotRecommendBean {
            /**
             * novelId : 7d2f9ce60b254b22977f1d979abe3c5b
             * novelTitle : 买断管理员在线发布章节
             */

            private String novelId;
            private String novelTitle;

            public static HotRecommendBean objectFromData(String str) {

                return new Gson().fromJson(str, HotRecommendBean.class);
            }

            public static List<HotRecommendBean> arrayHotRecommendBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<HotRecommendBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelTitle() {
                return novelTitle;
            }

            public void setNovelTitle(String novelTitle) {
                this.novelTitle = novelTitle;
            }
        }

        public static class EditorRecommendBean {
            /**
             * novelId : fd1dc45252b6493bb9be18133be42164
             * novelPhoto : http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg
             * novelTitle : 有卷小说002
             */

            private String novelId;
            private String novelPhoto;
            private String novelTitle;

            public static EditorRecommendBean objectFromData(String str) {

                return new Gson().fromJson(str, EditorRecommendBean.class);
            }

            public static List<EditorRecommendBean> arrayEditorRecommendBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<EditorRecommendBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
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
        }

        public static class BestAuthorZoneBean {
            /**
             * novelId : d9485330914348038a5f1d347bc84ab3
             * novelAuthor : 我是大作家
             * novelPhoto : http://59.110.124.41:80/novel_a/novel/d9485330914348038a5f1d347bc84ab3/b0d853b5c0964ad7a7a878430f8838f2.jpg
             * novelType : 武侠
             * novelTitle : 无卷
             * novelIntro : 我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介我是小说简介
             */

            private String novelId;
            private String novelAuthor;
            private String novelPhoto;
            private String novelType;
            private String novelTitle;
            private String novelIntro;

            public static BestAuthorZoneBean objectFromData(String str) {

                return new Gson().fromJson(str, BestAuthorZoneBean.class);
            }

            public static List<BestAuthorZoneBean> arrayBestAuthorZoneBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<BestAuthorZoneBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelAuthor() {
                return novelAuthor;
            }

            public void setNovelAuthor(String novelAuthor) {
                this.novelAuthor = novelAuthor;
            }

            public String getNovelPhoto() {
                return novelPhoto;
            }

            public void setNovelPhoto(String novelPhoto) {
                this.novelPhoto = novelPhoto;
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

            public String getNovelIntro() {
                return novelIntro;
            }

            public void setNovelIntro(String novelIntro) {
                this.novelIntro = novelIntro;
            }
        }

        public static class FreeRecommendBean {
            /**
             * novelId : 7e01e72401d9443d8416f3bb8020070c
             * novelAuthor : tianx
             * novelPhoto : http://59.110.124.41:80/novel_a/novel/7e01e72401d9443d8416f3bb8020070c/ab47b30a7ee74a5db261f78e647502c0.jpg
             * novelType : 科幻
             * novelTitle : 作者创建独家首发无卷小说001
             * novelIntro : 作者创建独家首发无卷小说001
             */

            private String novelId;
            private String novelAuthor;
            private String novelPhoto;
            private String novelType;
            private String novelTitle;
            private String novelIntro;

            public static FreeRecommendBean objectFromData(String str) {

                return new Gson().fromJson(str, FreeRecommendBean.class);
            }

            public static List<FreeRecommendBean> arrayFreeRecommendBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<FreeRecommendBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelAuthor() {
                return novelAuthor;
            }

            public void setNovelAuthor(String novelAuthor) {
                this.novelAuthor = novelAuthor;
            }

            public String getNovelPhoto() {
                return novelPhoto;
            }

            public void setNovelPhoto(String novelPhoto) {
                this.novelPhoto = novelPhoto;
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

            public String getNovelIntro() {
                return novelIntro;
            }

            public void setNovelIntro(String novelIntro) {
                this.novelIntro = novelIntro;
            }
        }

        public static class BestNovelRecommendBean {
            /**
             * novelId : bc35cf06340b498cb1e638b551616fbe
             * novelAuthor : tianx
             * novelPhoto : http://59.110.124.41:80/novel_b/novel/bc35cf06340b498cb1e638b551616fbe/2af1b94cf0e34c7faf64a8bf75faaf8d.jpg
             * novelType : 科幻
             * novelTitle : 驻站有卷小说001
             * novelIntro : 驻站有卷小说001
             */

            private String novelId;
            private String novelAuthor;
            private String novelPhoto;
            private String novelType;
            private String novelTitle;
            private String novelIntro;

            public static BestNovelRecommendBean objectFromData(String str) {

                return new Gson().fromJson(str, BestNovelRecommendBean.class);
            }

            public static List<BestNovelRecommendBean> arrayBestNovelRecommendBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<BestNovelRecommendBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelAuthor() {
                return novelAuthor;
            }

            public void setNovelAuthor(String novelAuthor) {
                this.novelAuthor = novelAuthor;
            }

            public String getNovelPhoto() {
                return novelPhoto;
            }

            public void setNovelPhoto(String novelPhoto) {
                this.novelPhoto = novelPhoto;
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

            public String getNovelIntro() {
                return novelIntro;
            }

            public void setNovelIntro(String novelIntro) {
                this.novelIntro = novelIntro;
            }
        }

        public static class RecentUpdateBean {
            /**
             * chapter : 14
             * novelId : 6df3bce9e22c4f1da6ec2e128dfd4f17
             * chapterId : 88d287f5953442d7b971286d0811ce11
             * novelType : 历史
             * novelTitle : 订阅有卷1
             * chapterTitle : 骄傲杨
             */

            private String chapter;
            private String novelId;
            private String chapterId;
            private String novelType;
            private String novelTitle;
            private String chapterTitle;

            public static RecentUpdateBean objectFromData(String str) {

                return new Gson().fromJson(str, RecentUpdateBean.class);

            }

            public static List<RecentUpdateBean> arrayRecentUpdateBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<RecentUpdateBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getChapter() {
                return chapter;
            }

            public void setChapter(String chapter) {
                this.chapter = chapter;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
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

            public String getChapterTitle() {
                return chapterTitle;
            }

            public void setChapterTitle(String chapterTitle) {
                this.chapterTitle = chapterTitle;
            }
        }
    }
}
