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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.exhibition.share.ShareFragment;
import com.alex.vmandroid.display.exhibition.share.SharePresenter;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementFragment;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementPresenter;
import com.alex.vmandroid.display.spread.list.StoreListActivity;

public class DiscoverFragment extends BaseFragment implements MainContract.DiscoverView, View.OnClickListener {
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


        View view = inflater.inflate(R.layout.fragment_main_discover, container, false);

        LoopAdvertisementFragment fragment = new LoopAdvertisementFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        new LoopAdvertisementPresenter(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_loop_advertisement_frame_layout, fragment);
        transaction.commit();


        LinearLayout mBarLinearLayout = (LinearLayout) view.findViewById(R.id.main_discover_bar_ll);
        mBarLinearLayout.setOnClickListener(this);

        LinearLayout mKTVLinearLayout = (LinearLayout) view.findViewById(R.id.main_discover_ktv_ll);
        mKTVLinearLayout.setOnClickListener(this);

        LinearLayout mRestaurantLinearLayout = (LinearLayout) view.findViewById(R.id.main_discover_restaurant_ll);
        mRestaurantLinearLayout.setOnClickListener(this);

        LinearLayout mOtherLinearLayout = (LinearLayout) view.findViewById(R.id.main_discover_other_ll);
        mOtherLinearLayout.setOnClickListener(this);

        // TODO 可能出错
        ShareFragment shareFragment = new ShareFragment();
        FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
        new SharePresenter(shareFragment, getActivity());
        FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
        transaction2.replace(R.id.main_share_frame_layout, shareFragment);
        transaction2.commit();

        return view;
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

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId(), MainContract.DISCOVER_TAG);
    }

    /**
     * 跳转到显示商店列表的Activity
     *
     * @param type 跳转的类型
     */
    @Override
    public void showStoreListActivity(String type) {
        Intent intent = new Intent(getActivity(), StoreListActivity.class);
        intent.putExtra("Type", type);
        startActivity(intent);
    }
}
