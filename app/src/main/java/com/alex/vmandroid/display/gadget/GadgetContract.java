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
package com.alex.vmandroid.display.gadget;

import com.alex.vmandroid.base.BaseContract;

import java.util.List;

public class GadgetContract {

    interface Presenter extends BaseContract.BasePresenter {


    }

    interface View extends BaseContract.BaseView<GadgetContract.Presenter> {

        /**
         * 设置PowerfulListView显示的数据
         *
         * @param data 需要显示的数据
         */
        void setListViewData(List<String> data);

        /**
         * 显示查询天气的界面
         */
        void showInquiryWeather();
    }

}
