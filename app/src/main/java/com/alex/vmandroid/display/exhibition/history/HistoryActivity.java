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
 */
package com.alex.vmandroid.display.exhibition.history;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.alex.style.formatter.XAxisValueFormatter;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends BaseActivity implements OnChartValueSelectedListener {

    private BarChart barchart;


    private int[][] data = {{1, 0}, {9, 3}, {4, 0}, {3, 0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        barchart = (BarChart) findViewById(R.id.history_bc);

        List<BarEntry> entries = new ArrayList<>();

        barchart.setNoDataText(getResources().getString(R.string.no_data_can_be_displayed));

        XAxis xAxis = barchart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(true);

        xAxis.setValueFormatter(new XAxisValueFormatter(2016, 3));

        initview();
    }

    void initview() {
        barchart.setOnChartValueSelectedListener(this);

        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            entries.add(new BarEntry(i, data[i][0]));
            entries1.add(new BarEntry(i, data[i][1]));
        }


        BarDataSet dataSet = new BarDataSet(entries, "本期");
        dataSet.setColor(Color.YELLOW);//设置第一组数据颜色

        BarDataSet dataSet1 = new BarDataSet(entries1, "上年同期");
        dataSet1.setColor(Color.BLUE);//设置第一组数据颜色

        List<IBarDataSet> mIBarDataSets = new ArrayList<>();
        mIBarDataSets.add(dataSet);
        mIBarDataSets.add(dataSet1);

        BarData barData = new BarData(mIBarDataSets);
        barData.setValueFormatter(new LargeValueFormatter());

        barchart.setData(barData);


        barchart.animateY(1000);//动画
        //barchart.setDescription("");// 取消描述
        barchart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);//设置注解的位置在左上方
        barchart.getAxisLeft().setAxisMinValue(0.0f);//设置Y轴显示最小值，不然0下面会有空隙
        barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置

        Legend l = barchart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        //l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xl = barchart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);


        YAxis leftAxis = barchart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        barchart.getAxisRight().setEnabled(false);


        float groupSpace = 0.04f;
        float barSpace = 0.03f; // x3 dataset
        float barWidth = 0.45f; // x3 dataset

        barchart.getBarData().setBarWidth(barWidth);
        barchart.getXAxis().setAxisMinValue(0);
        Log.i("TAG", "onCreate: " + barchart.getBarData().getGroupWidth(groupSpace, barSpace));
        barchart.getXAxis().setAxisMaxValue(4);
        barchart.groupBars(0, groupSpace, barSpace);
        //mChart.invalidate();

        barchart.invalidate(); // refresh
    }


    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry
     * @param h The corresponding highlight object that contains information
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.i("TAG", "onValueSelected: " + e.toString() + h.toString());

    }

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }
}
