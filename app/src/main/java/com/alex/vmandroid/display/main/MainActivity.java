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
package com.alex.vmandroid.display.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.alex.utils.FragmentNavigator;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.databases.UserInfo;

import com.alex.vmandroid.display.main.fragments.SignUpFragment;
import com.alex.vmandroid.display.main.fragments.UnLoginFragment;
import com.alex.vmandroid.display.main.fragments.MainFragment;

public class MainActivity extends BaseActivity implements UnLoginFragment.CallBack {

    public final String TAG = Base_TAG;

    private MainContract.MainPresenter mMainPresenter;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.VMTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter();

        mMainPresenter.setApplicationContext(getApplicationContext());

        fragmentManager = getSupportFragmentManager();


        if (UserInfo.getUserName(getApplicationContext()) == null) {
            UnLoginFragment fragment = UnLoginFragment.newInstance(this);

            mMainPresenter.initUnLoginView(fragment);

            FragmentNavigator.moveTo(fragmentManager, fragment, R.id.main_frame_layout, true);
        } else {
            showMainFragment();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void showMainFragment() {

        MainFragment fragment = MainFragment.newInstance();

        mMainPresenter.initMainView(fragment);

        FragmentNavigator.moveTo(fragmentManager, fragment, R.id.main_frame_layout, false);

    }

}
