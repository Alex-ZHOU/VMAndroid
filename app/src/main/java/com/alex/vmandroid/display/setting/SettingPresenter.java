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
package com.alex.vmandroid.display.setting;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.alex.businesses.DownloadPic;
import com.alex.style.drawable.CircleImageDrawable;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.display.setting.SettingContract.Presenter;

public class SettingPresenter implements Presenter {

    private SettingContract.View mView;

    private Context mContext;

    private Handler mHandler = new Handler();

    public SettingPresenter(SettingContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;
    }


    @Override
    public void start() {
        mView.setNicknameTextView(UserInfo.getString(mContext, "Nickname"));

        new DownloadPic().getById(UserInfo.getInt(mContext, "HeadProtrait"), new DownloadPic.Listener() {
            @Override
            public void succeed(final Bitmap bm) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setHeadPortraitImageView(new CircleImageDrawable(bm));
                    }
                });
            }

            @Override
            public void failed() {

            }
        }, mContext);

    }

    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.setting_head_portrait_rl:
                break;
            case R.id.setting_change_nickname_rl:
                mView.showChangNicknameActivity();
                break;
        }
    }
}
