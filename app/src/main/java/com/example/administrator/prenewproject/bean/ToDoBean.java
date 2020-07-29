package com.example.administrator.prenewproject.bean;

import java.util.List;

/**
 * Created by maguotang on 2019/4/23
 */
public class ToDoBean {


    /**
     * data : {"doneList":[{"date":1532793600000,"todoList":[{"completeDate":1533052800000,"completeDateStr":"2018-08-01","content":"这里可以记录笔记，备忘信息等。","date":1532793600000,"dateStr":"2018-07-29","id":82,"status":1,"title":"已经完成的事情","type":0,"userId":2}]}],"todoList":[{"date":1532016000000,"todoList":[{"completeDate":null,"completeDateStr":"","content":"","date":1532016000000,"dateStr":"2018-07-20","id":73,"status":0,"title":"第一件未完成的事情","type":0,"userId":2}]},{"date":1532448000000,"todoList":[{"completeDate":null,"completeDateStr":"","content":"","date":1532448000000,"dateStr":"2018-07-25","id":80,"status":0,"title":"第二件未完成的事情","type":0,"userId":2}]}],"type":0}
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
         * doneList : [{"date":1532793600000,"todoList":[{"completeDate":1533052800000,"completeDateStr":"2018-08-01","content":"这里可以记录笔记，备忘信息等。","date":1532793600000,"dateStr":"2018-07-29","id":82,"status":1,"title":"已经完成的事情","type":0,"userId":2}]}]
         * todoList : [{"date":1532016000000,"todoList":[{"completeDate":null,"completeDateStr":"","content":"","date":1532016000000,"dateStr":"2018-07-20","id":73,"status":0,"title":"第一件未完成的事情","type":0,"userId":2}]},{"date":1532448000000,"todoList":[{"completeDate":null,"completeDateStr":"","content":"","date":1532448000000,"dateStr":"2018-07-25","id":80,"status":0,"title":"第二件未完成的事情","type":0,"userId":2}]}]
         * type : 0
         */

        private int type;
        private List<DoneListBean>   doneList;
        private List<TodoListBeanXX> todoList;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<DoneListBean> getDoneList() {
            return doneList;
        }

        public void setDoneList(List<DoneListBean> doneList) {
            this.doneList = doneList;
        }

        public List<TodoListBeanXX> getTodoList() {
            return todoList;
        }

        public void setTodoList(List<TodoListBeanXX> todoList) {
            this.todoList = todoList;
        }

        public static class DoneListBean {
            /**
             * date : 1532793600000
             * todoList : [{"completeDate":1533052800000,"completeDateStr":"2018-08-01","content":"这里可以记录笔记，备忘信息等。","date":1532793600000,"dateStr":"2018-07-29","id":82,"status":1,"title":"已经完成的事情","type":0,"userId":2}]
             */

            private long date;
            private List<TodoListBean> todoList;

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public List<TodoListBean> getTodoList() {
                return todoList;
            }

            public void setTodoList(List<TodoListBean> todoList) {
                this.todoList = todoList;
            }

            public static class TodoListBean {
                /**
                 * completeDate : 1533052800000
                 * completeDateStr : 2018-08-01
                 * content : 这里可以记录笔记，备忘信息等。
                 * date : 1532793600000
                 * dateStr : 2018-07-29
                 * id : 82
                 * status : 1
                 * title : 已经完成的事情
                 * type : 0
                 * userId : 2
                 */

                private long completeDate;
                private String completeDateStr;
                private String content;
                private long   date;
                private String dateStr;
                private int    id;
                private int    status;
                private String title;
                private int    type;
                private int    userId;

                public long getCompleteDate() {
                    return completeDate;
                }

                public void setCompleteDate(long completeDate) {
                    this.completeDate = completeDate;
                }

                public String getCompleteDateStr() {
                    return completeDateStr;
                }

                public void setCompleteDateStr(String completeDateStr) {
                    this.completeDateStr = completeDateStr;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public String getDateStr() {
                    return dateStr;
                }

                public void setDateStr(String dateStr) {
                    this.dateStr = dateStr;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }
            }
        }

        public static class TodoListBeanXX {
            /**
             * date : 1532016000000
             * todoList : [{"completeDate":null,"completeDateStr":"","content":"","date":1532016000000,"dateStr":"2018-07-20","id":73,"status":0,"title":"第一件未完成的事情","type":0,"userId":2}]
             */

            private long date;
            private List<TodoListBeanX> todoList;

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public List<TodoListBeanX> getTodoList() {
                return todoList;
            }

            public void setTodoList(List<TodoListBeanX> todoList) {
                this.todoList = todoList;
            }

            public static class TodoListBeanX {
                /**
                 * completeDate : null
                 * completeDateStr :
                 * content :
                 * date : 1532016000000
                 * dateStr : 2018-07-20
                 * id : 73
                 * status : 0
                 * title : 第一件未完成的事情
                 * type : 0
                 * userId : 2
                 */

                private Object completeDate;
                private String completeDateStr;
                private String content;
                private long   date;
                private String dateStr;
                private int    id;
                private int    status;
                private String title;
                private int    type;
                private int    userId;

                public Object getCompleteDate() {
                    return completeDate;
                }

                public void setCompleteDate(Object completeDate) {
                    this.completeDate = completeDate;
                }

                public String getCompleteDateStr() {
                    return completeDateStr;
                }

                public void setCompleteDateStr(String completeDateStr) {
                    this.completeDateStr = completeDateStr;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public String getDateStr() {
                    return dateStr;
                }

                public void setDateStr(String dateStr) {
                    this.dateStr = dateStr;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }
            }
        }
    }
}
