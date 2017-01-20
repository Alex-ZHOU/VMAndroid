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
package com.alex.vmandroid.display.spread.store;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.alex.businesses.DownloadPic;
import com.alex.businesses.StoreRecordDbListBiz;
import com.alex.vmandroid.R;
import com.alex.vmandroid.entities.StoreRecordDb;

import java.util.List;

public class StoreRecordDbListPresenter implements StoreRecordDbListContract.Presenter {

    private final String mStoreTitle;
    private int mStoreId;

    private StoreRecordDbListContract.View mView;

    private Handler handler = new Handler();

    private List<StoreRecordDb> mStoreRecordDbList;

    public StoreRecordDbListPresenter(StoreRecordDbListContract.View view, int storeId,String storeTitle) {
        mView = view;
        mView.setPresenter(this);
        mStoreId = storeId;
        mStoreTitle = storeTitle;
    }

    @Override
    public void start() {
        new StoreRecordDbListBiz().get(mStoreId, new StoreRecordDbListBiz.Listener() {
            @Override
            public void succeed(final List<StoreRecordDb> list) {
                mStoreRecordDbList = list;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setListViewData(mStoreRecordDbList);
                    }
                });
            }

            @Override
            public void failed() {

            }
        });
    }

    /**
     * 设置ListView的用户头像
     *
     * @param iv ImageView
     * @param id 图片的id号
     */
    @Override
    public void setImageViewByImageId(final ImageView iv, int id) {
        new DownloadPic().getById(id, new DownloadPic.Listener() {
            @Override
            public void succeed(final Bitmap bm) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bm);
                    }
                });
            }

            @Override
            public void failed() {

            }
        });
    }

    /**
     * 监听ListView 点击的item号
     *
     * @param i item号
     */
    @Override
    public void onItemClick(int i) {
        StoreRecordDb storeRecordDb = mStoreRecordDbList.get(i);
    }

    /**
     * 点击监听
     *
     * @param id 点击的view的id号
     */
    @Override
    public void onClick(int id) {
        if (id == R.id.store_record_db_list_fragment_iv){
            mView.showStoreDbRecordActivity(mStoreId,mStoreTitle);
        }
    }
}
