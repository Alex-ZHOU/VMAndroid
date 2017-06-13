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
package com.alex.vmandroid.display.main.fragments.fragments;

import android.os.Handler;

import com.alex.businesses.AdvertisingColumnBiz;
import com.alex.utils.AppLog;
import com.alex.vmandroid.entities.AdvertisingColumn;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoopAdvertisementPresenter implements LoopAdvertisementContract.Presenter {

    private final String TAG = LoopAdvertisementPresenter.class.getName();

    private LoopAdvertisementContract.View mView;

    private Handler mHandler = new Handler();

    private List<? extends AdvertisingColumn> mAdvertisingColumnList;

    public LoopAdvertisementPresenter(LoopAdvertisementContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        new AdvertisingColumnBiz().getData(new AdvertisingColumnBiz.Listener() {
            @Override
            public void succeed(@NotNull List<? extends AdvertisingColumn> list) {
                mAdvertisingColumnList = list;
                final List<Integer> pictureList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    pictureList.add(list.get(i).getImage_Id());
                    AppLog.debug("list.get(i).getImageId()"+list.get(i).getImage_Id());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setViewPagerData(pictureList);
                    }
                });
            }

//            @Override
//            public void succeed(List<AdvertisingColumn> list) {
//                mAdvertisingColumnList = list;
//                final List<Integer> pictureList = new ArrayList<>();
//
//                for (int i = 0; i < list.size(); i++) {
//                    pictureList.add(list.get(i).getImage_Id());
//                    AppLog.debug("list.get(i).getImageId()"+list.get(i).getImage_Id());
//                }
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mView.setViewPagerData(pictureList);
//                    }
//                });
//            }

            @Override
            public void failed() {

            }
        });
    }

    @Override
    public void onViewPagerItemClick(int id) {
        int i = id % mAdvertisingColumnList.size();
        AppLog.debug(TAG, "onViewPagerItemClick:" + i);

        mView.showAdvertisementActivity(mAdvertisingColumnList.get(i).getTitle(), mAdvertisingColumnList.get(i).getAdvertisement_Id());
    }
}
