package com.example.administrator.prenewproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.prenewproject.MyApplication;


/**
 * SharedPreferences
 */
public class SharedPreferencesUtils {
	// 名字
	public static String SP_NAME = "config";
	private static SharedPreferences sp;

	public static void saveBoolean(String key, boolean value) {
		if (sp == null) {
			sp = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, false);
	}

	public static void saveString(String key, String value) {
		if (sp == null) {
			Log.e("TAG","value---"+value);
		}
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static String getSting(String key) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getString(key, "");
	}
	public static void saveInt(String key, int value) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putInt(key, value).commit();
	}
	
	public static Integer getInt(String key) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getInt(key, -1);
	}
	
	public static void saveLong(String key, long value) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putLong(key, value).commit();
	}
	
	public static long getLong( String key) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getLong(key, -1);
	}
	public static void removeString(String key) {
		if (sp == null) {
			sp =  MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();
	}
}
