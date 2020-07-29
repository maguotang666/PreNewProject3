package com.example.administrator.prenewproject.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     * 获取指定日期time值
     *
     * @param date
     * @return
     */
    public static long getTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date parse = sdf.parse(date);
            return parse.getTime();
        } catch (ParseException e) {
            LogUtils.e("date=" + date + ";" + e.getMessage());
            return 0;
        }
    }

    /**
     * 获取指定格式当前时间字符串
     *
     * @param format
     * @param date
     * @return
     */
    public static String getCurrentTime(String format, int date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 电子报单查询格式 yyyy-MM-dd-HH:mm:ss
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHourM(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault());
            Date currentTime = sdf.parse(date);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // System.out.println(currentTime);
            return df.format(currentTime);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 电子报单查询格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHourM1(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date currentTime = sdf.parse(date);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // System.out.println(currentTime);
            return df.format(currentTime);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime1(long time) {
        if (time != 0) {
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return formatTime.format(time);
        } else {
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeYear(long time) {
        if (time != 0) {
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
            return formatTime.format(time);
        } else {
            return "";
        }
    }

    //考试时间
    public static String formatTimeYear1(long time) {
        if (time != 0) {
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return formatTime.format(time);
        } else {
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeMonth(long time) {
        if (time != 0) {
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM");
            return formatTime.format(time);
        } else {
            return "";
        }
    }

    /**
     * 获取当前日期前一天
     *
     * @param format
     * @return
     */
    public static String getBeforeCurrentTime(String format) {
        Calendar instance1 = Calendar.getInstance(Locale.getDefault());

        System.out.println(instance1.get(Calendar.YEAR));
        System.out.println(instance1.get(Calendar.MONTH) + 1);
        instance1.set(Calendar.YEAR, instance1.get(Calendar.DAY_OF_MONTH));
        instance1.set(Calendar.MONTH, instance1.get(Calendar.DAY_OF_MONTH));
        instance1.set(Calendar.DAY_OF_MONTH, instance1.get(Calendar.DAY_OF_MONTH) - 1);
        System.out.println(instance1.get(Calendar.DAY_OF_MONTH));
        @SuppressWarnings("deprecation")
        Date date = new Date(instance1.get(Calendar.YEAR), instance1.get(Calendar.MONTH),
                instance1.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        System.out.println(currentTime);
        return currentTime;
    }

    /**
     * @param date
     * @return
     */
    public static String getArgsTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    /**
     * 获取当前日期的格式化字符串
     *
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 获取 日期
     */

    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 获取日期格式 2013年9月3日 14:44
     */
    public static String getCurrentTime_1() {
        return getCurrentTime("yyyy年MM月dd日HH:mm");
    }

    public static String getCurrentTime_2() {
        return getCurrentTime("yyyy年MM月dd日");
    }

    /**
     * 比较时间 大小 1 dt1>dt2 -1 dt1<dt2 0 相等
     */

    public static boolean compareDate(Date dt1, Date dt2) {

        // LogUtils.i(dt1.getTime()+"");
        // LogUtils.i(dt2.getTime()+"");
        if (dt1.getTime() >= dt2.getTime()) {
            // dt1 日期更大 2014 》= 2013
            LogUtils.i("大于");
            return true;
        } else {
            LogUtils.i("小于");
            return false;
        }

        // if (dt1.getTime() > dt2.getTime()) {
        // System.out.println("dt1 在dt2前");
        // return 1;
        // } else if (dt1.getTime() < dt2.getTime()) {
        // System.out.println("dt1在dt2后");
        // return -1;
        // } else {// 相等
        // return 0;
        // }
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date 格式 必须严格 按照： yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getIntercept(String time) {
        String t = time.substring(time.length() - 15, time.length() - 2);
        return DateUtils.formatTime(Long.parseLong(t));
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time) {
        if (time != 0) {
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
            return formatTime.format(time);
        } else {
            return "";
        }
    }

    //比较前后时间差
    public static boolean CompareTimeDiff(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");

        try {

            if (TextUtils.isEmpty(startTime)) {
                startTime = SharedPreferencesUtils.getSting("startTalkTime");
            }


            Date d1 = df.parse(endTime);

            Date d2 = df.parse(startTime);

            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

//            long days = diff / (1000 * 60 * 60 * 24);


//            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

//            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            if (diff / 1000 > 60) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //比较前后时间差(分钟)
    public static long CompareTimeMin(String startTime, long millseconds) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long preMinutes = -1;
        try {

            Date d2 = df.parse(startTime);
            long millseconds2 = d2.getTime();
            long diff = millseconds - millseconds2; //这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);


            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

            preMinutes = diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return preMinutes;
    }


}
