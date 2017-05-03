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
import com.alex.vmandroid.entities.Feedback;
import com.alex.vmandroid.entities.Return;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FeedbackBiz {

    public static final String TAG = FeedbackBiz.class.getName();

    public void upload(final String feedbackText, final String time, final int userId,
                       final Listener listener) {

        Feedback feedback = new Feedback();
        feedback.setDescription(feedbackText);
        feedback.setTime(time);
        feedback.setUserId(userId);

        String json = EncapsulateParseJson.encapsulate(feedback);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request requestPost = new Request.Builder()
                .url(URLs.URL_FEEDBACK)
                .post(requestBody)
                .build();

        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取返回的json数据
                final String json = response.body().string();
                response.close();
                Return back = EncapsulateParseJson.parse(Return.class, json);
                if (back != null) {
                    if (back.isSucceed()) {
                        listener.succeed();
                    } else {
                        listener.failed(back.getStr());
                    }
                }
            }
        });

    }

    public interface Listener {
        void succeed();

        void failed(String str);
    }

}
