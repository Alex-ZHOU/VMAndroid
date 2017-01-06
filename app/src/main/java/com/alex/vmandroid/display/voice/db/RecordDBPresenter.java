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

import android.os.Handler;
import android.util.Log;

import com.alex.vmandroid.R;
import com.alex.vmandroid.display.voice.RecordDBTool;

public class RecordDBPresenter implements RecordDBContract.Presenter, RecordDBTool.Listener {

    private final String TAG = RecordDBPresenter.class.getName();

    private RecordDBContract.View mView;

    private RecordDBTool mRecordDBTool;

    private boolean isRecording = false;


    public RecordDBPresenter(RecordDBContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

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
                if (mRecordDBTool == null) {
                    //mRecordDBTool = new RecordDBTool(this);
                }
                if (isRecording) {
                    //mRecordDBTool.close();
                    isRecording = false;
                    mView.setButtonText(R.string.start);
                    mView.setDBTextView(R.string.double_minus);
                    mView.stopService();
                } else {
                    //mRecordDBTool.start();
                    isRecording = true;
                    mView.setButtonText(R.string.stop);
                    mView.startService();
                }
                break;
            default:
                break;
        }
    }

    private Handler handle = new Handler();

    @Override
    public void onDB(final int db) {
        handle.post(new Runnable() {
            @Override
            public void run() {
                mView.setDBTextView(db);
            }
        });

    }
}
