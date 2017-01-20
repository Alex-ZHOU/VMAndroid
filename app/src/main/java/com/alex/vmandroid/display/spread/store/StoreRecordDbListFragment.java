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
import com.alex.vmandroid.display.voice.store.StoreDbRecordActivity;
import com.alex.vmandroid.entities.StoreRecordDb;

import java.util.List;

public class StoreRecordDbListFragment extends BaseFragment
        implements StoreRecordDbListContract.View, AdapterView.OnItemClickListener, View.OnClickListener {

    private StoreRecordDbListContract.Presenter mPresenter;

    private ListView mListView;

    public static StoreRecordDbListFragment newInstance() {
        return new StoreRecordDbListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_record_db_list, container, false);

        mListView = (ListView) view.findViewById(R.id.store_record_db_list_fragment_lv);

        mListView.setOnItemClickListener(this);

        ImageView imageView = (ImageView) view.findViewById(R.id.store_record_db_list_fragment_iv);

        imageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(StoreRecordDbListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置ListView显示的数据
     *
     * @param listViewData 数据
     */
    @Override
    public void setListViewData(List<StoreRecordDb> listViewData) {
        PowerfulAdapter adapter = new PowerfulAdapter<StoreRecordDb>(getActivity(), listViewData, R.layout.item_store_record_list) {

            @Override
            public void convert(ViewHolder var1, StoreRecordDb var2) {
                mPresenter.setImageViewByImageId(((ImageView) var1.getView(R.id.store_record_item_head_portrait_iv)), var2.getUserHeadPortraitImageId());

                var1.setText_TextView(R.id.store_record_item_nickname_tv, var2.getNickName());
                var1.setText_TextView(R.id.store_record_item_time_tv, var2.getYear() + "-" + var2.getMonth() + "-" + var2.getDay());
            }
        };

        mListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    /**
     * 跳转到商家记录界面
     *
     * @param storeId 商家id号
     */
    @Override
    public void showStoreDbRecordActivity(int storeId,String title) {
        Intent intent = new Intent(getActivity(), StoreDbRecordActivity.class);
        intent.putExtra("StoreId", storeId);
        intent.putExtra("StoreTitle", title);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.onItemClick(i);
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }
}
