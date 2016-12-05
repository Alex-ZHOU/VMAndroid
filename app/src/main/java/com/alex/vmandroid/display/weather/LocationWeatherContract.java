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

import com.alex.vmandroid.base.BaseContract;

public class LocationWeatherContract {

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 查询天气预报
         */
        void searchForecastsWeather();

        /**
         * 查询实时天气
         */
        void searchLiveWeather();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void updateLiveTextView(String reportTime, String weather, String temperature, String wind, String humidity);

        void updateForecastReportTextView(String reportTime);

        void updateForecastTextView(String forecast);
    }
}
