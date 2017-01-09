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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 将json解析或封装json
 *
 * @author AlexZHOU
 */

public final class EncapsulateParseJson {
    
    private static Gson mGson = new Gson();

    public static <T> T parse(Class<T> classOfT, String json) {
        T t;
        try {
            t = mGson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            t = null;
        }
        return t;
    }

    public static String encapsulate(Object src) {
        return mGson.toJson(src);
    }

    public static Gson getGson() {
        return mGson;
    }
}

