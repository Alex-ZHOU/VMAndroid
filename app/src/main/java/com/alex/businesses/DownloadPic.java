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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.alex.utils.AppLog;
import com.alex.utils.URLs;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 下载图片
 */
public class DownloadPic  {

    /**
     * 通过图片id下载图片
     * @param imageId 图片id号
     * @param listener 监听
     */
    public void getById(int imageId,@NonNull final Listener listener){

        OkHttpClient client = new OkHttpClient();

        RequestBody body =  new FormBody.Builder()
                .add("ImageId", String.valueOf(imageId))
                .build();
        Request request = new Request.Builder().url(URLs.URL_GET_PIC).post(body).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                listener.succeed(bm);
            }
        });

    }

    /**
     * 通过图片地址获取图片
     * @param url 图片地址
     * @param listener 监听
     */
    public void getByUrl(@NonNull String url,@NonNull final Listener listener){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                listener.succeed(bm);
                AppLog.debug("succeed");
            }
        });
    }

    public interface Listener {
        void succeed(Bitmap bm);

        void failed();
    }
}
