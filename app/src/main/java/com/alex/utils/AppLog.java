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

import android.util.Log;

public final class AppLog {


    public static boolean ifLog = true;

    public static void setIfLog(boolean ifLog) {
        AppLog.ifLog = ifLog;
    }

    public static void i(String tag, String str) {
        if (ifLog)
            Log.i(tag, str);
    }
}
