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
package com.alex.vmandroid.display.gadget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.alex.powerfullistview.adapter.PowerfulAdapter;
import com.alex.powerfullistview.holder.ViewHolder;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.display.weather.inquiry.InquiryWeatherActivity;

import java.util.List;

public class GadgetActivity extends BaseActivity implements GadgetContract.View,
        AdapterView.OnItemClickListener {

    private final String TAG = Base_TAG;

    private GadgetContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gadget);

        Log.i(TAG, "onCreate");

        new GadgetPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(GadgetContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemClick: i" + i);
        showInquiryWeather();
    }


    /**
     * 设置ListView显示的数据
     *
     * @param data 需要显示的数据
     */
    @Override
    public void setListViewData(List<String> data) {

        Log.i(TAG, "setListViewData");

        ListView listView = (ListView) findViewById(R.id.gadget_lv);

        PowerfulAdapter adapter = new PowerfulAdapter<String>(getApplicationContext(), data, R.layout.item_single_text_line) {

            @Override
            public void convert(ViewHolder var1, String var2) {
                var1.setText_TextView(R.id.item_single_text_line_tv, var2);
                Log.i(TAG, "convert: " + var2);
            }
        };

        if (listView != null) {
            listView.setOnItemClickListener(this);
            listView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示查询天气的界面
     */
    @Override
    public void showInquiryWeather() {
        Intent intent = new Intent(getApplicationContext(), InquiryWeatherActivity.class);
        startActivity(intent);
    }
}
