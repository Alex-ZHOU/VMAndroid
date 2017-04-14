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
package com.alex.vmandroid.display.exhibition.share;

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
import com.alex.vmandroid.entities.ShareRecordDb;

import java.util.List;

public class ShareFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, ShareContract.View {

    private ShareContract.Presenter mPresenter;

    private ListView mListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        mListView = (ListView) view.findViewById(R.id.share_fragment_lv);

        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(ShareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置ListView显示的数据
     *
     * @param listViewData 数据
     */
    @Override
    public void setListViewData(List<ShareRecordDb> listViewData) {

        PowerfulAdapter adapter = new PowerfulAdapter<ShareRecordDb>(getActivity(), listViewData, R.layout.item_share) {


            @Override
            public void convert(ViewHolder var1, ShareRecordDb var2) {
                mPresenter.setImageViewByImageId(((ImageView) var1.getView(R.id.share_item_head_portrait_iv)), var2.getUserHeadPortraitImageId());

                var1.setText_TextView(R.id.share_item_store_name_tv, var2.getStoreName());
                var1.setText_TextView(R.id.share_item_time_tv, var2.getTime());
            }
        };

        mListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.onItemClick(i);
    }
}
