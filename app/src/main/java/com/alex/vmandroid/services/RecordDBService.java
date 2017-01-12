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
package com.alex.vmandroid.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.alex.businesses.UploadRecordDBBiz;
import com.alex.utils.AppLog;
import com.alex.utils.TimeMeter;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.display.voice.RecordDBTool;
import com.alex.vmandroid.display.voice.db.RecordDBActivity;
import com.alex.vmandroid.entities.RecordDB;
import com.alex.vmandroid.receivers.RecordDBReceiver;
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

/**
 * 记录分贝时所需要的服务，是前台服务
 */
public class RecordDBService extends Service implements AMapLocationListener {

    private static final String TAG = RecordDBService.class.getName();

    public static final String RecordDBServiceName = "com.alex.vmandroid.services.RecordDBService";

    public static final String RecordDBServiceAction = "com.alex.vmandroid.services.RecordDBServiceAction";

    public static final String RECORD_DB_SERVICE_DB = "RECORD_DB_SERVICE_DB";

    public static final String RECORD_DB_SERVICE_TIME = "RECORD_DB_SERVICE_TIME";

    public static final String RECORD_DB_SERVICE_MAX_DB = "RECORD_DB_SERVICE_MAX_DB";

    public static final String RECORD_DB_SERVICE_AVERAGE_DB = "RECORD_DB_SERVICE_AVERAGE_DB";

    public static final String RECORD_DB_SERVICE_UPLOAD_SUCCEED = "RECORD_DB_SERVICE_UPLOAD_SUCCEED";

    /**
     * 广播声明
     */
    private RecordDBReceiver mReceiver;

    private MessageReceiver mReceiver2;

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

    /**
     * 声明记录列表
     */
    private List<RecordDB> mRecordDBList = new ArrayList<>();

    private RecordDBTool mRecordDBTool = new RecordDBTool(new RecordDBTool.Listener() {
        @Override
        public void onDB(int db) {
            AppLog.info(TAG, "The db is " + db + ".");
            Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_DB, db);
            sendBroadcast(intent);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String time = df.format(new Date());
            mRecordDBTimes++;
            mTotalDB += db;
            mMaxDB = mMaxDB > db ? mMaxDB : db;
            int averageDB = (int) (mTotalDB / mRecordDBTimes);
            intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_MAX_DB, mMaxDB);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_AVERAGE_DB, averageDB);
            sendBroadcast(intent);


            RecordDB recordDB = new RecordDB();
            recordDB.setDb(db);
            recordDB.setLatitude(mLatitude);
            recordDB.setLongitude(mLongitude);
            recordDB.setTime(time);
            recordDB.setTimekeeper(mTimeMeter.getTime());
            mRecordDBList.add(recordDB);
        }


    });


    private TimeMeter mTimeMeter = new TimeMeter(new TimeMeter.CallBack() {
        @Override
        public void getTime(String time) {
            AppLog.info(TAG, "The time is " + time + ".");
            Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_TIME, time);
            sendBroadcast(intent);
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();

        //region 前台服务通知配置
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this, RecordDBActivity.class);
        //PendingIntent 跳转动作
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setSmallIcon(R.drawable.vm_android_app_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.record))
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();

        //设置通知  消息  图标
        //mNotification.icon = R.drawable.icon_upload_location;
        //在通知栏上点击此通知后自动清除此通知
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
        //设置显示通知时的默认的发声、震动、Light效果
        mNotification.defaults = Notification.DEFAULT_VIBRATE;
        //设置发出消息的内容
        mNotification.tickerText = "位置上报已经启动";
        //设置发出通知的时间
        mNotification.when = System.currentTimeMillis();

        //设置通知  消息  图标
        //mNotification.icon = R.drawable.icon_upload_location;
        //在通知栏上点击此通知后自动清除此通知
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
        //设置显示通知时的默认的发声、震动、Light效果
        mNotification.defaults = Notification.DEFAULT_VIBRATE;
        //设置发出消息的内容
        mNotification.tickerText = "记录服务已经启动";
        //设置发出通知的时间
        mNotification.when = System.currentTimeMillis();
        //通知的id号码
        int notifyId = 1001;
        startForeground(notifyId, mNotification);
        //endregion

        //region 注册广播配置
        mReceiver = new RecordDBReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecordDBService.RecordDBServiceAction);
        registerReceiver(mReceiver, intentFilter);
        mReceiver2 = new MessageReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(RecordDBReceiver.ACTION);
        registerReceiver(mReceiver2, intentFilter);
        //endregion

        //region 定位配置
        mLocationClient = new AMapLocationClient(this);
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


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AppLog.debug("RecordDBService onBind() is called.");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLog.debug("RecordDBService onStartCommand() is called.");

        //启动噪声检测
        mRecordDBTool.start();
        //启动计时
        mTimeMeter.start();
        //启动定位
        mLocationClient.startLocation();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        AppLog.debug("RecordDBService onDestroy() is called.");

        unregisterReceiver(mReceiver);
        unregisterReceiver(mReceiver2);

        super.onDestroy();
    }

    private void uploadRecordInfo(List<RecordDB> recordDBList) {

        mRecordDBTool.close();

        mTimeMeter.close();

        mLocationClient.stopLocation();

        mLocationClient.onDestroy();


        UploadRecordDBBiz.commit(recordDBList, UserInfo.getUsrId(getApplicationContext()), UserInfo.getInt(this,"RecordTimes"), new UploadRecordDBBiz.Listener() {
            @Override
            public void succeed() {
                Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
                intent.putExtra(RecordDBService.RECORD_DB_SERVICE_UPLOAD_SUCCEED, true);
                sendBroadcast(intent);

                Intent intent2 = new Intent(getApplicationContext(), RecordDBService.class);
                stopService(intent2);
            }

            @Override
            public void failure() {
                // TODO 写入缓存

                Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
                intent.putExtra(RecordDBService.RECORD_DB_SERVICE_UPLOAD_SUCCEED, false);
                sendBroadcast(intent);
                // 上传失败后完成写入缓冲也需要把服务关掉
                Intent intent2 = new Intent(getApplicationContext(), RecordDBService.class);
                stopService(intent2);
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

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RecordDBReceiver.ACTION)) {
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_UPLOAD_DATA)) {
                    boolean b = intent.getBooleanExtra(RecordDBReceiver.RECORD_DB_UPLOAD_DATA, false);
                    if (b) {
                        uploadRecordInfo(mRecordDBList);
                    }
                }
            }
        }
    }


}
