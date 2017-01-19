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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementContract;

public class AdvertisementActivity extends BaseActivity {
    private String mTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        Intent intent = getIntent();
        int advertisementId = intent.getIntExtra(LoopAdvertisementContract.ADVERTISEMENT_ID, -1);
        mTitle = intent.getStringExtra(LoopAdvertisementContract.ADVERTISEMENT_TITLE);

        AdvertisementFragment fragment = AdvertisementFragment.newInstance(advertisementId);
        new AdvertisementPresenter(fragment,this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.advertisement_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }
}
