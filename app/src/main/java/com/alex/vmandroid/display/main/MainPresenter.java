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
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.alex.businesses.DownloadPic;
import com.alex.businesses.GetBaseInfoBiz;
import com.alex.businesses.LoginBiz;
import com.alex.businesses.SignUpBiz;
import com.alex.style.drawable.CircleImageDrawable;
import com.alex.utils.AppLog;
import com.alex.utils.ServiceCheck;
import com.alex.vmandroid.display.voice.AudioRecordDemo;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.entities.BaseInfo;
import com.alex.vmandroid.entities.Login;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.alex.vmandroid.services.RecordDBService.RecordDBServiceName;


public class MainPresenter implements MainContract.MainPresenter, AudioRecordDemo.Listener,
        WeatherSearch.OnWeatherSearchListener {

    private final String TAG = MainPresenter.class.getName();


    private MainContract.MainView mMainView;

    private MainContract.RecordView mRecordView;

    private MainContract.DiscoverView mDiscoverView;

    private MainContract.MeView mMeView;

    private MainContract.UnLoginView mUnLoginView;

    private MainContract.SignUpView mSignUpView;

    private MainContract.LoginView mLoginView;

    private Context mContext;

    private Handler handle = new Handler();

    public MainPresenter() {

    }


    public MainPresenter(MainContract.MeView view) {
        mMeView = view;
    }

    @Override
    public void start() {
        AppLog.debug(TAG, "start");
    }

    @Override
    public void setApplicationContext(Context context) {
        mContext = context;
    }

    @Override
    public void initMainView(MainContract.MainView mainView) {
        AppLog.debug(TAG, "initMainView");
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void initRecordView(MainContract.RecordView recordView) {
        Log.d(TAG, "initRecordView");
        mRecordView = recordView;
        mRecordView.setPresenter(this);
    }

    private AudioRecordDemo recordDemo;

    @Override
    public void startRecord(Context context) {
        new GetBaseInfoBiz().get(new GetBaseInfoBiz.Listener() {
            @Override
            public void succeed(final BaseInfo baseInfo) {
                UserInfo.putString(mContext, "Nickname", baseInfo.getNickname());
                UserInfo.putUserName(mContext, baseInfo.getUsername());
                UserInfo.putInt(mContext, "AverageDb", baseInfo.getAverageDb());
                UserInfo.putInt(mContext, "MaxDb", baseInfo.getMaxDb());
                UserInfo.putInt(mContext, "MinDb", baseInfo.getMinDb());
                UserInfo.putInt(mContext, "RecordTimes", baseInfo.getRecordTimes());
                UserInfo.putInt(mContext, "RecordMinter", (int) baseInfo.getRecordMinter());
                UserInfo.putInt(mContext, "HeadProtrait", (int) baseInfo.getHeadProtrait());
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        mRecordView.setRecordMinter(String.valueOf((int) baseInfo.getRecordMinter()));
                        mRecordView.setRecordTimes(String.valueOf(baseInfo.getRecordTimes()));
                        mRecordView.setAverageDb(String.valueOf(baseInfo.getAverageDb()));
                        mRecordView.setMaxMin(baseInfo.getMaxDb() + "/" + baseInfo.getMinDb());
                    }
                });

            }

            @Override
            public void failed() {

            }
        }, UserInfo.getUsrId(mContext));
        recordDemo = new AudioRecordDemo(this);
        if (ServiceCheck.isServiceWork(mContext, RecordDBServiceName)) {
            AppLog.info(TAG, "startRecord: service is running");
        } else {
            recordDemo.getNoiseLevel();
        }
    }

    @Override
    public void closeRecord() {
        recordDemo.close();
    }

    @Override
    public void searchWeatherRecord(Context context, String city) {
        WeatherSearch weatherSearch = new WeatherSearch(context);
        weatherSearch.setQuery(new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE));
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.searchWeatherAsyn();
    }


    @Override
    public void initDiscoverView(MainContract.DiscoverView discoverView) {
        Log.d(TAG, "initDiscoverView");
        mDiscoverView = discoverView;
        mDiscoverView.setPresenter(this);
    }

    @Override
    public void initSignUpView(MainContract.SignUpView signUpView) {
        Log.d(TAG, "initSignUpView");
        mSignUpView = signUpView;
        mSignUpView.setPresenter(this);
    }

    @Override
    public void initMeView(MainContract.MeView meView) {
        Log.d(TAG, "initMeView");
        mMeView = meView;
        mMeView.setPresenter(this);
    }

    /**
     * 设置头像
     *
     * @param iv 显示头像的ImageView
     */
    @Override
    public void setHeadPortrait(final ImageView iv) {
        new DownloadPic().getById(UserInfo.getInt(mContext, "HeadProtrait"), new DownloadPic.Listener() {
            @Override
            public void succeed(final Bitmap bm) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageDrawable(new CircleImageDrawable(bm));
                    }
                });
            }

            @Override
            public void failed() {

            }
        }, mContext);

        String str = UserInfo.getString(mContext, "Nickname");
        mMeView.setUserNickName(str);
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
    public void onClick(int id, int tag) {

        switch (tag) {
            //region : RecordFragment 界面的点击事件
            case MainContract.RECORD_TAG:
                switch (id) {
                    case R.id.main_record_title_bar_record_begin_iv:
                        mRecordView.showRecordDBActivity();
                        break;

                    case R.id.main_record_total_ll:
                        mRecordView.showHistoryActivity();
                        break;

                    case R.id.main_record_location_weather_ll:
                        Log.i(TAG, "onClick: main_record_location_weather_ll");
                        mRecordView.showLocationWeatherActivity();
                        break;
                }
                break;
            //endregion
            //region : DiscoverFragment 界面的点击事件
            case MainContract.DISCOVER_TAG:
                switch (id) {
                    case R.id.main_discover_bar_ll:
                        mDiscoverView.showStoreListActivity("bar");
                        break;
                    case R.id.main_discover_ktv_ll:
                        mDiscoverView.showStoreListActivity("ktv");
                        break;
                    case R.id.main_discover_restaurant_ll:
                        mDiscoverView.showStoreListActivity("restaurant");
                        break;
                    case R.id.main_discover_other_ll:
                        mDiscoverView.showStoreListActivity("other");
                        break;
                }
                break;
            //endregion
            //region : MeFragment 界面的点击事件
            case MainContract.ME_TAG:
                switch (id) {
                    case R.id.main_me_title_bar_setting_iv:
                        mMeView.showSettingActivity();
                        break;
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
                    case R.id.main_me_feedback_ll:
                        mMeView.showFeedbackActivity();
                        break;
                    case R.id.main_me_exit_login_ll:
                        UserInfo.putUserName(mContext, null);
                        mMeView.applicationExit();
                        break;
                }
                break;
            //endregion
        }

        switch (id) {
            case R.id.main_login_immediate_experience_btn:
                mUnLoginView.showMainFragment();
                break;
            case R.id.main_unlogin_login_btn:
                mUnLoginView.showLoginFragment();
                break;
            case R.id.main_sign_up_btn:
                this.signUp();
                break;
            case R.id.main_login_login_btn:
                this.login();
                break;
        }
    }

    private void signUp() {

        final String username = mSignUpView.getUsername();

        final String password = mSignUpView.getPassword();

        final String passwordAgain = mSignUpView.getPasswordAgain();

        if (password.equals(passwordAgain)){

            new SignUpBiz().signUP(username, password, new SignUpBiz.Listener() {
                @Override
                public void succeed(Login.User user) {
                    UserInfo.putUsrId(mContext, user.getUserId());
                    UserInfo.putUserName(mContext, user.getUserName());
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            mSignUpView.showToast(mContext.getResources().getString(R.string.sign_up_succeed));
                            mSignUpView.showMainFragment();
                        }
                    });
                }

                @Override
                public void failed(int i) {
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            mSignUpView.showToast(mContext.getResources().getString(R.string.account_exit));
                        }
                    });
                }
            });


        }else{
            mSignUpView.showToast(mContext.getResources().getString(R.string.different_password));
            mSignUpView.clearPassword();
        }


    }

    private void login() {

        final String username = mLoginView.getUsername();

        final String password = mLoginView.getPassword();


        // 观察者
        Subscriber observer = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                mLoginView.showToast(mContext.getResources().getString(R.string.login_success));
                mLoginView.showMainFragment();

            }

            @Override
            public void onError(Throwable e) {
                AppLog.error("Login error");
                mLoginView.showToast(mContext.getResources().getString(R.string.unknown_error));
            }

            @Override
            public void onNext(Integer str) {
                switch (str) {
                    case LoginBiz.PASSWORD_WRONG:
                        mLoginView.showToast(mContext.getResources().getString(R.string.login_password_wrong));
                        break;
                    case LoginBiz.ACCOUNT_NO_EXITED:
                        mLoginView.showToast(mContext.getResources().getString(R.string.login_account_no_exit));
                        break;
                    case LoginBiz.UNKNOWN_WRONG:
                        mLoginView.showToast(mContext.getResources().getString(R.string.unknown_error));
                        break;
                }
                Log.i(TAG, "onNext");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(final Subscriber<? super Integer> subscriber) {

                LoginBiz.Listener listener = new LoginBiz.Listener() {
                    @Override
                    public void succeed(Login.User user) {
                        UserInfo.putUsrId(mContext, user.getUserId());
                        UserInfo.putUserName(mContext, user.getUserName());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void failed(int i) {
                        subscriber.onNext(i);
                    }
                };

                LoginBiz.login(username, password, listener);

            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);

    }


    /**
     * 实况天气返回查询
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        LocalWeatherLive localWeatherLive = localWeatherLiveResult.getLiveResult();

        mRecordView.setWeather(localWeatherLive.getWeather() + " " + localWeatherLive.getTemperature() + "°");
    }

    /**
     * 预告天气查询返回
     */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }


    @Override
    public void back(final double value) {

        handle.post(new Runnable() {
            @Override
            public void run() {
                mRecordView.updateRealTimeNoise((int) value);
            }
        });

    }
}
