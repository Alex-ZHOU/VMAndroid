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
package com.alex.style.formatter;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class AnalysisXAxisValueFormatter implements IAxisValueFormatter {

    private List<String> mList = new ArrayList<>();

    public static final String _20_TIMES = "0-20";

    public static final String _40_TIMES = "20-40";

    public static final String _60_TIMES = "40-60";

    public static final String _70_TIMES = "60-70";

    public static final String _90_TIMES = "70-90";

    public static final String _100_TIMES = "90-100";

    public static final String _120_TIMES = "100-120";

    public static final String _120_UP_TIMES = "120+";

    /**
     * Show the null point to make sure the analysis chart had three point.
     */
    public static final String _NULL_AXIS = "NULL SPOT";

    public void addXAxisText(String str) {
        mList.add(str);
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return x轴显示的内容
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return  mList.get((int) value % mList.size());
    }
}
