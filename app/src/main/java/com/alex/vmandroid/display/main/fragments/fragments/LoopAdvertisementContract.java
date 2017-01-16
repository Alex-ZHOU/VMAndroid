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
package com.alex.vmandroid.display.main.fragments.fragments;

import com.alex.vmandroid.base.BaseContract;

import java.util.List;

public class LoopAdvertisementContract {

    public static final String ADVERTISEMENT_TITLE = "ADVERTISEMENT_TITLE";

    public static final String ADVERTISEMENT_ID = "ADVERTISEMENT_ID";

    interface Presenter extends BaseContract.BasePresenter {
        void onViewPagerItemClick(int id);
    }

    interface View extends BaseContract.BaseView<LoopAdvertisementContract.Presenter> {
        void setViewPagerData(List<Integer> pictureList);

        /**
         * 显示广告界面
         *
         * @param title 标题信息
         * @param advertisementId 广告对应id号
         */
        void showAdvertisementActivity(String title,int advertisementId);

    }
}
