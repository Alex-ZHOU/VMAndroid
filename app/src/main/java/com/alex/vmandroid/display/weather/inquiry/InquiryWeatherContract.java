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

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.alex.vmandroid.base.BaseContract;

public class InquiryWeatherContract {

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 点击监听
         *
         * @param id   被点击的view的id号
         * @param city 输入的城市名称
         */
        void onClick(int id, String city);


    }

    interface View extends BaseContract.BaseView<Presenter> {
        /**
         * 初始化View
         */
        void initView();

        void showToast(@StringRes int resId);

        /**
         * 初始化并显示天气的view
         *
         * @param city 城市名
         */
        void initAndShowWeatherFrameLayout(@NonNull String city);
    }
}
