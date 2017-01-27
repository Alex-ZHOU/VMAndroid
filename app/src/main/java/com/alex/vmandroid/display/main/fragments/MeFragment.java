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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.utils.AppLog;
import com.alex.vmandroid.display.exhibition.analysis.AnalysisActivity;
import com.alex.vmandroid.display.gadget.GadgetActivity;
import com.alex.vmandroid.display.exhibition.history.HistoryActivity;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.display.map.MapSettingActivity;
import com.alex.vmandroid.display.map.om.OfflineMapActivity;
import com.alex.vmandroid.display.setting.SettingActivity;


public class MeFragment extends BaseFragment implements View.OnClickListener, MainContract.MeView {

    private final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

    private int[] mIdGroup = {
            R.id.main_me_title_bar_setting_iv,
            R.id.main_me_history_ll,
            R.id.main_me_analysis_ll,
            R.id.main_me_map_setting_ll,
            R.id.main_me_offline_map_ll,
            R.id.main_me_gadget_ll,
            R.id.main_me_exit_login_ll};

    private TextView mTextView;


    public static MeFragment newInstance() {
        return new MeFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AppLog.info(TAG, "onCreateView: MeFragment");

        View view = inflater.inflate(R.layout.fragment_main_me, container, false);

        mTextView = (TextView) view.findViewById(R.id.main_me_username_tv);

        ImageView ht = (ImageView) view.findViewById(R.id.main_me_head_portrait_iv);

        mPresenter.setHeadPortrait(ht);

        for (int id : mIdGroup) {
            view.findViewById(id).setOnClickListener(this);
        }

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
    public void onClick(View view) {
        mPresenter.onClick(view.getId(), MainContract.ME_TAG);
    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 跳转到设置界面
     */
    @Override
    public void showSettingActivity() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 设置显示用户名字
     *
     * @param str 用户昵称
     */
    @Override
    public void setUserNickName(String str) {
        mTextView.setText(str);
    }

    /**
     * 跳转到显示历史记录界面
     */
    @Override
    public void showHistoryActivity() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到显示分析界面
     */
    @Override
    public void showAnalysisActivity() {
        Intent intent = new Intent(getActivity(), AnalysisActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到显示地图参数设置界面
     */
    @Override
    public void showMapSettingActivity() {
        Intent intent = new Intent(getActivity(), MapSettingActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到离线地图下载界面
     */
    @Override
    public void showOfflineMapActivity() {
        Intent intent = new Intent(getActivity(), OfflineMapActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到小工具选择界面
     */
    @Override
    public void showGadgetActivity() {
        Intent intent = new Intent(getActivity(), GadgetActivity.class);
        startActivity(intent);
    }

    /**
     * 退出应用
     */
    @Override
    public void applicationExit() {
        getActivity().finish();
    }
}
