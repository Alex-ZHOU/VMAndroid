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

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.setting._change_nickname.ChangeNicknameActivity;

public class SettingFragment extends BaseFragment implements SettingContract.View, View.OnClickListener {
    private SettingContract.Presenter mPresenter;

    private ImageView mHeadPortraitImageView;

    private TextView mNicknameTextView;

    private int idGround[] = {
            R.id.setting_head_portrait_rl,
            R.id.setting_change_nickname_rl
    };

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        for (int i : idGround) {
            view.findViewById(i).setOnClickListener(this);
        }

        mHeadPortraitImageView = (ImageView) view.findViewById(R.id.setting_head_portrait_iv);

        mNicknameTextView = (TextView) view.findViewById(R.id.setting_nickname_tv);

        return view;
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * 设置头像显示的Bitmap
     *
     * @param bitmap 头像显示的内容
     */
    @Override
    public void setHeadPortraitImageView(Drawable bitmap) {
        mHeadPortraitImageView.setImageDrawable(bitmap);
    }

    /**
     * 设置显示昵称的TextView
     *
     * @param str 显示的内容
     */
    @Override
    public void setNicknameTextView(@NonNull String str) {
        mNicknameTextView.setText(str);
    }

    /**
     * 跳转到修改昵称界面
     */
    @Override
    public void showChangNicknameActivity() {
        Intent intent = new Intent(getActivity(), ChangeNicknameActivity.class);
        getContext().startActivity(intent);
    }

    /**
     * 显示Toast
     *
     * @param str 显示内容
     */
    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }
}
