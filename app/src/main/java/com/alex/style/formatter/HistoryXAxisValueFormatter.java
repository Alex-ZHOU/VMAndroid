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
package com.alex.style.formatter;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class HistoryXAxisValueFormatter implements IAxisValueFormatter {

    private final String TAG = HistoryXAxisValueFormatter.class.getName();

    private int mYear;

    private int mMonth;

    public HistoryXAxisValueFormatter(int startYear, int startMonth) {
        mYear = startYear;
        mMonth = startMonth;
    }


    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return X轴的信息字符串
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int month = (int) value + mMonth;

        int addYear = 0;

        if (month > 12) {
            addYear = month / 12;
            month = month % 12;
        }
        
        int year = mYear + addYear;

        Log.i(TAG, "getFormattedValue: month" + month + " value:" + value + " year:" + year + " month" + month);

        return year + "年" + month + "月";
    }

//    /**
//     * Returns the number of decimal digits this formatter uses or -1, if unspecified.
//     *
//     * @return 0
//     */
//    @Override
//    public int getDecimalDigits() {
//        return 0;
//    }
}
