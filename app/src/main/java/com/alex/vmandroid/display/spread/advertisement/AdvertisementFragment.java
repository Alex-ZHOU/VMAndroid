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
package com.alex.vmandroid.display.spread.advertisement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.utils.EllipsizeTextView;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.spread.store.StoreRecordDbListActivity;
import com.alex.vmandroid.entities.Login;
import com.alex.vmandroid.entities.StoreInfo;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

public class AdvertisementFragment extends BaseFragment implements View.OnClickListener, AdvertisementContract.View {

    private AdvertisementContract.Presenter mPresenter;

    private static int mAdvertisementId;

    private ImageView mImageView;

    private TextView mVoiceStatusTextView;

    private TextView mSummaryTextView;

    private MapView mMapView;

    private AMap mAMap;

    private TextView mAddressTextView;


    public static AdvertisementFragment newInstance(int advertisementId) {
        mAdvertisementId = advertisementId;
        return new AdvertisementFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advertisement, container, false);

        mImageView = (ImageView) view.findViewById(R.id.advertisement_frame_layout_iv);

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.advertisement_frame_layout_voice_status_rl);
        relativeLayout.setOnClickListener(this);

        mVoiceStatusTextView = (TextView) view.findViewById(R.id.advertisement_frame_layout_voice_status_tv);

        mMapView = (MapView) view.findViewById(R.id.advertisement_frame_layout_mv);
        mMapView.onCreate(savedInstanceState);

        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }

        mAddressTextView = (TextView) view.findViewById(R.id.advertisement_frame_layout_address_mv);

        mSummaryTextView = (TextView) view.findViewById(R.id.advertisement_frame_layout_summary_tv);

        return view;
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void setPresenter(AdvertisementContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置图片显示内容
     *
     * @param bim Bitmap
     */
    @Override
    public void setImageViewByBtm(Bitmap bim) {
        mImageView.setImageBitmap(bim);
    }

    /**
     * 设置声音环境文本
     *
     * @param str 要显示的文本
     */
    @Override
    public void setVoiceStatusTextViewText(String str) {
        mVoiceStatusTextView.setText(str);
    }

    /**
     * 设置简介文本
     *
     * @param str 要显示的文本
     */
    @Override
    public void setSummaryTextViewText(String str) {
        EllipsizeTextView.toggleEllipsize(mSummaryTextView, str);
    }

    /**
     * 获取广告的id号
     *
     * @return 广告id号
     */
    @Override
    public int getAdvertisementId() {
        return mAdvertisementId;
    }

    /**
     * 地图跳转到经纬度的位置
     *
     * @param latitude  latitude
     * @param longitude longitude
     */
    @Override
    public void changeLocationToStore(double latitude, double longitude) {
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(latitude, longitude), 18, 0, 30)), 1000, null);
        mAMap.clear();
        mAMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    /**
     * 设置地址显示的TextView是否显示
     *
     * @param i 设置显示与否的参数
     */
    @Override
    public void setAddressTextViewVisibility(int i) {
        mAddressTextView.setVisibility(i);
    }

    /**
     * 设置AddressTextView的显示内容
     *
     * @param str 内容
     */
    @Override
    public void setAddressTextViewText(@NonNull String str) {
        mAddressTextView.setText(str);
    }

    /**
     * 显示记录列表
     */
    @Override
    public void showStoreRecordDbListActivity(StoreInfo storeInfo) {
        Intent intent = new Intent(getActivity(), StoreRecordDbListActivity.class);
        intent.putExtra("StoreId", mAdvertisementId);
        intent.putExtra("StoreTitle", storeInfo.getTitle());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }
}
