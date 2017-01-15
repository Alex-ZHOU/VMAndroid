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
package com.alex.vmandroid.display.exhibition.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alex.powerfullistview.adapter.PowerfulAdapter;
import com.alex.powerfullistview.holder.ViewHolder;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

import java.util.List;


public class HistoryFragment extends BaseFragment implements HistoryContract.View {

    private HistoryContract.Presenter mPresenter;

    //private BarChart mBarChart;

    private ListView mListView;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

//        mBarChart = (BarChart) view.findViewById(R.id.history_fragment_bc);
//        mBarChart.setDrawBarShadow(false);
//        mBarChart.setDrawValueAboveBar(true);
//        mBarChart.getDescription().setEnabled(false);
//        mBarChart.setDrawGridBackground(false);
//        // 左右y轴都不显示
//        mBarChart.getAxisLeft().setEnabled(false);
//        mBarChart.getAxisRight().setEnabled(false);
//        Legend l = mBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);

        mListView = (ListView) view.findViewById(R.id.history_fragment_lv);

        return view;
    }

    @Override
    public void setListViewData(List<HistoryContract.HistoryString> list){

        PowerfulAdapter adapter= new PowerfulAdapter<HistoryContract.HistoryString>(getActivity(), list, R.layout.item_history) {
            @Override
            public void convert(ViewHolder var1, HistoryContract.HistoryString var2) {
                var1.setText_TextView(R.id.item_history_times,var2.getTimes());
                var1.setText_TextView(R.id.item_history_line1,var2.getLine1());
                var1.setText_TextView(R.id.item_history_line2,var2.getLine2());
            }
        };

        mListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
