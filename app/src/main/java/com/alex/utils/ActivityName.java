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
package com.alex.utils;

import com.alex.vmandroid.R;
import com.alex.vmandroid.display.exhibition.analysis.AnalysisActivity;
import com.alex.vmandroid.display.exhibition.history.HistoryActivity;
import com.alex.vmandroid.display.gadget.GadgetActivity;
import com.alex.vmandroid.display.weather.inquiry.InquiryWeatherActivity;
import com.alex.vmandroid.display.weather.location.LocationWeatherActivity;

import java.util.HashMap;
import java.util.Map;

public class ActivityName {

    private static Map<String, Integer> mName;


    private static void init() {
        mName = new HashMap<>();

        mName.put(GadgetActivity.TAG, R.string.gadget);

        mName.put(LocationWeatherActivity.TAG, R.string.location_weather);

        mName.put(InquiryWeatherActivity.TAG, R.string.inquire_weather);

        /**
         * 分析标题栏名字
         */
        mName.put(AnalysisActivity.TAG, R.string.analysis);

        /**
         * 历史标题栏名字
         */
        mName.put(HistoryActivity.TAG, R.string.history);
    }

    public static int getName(String simpleName) {
        if (mName == null) {
            init();
        }
        return mName.get(simpleName) == null ? R.string.app_name : mName.get(simpleName);
    }
}
