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
package com.alex.utils;

public class URLs {

    // 家里路由固定地址
    private static final String LOCATION_SERVER = "http://192.168.0.144:8080/VMServer/";

    // 宿舍路由固定地址
    private static final String DORMITORY_SERVER = "http://192.168.1.109:8080/VMServer/";

    // Using address.
    private static final String SERVER_ADDRESS = DORMITORY_SERVER;

    /**
     * 登陆访问的地址
     */
    public static final String URL_LOGIN = SERVER_ADDRESS + "Android_Login";

    /**
     * 上传噪声记录的地址
     */
    public static final String URL_RECORD_DB = SERVER_ADDRESS + "Android_RecordDB";

    /**
     * 获取基础信息的地址
     */
    public static final String URL_GET_BASE_INFO = SERVER_ADDRESS + "Android_GetBaseInfo";

    /**
     * 获取服务器分析返回数据的地址
     */
    public static final String URL_Analysis = SERVER_ADDRESS + "Android_Analysis";

    /**
     * 获取历史记录信息的地址
     */
    public static final String URL_History = SERVER_ADDRESS + "Android_History";

    /**
     * 获取广告栏的基本信息
     */
    public static final String URL_ADVERTISING_COLUMN = SERVER_ADDRESS + "Android_AdvertisingColumn";

    /**
     * 获取图片的地址
     */
    public static final String URL_GET_PIC = SERVER_ADDRESS + "Android_GetPic";

    /**
     * 通过广告id获取店铺的基本信息地址
     */
    public static final String URL_GET_STORE_INFO = SERVER_ADDRESS + "Android_StroeInfo";

    /**
     * 获取商家声音记录的地址
     */
    public static final String URL_GET_STORE_RECORD_DB_LIST = SERVER_ADDRESS + "Android_StoreRecordDbList";

    /**
     * 商家记录上传
     */
    public static final String URL_STORE_DB_RECORD = SERVER_ADDRESS + "Android_StoreDbRecord";

    /**
     * 查询商家列表
     */
    public static final String URL_STORE_LIST = SERVER_ADDRESS + "Android_StoreList";

    /**
     * 分享出来的信息
     */
    public static final String URL_SHARE_RECORD = SERVER_ADDRESS + "Android_ShareRecord";

    /**
     * 注册
     */
    public static final String URL_SIGN_UP = SERVER_ADDRESS + "Android_SignUp";

    /**
     * 修改昵称
     */
    public static final String URL_CHANG_NICKNAME = SERVER_ADDRESS + "Android_ChangNickname";


    public static final String URL_FEEDBACK = SERVER_ADDRESS + "Android_Feedback";
}
