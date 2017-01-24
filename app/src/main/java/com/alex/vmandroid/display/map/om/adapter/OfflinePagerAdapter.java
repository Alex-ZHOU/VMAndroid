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
package com.alex.vmandroid.display.map.om.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager数据
 */
public class OfflinePagerAdapter extends PagerAdapter {

    private View mOfflineMapAllList;
    private View mOfflineDowloadedList;

    private ViewPager mContentViewPager;

    public OfflinePagerAdapter(ViewPager contentViewPager,
                               View offlineMapAllList, View offlineDowloadedList) {
        mContentViewPager = contentViewPager;
        this.mOfflineMapAllList = offlineMapAllList;
        this.mOfflineDowloadedList = offlineDowloadedList;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (arg1 == 0) {
            mContentViewPager.removeView(mOfflineMapAllList);
        } else {
            mContentViewPager.removeView(mOfflineDowloadedList);
        }

    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {

        if (arg1 == 0) {
            mContentViewPager.addView(mOfflineMapAllList);
            return mOfflineMapAllList;
        } else {
            mContentViewPager.addView(mOfflineDowloadedList);
            return mOfflineDowloadedList;
        }

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
