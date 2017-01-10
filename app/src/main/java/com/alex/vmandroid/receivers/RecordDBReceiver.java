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
package com.alex.vmandroid.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alex.utils.AppLog;
import com.alex.vmandroid.services.RecordDBService;

public class RecordDBReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.alex.vmandroid.receivers.RecordDBReceiver";

    public static final String RECORD_DB_RECEIVER_DB = "RECORD_DB_RECEIVER_DB";

    public static final String RECORD_DB_RECEIVER_TIME = "RECORD_DB_RECEIVER_TIME";

    public static final String RECORD_DB_RECEIVER_MAX_DB = "RECORD_DB_RECEIVER_MAX_DB";

    public static final String RECORD_DB_RECEIVER_AVERAGE_DB = "RECORD_DB_RECEIVER_AVERAGE_DB";

    public static final String RECORD_DB_RECEIVER_UPLOAD_SUCCEED = "RECORD_DB_RECEIVER_UPLOAD_SUCCEED";

    public static final String RECORD_DB_UPLOAD_DATA = "RECORD_DB_UPLOAD_DATA";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (intent.getAction().equals(RecordDBService.RecordDBServiceAction)) {
            send(context, bundle);
        }
    }

    private void send(Context context, @NonNull Bundle bundle) {

        if (bundle.containsKey(RecordDBService.RECORD_DB_SERVICE_DB)) {
            int db = bundle.getInt(RecordDBService.RECORD_DB_SERVICE_DB, -1);
            Intent intent = new Intent(RecordDBReceiver.ACTION);
            intent.putExtra(RecordDBReceiver.RECORD_DB_RECEIVER_DB, db);
            context.sendBroadcast(intent);
        }

        if (bundle.containsKey(RecordDBService.RECORD_DB_SERVICE_TIME)) {
            String time = bundle.getString(RecordDBService.RECORD_DB_SERVICE_TIME);
            Intent intent = new Intent(RecordDBReceiver.ACTION);
            intent.putExtra(RecordDBReceiver.RECORD_DB_RECEIVER_TIME, time);
            context.sendBroadcast(intent);
        }

        if (bundle.containsKey(RecordDBService.RECORD_DB_SERVICE_MAX_DB)) {
            int maxDb = bundle.getInt(RecordDBService.RECORD_DB_SERVICE_MAX_DB);
            Intent intent = new Intent(RecordDBReceiver.ACTION);
            intent.putExtra(RecordDBReceiver.RECORD_DB_RECEIVER_MAX_DB, maxDb);
            context.sendBroadcast(intent);
        }

        if (bundle.containsKey(RecordDBService.RECORD_DB_SERVICE_AVERAGE_DB)) {
            int averageDb = bundle.getInt(RecordDBService.RECORD_DB_SERVICE_AVERAGE_DB);
            Intent mIntent = new Intent(RecordDBReceiver.ACTION);
            mIntent.putExtra(RecordDBReceiver.RECORD_DB_RECEIVER_AVERAGE_DB, averageDb);
            context.sendBroadcast(mIntent);
        }

        if (bundle.containsKey(RecordDBService.RECORD_DB_SERVICE_UPLOAD_SUCCEED)) {
            boolean isSucceed = bundle.getBoolean(RecordDBService.RECORD_DB_SERVICE_UPLOAD_SUCCEED);
            Intent mIntent = new Intent(RecordDBReceiver.ACTION);
            mIntent.putExtra(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED, isSucceed);
            context.sendBroadcast(mIntent);
        }


        if (bundle.containsKey(RecordDBReceiver.RECORD_DB_UPLOAD_DATA)) {
            AppLog.info("Called RECORD_DB_UPLOAD_DATA.");
            boolean b = bundle.getBoolean(RecordDBReceiver.RECORD_DB_UPLOAD_DATA);
            Intent mIntent = new Intent(RecordDBReceiver.ACTION);
            mIntent.putExtra(RecordDBReceiver.RECORD_DB_UPLOAD_DATA, b);
            context.sendBroadcast(mIntent);
        }

    }
}
