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
package com.alex.vmandroid.display.setting._change_nickname;

import android.support.annotation.Nullable;

import com.alex.vmandroid.base.BaseContract;

public class ChangeNicknameContract {

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 点击监听
         *
         * @param id id号
         */
        void onClick(int id);
    }

    interface View extends BaseContract.BaseView<Presenter> {
        /**
         * 设置昵称的内容
         *
         * @param str 内容
         */
        void setEditTextString(@Nullable String str);

        /**
         * 获取昵称的内容
         *
         * @return 昵称
         */
        String getEditTextString();
    }
}
