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

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.receivers.RecordDBReceiver;
import com.alex.vmandroid.services.RecordDBService;

public class RecordDBFragment extends BaseFragment implements View.OnClickListener, RecordDBContract.View {

    private final String TAG = Base_TAG;

    private RecordDBContract.Presenter mPresenter;

    private TextView mDBTextView;

    private TextView mDurationTimeView;

    private TextView mAverageTextView;

    private TextView mMaxTextView;

    private Button mBtn;

    private ListView mListView;

    private MessageReceiver mReceiver;

    private ProgressDialog mProgressDialog;

    public RecordDBFragment() {
        new RecordDBPresenter(this);
    }

    public static RecordDBFragment newInstance() {
        return new RecordDBFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new RecordDBFragment.MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecordDBReceiver.ACTION);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_db_record_db, container, false);

        mDBTextView = (TextView) view.findViewById(R.id.record_db_fragment_db_tv);

        mDurationTimeView = (TextView) view.findViewById(R.id.record_db_fragment_duration_tv);

        mAverageTextView = (TextView) view.findViewById(R.id.record_db_fragment_average_tv);

        mMaxTextView = (TextView) view.findViewById(R.id.record_db_fragment_max_tv);

        mBtn = (Button) view.findViewById(R.id.record_db_fragment_switch_btn);
        mBtn.setOnClickListener(this);

        mListView = (ListView) view.findViewById(R.id.record_db_fragment_info_lv);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void setPresenter(RecordDBContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * 设置被点击按钮的文本
     *
     * @param str 需要显示的文本
     */
    @Override
    public void setButtonText(int str) {
        mBtn.setText(str);
    }

    /**
     * 设置显示分贝的view显示的内容
     *
     * @param dbOrId 分贝数或者资源id
     */
    @Override
    public void setDBTextView(int dbOrId) {
        if (dbOrId < 120) {
            mDBTextView.setText(String.valueOf(dbOrId));
        } else {
            mDBTextView.setText(dbOrId);
        }
    }

    /**
     * 设置记录的时长
     *
     * @param time 时长
     */
    @Override
    public void setDurationTimeView(String time) {
        mDurationTimeView.setText(time);
    }

    /**
     * 设置显示平均值
     *
     * @param average 平均值
     */
    @Override
    public void setAverageTextView(@NonNull String average) {
        mAverageTextView.setText(average);
    }

    /**
     * 设置显示最大值
     *
     * @param max 最大值
     */
    @Override
    public void setMaxTextView(@NonNull String max) {
        mMaxTextView.setText(max);
    }

    /**
     * 启动记录服务
     */
    @Override
    public void startService() {
        Intent intent = new Intent(getContext(), RecordDBService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(RecordDBService.RecordDBServiceName);
        getContext().startService(intent);
    }

    /**
     * 关闭记录服务
     */
    @Override
    public void uploadAndStopService() {
        Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
        intent.putExtra(RecordDBReceiver.RECORD_DB_UPLOAD_DATA, true);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getResources().getString(R.string.uploading));
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int i) {
        Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取上下文内容
     *
     * @return 上下文
     */
    @Override
    public Context getViewContext() {
        return getContext();
    }

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RecordDBReceiver.ACTION)) {
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_DB)) {
                    int db = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_DB, -1);
                    mPresenter.getDB(db);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_TIME)) {
                    String time = intent.getStringExtra(RecordDBReceiver.RECORD_DB_RECEIVER_TIME);
                    mPresenter.getTime(time);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_MAX_DB)) {
                    int max = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_MAX_DB, -1);
                    mPresenter.getMaxDB(max);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_AVERAGE_DB)) {
                    int average = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_AVERAGE_DB, -1);
                    mPresenter.getAverageDB(average);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED)) {
                    //boolean isSucceed = intent.getBooleanExtra(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED, false);
                    mPresenter.isUploadSucceed(intent.getBooleanExtra(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED, false));
                }
            }
        }
    }
}
