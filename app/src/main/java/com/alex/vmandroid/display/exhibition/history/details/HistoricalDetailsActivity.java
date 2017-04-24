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
package com.alex.vmandroid.display.exhibition.history.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.display.exhibition.analysis2.AnalysisChartFragment;
import com.alex.vmandroid.display.exhibition.analysis2.AnalysisChartPresenter;
import com.alex.vmandroid.display.exhibition.history.HistoryFragment;
import com.alex.vmandroid.display.exhibition.history.HistoryPresenter;
import com.alex.vmandroid.entities.History;

public class HistoricalDetailsActivity extends BaseActivity{

    public static final String TAG = HistoricalDetailsActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_details);


        Intent intent = getIntent();
        History mMission = (History) intent.getSerializableExtra("HistoryData");


        AnalysisChartFragment fragment = AnalysisChartFragment.newInstance();
        new AnalysisChartPresenter(fragment,getApplicationContext(),mMission);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.historical_details_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
