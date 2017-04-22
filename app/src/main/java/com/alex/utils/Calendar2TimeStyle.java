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
package com.alex.utils;

import java.util.Calendar;

public class Calendar2TimeStyle {

    /**
     * 获取当前时间Calendar
     *
     * @return 当前时间Calendar
     */
    public Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 将Calendar转换为日期字符串
     *
     * @param calendar 输入的日期
     * @return 返回日期字符串 2017-04-22
     */
    public String calendar2Data(Calendar calendar) {
        String str;

        str = calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1 > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)) + "-" +
                (calendar.get(Calendar.DAY_OF_MONTH) > 9 ? calendar.get(Calendar.DAY_OF_MONTH) : "0" + calendar.get(Calendar.DAY_OF_MONTH));

        return str;
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期 2017-04-22
     */
    public String getCurrentData(){
        return calendar2Data(getCurrentCalendar());
    }

    /**
     * 将Calendar转化为时间字符串
     *
     * @param calendar 输入的日期
     * @return 返回时间字符串 00:00
     */
    public String calendar2Time(Calendar calendar) {
        String str;

        str = (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY)) +
                ":" +
                (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));

        return str;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间 00:00
     */
    public String getCurrentTime(){
        return calendar2Time(getCurrentCalendar());
    }
}
