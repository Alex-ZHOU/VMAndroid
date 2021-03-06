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

import android.support.annotation.NonNull;

import com.alex.utils.EncapsulateParseJson;
import com.alex.utils.URLs;
import com.alex.vmandroid.entities.RecordDB;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 记录噪声量并上传
 */
public class UploadRecordDBBiz {

    public static final String TAG = UploadRecordDBBiz.class.getName();

    public static void commit(List<RecordDB> recordDBList, int usrId, int times, @NonNull final Listener listener) {

        UploadData uploadData = new UploadData();
        uploadData.setRecordList(recordDBList);
        uploadData.setUserId(usrId);
        uploadData.setTimes(times);

        String json = EncapsulateParseJson.encapsulate(uploadData);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request requestPost = new Request.Builder()
                .url(URLs.URL_RECORD_DB)
                .post(requestBody)
                .build();

        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failure();
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
                        listener.failure();
                    }
                }

            }
        });
    }

    public interface Listener {
        void succeed();

        void failure();
    }


    private static class Return {
        private boolean isSucceed;

        private boolean isSucceed() {
            return isSucceed;
        }

        public void setSucceed(boolean succeed) {
            isSucceed = succeed;
        }
    }

    private static class UploadData {
        private int userId;

        private int times;

        private List<RecordDB> recordList;

        public int getUserId() {
            return userId;
        }

        private void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTimes() {
            return times;
        }

        private void setTimes(int times) {
            this.times = times;
        }

        public List<RecordDB> getRecordList() {
            return recordList;
        }

        private void setRecordList(List<RecordDB> recordList) {
            this.recordList = recordList;
        }


    }
}
