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
package com.alex.businesses;

import com.alex.utils.AppLog;
import com.alex.utils.EncapsulateParseJson;
import com.alex.utils.URLs;
import com.alex.vmandroid.entities.ShareRecordDb;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShareListBiz {


    public void get(final Listener listener) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URLs.URL_SHARE_RECORD).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                response.close();
                AppLog.debug(json);
                List<ShareRecordDb> list  = jsonToArrayList(json,ShareRecordDb.class);
                listener.succeed(list);
            }
        });

    }

    /**
     *
     * Author Young
     *
     * @param json json的值
     * @param clazz 类
     * @param <T> 类
     * @return 数组列
     */
    private static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = EncapsulateParseJson.getGson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(EncapsulateParseJson.getGson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    public interface Listener {
        void succeed(List<ShareRecordDb> list);

        void failed();
    }
}
