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

import android.content.Context;
import android.os.Handler;

import com.alex.businesses.FeedbackBiz;
import com.alex.utils.Calendar2TimeStyle;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;

public class FeedbackPresenter implements FeedbackContract.Presenter {

    private FeedbackContract.View mView;

    private Context mContext;

    private Calendar2TimeStyle mCalendar2TimeStyle;

    private String mTime;

    private Handler mHandler = new Handler();

    public FeedbackPresenter(FeedbackContract.View view, Context context) {
        mView = view;
        mContext = context;
        mView.setPresenter(this);
        mCalendar2TimeStyle = new Calendar2TimeStyle();
    }

    @Override
    public void start() {
        mTime = mCalendar2TimeStyle.getCurrentData() + " " + mCalendar2TimeStyle.getCurrentTime();
        mView.setTimeTextViewText(mTime);
        mView.setFeedbackBtnSetClickListener();
    }

    /**
     * 点击监听
     *
     * @param id 被点击的view的id号
     */
    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.feedback_btn:
                new FeedbackBiz().upload(mView.getFeedbackEditTextString(), mTime, UserInfo.getUsrId(mContext),
                        new FeedbackBiz.Listener() {
                            @Override
                            public void succeed() {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.showToast(mContext.getString(R.string.upload_succeed));
                                        mView.callFinish();
                                    }
                                });
                            }

                            @Override
                            public void failed(final String str) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.showToast(str);
                                    }
                                });
                            }
                        });

                break;
        }
    }
}
