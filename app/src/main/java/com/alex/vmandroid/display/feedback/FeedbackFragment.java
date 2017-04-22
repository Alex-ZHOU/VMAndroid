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
package com.alex.vmandroid.display.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

public class FeedbackFragment extends BaseFragment implements FeedbackContract.View, View.OnClickListener {

    private FeedbackContract.Presenter mPresenter;

    private TextView mTimeTv = null;

    private EditText mFeedbackEt = null;

    private Button mFeedbackBtn = null;

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        mTimeTv = (TextView) view.findViewById(R.id.feedback_time_tv);

        mFeedbackEt = (EditText) view.findViewById(R.id.feedback_et);

        mFeedbackBtn = (Button) view.findViewById(R.id.feedback_btn);

        return view;
    }

    @Override
    public void setPresenter(FeedbackContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(mFeedbackBtn.getId());
    }

    @Override
    public void setFeedbackBtnSetClickListener() {
        if (mFeedbackBtn != null) {
            mFeedbackBtn.setOnClickListener(this);
        }
    }

    /**
     * 设置显示时间的TextView的显示内容
     *
     * @param str 时间
     */
    @Override
    public void setTimeTextViewText(String str) {
        if (mTimeTv != null) {
            mTimeTv.setText(str);
        }
    }

    /**
     * 获取反馈信息
     *
     * @return 反馈信息
     */
    @Override
    public String getFeedbackEditTextString() {
        if (mFeedbackEt != null) {
            return mFeedbackEt.getText().toString();
        } else {
            return null;
        }
    }

    /**
     * Finish当前的Activity
     */
    @Override
    public void callFinish() {
        getActivity().finish();
    }

    /**
     * 显示Toast
     *
     * @param str 需要显示的内容
     */
    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }
}
