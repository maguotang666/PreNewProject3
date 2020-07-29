package com.example.administrator.prenewproject.bean;

import java.util.List;

/**
 * Created by maguotang on 2019/4/23
 */
public class CollectBean {


    /**
     * data : {"curPage":1,"datas":[{"author":"小码哥的freestyle","chapterId":346,"chapterName":"JVM","courseId":13,"desc":"","envelopePic":"","id":57571,"link":"https://www.jianshu.com/p/af05085f6ea3","niceDate":"17小时前","origin":"","originId":8276,"publishTime":1555926339000,"title":"搞定JVM垃圾回收就是这么简单","userId":22130,"visible":0,"zan":0},{"author":"空手接白刀","chapterId":124,"chapterName":"Fragment","courseId":13,"desc":"","envelopePic":"","id":57561,"link":"https://www.jianshu.com/p/d0306e377110","niceDate":"17小时前","origin":"","originId":8278,"publishTime":1555925534000,"title":"commit和commitAllowingStateLoss方法的区别","userId":22130,"visible":0,"zan":0},{"author":"鸿洋","chapterId":298,"chapterName":"我的博客","courseId":13,"desc":"","envelopePic":"","id":57557,"link":"http://www.wanandroid.com/blog/show/2030","niceDate":"17小时前","origin":"","originId":8004,"publishTime":1555925342000,"title":"来支持一下本站吧...","userId":22130,"visible":0,"zan":0},{"author":"liuhe688","chapterId":10,"chapterName":"Activity","courseId":13,"desc":"","envelopePic":"","id":57545,"link":"http://blog.csdn.net/liuhe688/article/details/6754323","niceDate":"17小时前","origin":"","originId":3,"publishTime":1555925111000,"title":"基础总结篇之二：Activity的四种launchMode","userId":22130,"visible":0,"zan":0},{"author":"hyman","chapterId":448,"chapterName":"慕课网","courseId":13,"desc":"","envelopePic":"","id":57506,"link":"https://www.imooc.com/learn/1116","niceDate":"20小时前","origin":"","originId":8283,"publishTime":1555915986000,"title":"我在慕课上的讲课：ViewPager+Tab特效实现微信主界面","userId":22130,"visible":0,"zan":0},{"author":"鸿洋","chapterId":360,"chapterName":"小编发布","courseId":13,"desc":"","envelopePic":"","id":57505,"link":"https://gitee.com/enterprises?utm_source=wechat_hongyang&amp;utm_medium=subscriptions&amp;utm_campaign=enterprise&amp;utm_term=development_process","niceDate":"20小时前","origin":"","originId":8282,"publishTime":1555915984000,"title":"我已经有GitHub了， 为什么还要了解这个？","userId":22130,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":6}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int    errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"小码哥的freestyle","chapterId":346,"chapterName":"JVM","courseId":13,"desc":"","envelopePic":"","id":57571,"link":"https://www.jianshu.com/p/af05085f6ea3","niceDate":"17小时前","origin":"","originId":8276,"publishTime":1555926339000,"title":"搞定JVM垃圾回收就是这么简单","userId":22130,"visible":0,"zan":0},{"author":"空手接白刀","chapterId":124,"chapterName":"Fragment","courseId":13,"desc":"","envelopePic":"","id":57561,"link":"https://www.jianshu.com/p/d0306e377110","niceDate":"17小时前","origin":"","originId":8278,"publishTime":1555925534000,"title":"commit和commitAllowingStateLoss方法的区别","userId":22130,"visible":0,"zan":0},{"author":"鸿洋","chapterId":298,"chapterName":"我的博客","courseId":13,"desc":"","envelopePic":"","id":57557,"link":"http://www.wanandroid.com/blog/show/2030","niceDate":"17小时前","origin":"","originId":8004,"publishTime":1555925342000,"title":"来支持一下本站吧...","userId":22130,"visible":0,"zan":0},{"author":"liuhe688","chapterId":10,"chapterName":"Activity","courseId":13,"desc":"","envelopePic":"","id":57545,"link":"http://blog.csdn.net/liuhe688/article/details/6754323","niceDate":"17小时前","origin":"","originId":3,"publishTime":1555925111000,"title":"基础总结篇之二：Activity的四种launchMode","userId":22130,"visible":0,"zan":0},{"author":"hyman","chapterId":448,"chapterName":"慕课网","courseId":13,"desc":"","envelopePic":"","id":57506,"link":"https://www.imooc.com/learn/1116","niceDate":"20小时前","origin":"","originId":8283,"publishTime":1555915986000,"title":"我在慕课上的讲课：ViewPager+Tab特效实现微信主界面","userId":22130,"visible":0,"zan":0},{"author":"鸿洋","chapterId":360,"chapterName":"小编发布","courseId":13,"desc":"","envelopePic":"","id":57505,"link":"https://gitee.com/enterprises?utm_source=wechat_hongyang&amp;utm_medium=subscriptions&amp;utm_campaign=enterprise&amp;utm_term=development_process","niceDate":"20小时前","origin":"","originId":8282,"publishTime":1555915984000,"title":"我已经有GitHub了， 为什么还要了解这个？","userId":22130,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 6
         */

        private int curPage;
        private int             offset;
        private boolean         over;
        private int             pageCount;
        private int             size;
        private int             total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * author : 小码哥的freestyle
             * chapterId : 346
             * chapterName : JVM
             * courseId : 13
             * desc :
             * envelopePic :
             * id : 57571
             * link : https://www.jianshu.com/p/af05085f6ea3
             * niceDate : 17小时前
             * origin :
             * originId : 8276
             * publishTime : 1555926339000
             * title : 搞定JVM垃圾回收就是这么简单
             * userId : 22130
             * visible : 0
             * zan : 0
             */

            private String author;
            private int    chapterId;
            private String chapterName;
            private int    courseId;
            private String desc;
            private String envelopePic;
            private int    id;
            private String link;
            private String niceDate;
            private String origin;
            private int    originId;
            private long   publishTime;
            private String title;
            private int    userId;
            private int    visible;
            private int    zan;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getOriginId() {
                return originId;
            }

            public void setOriginId(int originId) {
                this.originId = originId;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }
        }
    }
}
