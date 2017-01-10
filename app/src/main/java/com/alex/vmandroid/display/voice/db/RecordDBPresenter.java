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
package com.alex.vmandroid.display.voice.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alex.utils.ServiceCheck;
import com.alex.vmandroid.R;
import com.alex.vmandroid.services.RecordDBService;

public class RecordDBPresenter implements RecordDBContract.Presenter {

    private final String TAG = RecordDBPresenter.class.getName();

    private RecordDBContract.View mView;

    private boolean isRecording = false;


    public RecordDBPresenter(RecordDBContract.View view) {
        mView = view;
        mView.setPresenter(this);

    }

    @Override
    public void start() {
        isRecording = ServiceCheck.isServiceWork(mView.getViewContext(), RecordDBService.RecordDBServiceName);
        if (isRecording) {
            mView.setButtonText(R.string.stop);
        } else {
            mView.setDBTextView(R.string.double_minus);
        }
    }

    /**
     * 点击监听
     *
     * @param id 被点击的view的id号
     */
    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.record_db_fragment_switch_btn:
                Log.i(TAG, "onClick: ");
                if (isRecording) {
                    isRecording = false;
                    mView.setButtonText(R.string.start);
                    mView.setDBTextView(R.string.double_minus);
                    mView.uploadAndStopService();
                    mView.showProgressDialog();
                } else {
                    isRecording = true;
                    mView.setButtonText(R.string.stop);
                    mView.startService();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取当前的分贝数
     *
     * @param db 分贝数
     */
    @Override
    public void getDB(int db) {
        mView.setDBTextView(db);
    }

    /**
     * 获取记录的时长
     *
     * @param time 时长
     */
    @Override
    public void getTime(@NonNull String time) {
        mView.setDurationTimeView(time);
    }

    /**
     * 获取分贝平均数
     *
     * @param average 平均数
     */
    @Override
    public void getAverageDB(int average) {
        mView.setAverageTextView(String.valueOf(average));
    }

    /**
     * 获取最大数值
     *
     * @param max 最大值
     */
    @Override
    public void getMaxDB(int max) {
        mView.setMaxTextView(String.valueOf(max));
    }

    /**
     * 是否上传成功
     *
     * @param b 成功为true
     */
    @Override
    public void isUploadSucceed(boolean b) {
        mView.closeProgressDialog();
        if (b) {
            mView.showToast(R.string.upload_succeed);
        } else {
            mView.showToast(R.string.upload_failed);
        }
    }

}
