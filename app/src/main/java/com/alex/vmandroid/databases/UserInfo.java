/*
 * Copyright 2016 Alex_ZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alex.vmandroid.databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

final public class UserInfo {

    public static final String TAG = UserInfo.class.getSimpleName();

    //public static String FILE_KEY = UserInfo.class.getSimpleName();

    /**
     * FILE_KEY 在系统中产生一个login的文件,用于保存用户数据
     */
    public static final String FILE_KEY = "VMAndroid_Login";

    /**
     * 声明一个SharedPreferences.Editor的变量,用户通过字段保存对应的值
     */
    private static SharedPreferences.Editor editor;

    /**
     * 声明一个SharedPreferences的变量,用于通过字段获取对应的值
     */
    private static SharedPreferences reader;

    /**
     * 用户密码保存的字段
     */
    public static final String PASSWORD = "Password";

    /**
     * 用户id保存的字段
     */
    public static final String USER_ID = "UserId";

    /**
     * 用户名保存字段
     */
    public static final String USER_NAME = "UserName";

    /**
     * 是否记住密码的状态保存字段
     */
    public static final String REMEMBER_PASSWORD = "RememberPassword";

    /**
     * 是否自动登录的状态保存字段
     */
    public static final String AUTO_LOGIN = "AutoLogin";

    /**
     * 百度推送的设备唯一识别id号
     */
    public static final String PUSH_TAG = "PushTAG";

    /**
     * 保存用户在数据库中对应的id
     *
     * @param context 上下文,一把都是ApplicationContext
     * @param id      用户的id号
     * @return 如果保存成功返回true, 失败返回false
     */
    public static boolean putUsrId(Context context, int id) {
        return putInt(context, USER_ID, id);
    }

    /**
     * 获取用户在数据库中对应的id
     *
     * @param context 上下文,一把都是ApplicationContext
     * @return 返回用户的id
     */
    public static int getUsrId(Context context) {
        return getInt(context, USER_ID);
    }


    public static boolean putPassword(Context context, String password) {
        return putString(context, PASSWORD, password);
    }

    public static String getPassword(Context context) {
        return getString(context, PASSWORD);
    }

    public static boolean putUserName(Context context, String userName) {
        return putString(context, USER_NAME, userName);
    }

    public static String getUserName(Context context) {
        return getString(context, USER_NAME);
    }

    /**
     * 默认的插入String类型的方法
     *
     * @param context ApplicationContext上下文
     * @param key     键
     * @param value   值
     * @return 返回提交成功或者失败
     */
    public static boolean putString(Context context, String key, String value) {
        initEditor(context);
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 默认的获取String类型的方法,省缺值默认设置为空
     *
     * @param context ApplicationContext上下文
     * @param key     键
     * @return 如果找不到则返回空, 找到就返回对应的值
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 默认的获取String类型的方法,可以设置省缺值
     *
     * @param context      ApplicationContext上下文
     * @param key          键
     * @param defaultValue 手动设置省缺值
     * @return 如果找不到则返回空, 找到就返回对应的值
     */
    public static String getString(Context context, String key, String defaultValue) {
        initReader(context);
        return reader.getString(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, Boolean value) {
        initEditor(context);
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean b) {
        initReader(context);
        return reader.getBoolean(key, b);
    }

    /**
     * 保存整形的数据
     *
     * @param context ApplicationContext上下文
     * @param key     键
     * @param value   值
     * @return 返回提交成功(true)或者失败(false)
     */
    public static boolean putInt(Context context, String key, int value) {
        initEditor(context);
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 返回整形的数据,如果获取不到就返回省却值-1
     *
     * @param context ApplicationContext上下文
     * @param key     键
     * @return 值
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * 返回整形的数据,可以手动设置返回的省却缺值
     *
     * @param context ApplicationContext上下文
     * @param key     键
     * @param b       省却值
     * @return 值
     */
    public static int getInt(Context context, String key, int b) {
        initReader(context);
        return reader.getInt(key, b);
    }

    /**
     * 初始化SharedPreferences的Editor
     *
     * @param context ApplicationContext
     */
    @SuppressLint("CommitPrefEdits")
    private static void initEditor(Context context) {
        if (editor == null) {
            editor = context.getSharedPreferences(FILE_KEY,
                    Context.MODE_PRIVATE).edit();
        }
    }

    /**
     * 初始化SharedPreferences的Reader
     *
     * @param context ApplicationContext
     */
    private static void initReader(Context context) {
        if (reader == null) {
            reader = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        }
    }
}