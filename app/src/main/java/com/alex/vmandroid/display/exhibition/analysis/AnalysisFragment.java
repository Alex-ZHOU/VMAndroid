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
package com.alex.vmandroid.display.exhibition.analysis;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.style.formatter.AnalysisXAxisValueFormatter;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;


import java.util.ArrayList;

public class AnalysisFragment extends BaseFragment implements AnalysisContract.View {

    private AnalysisContract.Presenter mPresenter;

    private ProgressDialog mProgressDialog;

    private RadarChart mRadarChart;

    private TextView mRecordMinterTextView;

    private TextView mRecordTimesTextView;

    private TextView mAverageDbTextView;

    private TextView mMaxMinTextView;

//    private TextView _20TimesTextView;
//    private TextView _40TimesTextView;
//    private TextView _60TimesTextView;
//    private TextView _70TimesTextView;
//    private TextView _90TimesTextView;
//    private TextView _100TimesTextView;
//    private TextView _120TimesTextView;
//    private TextView _120UpTimesTextView;

    private TextView[] mTimesTextViewArray;

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        mRecordMinterTextView = (TextView) view.findViewById(R.id.analysis_fragment_minutes_tv);

        mRecordTimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_record_times_tv);

        mAverageDbTextView = (TextView) view.findViewById(R.id.analysis_fragment_average_tv);

        mMaxMinTextView = (TextView) view.findViewById(R.id.analysis_fragment_max_min_tv);

        mRadarChart = (RadarChart) view.findViewById(R.id.analysis_fragment_rc);

        //mRadarChart.setBackgroundColor(Color.rgb(60, 65, 82));

        mRadarChart.getDescription().setEnabled(false);

        mRadarChart.setWebLineWidth(1f);
        mRadarChart.setWebColor(Color.LTGRAY);
        mRadarChart.setWebLineWidthInner(1f);
        mRadarChart.setWebColorInner(Color.LTGRAY);
        mRadarChart.setWebAlpha(100);

        mRadarChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        TextView _20TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_20_times_tv);
        TextView _40TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_40_times_tv);
        TextView _60TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_60_times_tv);
        TextView _70TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_70_times_tv);
        TextView _90TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_90_times_tv);
        TextView _100TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_100_times_tv);
        TextView _120TimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_120_times_tv);
        TextView _120UpTimesTextView = (TextView) view.findViewById(R.id.analysis_fragment_120_up_times_tv);

        mTimesTextViewArray = new TextView[]{
                _20TimesTextView,
                _40TimesTextView,
                _60TimesTextView,
                _70TimesTextView,
                _90TimesTextView,
                _100TimesTextView,
                _120TimesTextView,
                _120UpTimesTextView};

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getResources().getString(R.string.loading));
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setAxis(AnalysisXAxisValueFormatter formatter, float yAxisMaxValue) {

        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(yAxisMaxValue);
        yAxis.setDrawLabels(false);

        Legend l = mRadarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }

    @Override
    public void setData(float[] data) {


        ArrayList<RadarEntry> entries1 = new ArrayList<>();

        for (float aData : data) {
            entries1.add(new RadarEntry(aData));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, getString(R.string.app_name));
        set1.setColor(Color.rgb(121, 162, 175));
        set1.setFillColor(Color.rgb(121, 162, 175));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarData radarData = new RadarData(set1);
        radarData.setValueTextSize(8f);
        radarData.setDrawValues(false);
        radarData.setValueTextColor(Color.WHITE);

        mRadarChart.setData(radarData);
        mRadarChart.invalidate();
    }

    /**
     * 设置记录的总分钟数
     *
     * @param str 分钟数
     */
    @Override
    public void setRecordMinter(String str) {
        mRecordMinterTextView.setText(str);
    }

    /**
     * 设置记录的次数
     *
     * @param str 记录的次数
     */
    @Override
    public void setRecordTimes(String str) {
        mRecordTimesTextView.setText(str);
    }

    /**
     * 设置平均数
     *
     * @param str 平均数
     */
    @Override
    public void setAverageDb(String str) {
        mAverageDbTextView.setText(str);
    }

    /**
     * 设置最大最小值
     *
     * @param str 最大最小值
     */
    @Override
    public void setMaxMin(String str) {
        mMaxMinTextView.setText(str);
    }

    /**
     * 设置次数数组的内容
     *
     * @param str   设置的内容
     * @param index 数组的索引值
     */
    @Override
    public void setTimesTextViewArray(String str, int index,boolean visibility) {
        if (visibility){
            mTimesTextViewArray[index].setVisibility(View.VISIBLE);
            mTimesTextViewArray[index].setText(str);
        }else{
            mTimesTextViewArray[index].setVisibility(View.GONE);
        }

    }

    @Override
    public void setPresenter(AnalysisContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
