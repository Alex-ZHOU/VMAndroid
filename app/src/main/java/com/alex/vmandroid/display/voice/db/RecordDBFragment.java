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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

public class RecordDBFragment extends BaseFragment implements View.OnClickListener, RecordDBContract.View {

    private final String TAG = Base_TAG;

    private RecordDBContract.Presenter mPresenter;

    private TextView mDBTextView;

    private TextView mDurationTimeView;

    private TextView mAverageTextView;

    private TextView mMaxTextView;

    private Button mBtn;

    private ListView mListView;

    public RecordDBFragment() {
        new RecordDBPresenter(this);
    }

    public static RecordDBFragment newInstance() {
        return new RecordDBFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_db_record_db, container, false);

        mDBTextView = (TextView) view.findViewById(R.id.record_db_fragment_db_tv);

        mDurationTimeView = (TextView) view.findViewById(R.id.record_db_fragment_duration_tv);

        mAverageTextView = (TextView) view.findViewById(R.id.main_record_average_tv);

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
}
