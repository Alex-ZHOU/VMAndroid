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
package com.alex.vmandroid.display.exhibition.history.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

public class HistoricalDetailsFragment extends BaseFragment implements HistoricalDetailsContract.View {

    private HistoricalDetailsContract.Presenter mPresenter;

    private TextView mAverageTextView = null;

    private TextView mMaxTextView = null;

    private TextView mMinTextView = null;

    private TextView mTimekeeperTextView = null;

    private TextView mRecordTimeTextView = null;

    public static HistoricalDetailsFragment newInstance() {
        return new HistoricalDetailsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historical_details, container, false);

        mAverageTextView = (TextView) view.findViewById(R.id.historical_details_fragment_average_tv);
        mMaxTextView = (TextView) view.findViewById(R.id.historical_details_fragment_max_tv);
        mMinTextView = (TextView) view.findViewById(R.id.historical_details_fragment_min_tv);
        mTimekeeperTextView = (TextView) view.findViewById(R.id.historical_details_fragment_timekeeper_tv);
        mRecordTimeTextView = (TextView) view.findViewById(R.id.historical_details_fragment_record_time_tv);

        return view;
    }

    @Override
    public void setPresenter(HistoricalDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置平均数
     *
     * @param str 平均数
     */
    @Override
    public void setAverageTextView(String str) {
        if (mAverageTextView != null) {
            mAverageTextView.setText(str);
        }
    }

    /**
     * 设置最大值
     *
     * @param str 最大值
     */
    @Override
    public void setMaxTextView(String str) {
        if (mMaxTextView != null) {
            mMaxTextView.setText(str);
        }
    }

    /**
     * 设置最小值
     *
     * @param str 最小值
     */
    @Override
    public void setMinTextView(String str) {
        if (mMinTextView != null) {
            mMinTextView.setText(str);
        }
    }

    /**
     * 设置记录时长
     *
     * @param str 时长
     */
    @Override
    public void setTimekeeperTextView(String str) {
        if (mTimekeeperTextView != null) {
            mTimekeeperTextView.setText(str);
        }
    }

    /**
     * 设置记录的时间
     *
     * @param str 时间
     */
    @Override
    public void setRecordTimeTextView(String str) {
        if (mRecordTimeTextView != null) {
            mRecordTimeTextView.setText(str);
        }
    }
}
