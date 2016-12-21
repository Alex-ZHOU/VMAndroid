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

package com.alex.vmandroid.display.weather;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alex.vmandroid.R;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.List;

public class WeatherPresenter implements WeatherContract.Presenter,
        WeatherSearch.OnWeatherSearchListener {

    private WeatherContract.View mView;

    private String mCity;

    private Context mContext;

    private WeatherSearchQuery mWeatherSearchQuery;

    private WeatherSearch mWeatherSearch;

    private LocalWeatherForecast mLocalWeatherForecast;

    private List<LocalDayWeatherForecast> mForecastList = null;

    public WeatherPresenter(@NonNull WeatherContract.View view, String city, Context context) {
        mView = view;
        mCity = city;
        mContext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgressDialog();
        this.searchForecastsWeather();
        this.searchLiveWeather();
    }

    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {

                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                mView.updateLiveTextView(weatherlive.getReportTime() + "发布",
                        weatherlive.getWeather(),
                        weatherlive.getTemperature() + "°",
                        weatherlive.getWindDirection() + "风     " + weatherlive.getWindPower() + "级",
                        "湿度         " + weatherlive.getHumidity() + "%");
            } else {
                mView.showToast(R.string.no_result);
            }
        } else {
            // 数据出错
            mView.showToast("Error:" + rCode);
        }
        mView.closeProgressDialog();
    }

    /**
     * 天气预报查询结果回调
     */
    @Override
    public void onWeatherForecastSearched(
            LocalWeatherForecastResult weatherForecastResult, int rCode) {
        if (rCode == 1000) {
            if (weatherForecastResult != null && weatherForecastResult.getForecastResult() != null
                    && weatherForecastResult.getForecastResult().getWeatherForecast() != null
                    && weatherForecastResult.getForecastResult().getWeatherForecast().size() > 0) {
                mLocalWeatherForecast = weatherForecastResult.getForecastResult();
                mForecastList = mLocalWeatherForecast.getWeatherForecast();
                fillForecast();

            } else {
                //ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
                mView.showToast(R.string.no_result);
            }
        } else {
            // 数据出错
            mView.showToast("Error:" + rCode);
        }
        mView.closeProgressDialog();
    }

    @Override
    public void searchForecastsWeather() {
        mWeatherSearchQuery = new WeatherSearchQuery(mCity, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mWeatherSearch = new WeatherSearch(mContext);
        mWeatherSearch.setOnWeatherSearchListener(this);
        mWeatherSearch.setQuery(mWeatherSearchQuery);
        mWeatherSearch.searchWeatherAsyn(); //异步搜索
    }

    @Override
    public void searchLiveWeather() {
        mWeatherSearchQuery = new WeatherSearchQuery(mCity, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mWeatherSearch = new WeatherSearch(mContext);
        mWeatherSearch.setOnWeatherSearchListener(this);
        mWeatherSearch.setQuery(mWeatherSearchQuery);
        mWeatherSearch.searchWeatherAsyn(); //异步搜索
    }

    private void fillForecast() {
        mView.updateForecastReportTextView(mLocalWeatherForecast.getReportTime() + "发布");
        String forecast = "";
        for (int i = 0; i < mForecastList.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast = mForecastList.get(i);
            String week = null;
            switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
                case 1:
                    week = "周一";
                    break;
                case 2:
                    week = "周二";
                    break;
                case 3:
                    week = "周三";
                    break;
                case 4:
                    week = "周四";
                    break;
                case 5:
                    week = "周五";
                    break;
                case 6:
                    week = "周六";
                    break;
                case 7:
                    week = "周日";
                    break;
                default:
                    break;
            }
            String temp = String.format("%-3s/%3s",
                    localdayweatherforecast.getDayTemp() + "°",
                    localdayweatherforecast.getNightTemp() + "°");
            String date = localdayweatherforecast.getDate();
            forecast += date + "  " + week + "                       " + temp + "\n\n";
        }
        mView.updateForecastTextView(forecast);
    }

}
