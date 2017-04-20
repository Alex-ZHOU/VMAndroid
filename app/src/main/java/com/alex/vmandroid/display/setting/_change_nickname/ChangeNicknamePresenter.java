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
package com.alex.vmandroid.display.setting._change_nickname;

import android.content.Context;
import android.os.Handler;

import com.alex.businesses.ChangeNickNameBiz;
import com.alex.vmandroid.R;
import com.alex.vmandroid.databases.UserInfo;

public class ChangeNicknamePresenter implements ChangeNicknameContract.Presenter {

    private ChangeNicknameContract.View mView;

    private Context mContext;

    private String mNickname;

    private Handler handler = new Handler();

    public ChangeNicknamePresenter(ChangeNicknameContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;

        mNickname = UserInfo.getString(mContext, "Nickname");
    }

    @Override
    public void start() {
        mView.setEditTextString(mNickname);
    }

    @Override
    public void onClick(int id) {
        if (id == R.id.change_nickname_btn) {
            // 新昵称
            final String str = mView.getEditTextString();
            // 用户id号
            int userId = UserInfo.getUsrId(mContext);

            if (!mNickname.equals(str)) {
                new ChangeNickNameBiz().change(userId, str, new ChangeNickNameBiz.Listener() {
                    /**
                     * 成功回掉函数
                     */
                    @Override
                    public void succeed() {
                        UserInfo.putString(mContext,"Nickname",str);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mView.showToast("修改成功");
                                mView.finish();
                            }
                        });
                    }

                    /**
                     * 登陆失败回掉函数
                     *
                     * @param str 失败的原因
                     */
                    @Override
                    public void failed(String str) {
                        mView.showToast(str);
                    }
                });
            }
        }
    }
}
