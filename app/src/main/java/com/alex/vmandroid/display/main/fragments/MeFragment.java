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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.vmandroid.display.exhibition.analysis.AnalysisActivity;
import com.alex.vmandroid.display.gadget.GadgetActivity;
import com.alex.vmandroid.display.exhibition.history.HistoryActivity;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.style.drawable.CircleImageDrawable;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.display.map.MapSettingActivity;
import com.alex.vmandroid.display.map.OfflineMapActivity;

import pub.devrel.easypermissions.AfterPermissionGranted;

import static com.alex.utils.PermissionRequest.MICROPHONE_PERM_RECORD_AUDIO;

public class MeFragment extends BaseFragment implements View.OnClickListener, MainContract.MeView {

    private final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

    private int[] mIdGroup = {
            R.id.main_me_title_bar_setting_iv,
            R.id.main_me_history_ll,
            R.id.main_me_analysis_ll,
            R.id.main_me_map_setting_ll,
            R.id.main_me_offline_map_ll,
            R.id.main_me_gadget_ll};


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
        Log.i(TAG, "onCreateView: MeFragment");

        View view = inflater.inflate(R.layout.fragment_main_me, container, false);

        ImageView ht = (ImageView) view.findViewById(R.id.main_me_head_portrait_iv);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.vm_android_app_icon);

        ht.setImageDrawable(new CircleImageDrawable(bitmap));

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
        Log.i(TAG, "setPresenter");
        mPresenter = presenter;
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
    @AfterPermissionGranted(MICROPHONE_PERM_RECORD_AUDIO)
    @Override
    public void showAnalysisActivity() {
//        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.RECORD_AUDIO)) {
//            Log.i(TAG, "microphonePermission");
//        } else {
//            EasyPermissions.requestPermissions(this, "",
//                    MICROPHONE_PERM_RECORD_AUDIO, Manifest.permission.RECORD_AUDIO);
//        }
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
}
