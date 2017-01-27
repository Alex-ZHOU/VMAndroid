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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

public class ChangeNicknameFragment extends BaseFragment implements View.OnClickListener, ChangeNicknameContract.View {

    private ChangeNicknameContract.Presenter mPresenter;

    private EditText mEditText;

    public static ChangeNicknameFragment newInstance() {
        return new ChangeNicknameFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_nickname, container, false);

        mEditText = (EditText) view.findViewById(R.id.change_nickname_et);

        Button button = (Button) view.findViewById(R.id.change_nickname_btn);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void setPresenter(ChangeNicknameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * 设置昵称的内容
     *
     * @param str 内容
     */
    @Override
    public void setEditTextString(@Nullable String str) {
        mEditText.setText(str);
    }

    /**
     * 获取昵称的内容
     *
     * @return 昵称
     */
    @Override
    public String getEditTextString() {
        if (mEditText != null) {
            return mEditText.getText().toString();
        } else {
            return null;
        }
    }
}
