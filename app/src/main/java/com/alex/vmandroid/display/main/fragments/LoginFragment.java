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
import android.widget.ImageView;

import com.alex.utils.FragmentNavigator;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.entities.User;

import rx.Observable;
import rx.Subscriber;

public class LoginFragment extends BaseFragment implements MainContract.LoginView, View.OnClickListener {

    private final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

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


        // 观察者
        Subscriber<User> observer = new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {

            }
        };

        // 被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<User>() {

            @Override
            public void call(Subscriber<? super User> subscriber) {
                subscriber.onNext(new User());
                subscriber.onCompleted();
            }
        });
        // 定阅
        observable.subscribe(observer);


        return view;
    }

    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * 登陆成功，跳转到主页界面
     */
    @Override
    public void showMainFragment() {
        Log.i(TAG, "showMainFragment");
        FragmentNavigator.moveTo(getFragmentManager(), R.id.main_frame_layout, UnLoginFragment.class, false, getContext(), null);
    }
}
