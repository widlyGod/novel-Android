package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/14.
 */

public class BookShelfBean {




    /**
     * basePage : null
     * code : 1
     * data : {"total":13,"book":[{"auto":true,"isRecord":true,"newChapter":74,"newChapterId":"fe6278cabdf748a5ae96e88c339f6faf","newChapterTitle":"橘子熟了171","newIsFree":1,"newMoney":null,"novelId":"fd1dc45252b6493bb9be18133be42164","novelPoto":"http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg","novelTitle":"有卷小说002","readChapterId":"fe6278cabdf748a5ae96e88c339f6faf","readIsFree":1,"readMoney":15,"times":null}]}
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
         * total : 13
         * book : [{"auto":true,"isRecord":true,"newChapter":74,"newChapterId":"fe6278cabdf748a5ae96e88c339f6faf","newChapterTitle":"橘子熟了171","newIsFree":1,"newMoney":null,"novelId":"fd1dc45252b6493bb9be18133be42164","novelPoto":"http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg","novelTitle":"有卷小说002","readChapterId":"fe6278cabdf748a5ae96e88c339f6faf","readIsFree":1,"readMoney":15,"times":null}]
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
             * auto : true
             * isRecord : true
             * newChapter : 74
             * newChapterId : fe6278cabdf748a5ae96e88c339f6faf
             * newChapterTitle : 橘子熟了171
             * newIsFree : 1
             * newMoney : null
             * novelId : fd1dc45252b6493bb9be18133be42164
             * novelPoto : http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg
             * novelTitle : 有卷小说002
             * readChapterId : fe6278cabdf748a5ae96e88c339f6faf
             * readIsFree : 1
             * readMoney : 15
             * times : null
             */

            private boolean auto;
            private boolean isRecord;
            private int newChapter;
            private String newChapterId;
            private String newChapterTitle;
            private int newIsFree;
            private Object newMoney;
            private String novelId;
            private String novelPoto;
            private String novelTitle;
            private String readChapterId;
            private int readIsFree;
            private int readMoney;
            private Object times;

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

            public Object getTimes() {
                return times;
            }

            public void setTimes(Object times) {
                this.times = times;
            }
        }
    }
}
