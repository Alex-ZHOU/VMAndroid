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
package com.alex.vmandroid.display.voice.db;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.receivers.RecordDBReceiver;
import com.alex.vmandroid.services.RecordDBService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

public class RecordDBFragment extends BaseFragment implements View.OnClickListener, RecordDBContract.View,OnChartValueSelectedListener {

    private RecordDBContract.Presenter mPresenter;

    private TextView mDBTextView;

    private TextView mDurationTimeView;

    private TextView mAverageTextView;

    private TextView mMaxTextView;

    private Button mBtn;

    private LineChart mLineChart;

    private MessageReceiver mReceiver;

    private ProgressDialog mProgressDialog;

    public RecordDBFragment() {
        new RecordDBPresenter(this);
    }

    public static RecordDBFragment newInstance() {
        return new RecordDBFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new RecordDBFragment.MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RecordDBReceiver.ACTION);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_db_record_db, container, false);

        mDBTextView = (TextView) view.findViewById(R.id.record_db_fragment_db_tv);

        mDurationTimeView = (TextView) view.findViewById(R.id.record_db_fragment_duration_tv);

        mAverageTextView = (TextView) view.findViewById(R.id.record_db_fragment_average_tv);

        mMaxTextView = (TextView) view.findViewById(R.id.record_db_fragment_max_tv);

        mBtn = (Button) view.findViewById(R.id.record_db_fragment_switch_btn);
        mBtn.setOnClickListener(this);

        mLineChart = (LineChart) view.findViewById(R.id.record_db_fragment_info_lc);
        mLineChart.setOnChartValueSelectedListener(this);
        // 设置表描述为不可见
        mLineChart.getDescription().setEnabled(false);
        // 设置表描述可见，颜色值以及显示的文本
        // mLineChart.getDescription().setEnabled(true);
        // mLineChart.getDescription().setText(getString(R.string.db));
        // mLineChart.getDescription().setTextColor(Color.WHITE);
        // enable touch gestures
        mLineChart.setTouchEnabled(true);
        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);
        // set an alternative background color
        mLineChart.setBackgroundColor(Color.parseColor("#6A1B9A") );
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mLineChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(mTfLight);
        l.setTextColor(Color.WHITE);

        // 获得图表的x轴
        XAxis xAxis = mLineChart.getXAxis();
        // 设置x轴的文本颜色为白色
        xAxis.setTextColor(Color.WHITE);
        // 不设置纵向的网格线，即与x轴垂直的线
        xAxis.setDrawGridLines(false);
        // 避免图表剪辑的边缘是屏幕
        xAxis.setAvoidFirstLastClipping(true);
        // 设置x轴可见
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxis = mLineChart.getAxisLeft();
        leftYAxis.setTextColor(Color.WHITE);
        leftYAxis.setAxisMaximum(100f);
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setDrawGridLines(true);
        // 获取右y轴并将其设置为可见
        YAxis rightYAxis  = mLineChart.getAxisRight();
        rightYAxis.setEnabled(true);
        rightYAxis.setDrawGridLines(false);
        // 设置与背景颜色相同使文本不可见
        rightYAxis.setTextColor(Color.parseColor("#6A1B9A"));

        addEntry(UserInfo.getInt(getContext(),"AverageDb"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void setPresenter(RecordDBContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    /**
     * 设置被点击按钮的文本
     *
     * @param str 需要显示的文本
     */
    @Override
    public void setButtonText(int str) {
        mBtn.setText(str);
    }

    /**
     * 设置显示分贝的view显示的内容
     *
     * @param dbOrId 分贝数或者资源id
     */
    @Override
    public void setDBTextView(int dbOrId) {
        if (dbOrId < 120) {
            mDBTextView.setText(String.valueOf(dbOrId));
            addEntry(dbOrId);
        } else {
            mDBTextView.setText(dbOrId);
        }
    }

    /**
     * 设置记录的时长
     *
     * @param time 时长
     */
    @Override
    public void setDurationTimeView(String time) {
        mDurationTimeView.setText(time);
    }

    /**
     * 设置显示平均值
     *
     * @param average 平均值
     */
    @Override
    public void setAverageTextView(@NonNull String average) {
        mAverageTextView.setText(average);
    }

    /**
     * 设置显示最大值
     *
     * @param max 最大值
     */
    @Override
    public void setMaxTextView(@NonNull String max) {
        mMaxTextView.setText(max);
    }

    /**
     * 启动记录服务
     */
    @Override
    public void startService() {
        Intent intent = new Intent(getContext(), RecordDBService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(RecordDBService.RecordDBServiceName);
        getContext().startService(intent);
    }

    /**
     * 关闭记录服务
     */
    @Override
    public void uploadAndStopService() {
        Intent intent = new Intent(RecordDBService.RecordDBServiceAction);
        intent.putExtra(RecordDBReceiver.RECORD_DB_UPLOAD_DATA, true);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getResources().getString(R.string.uploading));
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
    public void showToast(int i) {
        Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    /**
     * 获取上下文内容
     *
     * @return 上下文
     */
    @Override
    public Context getViewContext() {
        return getContext();
    }

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry
     * @param h The corresponding highlight object that contains information
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }

    private void addEntry(int db) {

        LineData data = mLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), db), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mLineChart.notifyDataSetChanged();

            // limit the number of visible entries
            mLineChart.setVisibleXRangeMaximum(60);
            //mLineChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mLineChart.moveViewToX(data.getEntryCount());

        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, getString(R.string.app_name));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(1f);
        set.setCircleRadius(1.5f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RecordDBReceiver.ACTION)) {
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_DB)) {
                    int db = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_DB, -1);
                    mPresenter.getDB(db);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_TIME)) {
                    String time = intent.getStringExtra(RecordDBReceiver.RECORD_DB_RECEIVER_TIME);
                    mPresenter.getTime(time);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_MAX_DB)) {
                    int max = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_MAX_DB, -1);
                    mPresenter.getMaxDB(max);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_AVERAGE_DB)) {
                    int average = intent.getIntExtra(RecordDBReceiver.RECORD_DB_RECEIVER_AVERAGE_DB, -1);
                    mPresenter.getAverageDB(average);
                }
                if (intent.getExtras().containsKey(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED)) {
                    //boolean isSucceed = intent.getBooleanExtra(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED, false);
                    mPresenter.isUploadSucceed(intent.getBooleanExtra(RecordDBReceiver.RECORD_DB_RECEIVER_UPLOAD_SUCCEED, false));
                }
            }
        }
    }
}
