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
package com.alex.vmandroid.display.spread.list;

import android.widget.ImageView;

import com.alex.vmandroid.base.BaseContract;
import com.alex.vmandroid.entities.StoreInfo;

import java.util.List;

public class StoreListContract {

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 设置ListView的用户头像
         *
         * @param iv ImageView
         * @param id 图片的id号
         */
        void setImageViewByImageId(ImageView iv, int id);

        /**
         * 监听ListView 点击的item号
         *
         * @param i item号
         */
        void onItemClick(int i);
    }

    interface View extends BaseContract.BaseView<Presenter> {
        /**
         * 设置ListView显示的数据
         *
         * @param listViewData 数据
         */
        void setListViewData(List<StoreInfo> listViewData);

        /**
         * 显示AdvertisementActivity
         *
         * @param storeId 商家广告的id号
         * @param title   商家标题
         */
        void showAdvertisementActivity(int storeId, String title);
    }
}
