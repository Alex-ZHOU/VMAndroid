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
package com.alex.businesses;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alex.utils.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登陆处理业务
 */
public class LoginBiz {

    public static final String TAG = LoginBiz.class.getName();


    public static void login(@NonNull final String username, @NonNull final String password,
                             @NonNull final Listener listener) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("loginAccount", username)
                .add("loginPwd", password)
                .build();

        Request requestPost = new Request.Builder()
                .url(URLs.URL_LOGIN)
                .post(requestBodyPost)
                .build();


        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取返回的json数据
                final String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);


                try {
                    JSONObject json2 = new JSONObject(string);
                    String str = (String) json2.get("success");
                    if (str.equals("true")) {
                        listener.succeed();
                    } else {
                        listener.failed(str);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.failed(e.toString());
                }
            }
        });
    }

    /**
     * 登陆监听
     */
    public interface Listener {
        /**
         * 成功回掉函数
         */
        void succeed();

        /**
         * 登陆失败回掉函数
         *
         * @param str 失败的原因以及说明
         */
        void failed(String str);
    }
}
