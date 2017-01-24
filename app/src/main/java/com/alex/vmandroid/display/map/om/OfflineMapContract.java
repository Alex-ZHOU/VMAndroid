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
package com.alex.vmandroid.display.map.om;

import android.content.Context;

import com.alex.vmandroid.base.BaseContract;
import com.alex.vmandroid.display.map.om.adapter.OfflineDownloadedAdapter;
import com.alex.vmandroid.display.map.om.adapter.OfflineMapListAdapter;

public class OfflineMapContract {

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * Called when the fragment is no longer in use.
         */
        void onDestroy();

        /**
         * 点击监听
         *
         * @param id 被点击的view的id
         */
        void onClick(int id);

        /**
         * 更新列表
         *
         * @param position 当前位置
         */
        void updateList(int position);
    }

    interface View extends BaseContract.BaseView<Presenter> {
        /**
         * 初始化所有城市列表，主要是未下载的
         *
         * @param context 上下文
         * @param adapter 设配器
         */
        void initAllCityList(Context context, OfflineMapListAdapter adapter);

        /**
         * 初始化已经下载的城市列表
         *
         * @param context 上下文
         * @param adapter 设配器
         */
        void initDownloadedList(Context context, OfflineDownloadedAdapter adapter);

        /**
         * 显示未下载的列表
         */
        void showDownloadView();

        /**
         * 显示已经下载的列表
         */
        void showDownloadedView();
    }
}
