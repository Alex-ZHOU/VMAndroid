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

import android.util.Log;

import com.alex.vmandroid.R;

public class MainPresenter implements MainContract.MainPresenter {

    private final String TAG = MainPresenter.class.getName();
    private MainContract.MainView mMainView;

    private MainContract.RecordView mRecordView;

    private MainContract.DiscoverView mDiscoverView;

    private MainContract.MeView mMeView;

    private MainContract.UnLoginView mUnLoginView;

    private MainContract.LoginView mLoginView;


    public MainPresenter() {

    }


    public MainPresenter(MainContract.MeView view) {
        mMeView = view;
    }

    @Override
    public void start() {
        Log.d(TAG, "start");
    }

    @Override
    public void initMainView(MainContract.MainView mainView) {
        Log.d(TAG, "initMainView");
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void initRecordView(MainContract.RecordView recordView) {
        Log.d(TAG, "initRecordView");
        mRecordView = recordView;
        mRecordView.setPresenter(this);
    }


    @Override
    public void initDiscoverView(MainContract.DiscoverView discoverView) {
        Log.d(TAG, "initDiscoverView");
        mDiscoverView = discoverView;
        mDiscoverView.setPresenter(this);
    }

    @Override
    public void initMeView(MainContract.MeView meView) {
        Log.d(TAG, "initMeView");
        mMeView = meView;
        mMeView.setPresenter(this);
    }

    @Override
    public void initUnLoginView(MainContract.UnLoginView loginView) {
        mUnLoginView = loginView;
        mUnLoginView.setPresenter(this);
    }

    @Override
    public void initLoginView(MainContract.LoginView loginView) {
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }


    @Override
    public void onClick(int id) {
        switch (id) {
            //region : MeFragment 界面的点击事件
            case R.id.main_me_history_ll:
                mMeView.showHistoryActivity();
                break;
            case R.id.main_me_analysis_ll:
                mMeView.showAnalysisActivity();
                break;
            case R.id.main_me_map_setting_ll:
                mMeView.showMapSettingActivity();
                break;
            case R.id.main_me_offline_map_ll:
                mMeView.showOfflineMapActivity();
                break;
            case R.id.main_me_gadget_ll:
                mMeView.showGadgetActivity();
                break;
            //endregion
            case R.id.main_login_immediate_experience_btn:
                mUnLoginView.showMainFragment();
                break;
            case R.id.main_unlogin_login_btn:
                mUnLoginView.showLoginFragment();
                break;
            case R.id.main_login_login_btn:
                mLoginView.showMainFragment();
                break;
        }
    }
}
