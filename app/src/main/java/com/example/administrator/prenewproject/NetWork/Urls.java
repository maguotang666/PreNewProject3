package com.example.administrator.prenewproject.NetWork;

import android.text.TextUtils;

import com.example.administrator.prenewproject.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Urls {

    public static final String URLS_IP = "urls_ip";
    public static final String URLS_PORT = "urls_port";

    private Urls() {
    }

    private static Urls url;

    public static Urls getInstance() {
        if (url == null) {
            synchronized (Urls.class) {
                if (url == null) {
                    url = new Urls();
                }
            }
        }
        return url;
    }

    public String PORT = getprot();// "9067";// "8067";// "8002";//

    public String ImgPort = getImgProt();

    public String BASE_IP = getip();

    // 设置ip 地址
    public String getip() {
        String ip = SharedPreferencesUtils.getSting(URLS_IP);
//        if (!TextUtils.isEmpty(ip)) {
//            return ip;
//        } else {

        return "60.164.191.84";//机务段专用统一IP
//        }
    }

    //设置端口号

    /**
     * 接口端口
     *
     * @return
     */
    public String getprot() {
        String prot = SharedPreferencesUtils.getSting(URLS_PORT);

        return "8030";// */ "9067";
//        }
    }

    /**
     * 图片端口
     *
     * @return
     */
    public String getImgProt() {
        String prot = SharedPreferencesUtils.getSting("imgPort");
        if (!TextUtils.isEmpty(prot)) {
            return prot;
        }
        return "8066";// */"9066";
    }


}
