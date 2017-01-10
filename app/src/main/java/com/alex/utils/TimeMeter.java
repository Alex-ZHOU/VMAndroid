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

/**
 * 简易计时器，利用线程睡眠进行计时
 */
public class TimeMeter implements Runnable {

    private Thread mThread;

    private int mSecond;

    private int mMinter;

    private int mHour;

    private CallBack mCallBack;

    private boolean isTimeRecord = false;

    public TimeMeter(CallBack callBack) {
        mThread = new Thread(this);
        this.mCallBack = callBack;
    }

    public void start() {
        isTimeRecord = true;
        mSecond = 0;
        mMinter = 0;
        mHour = 0;
        mThread.start();
    }

    public void close() {
        isTimeRecord = false;
        mSecond = 0;
        mMinter = 0;
        mHour = 0;
        mThread = null;
    }

    public String getTime() {
        return (mHour >= 10 ? mHour : "0" + mHour) + ":" + (mMinter >= 10 ? mMinter : "0" + mMinter) + ":" + (mSecond >= 10 ? mSecond : "0" + mSecond);
    }

    @Override
    public void run() {
        while (isTimeRecord) {
            try {
                Thread.sleep(1000);
                mSecond++;

                if (mSecond == 60) {
                    mSecond = 0;
                    mMinter++;
                }
                if (mMinter == 60) {
                    mMinter = 0;
                    mHour++;
                }

                mCallBack.getTime((mHour >= 10 ? mHour : "0" + mHour) + ":" + (mMinter >= 10 ? mMinter : "0" + mMinter) + ":" + (mSecond >= 10 ? mSecond : "0" + mSecond));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public interface CallBack {
        void getTime(String time);
    }

}
