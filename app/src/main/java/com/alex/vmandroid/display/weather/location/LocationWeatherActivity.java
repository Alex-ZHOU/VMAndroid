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

package com.alex.vmandroid.display.weather.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.display.weather.WeatherFragment;
import com.alex.vmandroid.display.weather.WeatherPresenter;

public class LocationWeatherActivity extends BaseActivity {

    public static final String TAG = LocationWeatherActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_weather);

        String city = getIntent().getStringExtra("LocationCity");

        WeatherFragment fragment = WeatherFragment.newInstance(city);
        new WeatherPresenter(fragment, city, getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.loaction_weather_frame_layout, fragment);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
