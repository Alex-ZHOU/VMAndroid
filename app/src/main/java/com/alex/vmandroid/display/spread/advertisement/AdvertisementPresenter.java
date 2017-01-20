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
package com.alex.vmandroid.display.spread.advertisement;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;

import com.alex.businesses.DownloadPic;
import com.alex.businesses.GetStoreInfoBiz;
import com.alex.utils.AppLog;
import com.alex.vmandroid.R;
import com.alex.vmandroid.entities.StoreInfo;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class AdvertisementPresenter implements AdvertisementContract.Presenter, GeocodeSearch.OnGeocodeSearchListener {

    private AdvertisementContract.View mView;

    private Handler mHandler = new Handler();

    private StoreInfo mStoreInfo;

    /**
     * 高德地图逆编码声明
     */
    private GeocodeSearch mGeocodeSearch;

    public AdvertisementPresenter(AdvertisementContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mGeocodeSearch = new GeocodeSearch(context);
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }


    @Override
    public void start() {

        new GetStoreInfoBiz().get(mView.getAdvertisementId(), new GetStoreInfoBiz.Listener() {
            @Override
            public void succeed(final StoreInfo storeInfo) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStoreInfo = storeInfo;
                        setVoiceStatusTextViewText(mStoreInfo.getAverageDb());
                        mView.setSummaryTextViewText(mStoreInfo.getSummary());
                        if (mStoreInfo == null) {
                            AppLog.error("StoreInfo is null.");
                        }
                        mView.changeLocationToStore(storeInfo.getLatitude(), storeInfo.getLongitude());
                        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                        RegeocodeQuery query = new RegeocodeQuery
                                (
                                        new LatLonPoint(mStoreInfo.getLatitude(), mStoreInfo.getLongitude()),
                                        200,
                                        GeocodeSearch.AMAP
                                );
                        // 设置同步逆地理编码请求
                        mGeocodeSearch.getFromLocationAsyn(query);

                        new DownloadPic().getById(mStoreInfo.getImageId(), new DownloadPic.Listener() {
                            @Override
                            public void succeed(final Bitmap bm) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.setImageViewByBtm(bm);
                                    }
                                });
                            }

                            @Override
                            public void failed() {

                            }
                        });

                    }
                });
            }

            @Override
            public void failed() {

            }
        });

    }


    /**
     * 点击事件回掉
     *
     * @param id 被点击的view的id号
     */
    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.advertisement_frame_layout_voice_status_rl:
                mView.showStoreRecordDbListActivity(mStoreInfo);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = "地址：" + result.getRegeocodeAddress().getFormatAddress()
                        + "附近";
                mView.setAddressTextViewVisibility(View.VISIBLE);
                mView.setAddressTextViewText(addressName);
            } else {
                mView.setAddressTextViewVisibility(View.GONE);
            }
        } else {
            mView.setAddressTextViewVisibility(View.GONE);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    private void setVoiceStatusTextViewText(int averageDb) {

        int max = ((averageDb / 10) + 1) * 10;
        int min = (averageDb / 10) * 10;
        mView.setVoiceStatusTextViewText("噪声环境：" + min + "～" + max + " db");

    }
}
