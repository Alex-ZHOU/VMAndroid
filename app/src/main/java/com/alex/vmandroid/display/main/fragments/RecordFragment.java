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

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.MainContract;
import com.alex.vmandroid.display.voice.db.RecordDBActivity;
import com.alex.vmandroid.display.weather.location.LocationWeatherActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.alex.utils.PermissionRequest.LOCATION_MICROPHONE_PERM;

/**
 * 记录界面显示的fragment
 */
public class RecordFragment extends BaseFragment implements MainContract.RecordView, View.OnClickListener,
        LocationSource, AMapLocationListener, AMap.OnMapLoadedListener {
    public final String TAG = Base_TAG;

    private MainContract.MainPresenter mPresenter;

    private TextView mRealTimeNoiseTextView;

    private MapView mapView;

    private TextView mCityTextView;

    private TextView mWeatherView;

    private String mCity;

    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;

    private AMap mAMap;

    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @AfterPermissionGranted(LOCATION_MICROPHONE_PERM)
    @Override
    public void onResume() {
        super.onResume();

        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            mPresenter.startRecord(getContext());
            mapView.onResume();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "",
                    LOCATION_MICROPHONE_PERM, perms);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.closeRecord();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: RecordFragment");

        View view = inflater.inflate(R.layout.fragment_main_record, container, false);

        // 标题栏记录按钮
        ImageView recordBeginImageView = (ImageView) view.findViewById(R.id.main_record_title_bar_record_begin_iv);
        recordBeginImageView.setOnClickListener(this);

        mRealTimeNoiseTextView = (TextView) view.findViewById(R.id.main_record_real_db_tv);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        // 天气模块
        mCityTextView = (TextView) view.findViewById(R.id.main_record_location_tv);
        mWeatherView = (TextView) view.findViewById(R.id.main_record_weather_tv);
        LinearLayout weather = (LinearLayout) view.findViewById(R.id.main_record_location_weather_ll);
        weather.setOnClickListener(this);


        // FIXME: 26/10/2016 mvp
        mAMap = mapView.getMap();
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.setOnMapLoadedListener(this);
        //mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示

        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        //  mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
//        mAMap.moveCamera(CameraUpdateFactory.zoomBy(4));

        view.findViewById(R.id.main_record_total_ll).setOnClickListener(this);

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void setPresenter(MainContract.MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    /**
     * 跳转到记录噪声分贝界面
     */
    @Override
    public void showRecordDBActivity() {
        Log.i(TAG, "showRecordDBActivity");
        Intent intent = new Intent(getActivity(), RecordDBActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到显示历史记录界面
     */
    @Override
    public void showHistoryActivity() {
        Log.i(TAG, "showHistoryActivity");
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
    }

    @Override
    public void setWeather(String str) {
        mWeatherView.setText(str);
    }

    /**
     * 更新实时的噪声数值
     *
     * @param d 实时噪声数值
     */
    @Override
    public void updateRealTimeNoise(double d) {

        mRealTimeNoiseTextView.setText(String.valueOf(d));

    }

    /**
     * 显示详细当地天气信息
     */
    @Override
    public void showLocationWeatherActivity() {
        Intent intent = new Intent(getActivity(), LocationWeatherActivity.class);
        intent.putExtra("LocationCity", mCity);
        startActivity(intent);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    /**
     * 定位监听回掉
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mCity = amapLocation.getCity();
                mCityTextView.setText(mCity);

                mPresenter.searchWeatherRecord(getContext(), amapLocation.getCity());

                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                //mLocationErrText.setVisibility(View.VISIBLE);
                //mLocationErrText.setText(errText);
            }
        }
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId(), MainContract.RECORD_TAG);
    }


    @Override
    public void onMapLoaded() {

        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
        mAMap.showBuildings(true);
    }

}
