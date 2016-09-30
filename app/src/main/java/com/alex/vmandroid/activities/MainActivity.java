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
package com.alex.vmandroid.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alex.view.TabLayout;
import com.alex.vmandroid.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();


    }

    ViewPager mViewPager;
    TabLayout mTabLayout;

    private void init() {
         mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));

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


    }
    private class MainAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                fragments.add(ContentFragment.newInstance("ViewPager", i));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
