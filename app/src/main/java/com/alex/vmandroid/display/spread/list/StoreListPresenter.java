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

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.alex.businesses.DownloadPic;
import com.alex.businesses.StoreListBiz;
import com.alex.style.drawable.CircleImageDrawable;
import com.alex.vmandroid.entities.StoreInfo;

import java.util.List;

public class StoreListPresenter implements StoreListContract.Presenter {

    private StoreListContract.View mView;

    private String mType;

    private Context mContext;

    private Handler mHandler = new Handler();

    private List<StoreInfo> mList;

    public StoreListPresenter(StoreListContract.View view, String type, Context applicationContext) {
        mView = view;
        mView.setPresenter(this);
        mType = type;
        mContext = applicationContext;
    }

    @Override
    public void start() {
        new StoreListBiz().get(mType, new StoreListBiz.Listener() {
            @Override
            public void succeed(List<StoreInfo> list) {
                mList = list;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setListViewData(mList);
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
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageDrawable(new CircleImageDrawable(bm));
                    }
                });
            }

            @Override
            public void failed() {

            }
        }, mContext);
    }

    /**
     * 监听ListView 点击的item号
     *
     * @param i item号
     */
    @Override
    public void onItemClick(int i) {
        mView.showAdvertisementActivity(mList.get(i).getAdvertisementId(), mList.get(i).getTitle());
    }

}
