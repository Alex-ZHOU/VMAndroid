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
package com.alex.vmandroid.display.exhibition.analysis2;

import com.alex.style.formatter.AnalysisXAxisValueFormatter;
import com.alex.vmandroid.base.BaseContract;

public class AnalysisChartContract {

    public interface Presenter extends BaseContract.BasePresenter {

    }

    public interface View extends BaseContract.BaseView<Presenter> {

        void setAxis(AnalysisXAxisValueFormatter formatter, float yAxisMaxValue);

        void setData(float[] data);

        /**
         * 设置次数数组的内容
         *
         * @param str   设置的内容
         * @param index 数组的索引值
         * @param visibility 是否显示view
         */
        void setTimesTextViewArray(String str, int index,boolean visibility);
    }
}
