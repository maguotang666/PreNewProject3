package com.example.administrator.prenewproject.utils;

import android.os.Environment;


import java.io.File;

public class FileUtil {

    private final String voiceAndImgPath;
    private final String downloadPath;//轨迹路径
    private String sdCardRootPath;
    private final String downloadsPath;//文件下载的路径
    private final String pickedPath;//时刻表图片保存路径

    public FileUtil() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdCardRootPath = Environment.getExternalStorageDirectory() + File.separator
                    + AContext.getApplication().getPackageName();
        } else {
            sdCardRootPath = AContext.getApplication().getFilesDir() + File.separator
                    + AContext.getApplication().getPackageName();
        }


        downloadsPath = sdCardRootPath + "/downloads";
        pickedPath = sdCardRootPath + "/download/ueditor/asp/upload/image";
        voiceAndImgPath = sdCardRootPath + "/xltRecord";
        downloadPath = sdCardRootPath + "/download";
    }

    public String getDownloadsPath() {
        return downloadsPath;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getPickedPath() {
        return pickedPath;
    }

    public String getVoiceAndImgPath() {
        return voiceAndImgPath;
    }

    //创建轨迹文本路径
    public File makDir(){
        File file = new File(downloadPath, "轨迹变化文本.txt");
        return file;
    };
//	//截串
//	public void getFileFolderTwo(String url) {
//		int lastIndex = url.lastIndexOf("/");
//		if (lastIndex > 0) {
//			destFileName = url.substring(url.lastIndexOf("/") + 1);
//		}
//		String sub = url.substring(0, lastIndex);
//
//		rootFile = sub.substring(sub.lastIndexOf("/"));
//		Log.e("TAG", "url-------" + url + ",destFileName---" + destFileName + ";rootFile---" + rootFile);
//	}

}
