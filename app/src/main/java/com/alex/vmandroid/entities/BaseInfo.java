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

public class BaseInfo {
    private int UserId;

    private String Username;

    private String Nickname;

    private int RecordTimes;

    private int AverageDb;

    private int MaxDb;

    private int MinDb;

    private double RecordMinter;

    private int HeadProtrait;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getMinDb() {
        return MinDb;
    }

    public void setMinDb(int minDb) {
        MinDb = minDb;
    }

    public int getMaxDb() {
        return MaxDb;
    }

    public void setMaxDb(int maxDb) {
        MaxDb = maxDb;
    }

    public int getAverageDb() {
        return AverageDb;
    }

    public void setAverageDb(int averageDb) {
        AverageDb = averageDb;
    }

    public int getRecordTimes() {
        return RecordTimes;
    }

    public void setRecordTimes(int recordTimes) {
        RecordTimes = recordTimes;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public double getRecordMinter() {
        return RecordMinter;
    }

    public void setRecordMinter(double recordMinter) {
        RecordMinter = recordMinter;
    }


    public int getHeadProtrait() {
        return HeadProtrait;
    }

    public void setHeadProtrait(int headProtrait) {
        HeadProtrait = headProtrait;
    }
}
