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
package com.alex.vmandroid.display.spread.store;

import android.widget.ImageView;

import com.alex.vmandroid.base.BaseContract;
import com.alex.vmandroid.entities.StoreRecordDb;

import java.util.List;

public class StoreRecordDbListContract {

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

        /**
         * 点击监听
         *
         * @param id 点击的view的id号
         */
        void onClick(int id);
    }

    interface View extends BaseContract.BaseView<Presenter> {

        /**
         * 设置ListView显示的数据
         *
         * @param listViewData 数据
         */
        void setListViewData(List<StoreRecordDb> listViewData);

        /**
         * 跳转到商家记录界面
         *
         * @param storeId 商家id号
         */
        void showStoreDbRecordActivity(int storeId,String title);
    }

}
