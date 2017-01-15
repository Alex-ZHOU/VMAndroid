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

package com.alex.vmandroid.entities;

import java.util.ArrayList;
import java.util.List;

public class History {
    private int userId;

    private int RecordId;

    private int Year;

    private int Month;

    private int Day;

    private int times;

    private List<Data> recordList = new ArrayList<>();

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int recordId) {
        RecordId = recordId;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public List<Data> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Data> recordList) {
        this.recordList = recordList;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Data instancesData() {
        return new Data();
    }

    public class Data {

        /**
         * 音量
         */
        private int db;

        /**
         * 经度
         */
        private double longitude;

        /**
         * 纬度
         */
        private double latitude;

        /**
         * The time when record this data.
         */
        private String time;

        /**
         * 计时器
         */
        private String timekeeper;

        public int getDb() {
            return db;
        }

        public void setDb(int db) {
            this.db = db;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimekeeper() {
            return timekeeper;
        }

        public void setTimekeeper(String timekeeper) {
            this.timekeeper = timekeeper;
        }

    }
}
