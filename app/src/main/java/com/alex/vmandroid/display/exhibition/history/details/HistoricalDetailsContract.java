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
package com.alex.vmandroid.display.exhibition.history.details;

import com.alex.vmandroid.base.BaseContract;

public class HistoricalDetailsContract {

    public interface Presenter extends BaseContract.BasePresenter {

    }

    public interface View extends BaseContract.BaseView<Presenter> {

        /**
         * 设置平均数
         *
         * @param str 平均数
         */
        void setAverageTextView(String str);

        /**
         * 设置最大值
         *
         * @param str 最大值
         */
        void setMaxTextView(String str);

        /**
         * 设置最小值
         *
         * @param str 最小值
         */
        void setMinTextView(String str);

        /**
         * 设置记录时长
         *
         * @param str 时长
         */
        void setTimekeeperTextView(String str);

        /**
         * 设置记录的时间
         *
         * @param str 时间
         */
        void setRecordTimeTextView(String str);
    }
}
