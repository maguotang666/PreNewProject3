package com.example.administrator.prenewproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.prenewproject.MyApplication;
import com.example.administrator.prenewproject.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 全能工具类
 */

public class CommonUtils {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    public static final String SP_FontScale="字体大小调整";
    private CustomProgressDialog dialog;
    /**
     * 网络 工具 网络有无 网络类型 判断 等
     */
    public static ConnectivityManager connectivityManager;
    private static String locationStr;

    private static CommonUtils commonUtils;

    //双重锁的懒汉模式
    public static synchronized CommonUtils getInstance() {
        if (commonUtils == null) {
            synchronized (CommonUtils.class) {
                if (commonUtils == null) {
                    commonUtils = new CommonUtils();
                }
            }
        }
        return commonUtils;
    }


    public static String getStr(String checkStr) {

        String result;

        if (null == checkStr) {

            result = "";
        } else {

            result = checkStr;
        }

        return result;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 将字节数组转换为UTF-8字符串
     *
     * @param source 字节数据源
     * @return String UTF-8字符串
     * @throws UnsupportedEncodingException
     */
    public static String toUTF8Str(byte[] source) throws Exception {

        if (source != null) {

            try {

                return new String(source, "UTF-8");

            } catch (UnsupportedEncodingException e) {

                throw new Exception("Unsupported UTF8 Encoding Exception" + e);

            }

        }

        return null;

    }

    public static String toStr(byte[] source, String encoding) throws Exception {

        if (source != null) {

            try {

                return new String(source, encoding);

            } catch (UnsupportedEncodingException e) {

                throw new Exception("Unsupported Encoding Exception" + e);

            }

        }

        return null;

    }





    public void showDialog(Context context){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }

        dialog = new CustomProgressDialog(context, "正在加载...", R.drawable.loading);
        dialog.show();
    }


    public void hindDialog(){

        if (dialog==null){
            return;
        }
        if (dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }

    }



    /**
     * 根据给定规则拆分字符串
     *
     * @param source 待拆分字符串
     * @param split  拆分规则
     */
    public static String[] split(String source, String split) {

        if (isNull(source))
            return null;

        if (isNull(split))
            return new String[]{source};

        Vector<String> vector = new Vector<String>();

        int startIndex = 0;

        int endIndex = -1;

        while (true) {

            if ((endIndex = source.indexOf(split, startIndex)) != -1) {

                vector.add(source.substring(startIndex, endIndex));

                startIndex = endIndex + split.length();

            } else {

                if (startIndex <= source.length()) {

                    vector.add(source.substring(startIndex));

                }

                break;
            }

        }

        return vector.toArray(new String[vector.size()]);

    }

    /**
     * 判断字符串是否为空
     *
     * @param checkStr 被验证的字符串
     * @return boolean 如果为空,返回true,否则,返回false
     */
    public static boolean isNull(String checkStr) {

        boolean result = false;

        if (null == checkStr) {

            result = true;
        } else {
            if (checkStr.length() == 0) {

                result = true;
            }
        }
        return result;
    }

    /**
     * 将数字转换为两位数
     *
     * @param count
     * @return
     */
    public static String getTwoString(int count) {
        String time = "";
        if (count < 10) {
            time = "0" + count;
        } else {
            time = "" + count;
        }
        return time;
    }

    /*
    从路径中截取文件名
    比如:/data/data/com.xlt.securitymanagement/files/xltRecord/00001_11:25:48.amr
    截取得到:00001_11:25:48.amr
     */
    public static String getFileName(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }

    }

    public static String getLastTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

        long time = System.currentTimeMillis();

        if (0 == time)
            return "";

        String text = mDateFormat.format(new Date(time));

        mDateFormat = null;

        return text;
    }

    /***
     * 保留两位小数
     *
     * @param value
     * @return
     */
    public static String getDecimal(float value) {

        String result = "";

        try {

            DecimalFormat decimal = new DecimalFormat("##0.00");
            result = decimal.format(value);

        } catch (Exception ex) {
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /***
     * 保留两位小数
     *
     * @param value
     * @return
     */
    public static String getDecimal(String value) {

        String result = "";

        try {

            double tempValue = Double.parseDouble(value);

            DecimalFormat decimal = new DecimalFormat("##0.00");
            result = decimal.format(tempValue);

        } catch (Exception ex) {
            ex.printStackTrace();
            result = "";
        }

        return result;
    }

    /**
     * 是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isSpechars(String str) {

        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";

        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /***
     * 是否是数字字符
     *
     * @param str
     * @return
     */
    public static boolean isNumChar(String str) {

        String regEx = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /***
     * 空格
     *
     * @param str
     * @return
     */
    public static boolean isSpace(String str) {

        String regEx = "\\s";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }

    public static boolean isMobileNO(String mobiles) {

//        Pattern p = Pattern
//                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
//        Matcher m = p.matcher(mobiles);
//
//        return m.matches();

        if (CommonUtils.isNull(mobiles))
            return false;

        if (mobiles.length() == 11) {

            return true;
        }

        return false;
    }

    public static String getImgUrl(String accountId) {

        StringBuffer sb = new StringBuffer();

        try {

            sb.append("http://www.niuguwang.com/SaveYieldPhoto/");
            sb.append(accountId);
            sb.append(".png");
            sb.append("?");
            sb.append("i=");
            sb.append(System.currentTimeMillis());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

    public static String ToDBC(String input) {

        char[] c = input.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }

        return new String(c);
    }

    public static String StringFilter(String str) {

        Matcher matcher = null;

        try {

            str = str.replaceAll("【", "[").replaceAll("】", "]")
                    .replaceAll("！", "!");// 替换中文标号
            String regEx = "[『』]"; // 清除掉特殊字符
            Pattern p = Pattern.compile(regEx);
            matcher = p.matcher(str);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return matcher.replaceAll("").trim();
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content) {

        if (CommonUtils.isNull(content)) {
            return;
        }

        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) MyApplication.mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {

        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /***
     * 获取sdk版本
     *
     * @return
     */
    public static int getSDKVersion() {

        int version = 0;

        try {

            version = android.os.Build.VERSION.SDK_INT;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }


    /***
     * 隐藏键盘
     *
     * @param
     * @param editText
     */
    public static void hideInputManager(EditText editText) {

        try {

            InputMethodManager imm = (InputMethodManager) MyApplication.mContext.getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getSystemChinaTime() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    //将时间转化为字符形式

    public static String getSystemTime() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String getSystemTimeMin() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String getYearAndMonthTime() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String getTimeStr() {
        try {

            SimpleDateFormat df = new SimpleDateFormat("HHmmss");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /***
     * wifi网络
     *
     * @param activity
     * @return
     */
    public static boolean isWifi(Activity activity) {

        if (null == activity)
            return false;

        if (activity.isFinishing())
            return false;

        try {

            //获取当前网络
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = cm.getActiveNetworkInfo();

            //没有可用网络
            if (info == null || !info.isAvailable())
                return false;

            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                return true;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return false;
    }

    /***
     * 是否联网
     *
     * @param activity
     * @return
     */
    public static boolean isNetworkConnected(Activity activity) {

        if (null == activity) {
            return true;
        }

        if (activity.isFinishing()) {
            return true;
        }

        try {

            //获取当前网络
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = cm.getActiveNetworkInfo();

            //没有可用网络
            if (info == null || !info.isAvailable()) {
                return false;
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return true;
    }

    /**
     * 返回资源文件中指定colorId对应的颜色值
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * 返回当前应用的context实例 （返回的就是myApplication实例）
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.mContext;
    }

    public static boolean isPermissionGranted(
            Activity activity, String permissionName, int questCode) {
//        boolean isPermission = false;
        //6.0以下系统，取消请求权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    questCode);
            return false;
        } else {
            return true;
        }

    }

    /**
     * 判断服务是否在运行中
     *
     * @param context     即为Context对象
     * @param serviceName 即为Service的全名
     * @return 是否在运行中
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (!TextUtils.isEmpty(serviceName) && context != null) {
            ActivityManager activityManager
                    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfoList
                    = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(100);
            for (Iterator<ActivityManager.RunningServiceInfo> iterator = runningServiceInfoList.iterator(); iterator.hasNext(); ) {
                ActivityManager.RunningServiceInfo runningServiceInfo = (ActivityManager.RunningServiceInfo) iterator.next();
                if (serviceName.equals(runningServiceInfo.service.getClassName().toString())) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }


    //检测系统通知权限是否开启
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    //解决软键盘和屏幕的冲突---在透明状态栏情况下2
    public static void onMeasureViewHight(Activity activity, final View adjustView) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adjustView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, heightDifference);//设置ScrollView的marginBottom的值为软键盘占有的高度即可
                adjustView.requestLayout();
            }
        });
    }

    // 判断是否 有网
    public static boolean isHasNet(Context context) {
        if (context instanceof Activity) {
            context = context.getApplicationContext();
        }

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        if (networkInfo.length > 0) {
            for (int i = 0; i < networkInfo.length; i++) {
                if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * json解析
     *
     * @param resultStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getData(String resultStr, Class<T> cls) {

        if (CommonUtils.isNull(resultStr)){
            return null;
        }

        T object = null;

        try {
            Gson gson = new Gson();
            object = gson.fromJson(resultStr, cls);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("解析数据出错");
        }
        return object;
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void addFile(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //添加水印文字
    public static Bitmap getWatermarkText(Bitmap bmp, String botTetx, Context context) {

        Bitmap textBitmap1 = ImageUtil.drawTextToLeftTop(context, bmp, CommonUtils.getSystemAllTime(), 5, Color.YELLOW, 3, 3);
        Bitmap textBitmap2 = ImageUtil.drawTextToLeftBottom(context, textBitmap1, botTetx, 5, Color.YELLOW, 3, 10);
//        Bitmap textBitmap4 = ImageUtil.drawTextToLeftTop(this, textBitmap2, , 5, Color.WHITE, 3, 26);

        return textBitmap2;
    }

    /***
     * 得到系统全部时间
     *
     * @return
     */
    public static String getSystemAllTime() {

        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = new Date();

            String dateStr = df.format(date);

            date = null;
            df = null;

            return dateStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }



    public static String stringConversionLong(String dataTime) {

        String relieveTime = "";

        try {
            int beginInd = dataTime.lastIndexOf("(");
            int endInd = dataTime.lastIndexOf(")");
            String preTime = dataTime.substring(beginInd + 1, endInd);
            long DataTime = Long.valueOf(preTime);
            relieveTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataTime);
        } catch (Exception e) {

        }


        return relieveTime;
    }

    /**
     * 判断本应用是否存活
     * 如果需要判断本应用是否在后台还是前台用getRunningTask
     */
    public static boolean isAPPALive(Context mContext, String packageName) {
        boolean isAPPRunning = false;
        // 获取activity管理对象
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有正在运行的app
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        // 遍历，进程名即包名
        for (ActivityManager.RunningAppProcessInfo appInfo : appProcessInfoList) {
            if (packageName.equals(appInfo.processName)) {
                isAPPRunning = true;
                break;
            }
        }
        return isAPPRunning;
    }


    public void initSwipeRefresh(SwipeRefreshLayout refreshLayout) {

        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        refreshLayout.setProgressViewOffset(true, 50, 100);

        // 设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);

        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
//        refreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
        refreshLayout.setColorSchemeResources(R.color.line_bg_color);

        // 通过 setEnabled(false) 禁用下拉刷新
        refreshLayout.setEnabled(true);

        // 设定下拉圆圈的背景
        refreshLayout.setProgressBackgroundColor(R.color.white_color);
//        refreshLayout.setProgressBackgroundColor(R.color.colorAccent);

    }


}
