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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;


public class LocationWeatherFragment extends BaseFragment implements LocationWeatherContract.View {

    private static String mCity;

    private LocationWeatherContract.Presenter mPresenter;

    private TextView forecasttv;
    private TextView reporttime1;
    private TextView reporttime2;
    private TextView weather;
    private TextView Temperature;
    private TextView wind;
    private TextView humidity;


    public static LocationWeatherFragment newInstance(String city) {
        mCity = city;
        return new LocationWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_weather, container, false);

        TextView city = (TextView) view.findViewById(R.id.city);
        city.setText(mCity);
        forecasttv = (TextView) view.findViewById(R.id.forecast);
        reporttime1 = (TextView) view.findViewById(R.id.reporttime1);
        reporttime2 = (TextView) view.findViewById(R.id.reporttime2);
        weather = (TextView) view.findViewById(R.id.weather);
        Temperature = (TextView) view.findViewById(R.id.temp);
        wind = (TextView) view.findViewById(R.id.wind);
        humidity = (TextView) view.findViewById(R.id.humidity);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void setPresenter(LocationWeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateLiveTextView(String reportTime, String weather, String temperature, String wind, String humidity) {
        reporttime1.setText(reportTime);
        this.weather.setText(weather);
        Temperature.setText(temperature);
        this.wind.setText(wind);
        this.humidity.setText(humidity);
    }

    @Override
    public void updateForecastReportTextView(String reportTime) {
        reporttime2.setText(reportTime);
    }

    @Override
    public void updateForecastTextView(String forecast) {
        forecasttv.setText(forecast);
    }
}
