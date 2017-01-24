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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alex.powerfullistview.adapter.PowerfulAdapter;
import com.alex.powerfullistview.holder.ViewHolder;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.display.main.fragments.fragments.LoopAdvertisementContract;
import com.alex.vmandroid.display.spread.advertisement.AdvertisementActivity;
import com.alex.vmandroid.entities.StoreInfo;

import java.util.List;

public class StoreListFragment extends BaseFragment implements StoreListContract.View,AdapterView.OnItemClickListener {

    private StoreListContract.Presenter mPresenter;

    private ListView mListView;

    public static StoreListFragment newInstance() {
        return new StoreListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);

        mListView = (ListView) view.findViewById(R.id.store_list_fragment_lv);

        mListView.setOnItemClickListener(this);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(StoreListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置ListView显示的数据
     *
     * @param listViewData 数据
     */
    @Override
    public void setListViewData(List<StoreInfo> listViewData) {
        PowerfulAdapter adapter = new PowerfulAdapter<StoreInfo>(getActivity(), listViewData, R.layout.item_store_record_list) {

            @Override
            public void convert(ViewHolder var1, StoreInfo var2) {
                mPresenter.setImageViewByImageId(((ImageView) var1.getView(R.id.store_record_item_head_portrait_iv)), var2.getImageId());

                var1.setText_TextView(R.id.store_record_item_nickname_tv, var2.getTitle());
                var1.setText_TextView(R.id.store_record_item_time_tv, var2.getSummary());
            }
        };

        mListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    /**
     * 显示AdvertisementActivity
     *
     * @param storeId 商家广告的id号
     * @param title   商家标题
     */
    @Override
    public void showAdvertisementActivity(int storeId, String title) {
        Intent intent = new Intent(getActivity(), AdvertisementActivity.class);

        intent.putExtra(LoopAdvertisementContract.ADVERTISEMENT_TITLE, title);
        intent.putExtra(LoopAdvertisementContract.ADVERTISEMENT_ID, storeId);

        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.onItemClick(i);
    }
}
