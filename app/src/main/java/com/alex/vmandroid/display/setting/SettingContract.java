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
package com.alex.vmandroid.display.setting;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.alex.vmandroid.base.BaseContract;

public class SettingContract {

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
         * 设置头像显示的Bitmap
         *
         * @param bitmap 头像显示的内容
         */
        void setHeadPortraitImageView(Drawable bitmap);

        /**
         * 设置显示昵称的TextView
         *
         * @param str 显示的内容
         */
        void setNicknameTextView(@NonNull String str);

        /**
         * 跳转到修改昵称界面
         */
        void showChangNicknameActivity();

        /**
         * 显示Toast
         *
         * @param str 显示内容
         */
        void showToast(String str);

    }
}
