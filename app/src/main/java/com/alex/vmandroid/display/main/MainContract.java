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

    public interface MainPresenter extends BaseContract.BasePresenter {

        void initMainView(MainView mainView);

        void initRecordView(RecordView recordView);

        void startRecord(Context context);

        void closeRecord();

        void searchWeatherRecord(Context context, String city);

        void initDiscoverView(DiscoverView discoverView);

        void initMeView(MeView meView);

        void initUnLoginView(UnLoginView loginView);

        void initLoginView(LoginView loginView);

        void onClick(int id);


    }

    public interface MainView extends BaseContract.BaseView<MainPresenter> {

    }

    public interface RecordView extends BaseContract.BaseView<MainPresenter> {

        /**
         * 跳转到显示历史记录界面
         */
        void showHistoryActivity();

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
        void updateRealTimeNoise(double d);

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
         * 登陆成功，跳转到主页界面
         */
        void showMainFragment();


    }


}
