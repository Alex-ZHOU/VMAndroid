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
package com.alex.vmandroid.display.weather.inquiry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.display.weather.WeatherFragment;
import com.alex.vmandroid.display.weather.WeatherPresenter;
import com.alex.vmandroid.display.weather.location.LocationWeatherFragment;
import com.alex.vmandroid.display.weather.location.LocationWeatherPresenter;

public class InquiryWeatherActivity extends BaseActivity implements View.OnClickListener, InquiryWeatherContract.View {

    private InquiryWeatherContract.Presenter mPresenter;

    private EditText mCityEditText;

    private FrameLayout mWeatherShowFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_weather);
        new InquiryWeatherPresenter(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(InquiryWeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始化View
     */
    @Override
    public void initView() {
        mCityEditText = (EditText) findViewById(R.id.inquiry_weather_et);

        mWeatherShowFrameLayout = (FrameLayout) findViewById(R.id.inquiry_weather_frame_layout);

        Button button = (Button) findViewById(R.id.inquiry_weather_btn);

        if (button != null) {
            button.setOnClickListener(this);
        }

    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化并显示天气的view
     *
     * @param city 城市名
     */
    @Override
    public void initAndShowWeatherFrameLayout(@NonNull String city) {
        WeatherFragment fragment = WeatherFragment.newInstance(city);
        new WeatherPresenter(fragment, city, getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.inquiry_weather_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId(), mCityEditText.getText().toString());
    }
}
