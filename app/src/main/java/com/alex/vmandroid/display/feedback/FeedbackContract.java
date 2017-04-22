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
package com.alex.vmandroid.display.feedback;


import com.alex.vmandroid.base.BaseContract;

public class FeedbackContract {

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 点击监听
         *
         * @param id 被点击的view的id号
         */
        void onClick(int id);
    }

    interface View extends BaseContract.BaseView<FeedbackContract.Presenter> {

        /**
         * 按钮设置点击监听
         */
        void setFeedbackBtnSetClickListener();

        /**
         * 设置显示时间的TextView的显示内容
         *
         * @param str 时间
         */
        void setTimeTextViewText(String str);

        /**
         * 获取反馈信息
         *
         * @return 反馈信息
         */
        String getFeedbackEditTextString();

        /**
         * Finish当前的Activity
         */
        void callFinish();

        /**
         * 显示Toast
         * @param str 需要显示的内容
         */
        void showToast(String str);
    }
}
