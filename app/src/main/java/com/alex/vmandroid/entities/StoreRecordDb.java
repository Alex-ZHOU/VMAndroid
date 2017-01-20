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

public class StoreRecordDb {

    private int StoreRecordId;

    private int StoreId;

    private int UserId;

    private int times;

    private int Year;

    private int Month;

    private int Day;

    private int UserHeadPortraitImageId;

    private String NickName;

    private List<Data> RecordList = new ArrayList<>();

    public int getStoreRecordId() {
        return StoreRecordId;
    }

    public void setStoreRecordId(int storeRecordId) {
        StoreRecordId = storeRecordId;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
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

    public List<Data> getRecordList() {
        return RecordList;
    }

    public void setRecordList(List<Data> recordList) {
        RecordList = recordList;
    }

    public Data instancesData() {
        return new Data();
    }

    public int getUserHeadPortraitImageId() {
        return UserHeadPortraitImageId;
    }

    public void setUserHeadPortraitImageId(int userHeadPortraitImageId) {
        UserHeadPortraitImageId = userHeadPortraitImageId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
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
