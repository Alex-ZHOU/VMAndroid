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

package com.alex.vmandroid.display.map.om.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.vmandroid.R;
import com.alex.vmandroid.display.map.om.OfflineChild;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;

import java.util.HashMap;
import java.util.List;


public class OfflineMapListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnGroupCollapseListener, ExpandableListView.OnGroupExpandListener {


    private boolean[] isOpen;// 记录一级目录是否打开

    private List<OfflineMapProvince> provinceList = null;
    private OfflineMapManager amapManager;
    private Context mContext;


    public OfflineMapListAdapter(List<OfflineMapProvince> provinceList,
                              HashMap<Object, List<OfflineMapCity>> cityMap,
                              OfflineMapManager amapManager, Context mContext) {
        this.provinceList = provinceList;
        this.amapManager = amapManager;
        this.mContext = mContext;

        isOpen = new boolean[provinceList.size()];
    }

    public OfflineMapListAdapter(List<OfflineMapProvince> provinceList,
                              OfflineMapManager amapManager, Context mContext) {
        this.provinceList = provinceList;
        this.amapManager = amapManager;
        this.mContext = mContext;

        isOpen = new boolean[provinceList.size()];
    }

    @Override
    public int getGroupCount() {
        return provinceList.size();
    }

    /**
     * 获取一级标签内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return provinceList.get(groupPosition).getProvinceName();
    }

    /**
     * 获取一级标签的ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取一级标签下二级标签的总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (isNormalProvinceGroup(groupPosition)) {
            // 普通省份的第一个位置放省份
            return provinceList.get(groupPosition).getCityList().size() + 1;
        }
        return provinceList.get(groupPosition).getCityList().size();

    }

    /**
     * 获取一级标签下二级标签的内容
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    /**
     * 获取二级标签的ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 对一级标签进行设置
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView group_text;
        ImageView group_image;
        if (convertView == null) {
            convertView = (RelativeLayout) RelativeLayout.inflate(
                    mContext, R.layout.item_offline_map_group, null);
        }
        group_text = (TextView) convertView.findViewById(R.id.group_text);
        group_image = (ImageView) convertView
                .findViewById(R.id.group_image);
        group_text.setText(provinceList.get(groupPosition)
                .getProvinceName());
        if (isOpen[groupPosition]) {
            group_image.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.downarrow));
        } else {
            group_image.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.rightarrow));
        }
        return convertView;
    }

    /**
     * 对一级标签下的二级标签进行设置
     */
    @Override
    public View getChildView(int groupPosition,
                             int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            OfflineChild offLineChild = new OfflineChild(mContext, amapManager);
            convertView = offLineChild.getOffLineChildView();
            viewHolder.mOfflineChild = offLineChild;
            convertView.setTag(viewHolder);
        }
        OfflineMapCity mapCity = null;

        viewHolder.mOfflineChild.setProvince(false);

        if (isNormalProvinceGroup(groupPosition)) {
            if (isProvinceItem(groupPosition, childPosition)) {
                // 如果是省份，为了方便，进行一下处理
                mapCity = getCicy(provinceList.get(groupPosition));
                viewHolder.mOfflineChild.setProvince(true);
            } else {
                // 减1处理，第一个被放置了省份
                mapCity = provinceList.get(groupPosition).getCityList().get(childPosition - 1);
            }
        } else {
            mapCity = provinceList.get(groupPosition).getCityList().get(childPosition);
        }


        viewHolder.mOfflineChild.setOffLineCity(mapCity);

        return convertView;
    }


    private boolean isProvinceItem(int groupPosition, int childPosition) {
        // 不是特殊省份，而且子栏目中第一栏
        return isNormalProvinceGroup(groupPosition) && childPosition == 0;
    }

    /**
     * 是否为普通省份
     * 不是直辖市，概要图，港澳
     *
     * @param groupPosition
     * @return
     */
    private boolean isNormalProvinceGroup(int groupPosition) {
//		return groupPosition != 0 && groupPosition != 1 && groupPosition != 2;
        return groupPosition > 2;
    }

    /**
     * 当选择子节点的时候，调用该方法
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 把一个省的对象转化为一个市的对象
     */
    public OfflineMapCity getCicy(OfflineMapProvince aMapProvince) {
        OfflineMapCity aMapCity = new OfflineMapCity();
        aMapCity.setCity(aMapProvince.getProvinceName());
        aMapCity.setSize(aMapProvince.getSize());
        aMapCity.setCompleteCode(aMapProvince.getcompleteCode());
        aMapCity.setState(aMapProvince.getState());
        aMapCity.setUrl(aMapProvince.getUrl());
        return aMapCity;
    }

    public final class ViewHolder {
        public OfflineChild mOfflineChild;
    }

    public void onGroupCollapse(int groupPosition) {
        isOpen[groupPosition] = false;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        isOpen[groupPosition] = true;
    }
}
