/*
 * Copyright 2016-2017 Alex_ZHOU
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

import android.support.annotation.NonNull;
import android.util.Log;

public final class AppLog {

    private static final String TAG = AppLog.class.getName();

    private static boolean LOG_DEBUG = true;

    private static boolean LOG_INFO = true;

    private static boolean LOG_WARN = true;

    private static boolean LOG_ERROR = true;

    public static void debug(String tag, @NonNull String str) {
        if (AppLog.LOG_DEBUG) {
            if (tag != null) {
                Log.d(tag, str);
            } else {
                debug(str);
            }
        }
    }

    public static void debug(@NonNull String str) {
        if (AppLog.LOG_DEBUG) {
            Log.d(TAG, str);
        }
    }

    public static void info(String tag, @NonNull String str) {
        if (AppLog.LOG_INFO) {
            if (tag != null) {
                Log.i(tag, str);
            } else {
                info(str);
            }
        }
    }

    public static void info(@NonNull String str) {
        if (AppLog.LOG_INFO) {
            Log.i(TAG, str);
        }
    }

    public static void warn(String tag, @NonNull String str) {
        if (AppLog.LOG_WARN) {
            if (tag != null) {
                Log.w(tag, str);
            } else {
                warn(str);
            }
        }
    }

    public static void warn(@NonNull String str) {
        if (AppLog.LOG_WARN) {
            Log.w(TAG, str);
        }
    }

    public static void error(String tag, @NonNull String str) {
        if (AppLog.LOG_ERROR) {
            if (tag != null) {
                Log.e(tag, str);
            } else {
                error(str);
            }
        }
    }

    public static void error(@NonNull String str) {
        if (AppLog.LOG_ERROR) {
            Log.e(TAG, str);
        }
    }

}
