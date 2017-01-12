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
package com.alex.vmandroid.display.main;

import android.content.Context;

import com.alex.vmandroid.base.BaseContract;

public class MainContract {

    public static final int RECORD_TAG = 1;

    public static final int DISCOVER_TAG = 2;

    public static final int ME_TAG = 3;

    public interface MainPresenter extends BaseContract.BasePresenter {

        void setApplicationContext(Context context);

        void initMainView(MainView mainView);

        void initRecordView(RecordView recordView);

        void startRecord(Context context);

        void closeRecord();

        void searchWeatherRecord(Context context, String city);

        void initDiscoverView(DiscoverView discoverView);

        void initMeView(MeView meView);

        void initUnLoginView(UnLoginView loginView);

        void initLoginView(LoginView loginView);

        void onClick(int id, int tag);


    }

    public interface MainView extends BaseContract.BaseView<MainPresenter> {

    }

    public interface RecordView extends BaseContract.BaseView<MainPresenter> {

        /**
         * 跳转到记录噪声分贝界面
         */
        void showRecordDBActivity();

        /**
         * 跳转到显示历史记录界面
         */
        void showHistoryActivity();

        /**
         * 设置记录的总分钟数
         *
         * @param str 分钟数
         */
        void setRecordMinter(String str);

        /**
         * 设置记录的次数
         *
         * @param str 记录的次数
         */
        void setRecordTimes(String str);

        /**
         * 设置平均数
         *
         * @param str 平均数
         */
        void setAverageDb(String str);

        /**
         * 设置最大最小值
         *
         * @param str 最大最小值
         */
        void setMaxMin(String str);

        /**
         * 设置天气显示
         *
         * @param str 天气信息
         */
        void setWeather(String str);

        /**
         * 更新实时的噪声数值
         *
         * @param d 实时噪声数值
         */
        void updateRealTimeNoise(int d);

        /**
         * 显示详细当地天气信息
         */
        void showLocationWeatherActivity();

    }

    public interface DiscoverView extends BaseContract.BaseView<MainPresenter> {

    }

    public interface MeView extends BaseContract.BaseView<MainPresenter> {

        /**
         * 跳转到显示历史记录界面
         */
        void showHistoryActivity();

        /**
         * 跳转到显示分析界面
         */
        void showAnalysisActivity();

        /**
         * 跳转到显示地图参数设置界面
         */
        void showMapSettingActivity();

        /**
         * 跳转到离线地图下载界面
         */
        void showOfflineMapActivity();

        /**
         * 跳转到小工具选择界面
         */
        void showGadgetActivity();
    }

    public interface UnLoginView extends BaseContract.BaseView<MainPresenter> {
        void showMainFragment();

        void showLoginFragment();
    }

    public interface LoginView extends BaseContract.BaseView<MainPresenter> {

        /**
         * 显示toast 提示
         * @param str 显示的提示信息
         */
        void showToast(String str);

        /**
         * 登陆成功，跳转到主页界面
         */
        void showMainFragment();

        /**
         * 获取用户名
         *
         * @return 用户名
         */
        String getUsername();

        /**
         * 获取用户密码
         *
         * @return 用户密码
         */
        String getPassword();
    }


}
