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
package com.alex.vmandroid.display.exhibition.history;

import com.alex.vmandroid.base.BaseContract;
import com.alex.vmandroid.entities.History;

import java.util.List;

public class HistoryContract {

    interface Presenter extends BaseContract.BasePresenter {
        void onItemClickListener(int i);
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void setListViewData(List<HistoryContract.HistoryString> list);

        void showHistoricalDetailsActivity(History history);

        /**
         * 显示提示信息
         *
         * @param str 信息
         */
        void showToast(String str);
    }

    public static class HistoryString {

        private String times;

        private String line1;

        private String line2;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }
    }

}
