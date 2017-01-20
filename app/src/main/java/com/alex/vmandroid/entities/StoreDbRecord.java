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

import java.util.List;

public class StoreDbRecord {

    private int StoreId;

    private int UserId;

    private List<Data> DbData;

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

    public List<Data> getDbData() {
        return DbData;
    }

    public void setDbData(List<Data> dbData) {
        DbData = dbData;
    }

    public Data instanceData() {
        return new Data();
    }

    public class Data {
        private int DB;

        private double Latitude;

        private double Longitude;

        private String Time;

        private String Timekeeper;

        public int getDB() {
            return DB;
        }

        public void setDB(int DB) {
            this.DB = DB;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getTimekeeper() {
            return Timekeeper;
        }

        public void setTimekeeper(String timekeeper) {
            Timekeeper = timekeeper;
        }
    }
}
