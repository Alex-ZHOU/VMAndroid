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

import com.alex.vmandroid.R;

public class InquiryWeatherPresenter implements InquiryWeatherContract.Presenter {

    private InquiryWeatherContract.View mView;

    public InquiryWeatherPresenter(InquiryWeatherContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mView.initView();
    }

    @Override
    public void start() {

    }

    /**
     * 点击监听
     *
     * @param id   被点击的view的id号
     * @param city 输入的城市名称
     */
    @Override
    public void onClick(int id, String city) {
        if (id == R.id.inquiry_weather_btn) {
            if (city == null) {
                mView.showToast(R.string.analysis);
            } else {
                mView.initAndShowWeatherFrameLayout(city);
            }
        }
    }
}
