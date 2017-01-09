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
package com.alex.vmandroid.display.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alex.utils.FragmentNavigator;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;

public class LoginFragment extends BaseFragment implements MainContract.LoginView, View.OnClickListener {

    private final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

    private EditText mPassword;

    private EditText mAccount;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_login, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick");

                getFragmentManager().popBackStack();
            }
        });

        Button button = (Button) view.findViewById(R.id.main_login_login_btn);

        button.setOnClickListener(this);

        mPassword = (EditText) view.findViewById(R.id.main_login_password_et);

        mAccount = (EditText) view.findViewById(R.id.main_login_account_et);

        return view;
    }


    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId(),-1);
    }

    /**
     * 显示toast 提示
     *
     * @param str 显示的提示信息
     */
    @Override
    public void showToast(String str) {
        Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
    }

    /**
     * 登陆成功，跳转到主页界面
     */
    @Override
    public void showMainFragment() {
        Log.i(TAG, "showMainFragment");
        MainFragment fragment = MainFragment.newInstance();
        mPresenter.initMainView(fragment);
        FragmentNavigator.moveTo(getFragmentManager(), fragment, R.id.main_frame_layout, false);
        //FragmentNavigator.moveTo(getFragmentManager(), R.id.main_frame_layout, fragment, false, getContext(), null);
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return mAccount.getText().toString();
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }
}
