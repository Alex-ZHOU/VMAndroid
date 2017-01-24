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
import android.util.Log;

import com.alex.vmandroid.R;
import com.alex.vmandroid.display.map.om.adapter.OfflineDownloadedAdapter;
import com.alex.vmandroid.display.map.om.adapter.OfflineMapListAdapter;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.amap.api.maps.offlinemap.OfflineMapStatus;

import java.util.ArrayList;
import java.util.List;

public class OfflineMapPresenter implements OfflineMapContract.Presenter, OfflineMapManager.OfflineMapDownloadListener {

    private OfflineMapContract.View mView;

    private Context mContext;

    /**
     * 声明离线地图下载控制器
     */
    private OfflineMapManager mOfflineMapManager = null;

    /**
     * 保存一级目录的省直辖市的列表
     */
    private List<OfflineMapProvince> mProvinceList = new ArrayList<>();

    private OfflineMapListAdapter adapter;

    private OfflineDownloadedAdapter mDownloadedAdapter;


    public OfflineMapPresenter(OfflineMapContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;
    }

    @Override
    public void start() {
        mOfflineMapManager = new OfflineMapManager(mContext, this);
        initProvinceListAndCityMap();

        adapter = new OfflineMapListAdapter(mProvinceList, mOfflineMapManager,
                mContext);
        mView.initAllCityList(mContext, adapter);

        mDownloadedAdapter = new OfflineDownloadedAdapter(mContext, mOfflineMapManager);
        mView.initDownloadedList(mContext, mDownloadedAdapter);

    }

    /**
     * sdk内部存放形式为<br>
     * 省份 - 各自子城市<br>
     * 北京-北京<br>
     * ...<br>
     * 澳门-澳门<br>
     * 概要图-概要图<br>
     * <br>
     * 修改一下存放结构:<br>
     * 概要图-概要图<br>
     * 直辖市-四个直辖市<br>
     * 港澳-澳门香港<br>
     * 省份-各自子城市<br>
     */
    private void initProvinceListAndCityMap() {

        List<OfflineMapProvince> lists = mOfflineMapManager
                .getOfflineMapProvinceList();

        mProvinceList.add(null);
        mProvinceList.add(null);
        mProvinceList.add(null);
        // 添加3个null 以防后面添加出现 index out of bounds

        ArrayList<OfflineMapCity> cityList = new ArrayList<>();// 以市格式保存直辖市、港澳、全国概要图
        ArrayList<OfflineMapCity> gangaoList = new ArrayList<>();// 保存港澳城市
        ArrayList<OfflineMapCity> gaiyaotuList = new ArrayList<>();// 保存概要图

        for (int i = 0; i < lists.size(); i++) {
            OfflineMapProvince province = lists.get(i);
            if (province.getCityList().size() != 1) {
                // 普通省份
                mProvinceList.add(i + 3, province);
                // cityMap.put(i + 3, cities);
            } else {
                String name = province.getProvinceName();
                if (name.contains("香港")) {
                    gangaoList.addAll(province.getCityList());
                } else if (name.contains("澳门")) {
                    gangaoList.addAll(province.getCityList());
                } else if (name.contains("全国概要图")) {
                    gaiyaotuList.addAll(province.getCityList());
                } else {
                    // 直辖市
                    cityList.addAll(province.getCityList());
                }
            }
        }

        // 添加，概要图，直辖市，港口
        OfflineMapProvince gaiyaotu = new OfflineMapProvince();
        gaiyaotu.setProvinceName("概要图");
        gaiyaotu.setCityList(gaiyaotuList);
        mProvinceList.set(0, gaiyaotu);// 使用set替换掉刚开始的null

        OfflineMapProvince zhixiashi = new OfflineMapProvince();
        zhixiashi.setProvinceName("直辖市");
        zhixiashi.setCityList(cityList);
        mProvinceList.set(1, zhixiashi);

        OfflineMapProvince gaogao = new OfflineMapProvince();
        gaogao.setProvinceName("港澳");
        gaogao.setCityList(gangaoList);
        mProvinceList.set(2, gaogao);
    }


    @Override
    public void onDownload(int status, int completeCode, String downName) {
        switch (status) {
            case OfflineMapStatus.SUCCESS:
                // changeOfflineMapTitle(OfflineMapStatus.SUCCESS, downName);
                break;
            case OfflineMapStatus.LOADING:
                Log.d("amap-download", "download: " + completeCode + "%" + ","
                        + downName);
                // changeOfflineMapTitle(OfflineMapStatus.LOADING, downName);
                break;
            case OfflineMapStatus.UNZIP:
                Log.d("amap-unzip", "unzip: " + completeCode + "%" + "," + downName);
                // changeOfflineMapTitle(OfflineMapStatus.UNZIP);
                // changeOfflineMapTitle(OfflineMapStatus.UNZIP, downName);
                break;
            case OfflineMapStatus.WAITING:
                Log.d("amap-waiting", "WAITING: " + completeCode + "%" + ","
                        + downName);
                break;
            case OfflineMapStatus.PAUSE:
                Log.d("amap-pause", "pause: " + completeCode + "%" + "," + downName);
                break;
            case OfflineMapStatus.STOP:
                break;
            case OfflineMapStatus.ERROR:
                Log.e("amap-download", "download: " + " ERROR " + downName);
                break;
            case OfflineMapStatus.EXCEPTION_AMAP:
                Log.e("amap-download", "download: " + " EXCEPTION_AMAP " + downName);
                break;
            case OfflineMapStatus.EXCEPTION_NETWORK_LOADING:
                Log.e("amap-download", "download: " + " EXCEPTION_NETWORK_LOADING "
                        + downName);
//                Toast.makeText(OfflineMapActivity.this, "网络异常", Toast.LENGTH_SHORT)
//                        .show();
                mOfflineMapManager.pause();
                break;
            case OfflineMapStatus.EXCEPTION_SDCARD:
                Log.e("amap-download", "download: " + " EXCEPTION_SDCARD "
                        + downName);
                break;
            default:
                break;
        }

        // changeOfflineMapTitle(status, downName);
        updateList(-1);
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }


    /**
     * Called when the fragment is no longer in use.
     */
    @Override
    public void onDestroy() {
        if (mOfflineMapManager != null) {
            mOfflineMapManager.destroy();
        }
    }

    /**
     * 点击监听
     *
     * @param id 被点击的view的id
     */
    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.download_list_text:
                mView.showDownloadView();
                mDownloadedAdapter.notifyDataSetChanged();
                break;
            case R.id.downloaded_list_text:
                mView.showDownloadedView();
                mDownloadedAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 更新列表
     *
     * @param position 当前位置
     */
    @Override
    public void updateList(int position) {
        if (position == 0) {
            adapter.notifyDataSetChanged();
        } else {
            mDownloadedAdapter.notifyDataChange();
        }
    }
}
