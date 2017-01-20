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
package com.alex.vmandroid.display.voice.store;

import android.os.Handler;
import android.util.Log;

import com.alex.businesses.UploadStoreDbRecordBiz;
import com.alex.utils.AppLog;
import com.alex.utils.ServiceCheck;
import com.alex.utils.TimeMeter;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.display.voice.RecordDBTool;
import com.alex.vmandroid.entities.StoreDbRecord;
import com.alex.vmandroid.services.RecordDBService;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StoreDbRecordPresenter implements StoreDbRecordContract.Presenter, AMapLocationListener {

    private final String TAG = StoreDbRecordPresenter.class.getName();

    private StoreDbRecordContract.View mView;

    private int mStoreId;

    private StoreDbRecord mStoreDbRecord;

    private List<StoreDbRecord.Data> mDataList = new ArrayList<>();

    private boolean mRecordFlag = false;

    private Handler handler = new Handler();

    /**
     * 总值，用于求平均数
     */
    private long mTotalDB = 0L;

    /**
     * 本次记录已经记录的次数
     */
    private long mRecordDBTimes = 0;

    /**
     * 当前记录的最大分贝数
     */
    private int mMaxDB = 0;

    /**
     * 声明AMapLocationClient对象
     */
    private AMapLocationClient mLocationClient = null;

    /**
     * 纬度声明
     */
    private double mLatitude;

    /**
     * 经度声明
     */
    private double mLongitude;

    private RecordDBTool mRecordDBTool = new RecordDBTool(new RecordDBTool.Listener() {
        @Override
        public void onDB(final int db) {
            AppLog.info(TAG, "The db is " + db + ".");


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String time = df.format(new Date());
            mRecordDBTimes++;
            mTotalDB += db;
            mMaxDB = mMaxDB > db ? mMaxDB : db;
            final int averageDB = (int) (mTotalDB / mRecordDBTimes);

            StoreDbRecord.Data data = mStoreDbRecord.instanceData();
            data.setDB(db);
            data.setLatitude(mLatitude);
            data.setLongitude(mLongitude);
            data.setTime(time);
            data.setTimekeeper(mTimeMeter.getTime());
            mDataList.add(data);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setDBTextView(db);
                    mView.setAverageTextView(String.valueOf(averageDB));
                    mView.setMaxTextView(String.valueOf(mMaxDB));
                    mView.setDurationTimeView(mTimeMeter.getTime());
                }
            });

        }
    });

    private TimeMeter mTimeMeter = new TimeMeter(new TimeMeter.CallBack() {
        @Override
        public void getTime(String time) {
            AppLog.info(TAG, "The time is " + time + ".");
        }
    });

    public StoreDbRecordPresenter(StoreDbRecordContract.View fragment, int storeId, String storeTitle) {
        mView = fragment;
        mStoreId = storeId;
        mView.setPresenter(this);
        //region 定位配置
        mLocationClient = new AMapLocationClient(mView.getViewContext());
        //初始化定位参数
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(locationOption);
        //endregion
    }

    @Override
    public void start() {
        if (ServiceCheck.isServiceWork(mView.getViewContext(), RecordDBService.RecordDBServiceName)) {
            mView.showToast("记录服务已经启动，请先关闭已经启动的记录");
        }
        mStoreDbRecord = new StoreDbRecord();
    }

    /**
     * 点击监听
     *
     * @param id 被点击的view的id号
     */
    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.store_record_db_fragment_switch_btn:
                boolean isRecording = ServiceCheck.isServiceWork(mView.getViewContext(), RecordDBService.RecordDBServiceName);
                Log.i(TAG, "onClick: ");
                if (isRecording) {
                    mView.showToast("记录服务已经启动，请先关闭已经启动的记录");
                } else {
                    if (!mRecordFlag) {
                        mRecordFlag = true;
                        mView.setButtonText(R.string.stop);
                        //启动噪声检测
                        mRecordDBTool.start();
                        //启动计时
                        mTimeMeter.start();
                        //启动定位
                        mLocationClient.startLocation();
                    } else {
                        mRecordFlag = false;
                        mView.setButtonText(R.string.start);
                        mRecordDBTool.close();

                        mTimeMeter.close();

                        mLocationClient.stopLocation();

                        mLocationClient.onDestroy();

                        uploadData();
                    }

                }
                break;
            default:
                break;
        }
    }

    private void uploadData() {
        mView.showProgressDialog();
        mStoreDbRecord.setDbData(mDataList);
        mStoreDbRecord.setUserId(UserInfo.getUsrId(mView.getViewContext()));
        mStoreDbRecord.setStoreId(mStoreId);

        new UploadStoreDbRecordBiz().commit(mStoreDbRecord, new UploadStoreDbRecordBiz.Listener() {
            @Override
            public void succeed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.closeProgressDialog();
                    }
                });
            }

            @Override
            public void failure() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.closeProgressDialog();
                        mView.showToast("上传成功，审核通过将会展示出来");
                    }
                });
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                mLatitude = amapLocation.getLatitude();//获取纬度
                mLongitude = amapLocation.getLongitude();//获取经度
                AppLog.info(TAG, "Latitude:" + mLatitude + " ,Longitude" + mLongitude);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                AppLog.error("Amap Location Error"
                        + ", ErrCode:" + amapLocation.getErrorCode()
                        + ", ErrInfo:" + amapLocation.getErrorInfo());
            }
        }
    }
}
