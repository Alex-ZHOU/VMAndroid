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

import com.alex.utils.AppLog;
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
public class UploadRecordDB {

    public static final String TAG = UploadRecordDB.class.getName();

    public static void commit(List<RecordDB> recordDBList, int usrId, int times, @NonNull final Listener listener) {

        UploadData uploadData = new UploadData();
        uploadData.setRecordList(recordDBList);
        uploadData.setUserID(usrId);
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
                listener.succeed();
            }
        });
    }

    public interface Listener {
        void succeed();

        void failure();
    }

    private static class UploadData {
        private int userID;

        private int times;
        
        private List<RecordDB> recordList;

        public int getUserID() {
            return userID;
        }

        private void setUserID(int userID) {
            this.userID = userID;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
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
