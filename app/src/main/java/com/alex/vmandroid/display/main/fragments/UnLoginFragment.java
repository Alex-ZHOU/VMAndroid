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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alex.utils.FragmentNavigator;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;

public class UnLoginFragment extends BaseFragment implements View.OnClickListener, MainContract.UnLoginView {
    private final String TAG = Base_TAG;

    private static CallBack mCallBack = null;

    private MainContract.MainPresenter mPresenter;

    public static UnLoginFragment newInstance(CallBack callBack) {
        mCallBack = callBack;
        return new UnLoginFragment();
    }

    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_unlogin, container, false);

        Button button = (Button) view.findViewById(R.id.main_login_immediate_experience_btn);
        Button button2 = (Button) view.findViewById(R.id.main_unlogin_login_btn);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId(), -1);
    }

    @Override
    public void showMainFragment() {
        //mCallBack.showMainFragment();

        SignUpFragment fragment = SignUpFragment.newInstance();

        mPresenter.initSignUpView(fragment);

        FragmentNavigator.moveTo(getFragmentManager(), fragment, R.id.main_frame_layout, true);
    }

    @Override
    public void showLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();
        mPresenter.initLoginView(fragment);

        FragmentNavigator.moveTo(getFragmentManager(), fragment, R.id.main_frame_layout, true);

        // FragmentNavigator.moveTo(getFragmentManager(), R.id.main_frame_layout, LoginFragment.class, true, getContext(), null);
    }

    public interface CallBack {
        void showMainFragment();
    }
}
