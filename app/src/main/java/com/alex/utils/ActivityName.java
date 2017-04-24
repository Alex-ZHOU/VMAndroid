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
import com.alex.vmandroid.display.exhibition.history.details.HistoricalDetailsActivity;
import com.alex.vmandroid.display.feedback.FeedbackActivity;
import com.alex.vmandroid.display.gadget.GadgetActivity;
import com.alex.vmandroid.display.map.om.OfflineMapActivity;
import com.alex.vmandroid.display.setting.SettingActivity;
import com.alex.vmandroid.display.setting._change_nickname.ChangeNicknameActivity;
import com.alex.vmandroid.display.weather.inquiry.InquiryWeatherActivity;
import com.alex.vmandroid.display.weather.location.LocationWeatherActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置Activity标题栏显示的名字
 */
public class ActivityName {

    private static Map<String, Integer> mName;


    private static void init() {
        mName = new HashMap<>();

        // 小工具导航界面
        mName.put(GadgetActivity.TAG, R.string.gadget);

        // 当地天气显示
        mName.put(LocationWeatherActivity.TAG, R.string.location_weather);

        // 天气查询显示
        mName.put(InquiryWeatherActivity.TAG, R.string.inquire_weather);

        // 分析标题栏名字
        mName.put(AnalysisActivity.TAG, R.string.analysis);

        // 历史标题栏名字
        mName.put(HistoryActivity.TAG, R.string.history);

        // 离线地图标题栏名字
        mName.put(OfflineMapActivity.TAG, R.string.offline_map);

        // 设置界面的标题栏名字
        mName.put(SettingActivity.TAG, R.string.setting);

        // 修改昵称标题栏
        mName.put(ChangeNicknameActivity.TAG, R.string.change_nickname);

        // 反馈标题栏
        mName.put(FeedbackActivity.TAG,R.string.feedback);

        // 历史记录详情
        mName.put(HistoricalDetailsActivity.TAG,R.string.historical_details);
    }

    public static int getName(String simpleName) {
        if (mName == null) {
            init();
        }
        return mName.get(simpleName) == null ? R.string.app_name : mName.get(simpleName);
    }
}
