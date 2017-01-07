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
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.alex.utils.AppLog;
import com.alex.utils.TimeMeter;
import com.alex.vmandroid.R;
import com.alex.vmandroid.display.voice.RecordDBTool;
import com.alex.vmandroid.display.voice.db.RecordDBActivity;
import com.alex.vmandroid.receivers.RecordDBReceiver;

/**
 * 记录分贝时所需要的服务，是前台服务
 */
public class RecordDBService extends Service {

    private static final String TAG = RecordDBService.class.getName();

    public static final String RecordDBServiceName = "com.alex.vmandroid.services.RecordDBService";

    public static final String RecordDBServiceAction = "com.alex.vmandroid.services.RecordDBServiceAction";

    public static final String RECORD_DB_SERVICE_DB = "RECORD_DB_SERVICE_DB";

    public static final String RECORD_DB_SERVICE_TIME = "RECORD_DB_SERVICE_TIME";

    public static final String RECORD_DB_SERVICE_MAX_DB = "RECORD_DB_SERVICE_MAX_DB";

    public static final String RECORD_DB_SERVICE_AVERAGE_DB = "RECORD_DB_SERVICE_AVERAGE_DB";

    private RecordDBReceiver mReceiver;

    private long mTotalDB = 0L;

    private long mRecordDBTimes = 0;

    private int mMaxDB = 0;

    private RecordDBTool mRecordDBTool = new RecordDBTool(new RecordDBTool.Listener() {
        @Override
        public void onDB(int db) {
            AppLog.debug(TAG, "The db is " + db + ".");
            Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_DB, db);
            sendBroadcast(intent);

            mRecordDBTimes++;
            mTotalDB += db;
            mMaxDB = mMaxDB > db ? mMaxDB : db;
            int averageDB = (int) (mTotalDB / mRecordDBTimes);
            intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_MAX_DB, mMaxDB);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_AVERAGE_DB, averageDB);
            sendBroadcast(intent);
            
        }
    });

    private TimeMeter mTimeMeter = new TimeMeter(new TimeMeter.CallBack() {
        @Override
        public void getTime(String time) {
            AppLog.debug(TAG, "The time is " + time + ".");
            Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
            intent.putExtra(RecordDBService.RECORD_DB_SERVICE_TIME, time);
            sendBroadcast(intent);
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();

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

        //通知的id号,默认设置为100

        mReceiver = new RecordDBReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecordDBService.RecordDBServiceAction);
        registerReceiver(mReceiver, intentFilter);
        int notifyId = 1001;
        startForeground(notifyId, mNotification);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AppLog.debug("RecordDBService onBind() is called.");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRecordDBTool.start();
        mTimeMeter.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        AppLog.debug("RecordDBService onDestroy() is called.");

        mRecordDBTool.close();
        mTimeMeter.close();
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }
}
