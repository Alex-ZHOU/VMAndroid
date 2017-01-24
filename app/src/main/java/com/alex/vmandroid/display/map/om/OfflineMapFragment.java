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
package com.alex.vmandroid.display.map.om;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.map.om.adapter.OfflineDownloadedAdapter;
import com.alex.vmandroid.display.map.om.adapter.OfflineMapListAdapter;
import com.alex.vmandroid.display.map.om.adapter.OfflinePagerAdapter;

public class OfflineMapFragment extends BaseFragment implements View.OnClickListener, OfflineMapContract.View, ViewPager.OnPageChangeListener {

    private OfflineMapContract.Presenter mPresenter;

    private TextView mDownloadText;
    private TextView mDownloadedText;
    private ImageView mBackImage;

    // view pager 两个list以及他们的adapter
    private ViewPager mContentViewPage;
    private ExpandableListView mAllOfflineMapList;
    private ListView mDownLoadedList;

    public static OfflineMapFragment newInstance() {
        return new OfflineMapFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_map, container, false);

        // 顶部
        mDownloadText = (TextView) view.findViewById(R.id.download_list_text);
        mDownloadedText = (TextView) view.findViewById(R.id.downloaded_list_text);
        mDownloadText.setOnClickListener(this);
        mDownloadedText.setOnClickListener(this);

        // view pager 用到了所有城市list和已下载城市list所有放在最后初始化
        mContentViewPage = (ViewPager) view.findViewById(R.id.content_viewpage);



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    /**
     * 初始化所有城市列表，主要是未下载的
     *
     * @param context 上下文
     * @param adapter 设配器
     */
    @Override
    public void initAllCityList(Context context, OfflineMapListAdapter adapter) {
        // 扩展列表
        View provinceContainer = LayoutInflater.from(context)
                .inflate(R.layout.listview_offline_map_offline_province, null);
        mAllOfflineMapList = (ExpandableListView) provinceContainer
                .findViewById(R.id.province_download_list);

        // 为列表绑定数据源
        mAllOfflineMapList.setAdapter(adapter);
        // adapter实现了扩展列表的展开与合并监听
        mAllOfflineMapList.setOnGroupCollapseListener(adapter);
        mAllOfflineMapList.setOnGroupExpandListener(adapter);
        mAllOfflineMapList.setGroupIndicator(null);
    }

    /**
     * 初始化已经下载的城市列表
     *
     * @param context 上下文
     * @param adapter 设配器
     */
    @Override
    public void initDownloadedList(Context context, OfflineDownloadedAdapter adapter) {
        mDownLoadedList = (ListView) LayoutInflater.from(
                context).inflate(
                R.layout.listview_offline_map_downloaded_list, null);
        android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(
                android.widget.AbsListView.LayoutParams.MATCH_PARENT,
                android.widget.AbsListView.LayoutParams.WRAP_CONTENT);
        mDownLoadedList.setLayoutParams(params);
        mDownLoadedList.setAdapter(adapter);


        PagerAdapter mPageAdapter = new OfflinePagerAdapter(mContentViewPage,
                mAllOfflineMapList, mDownLoadedList);

        mContentViewPage.setAdapter(mPageAdapter);
        mContentViewPage.setCurrentItem(0);
        mContentViewPage.addOnPageChangeListener(this);
    }

    /**
     * 显示未下载的列表
     */
    @Override
    public void showDownloadView() {
        int paddingHorizontal = mDownloadText.getPaddingLeft();
        int paddingVertical = mDownloadText.getPaddingTop();
        mContentViewPage.setCurrentItem(0);

        mDownloadText
                .setBackgroundResource(R.drawable.offlinearrow_tab1_pressed);

        mDownloadedText
                .setBackgroundResource(R.drawable.offlinearrow_tab2_normal);

        mDownloadedText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);

        mDownloadText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);

    }

    /**
     * 显示已经下载的列表
     */
    @Override
    public void showDownloadedView() {
        int paddingHorizontal = mDownloadedText.getPaddingLeft();
        int paddingVertical = mDownloadedText.getPaddingTop();
        mContentViewPage.setCurrentItem(1);

        mDownloadText
                .setBackgroundResource(R.drawable.offlinearrow_tab1_normal);
        mDownloadedText
                .setBackgroundResource(R.drawable.offlinearrow_tab2_pressed);
        mDownloadedText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);
        mDownloadText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);
    }


    @Override
    public void setPresenter(OfflineMapContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        int paddingHorizontal = mDownloadedText.getPaddingLeft();
        int paddingVertical = mDownloadedText.getPaddingTop();

        switch (position) {
            case 0:
                mDownloadText
                        .setBackgroundResource(R.drawable.offlinearrow_tab1_pressed);
                mDownloadedText
                        .setBackgroundResource(R.drawable.offlinearrow_tab2_normal);
                // mPageAdapter.notifyDataSetChanged();
                break;
            case 1:
                mDownloadText
                        .setBackgroundResource(R.drawable.offlinearrow_tab1_normal);

                mDownloadedText
                        .setBackgroundResource(R.drawable.offlinearrow_tab2_pressed);
                // mDownloadedAdapter.notifyDataChange();
                break;
        }
        mPresenter.updateList(mContentViewPage.getCurrentItem());
        mDownloadedText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);
        mDownloadText.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
