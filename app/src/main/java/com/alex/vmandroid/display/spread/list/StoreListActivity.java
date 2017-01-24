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
package com.alex.vmandroid.display.spread.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.alex.utils.ActivityName;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;

public class StoreListActivity extends BaseActivity {
    private String mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        Intent intent = getIntent();
        mType = intent.getStringExtra("Type");

        StoreListFragment fragment = StoreListFragment.newInstance();
        new StoreListPresenter(fragment, mType, getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.store_list_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            switch (mType){
                case "bar":
                    actionBar.setTitle(R.string.bar);
                    break;
                case "ktv":
                    actionBar.setTitle(R.string.ktv);
                    break;
                case "restaurant":
                    actionBar.setTitle(R.string.restaurant);
                    break;
                case "other":
                    actionBar.setTitle(R.string.other);
                    break;
                default:
                    break;
            }
        }
    }
}
