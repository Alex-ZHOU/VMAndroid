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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementFragment;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementPresenter;

public class DiscoverFragment extends BaseFragment implements MainContract.DiscoverView {
    public final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: DiscoverFragment");


        LoopAdvertisementFragment fragment = new LoopAdvertisementFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        new LoopAdvertisementPresenter(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_loop_advertisement_frame_layout, fragment);
        transaction.commit();
        

        return inflater.inflate(R.layout.fragment_main_discover, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }
}
