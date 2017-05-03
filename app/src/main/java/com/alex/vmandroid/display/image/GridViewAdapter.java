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
package com.alex.vmandroid.display.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter<T> extends BaseAdapter {


    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mData;
    private int mItemLayoutId;

    /**
     * 文件夹路径
     */
    private String mDirPath;

    public GridViewAdapter(Context context, List<T> data, int itemLayoutId,String dirPath) {
        mContext = context;
        mData = data;
        mItemLayoutId = itemLayoutId;
        mDirPath = dirPath;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }



    static class ViewHolder{

        private final SparseArray<View> mViews;
        private int mPosition;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
            this.mPosition = position;
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder(context, parent, layoutId, position);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
            }
            return holder;
        }

        public View getConvertView()
        {
            return mConvertView;
        }

        public <T extends View> T getView(int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null)
            {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public ViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);

            return this;
        }
        public ViewHolder setImageBitmap(int viewId, Bitmap bm)
        {
            ImageView view = getView(viewId);
            view.setImageBitmap(bm);
            return this;
        }

        public ViewHolder setImageByUrl(int viewId, String url)
        {
            //ImageLoader.getInstance(3,Type.LIFO).loadImage(url, (ImageView) getView(viewId));
            return this;
        }

        public int getPosition()
        {
            return mPosition;
        }


    }
}
