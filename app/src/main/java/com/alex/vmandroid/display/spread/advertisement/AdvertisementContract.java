/*
 * Copyright 2017 Alex_ZHOU
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
package com.alex.vmandroid.display.spread.advertisement;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.alex.vmandroid.base.BaseContract;

public class AdvertisementContract {

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 点击事件回掉
         *
         * @param id 被点击的view的id号
         */
        void onClick(int id);

    }

    interface View extends BaseContract.BaseView<Presenter> {

        /**
         * 获取广告的id号
         *
         * @return 广告id号
         */
        int getAdvertisementId();

        /**
         * 设置图片显示内容
         *
         * @param bim Bitmap
         */
        void setImageViewByBtm(Bitmap bim);

        /**
         * 设置声音环境文本
         *
         * @param str 要显示的文本
         */
        void setVoiceStatusTextViewText(String str);

        /**
         * 设置简介文本
         *
         * @param str 要显示的文本
         */
        void setSummaryTextViewText(String str);

        /**
         * 地图跳转到经纬度的位置
         *
         * @param latitude  latitude
         * @param longitude longitude
         */
        void changeLocationToStore(double latitude, double longitude);

        /**
         * 设置地址显示的TextView是否显示
         *
         * @param i 设置显示与否的参数
         */
        void setAddressTextViewVisibility(int i);

        /**
         * 设置AddressTextView的显示内容
         *
         * @param str 内容
         */
        void setAddressTextViewText(@NonNull String str);
    }

}
