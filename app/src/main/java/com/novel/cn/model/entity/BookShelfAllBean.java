package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/16.
 */

public class BookShelfAllBean {


    /**
     * basePage : null
     * code : 1
     * data : [{"children":[{"id":"3","imgUrl":"http://59.110.124.41:80/novel_a/novel/08690de2591a46a89632b519e7b011c3/novel_img_1542590592037_0.jpg","novelNum":"28","parentId":1,"typeName":"灵异"},{"id":"4","imgUrl":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelNum":"12","parentId":1,"typeName":"科幻"},{"id":"5","imgUrl":"http://59.110.124.41:80/novel_b/novel/17ce0371bdab41eeb1dca6752834d9de/novel_img_1544005821190_0.jpg","novelNum":"10","parentId":1,"typeName":"武侠"},{"id":"6","imgUrl":"http://59.110.124.41:80/novel_b/novel/0d4399677147412c95f265ba45453393/novel_img_1544151946319_0.jpg","novelNum":"16","parentId":1,"typeName":"军事"},{"id":"7","imgUrl":"http://59.110.124.41:80/novel_a/novel/034890dc97884107a83b52356a072765/d8d2ccb297df4cd0b9b978382d736f15.jpg","novelNum":"16","parentId":1,"typeName":"历史"},{"id":"8","imgUrl":"http://59.110.124.41:80/novel_b/novel/1ac176d5d48a43e5ab30de44c8bf8ca3/e12d9fabdd094af29727ae25153c3838.jpg","novelNum":"8","parentId":1,"typeName":"社会"},{"id":"9","imgUrl":"http://59.110.124.41:80/novel_a/novel/27a1bc6dec80419ca9a844dd8a440e02/novel_img_1544151903656_0.jpg","novelNum":"4","parentId":1,"typeName":"玄幻"},{"id":"10","imgUrl":"http://59.110.124.41:80/novel_b/novel/00caeb3bed68447d961714ca537e5cc6/novel_img_1544005546788_0.jpg","novelNum":"6","parentId":1,"typeName":"都市"}],"id":1,"typeName":"男生频道"},{"children":[{"id":"4","imgUrl":"http://59.110.124.41:80/novel_b/novel/bc35cf06340b498cb1e638b551616fbe/2af1b94cf0e34c7faf64a8bf75faaf8d.jpg","novelNum":"1","parentId":2,"typeName":"科幻"},{"id":"6","imgUrl":"http://59.110.124.41:80/novel_a/novel/02256ec975b545db9eba76ddfcefec40/novel_img_1544005624098_0.jpg","novelNum":"1","parentId":2,"typeName":"军事"},{"id":"8","imgUrl":"http://59.110.124.41:80/novel_a/novel/186f2a73c9bf4b3ba181d68a472e1000/e2324b21deac46a8a947ede8ad5459ba.jpg","novelNum":"1","parentId":2,"typeName":"社会"},{"id":"10","imgUrl":"http://59.110.124.41:80/novel_a/novel/1b05ac4b9f2f46b189fdf31c3ad8180e/novel_img_1541734644691_0.jpg","novelNum":"1","parentId":2,"typeName":"都市"},{"id":"12","imgUrl":"http://59.110.124.41:80/novel_b/novel/3e047a7c8b3e43d4a35fc79c9fd85c48/4987e295f36b42dc8eb758914014dc5e.jpg","novelNum":"3","parentId":2,"typeName":"古言"}],"id":2,"typeName":"女生频道"},{"children":[{"id":"5","imgUrl":"http://59.110.124.41:80/novel_b/novel/69591839f3b146fa8af4374a104a0da7/45db79db132c41fd85865e5c8f277e7b.jpg","novelNum":"1","parentId":14,"typeName":"武侠"}],"id":14,"typeName":"11频道"},{"children":[{"id":"3","imgUrl":"http://59.110.124.41:80/novel_a/novel/08690de2591a46a89632b519e7b011c3/novel_img_1542590592037_0.jpg","novelNum":"28","parentId":null,"typeName":"灵异"},{"id":"4","imgUrl":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelNum":"13","parentId":null,"typeName":"科幻"},{"id":"5","imgUrl":"http://59.110.124.41:80/novel_b/novel/17ce0371bdab41eeb1dca6752834d9de/novel_img_1544005821190_0.jpg","novelNum":"11","parentId":null,"typeName":"武侠"},{"id":"6","imgUrl":"http://59.110.124.41:80/novel_a/novel/02256ec975b545db9eba76ddfcefec40/novel_img_1544005624098_0.jpg","novelNum":"17","parentId":null,"typeName":"军事"},{"id":"7","imgUrl":"http://59.110.124.41:80/novel_a/novel/034890dc97884107a83b52356a072765/d8d2ccb297df4cd0b9b978382d736f15.jpg","novelNum":"16","parentId":null,"typeName":"历史"},{"id":"8","imgUrl":"http://59.110.124.41:80/novel_a/novel/186f2a73c9bf4b3ba181d68a472e1000/e2324b21deac46a8a947ede8ad5459ba.jpg","novelNum":"9","parentId":null,"typeName":"社会"},{"id":"9","imgUrl":"http://59.110.124.41:80/novel_a/novel/27a1bc6dec80419ca9a844dd8a440e02/novel_img_1544151903656_0.jpg","novelNum":"4","parentId":null,"typeName":"玄幻"},{"id":"10","imgUrl":"http://59.110.124.41:80/novel_b/novel/00caeb3bed68447d961714ca537e5cc6/novel_img_1544005546788_0.jpg","novelNum":"7","parentId":null,"typeName":"都市"},{"id":"12","imgUrl":"http://59.110.124.41:80/novel_b/novel/3e047a7c8b3e43d4a35fc79c9fd85c48/4987e295f36b42dc8eb758914014dc5e.jpg","novelNum":"3","parentId":null,"typeName":"古言"}],"id":0,"typeName":"全部频道"}]
     * message : 操作成功
     * success : true
     */

    private Object basePage;
    private String code;
    private String message;
    private boolean success;
    private List<DataBean> data;

    public static BookShelfAllBean objectFromData(String str) {

        return new Gson().fromJson(str, BookShelfAllBean.class);
    }

    public static List<BookShelfAllBean> arrayBookShelfAllBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BookShelfAllBean>>() {
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
         * children : [{"id":"3","imgUrl":"http://59.110.124.41:80/novel_a/novel/08690de2591a46a89632b519e7b011c3/novel_img_1542590592037_0.jpg","novelNum":"28","parentId":1,"typeName":"灵异"},{"id":"4","imgUrl":"http://59.110.124.41:80/novel_a/novel/17d1cb767f344e5293037b778a0fd268/ff364e7be2434494a79373f672666ef1.jpg","novelNum":"12","parentId":1,"typeName":"科幻"},{"id":"5","imgUrl":"http://59.110.124.41:80/novel_b/novel/17ce0371bdab41eeb1dca6752834d9de/novel_img_1544005821190_0.jpg","novelNum":"10","parentId":1,"typeName":"武侠"},{"id":"6","imgUrl":"http://59.110.124.41:80/novel_b/novel/0d4399677147412c95f265ba45453393/novel_img_1544151946319_0.jpg","novelNum":"16","parentId":1,"typeName":"军事"},{"id":"7","imgUrl":"http://59.110.124.41:80/novel_a/novel/034890dc97884107a83b52356a072765/d8d2ccb297df4cd0b9b978382d736f15.jpg","novelNum":"16","parentId":1,"typeName":"历史"},{"id":"8","imgUrl":"http://59.110.124.41:80/novel_b/novel/1ac176d5d48a43e5ab30de44c8bf8ca3/e12d9fabdd094af29727ae25153c3838.jpg","novelNum":"8","parentId":1,"typeName":"社会"},{"id":"9","imgUrl":"http://59.110.124.41:80/novel_a/novel/27a1bc6dec80419ca9a844dd8a440e02/novel_img_1544151903656_0.jpg","novelNum":"4","parentId":1,"typeName":"玄幻"},{"id":"10","imgUrl":"http://59.110.124.41:80/novel_b/novel/00caeb3bed68447d961714ca537e5cc6/novel_img_1544005546788_0.jpg","novelNum":"6","parentId":1,"typeName":"都市"}]
         * id : 1
         * typeName : 男生频道
         */

        private int id;
        private boolean isClck;
        private String typeName;
        private List<ChildrenBean> children;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 3
             * imgUrl : http://59.110.124.41:80/novel_a/novel/08690de2591a46a89632b519e7b011c3/novel_img_1542590592037_0.jpg
             * novelNum : 28
             * parentId : 1
             * typeName : 灵异
             */

            private String id;
            private String imgUrl;
            private String novelNum;
            private int parentId;
            private String typeName;

            public static ChildrenBean objectFromData(String str) {

                return new Gson().fromJson(str, ChildrenBean.class);
            }

            public static List<ChildrenBean> arrayChildrenBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ChildrenBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getNovelNum() {
                return novelNum;
            }

            public void setNovelNum(String novelNum) {
                this.novelNum = novelNum;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }
        }
    }
}
