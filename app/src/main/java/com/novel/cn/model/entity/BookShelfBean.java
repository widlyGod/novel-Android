package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/3/12.
 */

public class BookShelfBean {


    /**
     * basePage : null
     * code : 1
     * data : {"total":14,"book":[{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"64af902a0dc74686942bd24269f21ce2","newChapterTitle":"5k字符","newIsFree":0,"newMoney":null,"noReadNum":0,"novelId":"c3e68d9d8d9f467a892e4ea0b4e98ed6","novelPoto":"http://59.110.124.41:80/novel_a/novel/c3e68d9d8d9f467a892e4ea0b4e98ed6/novel_img_1542076640865_0.png","novelTitle":"test11132","readChapterId":"64af902a0dc74686942bd24269f21ce2","readIsFree":0,"readMoney":30,"readTime":null},{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"23664fb388084a8a8460aefc9302a4ae","newChapterTitle":"test001","newIsFree":0,"newMoney":null,"noReadNum":0,"novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelPoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/novel_img_1542076587368_0.jpg","novelTitle":"test1113","readChapterId":"23664fb388084a8a8460aefc9302a4ae","readIsFree":0,"readMoney":6,"readTime":null},{"auto":false,"isRecord":true,"newChapter":4,"newChapterId":"c78b3a048e8d4f8bb74f1ae5caa4e61b","newChapterTitle":"10000","newIsFree":0,"newMoney":null,"noReadNum":3,"novelId":"e148bf8bde994d198d2ae27e627cfd79","novelPoto":"http://59.110.124.41:80/novel_b/novel/e148bf8bde994d198d2ae27e627cfd79/b4a5899e24204c93b64163dc87981a4d.jpg","novelTitle":"独家2","readChapterId":"92356f860d0e4f6397bb40580b7139bd","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":4,"newChapterId":"6c289f6046d548738618a51040fbae72","newChapterTitle":"10000","newIsFree":0,"newMoney":null,"noReadNum":3,"novelId":"5b5619e4738e404e8ae3962c8c6fa48b","novelPoto":"http://59.110.124.41:80/novel_a/novel/5b5619e4738e404e8ae3962c8c6fa48b/ac012d7f492c4b8c845f716a638dc3cd.jpg","novelTitle":"独家1","readChapterId":"8aa6bc448cd547e5b1b6b6c90b27cf15","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":50,"newChapterId":"6c85c693f5234f819438581db565b907","newChapterTitle":"李乐传","newIsFree":1,"newMoney":null,"noReadNum":49,"novelId":"d9485330914348038a5f1d347bc84ab3","novelPoto":"http://59.110.124.41:80/novel_a/novel/d9485330914348038a5f1d347bc84ab3/b0d853b5c0964ad7a7a878430f8838f2.jpg","novelTitle":"穿越之王爷你睡错王妃了","readChapterId":"0757c7903b0349fb9f0254a20ecb7604","readIsFree":0,"readMoney":36,"readTime":null},{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"de558eb959534389aa33ce4096b10d9b","newChapterTitle":"5k字符","newIsFree":0,"newMoney":null,"noReadNum":45,"novelId":"fb1a8d0b20ac4fdfb86c870e0561bc29","novelPoto":"http://59.110.124.41:80/novel_b/novel/fb1a8d0b20ac4fdfb86c870e0561bc29/517de32d30554fab873232eae2619b4c.png","novelTitle":"作者创建独家首发有卷小说001","readChapterId":"345a3b4007ff407eacef245376d12bc3","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":9,"newChapterId":"a635d0d37c6945e2abeabe3c3436cfa1","newChapterTitle":"new9","newIsFree":0,"newMoney":null,"noReadNum":8,"novelId":"17d1cb767f344e5293037b778a0fd268","novelPoto":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelTitle":"作者创建已完结小说001","readChapterId":"7e684f6d56c4499aa58064e8bd452c63","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":5,"newChapterId":"794d0881f2754499a8c2180a94493bcf","newChapterTitle":"撒烦烦烦","newIsFree":0,"newMoney":null,"noReadNum":4,"novelId":"1ac176d5d48a43e5ab30de44c8bf8ca3","novelPoto":"http://59.110.124.41:80/novel_b/novel/1ac176d5d48a43e5ab30de44c8bf8ca3/e12d9fabdd094af29727ae25153c3838.jpg","novelTitle":"驻站11","readChapterId":"0f7cd473773e421d9c8d1489d7ed8f1b","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":14,"newChapterId":"5d3dfb21e10545f3940b835f4e0ccef8","newChapterTitle":"new44","newIsFree":0,"newMoney":null,"noReadNum":43,"novelId":"ae41ae955920463e97c03a1df6318ea9","novelPoto":"http://59.110.124.41:80/novel_a/novel/ae41ae955920463e97c03a1df6318ea9/b2aa78f4b1774328af835f0b30f4345b.png","novelTitle":"管理员创建独家首发有卷小说001","readChapterId":"6320d60b35ee4ccbaf65cebf9c0a95d4","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":11,"newChapterId":"c5ed5b16ada1451581fb3c472d467354","newChapterTitle":"test","newIsFree":0,"newMoney":null,"noReadNum":10,"novelId":"44c8592d48e545e591dd9c804c2d6962","novelPoto":"http://59.110.124.41:80/novel_a/novel/44c8592d48e545e591dd9c804c2d6962/f00034d1b17d4d23bbaa9b920d5b4126.png","novelTitle":"管理员创建驻站作品有卷小说001","readChapterId":"8aeb6d821f844ecf87ba7a6d624aa1d4","readIsFree":0,"readMoney":0,"readTime":null}]}
     * message : 查询成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static BookShelfBean objectFromData(String str) {

        return new Gson().fromJson(str, BookShelfBean.class);
    }

    public static List<BookShelfBean> arrayBookShelfBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BookShelfBean>>() {
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
         * total : 14
         * book : [{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"64af902a0dc74686942bd24269f21ce2","newChapterTitle":"5k字符","newIsFree":0,"newMoney":null,"noReadNum":0,"novelId":"c3e68d9d8d9f467a892e4ea0b4e98ed6","novelPoto":"http://59.110.124.41:80/novel_a/novel/c3e68d9d8d9f467a892e4ea0b4e98ed6/novel_img_1542076640865_0.png","novelTitle":"test11132","readChapterId":"64af902a0dc74686942bd24269f21ce2","readIsFree":0,"readMoney":30,"readTime":null},{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"23664fb388084a8a8460aefc9302a4ae","newChapterTitle":"test001","newIsFree":0,"newMoney":null,"noReadNum":0,"novelId":"ce006ab1e5e8423e8c9de91aa12c2d34","novelPoto":"http://59.110.124.41:80/novel_b/novel/ce006ab1e5e8423e8c9de91aa12c2d34/novel_img_1542076587368_0.jpg","novelTitle":"test1113","readChapterId":"23664fb388084a8a8460aefc9302a4ae","readIsFree":0,"readMoney":6,"readTime":null},{"auto":false,"isRecord":true,"newChapter":4,"newChapterId":"c78b3a048e8d4f8bb74f1ae5caa4e61b","newChapterTitle":"10000","newIsFree":0,"newMoney":null,"noReadNum":3,"novelId":"e148bf8bde994d198d2ae27e627cfd79","novelPoto":"http://59.110.124.41:80/novel_b/novel/e148bf8bde994d198d2ae27e627cfd79/b4a5899e24204c93b64163dc87981a4d.jpg","novelTitle":"独家2","readChapterId":"92356f860d0e4f6397bb40580b7139bd","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":4,"newChapterId":"6c289f6046d548738618a51040fbae72","newChapterTitle":"10000","newIsFree":0,"newMoney":null,"noReadNum":3,"novelId":"5b5619e4738e404e8ae3962c8c6fa48b","novelPoto":"http://59.110.124.41:80/novel_a/novel/5b5619e4738e404e8ae3962c8c6fa48b/ac012d7f492c4b8c845f716a638dc3cd.jpg","novelTitle":"独家1","readChapterId":"8aa6bc448cd547e5b1b6b6c90b27cf15","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":50,"newChapterId":"6c85c693f5234f819438581db565b907","newChapterTitle":"李乐传","newIsFree":1,"newMoney":null,"noReadNum":49,"novelId":"d9485330914348038a5f1d347bc84ab3","novelPoto":"http://59.110.124.41:80/novel_a/novel/d9485330914348038a5f1d347bc84ab3/b0d853b5c0964ad7a7a878430f8838f2.jpg","novelTitle":"穿越之王爷你睡错王妃了","readChapterId":"0757c7903b0349fb9f0254a20ecb7604","readIsFree":0,"readMoney":36,"readTime":null},{"auto":false,"isRecord":true,"newChapter":1,"newChapterId":"de558eb959534389aa33ce4096b10d9b","newChapterTitle":"5k字符","newIsFree":0,"newMoney":null,"noReadNum":45,"novelId":"fb1a8d0b20ac4fdfb86c870e0561bc29","novelPoto":"http://59.110.124.41:80/novel_b/novel/fb1a8d0b20ac4fdfb86c870e0561bc29/517de32d30554fab873232eae2619b4c.png","novelTitle":"作者创建独家首发有卷小说001","readChapterId":"345a3b4007ff407eacef245376d12bc3","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":9,"newChapterId":"a635d0d37c6945e2abeabe3c3436cfa1","newChapterTitle":"new9","newIsFree":0,"newMoney":null,"noReadNum":8,"novelId":"17d1cb767f344e5293037b778a0fd268","novelPoto":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelTitle":"作者创建已完结小说001","readChapterId":"7e684f6d56c4499aa58064e8bd452c63","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":5,"newChapterId":"794d0881f2754499a8c2180a94493bcf","newChapterTitle":"撒烦烦烦","newIsFree":0,"newMoney":null,"noReadNum":4,"novelId":"1ac176d5d48a43e5ab30de44c8bf8ca3","novelPoto":"http://59.110.124.41:80/novel_b/novel/1ac176d5d48a43e5ab30de44c8bf8ca3/e12d9fabdd094af29727ae25153c3838.jpg","novelTitle":"驻站11","readChapterId":"0f7cd473773e421d9c8d1489d7ed8f1b","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":14,"newChapterId":"5d3dfb21e10545f3940b835f4e0ccef8","newChapterTitle":"new44","newIsFree":0,"newMoney":null,"noReadNum":43,"novelId":"ae41ae955920463e97c03a1df6318ea9","novelPoto":"http://59.110.124.41:80/novel_a/novel/ae41ae955920463e97c03a1df6318ea9/b2aa78f4b1774328af835f0b30f4345b.png","novelTitle":"管理员创建独家首发有卷小说001","readChapterId":"6320d60b35ee4ccbaf65cebf9c0a95d4","readIsFree":0,"readMoney":0,"readTime":null},{"auto":false,"isRecord":true,"newChapter":11,"newChapterId":"c5ed5b16ada1451581fb3c472d467354","newChapterTitle":"test","newIsFree":0,"newMoney":null,"noReadNum":10,"novelId":"44c8592d48e545e591dd9c804c2d6962","novelPoto":"http://59.110.124.41:80/novel_a/novel/44c8592d48e545e591dd9c804c2d6962/f00034d1b17d4d23bbaa9b920d5b4126.png","novelTitle":"管理员创建驻站作品有卷小说001","readChapterId":"8aeb6d821f844ecf87ba7a6d624aa1d4","readIsFree":0,"readMoney":0,"readTime":null}]
         */

        private int total;
        private List<BookBean> book;

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

        public List<BookBean> getBook() {
            return book;
        }

        public void setBook(List<BookBean> book) {
            this.book = book;
        }

        public static class BookBean {
            /**
             * auto : false
             * isRecord : true
             * newChapter : 1
             * newChapterId : 64af902a0dc74686942bd24269f21ce2
             * newChapterTitle : 5k字符
             * newIsFree : 0
             * newMoney : null
             * noReadNum : 0
             * novelId : c3e68d9d8d9f467a892e4ea0b4e98ed6
             * novelPoto : http://59.110.124.41:80/novel_a/novel/c3e68d9d8d9f467a892e4ea0b4e98ed6/novel_img_1542076640865_0.png
             * novelTitle : test11132
             * readChapterId : 64af902a0dc74686942bd24269f21ce2
             * readIsFree : 0
             * readMoney : 30
             * readTime : null
             */

            private boolean auto;
            private boolean isRecord;
            private int newChapter;
            private String newChapterId;
            private String newChapterTitle;
            private int newIsFree;
            private Object newMoney;
            private int noReadNum;
            private String novelId;
            private String novelPoto;
            private String novelTitle;
            private String readChapterId;
            private int readIsFree;
            private int readMoney;
            private Object readTime;

            public static BookBean objectFromData(String str) {

                return new Gson().fromJson(str, BookBean.class);
            }

            public static List<BookBean> arrayBookBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<BookBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public boolean isAuto() {
                return auto;
            }

            public void setAuto(boolean auto) {
                this.auto = auto;
            }

            public boolean isIsRecord() {
                return isRecord;
            }

            public void setIsRecord(boolean isRecord) {
                this.isRecord = isRecord;
            }

            public int getNewChapter() {
                return newChapter;
            }

            public void setNewChapter(int newChapter) {
                this.newChapter = newChapter;
            }

            public String getNewChapterId() {
                return newChapterId;
            }

            public void setNewChapterId(String newChapterId) {
                this.newChapterId = newChapterId;
            }

            public String getNewChapterTitle() {
                return newChapterTitle;
            }

            public void setNewChapterTitle(String newChapterTitle) {
                this.newChapterTitle = newChapterTitle;
            }

            public int getNewIsFree() {
                return newIsFree;
            }

            public void setNewIsFree(int newIsFree) {
                this.newIsFree = newIsFree;
            }

            public Object getNewMoney() {
                return newMoney;
            }

            public void setNewMoney(Object newMoney) {
                this.newMoney = newMoney;
            }

            public int getNoReadNum() {
                return noReadNum;
            }

            public void setNoReadNum(int noReadNum) {
                this.noReadNum = noReadNum;
            }

            public String getNovelId() {
                return novelId;
            }

            public void setNovelId(String novelId) {
                this.novelId = novelId;
            }

            public String getNovelPoto() {
                return novelPoto;
            }

            public void setNovelPoto(String novelPoto) {
                this.novelPoto = novelPoto;
            }

            public String getNovelTitle() {
                return novelTitle;
            }

            public void setNovelTitle(String novelTitle) {
                this.novelTitle = novelTitle;
            }

            public String getReadChapterId() {
                return readChapterId;
            }

            public void setReadChapterId(String readChapterId) {
                this.readChapterId = readChapterId;
            }

            public int getReadIsFree() {
                return readIsFree;
            }

            public void setReadIsFree(int readIsFree) {
                this.readIsFree = readIsFree;
            }

            public int getReadMoney() {
                return readMoney;
            }

            public void setReadMoney(int readMoney) {
                this.readMoney = readMoney;
            }

            public Object getReadTime() {
                return readTime;
            }

            public void setReadTime(Object readTime) {
                this.readTime = readTime;
            }
        }
    }
}
