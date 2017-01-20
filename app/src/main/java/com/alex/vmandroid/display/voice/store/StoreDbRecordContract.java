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
package com.alex.vmandroid.display.voice.store;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alex.vmandroid.base.BaseContract;

public class StoreDbRecordContract {

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 点击监听
         *
         * @param id 被点击的view的id号
         */
        void onClick(int id);
    }


    interface View extends BaseContract.BaseView<Presenter> {
        /**
         * 获取上下文内容
         *
         * @return 上下文
         */
        Context getViewContext();

        /**
         * 设置被点击按钮的文本
         *
         * @param str 需要显示的文本
         */
        void setButtonText(int str);

        /**
         * 设置显示分贝的view显示的内容
         *
         * @param dbOrId 分贝数或者资源id
         */
        void setDBTextView(int dbOrId);

        /**
         * 设置记录的时长
         *
         * @param time 时长
         */
        void setDurationTimeView(String time);

        /**
         * 设置显示平均值
         *
         * @param average 平均值
         */
        void setAverageTextView(@NonNull String average);

        /**
         * 设置显示最大值
         *
         * @param max 最大值
         */
        void setMaxTextView(@NonNull String max);

        void showProgressDialog();

        void closeProgressDialog();

        void showToast(int i);

        void showToast(String str);
    }


}
