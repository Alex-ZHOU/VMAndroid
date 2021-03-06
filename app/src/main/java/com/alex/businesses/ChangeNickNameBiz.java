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

import com.alex.utils.EncapsulateParseJson;
import com.alex.utils.URLs;
import com.alex.vmandroid.entities.Return;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeNickNameBiz {

    public void change(final int userId, final String nickName, final Listener listener) {


        OkHttpClient client = new OkHttpClient();

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("UserId", String.valueOf(userId))
                .add("NewNickname", nickName)
                .build();
        Request requestPost = new Request.Builder()
                .url(URLs.URL_CHANG_NICKNAME)
                .post(requestBodyPost)
                .build();

        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取返回的json数据
                final String string = response.body().string();
                response.close();

                Return returnJson = EncapsulateParseJson.parse(Return.class, string);
                if (returnJson != null) {
                    if (returnJson.isSucceed()) {
                        listener.succeed();
                    } else {
                        listener.failed(returnJson.getStr());
                    }
                }
            }
        });

    }

    public interface Listener {
        /**
         * 成功回掉函数
         */
        void succeed();

        /**
         * 登陆失败回掉函数
         *
         * @param str 失败的原因
         */
        void failed(String str);
    }


}
