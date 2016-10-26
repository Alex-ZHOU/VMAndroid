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
package com.alex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.alex.vmandroid.R;

import pub.devrel.easypermissions.AppSettingsDialog;

public class PermissionRequest {

    public static final int CALENDAR_PERM_READ_CALENDAR = 1;
    public static final int CALENDAR_PERM_WRITE_CALENDAR = 2;

    public static final int CAMERA_PERM_CAMERA = 3;

    public static final int CONTACTS_PERM_READ_CONTACTS = 4;
    public static final int CONTACTS_PERM_WRITE_CONTACTS = 5;
    public static final int CONTACTS_PERM_GET_ACCOUNTS = 6;

    public static final int LOCATION_PERM_ACCESS_FINE_LOCATION = 7;
    public static final int LOCATION_PERM_ACCESS_COARSE_LOCATION = 8;

    public static final int MICROPHONE_PERM_RECORD_AUDIO = 9;

    public static final int PHONE_PERM_READ_PHONE_STATE = 10;
    public static final int PHONE_PERM_CALL_PHONE = 11;
    public static final int PHONE_PERM_READ_CALL_LOG = 12;
    public static final int PHONE_PERM_WRITE_CALL_LOG = 13;
    public static final int PHONE_PERM_ADD_VOICEMAIL = 14;
    public static final int PHONE_PERM_USE_SIP = 15;
    public static final int PHONE_PERM_PROCESS_OUTGOING_CALLS = 16;

    public static final int SENSORS_PERM_BODY_SENSORS = 17;

    public static final int SMS_PERM_SEND_SMS = 18;
    public static final int SMS_PERM_RECEIVE_SMS = 19;
    public static final int SMS_PERM_READ_SMS = 20;
    public static final int SMS_PERM_RECEIVE_WAP_PUSH = 21;
    public static final int SMS_PERM_RECEIVE_MMS = 22;

    public static final int STORAGE_PERM_READ_EXTERNAL_STORAGE = 23;
    public static final int STORAGE_PERM_WRITE_EXTERNAL_STORAGE = 24;

    public static void onPermissionsDenied(final Fragment fragment, int requestCode) {
        onPermissionsDenied(fragment.getActivity(), fragment.getContext(), requestCode);

    }

    public static void onPermissionsDenied(final Activity activity, int requestCode) {
        onPermissionsDenied(activity, activity.getApplicationContext(), requestCode);
    }


    private static void onPermissionsDenied(final Activity activity, final Context context, int requestCode) {
        new AppSettingsDialog.Builder(activity, context.getString(R.string.rationale_ask_again))
                .setTitle(context.getString(R.string.ask_for_permission))
                .setPositiveButton(context.getString(R.string.move_to))
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "权限被拒绝，应用无法正常使用", Toast.LENGTH_LONG).show();
                    }
                })
                //.setRequestCode(RC_SETTINGS_SCREEN)
                .build()
                .show();
    }

}
