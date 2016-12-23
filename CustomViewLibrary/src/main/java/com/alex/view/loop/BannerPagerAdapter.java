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
 *
 * @author Alex_ZHOU
 *
 * @author junweiliu 2016/6/14
 */

package com.alex.view.loop;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * VP适配器
 */
public class BannerPagerAdapter extends PagerAdapter {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图像列表
     */
    private List<Integer> pictureList = new ArrayList<>();
    /**
     * 默认轮播个数
     */
    public static final int FAKE_BANNER_SIZE = 10000;

    public BannerPagerAdapter(Context context, List<Integer> pictureList) {
        this.mContext = context;
        this.pictureList = pictureList;
    }

    @Override
    public int getCount() {
        return FAKE_BANNER_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner_item);
        // 获取当前显示位置
        position %= pictureList.size();
        imageView.setImageResource(pictureList.get(position));
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
