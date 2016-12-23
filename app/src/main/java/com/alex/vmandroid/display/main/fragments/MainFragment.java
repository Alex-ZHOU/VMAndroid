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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.view.tab.TabLayout;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;


import java.util.ArrayList;

public class MainFragment extends BaseFragment implements MainContract.MainView {

    public final String TAG = Base_TAG;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private MainContract.MainPresenter mMainPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainPresenter.start();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: MainFragment");

        View view = inflater.inflate(R.layout.fragment_main_container, container, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.main_tabLayout);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mViewPager.setAdapter(new MainViewPaperAdapter(getActivity().getSupportFragmentManager()));

        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelect(View view, int position) {
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(View view, int position) {

            }
        };

        mTabLayout.setOnTabSelectedListener(listener);

        mTabLayout.applyConfigurationWithViewPager(mViewPager, true);

        return view;
    }

    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mMainPresenter = mainPresenter;
    }


    /**
     * ViewPaperAdapter 主页的ViewPaper适配器
     */
    private class MainViewPaperAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        private MainViewPaperAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                switch (i) {
                    case 0:
                        RecordFragment recordFragment = RecordFragment.newInstance();
                        mMainPresenter.initRecordView(recordFragment);
                        fragments.add(recordFragment);
                        break;
                    case 1:
                        DiscoverFragment discoverFragment = DiscoverFragment.newInstance();
                        mMainPresenter.initDiscoverView(discoverFragment);
                        fragments.add(discoverFragment);
                        break;
                    case 2:
                        MeFragment meFragment = MeFragment.newInstance();
                        mMainPresenter.initMeView(meFragment);
                        fragments.add(meFragment);
                        break;
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "Fragment getItem: " + position);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            Log.d(TAG, "Fragment getCount: " + fragments.size());
            return fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // ViewPaper 不重复加载出现过的paper
            // 注释掉父类方法即可
            // super.destroyItem(container, position, object);
        }
    }
}
