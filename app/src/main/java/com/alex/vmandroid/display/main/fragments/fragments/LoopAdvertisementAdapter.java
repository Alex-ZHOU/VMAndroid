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
package com.alex.vmandroid.display.main.fragments.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.businesses.DownloadPic;
import com.alex.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

public class LoopAdvertisementAdapter extends PagerAdapter {
    /**
     * 上下文
     */
    private Context mContext;

    private Handler handler = new Handler();

    /**
     * 图像列表
     */
//    private List<Integer> pictureList = new ArrayList<>();
    private List<Integer> pictureList;

    public LoopAdvertisementAdapter(Context context, List<Integer> pictureList) {
        mContext = context;
        this.pictureList = pictureList;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(com.alex.view.R.layout.banner_item, container, false);
        final ImageView imageView = (ImageView) view.findViewById(com.alex.view.R.id.iv_banner_item);
        // 获取当前显示位置
        position %= pictureList.size();

        //imageView.setImageResource(pictureList.get(position));
        final  int p = position;
        new DownloadPic().getById(pictureList.get(position), new DownloadPic.Listener() {
            @Override
            public void succeed(final Bitmap bm) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        AppLog.debug("positiont",pictureList.get(p).toString()+" "+pictureList.size()+"+"+p);

                        imageView.setImageBitmap(bm);
                    }
                });

            }

            @Override
            public void failed() {

            }
        });
        container.addView(view);
        return view;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
