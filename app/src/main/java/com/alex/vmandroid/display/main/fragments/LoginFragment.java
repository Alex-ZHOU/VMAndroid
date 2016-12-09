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

import com.alex.utils.URLs;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.entities.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


        // 观察者
        observer = new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onNext(User user) {
                Log.i(TAG, "onNext");
                Toast.makeText(getContext(), user.getUserName() + user.getPassword(), Toast.LENGTH_LONG).show();
            }
        };


        return view;
    }

    Subscriber<User> observer;


    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
        // 定阅
        //observable.subscribe(observer);

        final String username = mAccount.getText().toString();
        final String password = mPassword.getText().toString();

        Observable observable = Observable.create(new Observable.OnSubscribe<User>() {

            @Override
            public void call(final Subscriber<? super User> subscriber) {

                subscriber.onNext(new User());
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBodyPost = new FormBody.Builder()
                        .add("loginAccount", username)
                        .add("loginPwd", password)
                        .build();

                Request requestPost = new Request.Builder()
                        .url(URLs.URL_LOGIN)
                        .post(requestBodyPost)
                        .build();

                client.newCall(requestPost).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // 获取返回的json数据
                        final String string = response.body().string();
                        Log.i(TAG, "onResponse: " + string);

                        User user = new User();
                        user.setPassword(password);
                        user.setUserName(username);
                        subscriber.onNext(user);
                    }
                });

            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }

    /**
     * 登陆成功，跳转到主页界面
     */
    @Override
    public void showMainFragment() {
        Log.i(TAG, "showMainFragment");
        //FragmentNavigator.moveTo(getFragmentManager(), R.id.main_frame_layout, UnLoginFragment.class, false, getContext(), null);
    }
}
