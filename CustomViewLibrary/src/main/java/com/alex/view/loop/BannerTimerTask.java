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
 *
 * @author Alex_ZHOU
 *
 * @author junweiliu 2016/6/14
 */

package com.alex.view.loop;

import android.os.Handler;

import java.util.TimerTask;

/**
 * 定时器
 */
public class BannerTimerTask extends TimerTask {

    private Handler mHandler;

    public BannerTimerTask(Handler mHandler) {
        super();
        this.mHandler = mHandler;
    }


    //FIXME 0X1001不知道是什么
    @Override
    public void run() {
        mHandler.sendEmptyMessage(0X1001);
    }
}
