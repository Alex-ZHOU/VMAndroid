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

import com.alex.utils.AppLog;
import com.alex.utils.EncapsulateParseJson;
import com.alex.utils.URLs;
import com.alex.vmandroid.entities.Login;

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

    /**
     * 密码错误
     */
    public static final int PASSWORD_WRONG = 1;

    /**
     * 账号不存在
     */
    public static final int ACCOUNT_NO_EXITED = 2;

    /**
     * 发生未知错误
     */
    public static final int UNKNOWN_WRONG = 3;


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
                listener.failed(UNKNOWN_WRONG);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取返回的json数据
                final String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);

                Login login = EncapsulateParseJson.parse(Login.class, string);

                if (login != null) {
                    switch (login.getReturn()) {
                        case "success":
                            listener.succeed(login.getUser());
                            break;
                        case "password":
                            AppLog.info(TAG, "密码错误");
                            listener.failed(PASSWORD_WRONG);
                            break;
                        case "account":
                            AppLog.info(TAG, "账号不存在");
                            listener.failed(ACCOUNT_NO_EXITED);
                            break;
                    }
                } else {
                    AppLog.error(TAG, string);
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
        void succeed(Login.User user);

        /**
         * 登陆失败回掉函数
         *
         * @param i 失败的原因
         */
        void failed(int i);
    }
}
