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

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;

    public WeatherPresenter(@NonNull WeatherContract.View view, String city, Context context) {
        mView = view;
        mCity = city;
        mContext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
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

                weatherlive = weatherLiveResult.getLiveResult();
                mView.updateLiveTextView(weatherlive.getReportTime() + "发布",
                        weatherlive.getWeather(),
                        weatherlive.getTemperature() + "°",
                        weatherlive.getWindDirection() + "风     " + weatherlive.getWindPower() + "级",
                        "湿度         " + weatherlive.getHumidity() + "%");
//                reporttime1.setText(weatherlive.getReportTime() + "发布");
//                weather.setText(weatherlive.getWeather());
//                Temperature.setText(weatherlive.getTemperature() + "°");
//                wind.setText(weatherlive.getWindDirection() + "风     " + weatherlive.getWindPower() + "级");
//                humidity.setText("湿度         " + weatherlive.getHumidity() + "%");
            }
//            else {
//                // 无数据返回
//                //ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
//            }
        }
//        else {
//            // 数据出错
//            //ToastUtil.showerror(WeatherSearchActivity.this, rCode);
//        }
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
                weatherforecast = weatherForecastResult.getForecastResult();
                forecastlist = weatherforecast.getWeatherForecast();
                fillforecast();

            }
//            else {
//                //ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
//            }
        }
//        else {
//            //ToastUtil.showerror(WeatherSearchActivity.this, rCode);
//        }
    }

    @Override
    public void searchForecastsWeather() {
        mquery = new WeatherSearchQuery(mCity, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch = new WeatherSearch(mContext);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    @Override
    public void searchLiveWeather() {
        mquery = new WeatherSearchQuery(mCity, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch = new WeatherSearch(mContext);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    private void fillforecast() {
        mView.updateForecastReportTextView(weatherforecast.getReportTime() + "发布");
        //reporttime2.setText(weatherforecast.getReportTime() + "发布");
        String forecast = "";
        for (int i = 0; i < forecastlist.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast = forecastlist.get(i);
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
        //forecasttv.setText(forecast);
        mView.updateForecastTextView(forecast);
    }

}
