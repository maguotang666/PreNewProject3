package com.example.administrator.prenewproject.NetWork;

//网络接口
public class AppNetConfig {


    public  String DATA_URL = "https://www.wanandroid.com/";
    private AppNetConfig() {
    }

    private static AppNetConfig appNetConfig;

    public static AppNetConfig getInstance() {
        if (appNetConfig == null) {
            synchronized (AppNetConfig.class) {
                if (appNetConfig == null) {
                    appNetConfig = new AppNetConfig();
                }
            }
        }
        return appNetConfig;
    }

    public String Data_url_1="http://60.164.191.84:8067/I_IntelligentControl.ashx?";

    //banner轮播图
    public String DATA_BANNER=DATA_URL+"banner/json";

    public String DATA_HOME_LIST=DATA_URL+"article/list/";

    public String DATA_HOME_LIST2="/json";

    //知识体系列表
    public String DATA_KNOW=DATA_URL+"tree/json";

    //公众号导航栏
    public String DATA_PUB_NUM_TAB=DATA_URL+"wxarticle/chapters/json";

    //公众号列表1
    public String DATA_PUB_NUM_LIST1=DATA_URL+"wxarticle/list/";
    //公众号列表2
    public String DATA_PUB_NUM_LIST2="/json";

    //项目导航栏
    public String DATA_PROJECT_TAB=DATA_URL+"project/tree/json";

    //项目列表1
    public String DATA_PROJECT_LIST1=DATA_URL+"project/list/";

    //项目列表2
    public String DATA_PROJECT_LIST2="/json?cid=";


    public String DATA_NAVIGATION_URL=DATA_URL+"navi/json";

    //登录
    public String DATA_LOGIN=DATA_URL+"user/login";

    //热门搜索热词
    public String DATA_HOT_SEARCH=DATA_URL+"/hotkey/json";

    //搜索
    public String DATA_SEARCH=DATA_URL+"article/query/";

    //注册
    public String DATA_REGISTER=DATA_URL+"user/register";

    //收藏--列表
    public String DATA_COLLECT=DATA_URL+"lg/collect/list/";

    //收藏--添加
    public String DATA_ADD_COLLECT=DATA_URL+"lg/collect/";

    //收藏--删除
    public String DATA_UNCOLLECT=DATA_URL+"lg/uncollect_originId/";

    //我的收藏页面--删除
    public String DATA_MY_UNCOLLECT=DATA_URL+"lg/uncollect/";

    //返回TODO列表数据--未完成
    public String DATA_NOT_TODO_LIST=DATA_URL+"lg/todo/listnotdo/";

    public String DATA_NOT_AND_ALREADY_TODO_LIST="/json/";

    //返回TODO列表数据--已完成
    public String DATA_ALREADY_TODO_LIST=DATA_URL+"lg/todo/listdone/";

    //新增代办数据
    public String DATA_NEWPL_TODO=DATA_URL+"lg/todo/add/json";

    //未完成->已经完成 or 已经完成->未完成。
    public String DATA_TODO_FINISH_OR_NOFINISH=DATA_URL+"lg/todo/done/";

    //todo数据的删除
    public String DATA_TODO_DELECT=DATA_URL+"lg/todo/delete/";



}
