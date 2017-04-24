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
package com.alex.vmandroid.display.exhibition.analysis2;

import android.content.Context;
import android.os.Handler;

import com.alex.style.formatter.AnalysisXAxisValueFormatter;
import com.alex.utils.AppLog;
import com.alex.vmandroid.entities.History;

import java.util.ArrayList;
import java.util.List;

public class AnalysisChartPresenter implements AnalysisChartContract.Presenter {

    private AnalysisChartContract.View mView;

    private Context mContext;

    private Handler mHandler = new Handler();

    private AnalysisChart mAnalysisChart = null;

    public AnalysisChartPresenter(AnalysisChartContract.View view, Context context, History history) {
        this.setView(view);
        this.setContext(context);

        mAnalysisChart = new AnalysisChart();

        List<History.Data> list = history.getRecordList();

        for (int i = 0; i < list.size(); i++) {
            History.Data data = list.get(i);

            int thisDb = data.getDb();

            if (thisDb <= 20) {
                mAnalysisChart.set_20Times(mAnalysisChart.get_20Times() + 1);
            } else if (thisDb <= 40) {
                mAnalysisChart.set_40Times(mAnalysisChart.get_40Times() + 1);
            } else if (thisDb <= 60) {
                mAnalysisChart.set_60Times(mAnalysisChart.get_60Times() + 1);
            } else if (thisDb <= 70) {
                mAnalysisChart.set_70Times(mAnalysisChart.get_70Times() + 1);
            } else if (thisDb <= 90) {
                mAnalysisChart.set_90Times(mAnalysisChart.get_90Times() + 1);
            } else if (thisDb <= 100) {
                mAnalysisChart.set_100Times(mAnalysisChart.get_100Times() + 1);
            } else if (thisDb <= 120) {
                mAnalysisChart.set_120Times(mAnalysisChart.get_120Times() + 1);
            } else {
                mAnalysisChart.set_120UpTimes(mAnalysisChart.get_120UpTimes() + 1);
            }

        }

        showAnalysis();

    }

    public AnalysisChartPresenter(AnalysisChartContract.View view, Context context) {
        this.setView(view);
        this.setContext(context);
    }

    private void setContext(Context context) {
        mContext = context;
    }

    private void setView(AnalysisChartContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    private void showAnalysis() {
        int max = -1;
        List<Float> timesList = new ArrayList<>();
        final AnalysisXAxisValueFormatter formatter = new AnalysisXAxisValueFormatter();
        if (mAnalysisChart.get_20Times() != 0) {
            timesList.add((float) mAnalysisChart.get_20Times());
            max = max > mAnalysisChart.get_20Times() ? max : mAnalysisChart.get_20Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._20_TIMES);
        }
        if (mAnalysisChart.get_40Times() != 0) {
            timesList.add((float) mAnalysisChart.get_40Times());
            max = max > mAnalysisChart.get_40Times() ? max : mAnalysisChart.get_40Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._40_TIMES);
        }
        if (mAnalysisChart.get_60Times() != 0) {
            timesList.add((float) mAnalysisChart.get_60Times());
            max = max > mAnalysisChart.get_60Times() ? max : mAnalysisChart.get_60Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._60_TIMES);
        }
        if (mAnalysisChart.get_70Times() != 0) {
            timesList.add((float) mAnalysisChart.get_70Times());
            max = max > mAnalysisChart.get_70Times() ? max : mAnalysisChart.get_70Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._70_TIMES);
        }
        if (mAnalysisChart.get_90Times() != 0) {
            timesList.add((float) mAnalysisChart.get_90Times());
            max = max > mAnalysisChart.get_90Times() ? max : mAnalysisChart.get_90Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._90_TIMES);
        }
        if (mAnalysisChart.get_100Times() != 0) {
            timesList.add((float) mAnalysisChart.get_100Times());
            max = max > mAnalysisChart.get_100Times() ? max : mAnalysisChart.get_100Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._100_TIMES);
        }
        if (mAnalysisChart.get_120Times() != 0) {
            timesList.add((float) mAnalysisChart.get_120Times());
            max = max > mAnalysisChart.get_120Times() ? max : mAnalysisChart.get_120Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._120_TIMES);
        }
        if (mAnalysisChart.get_120UpTimes() != 0) {
            timesList.add((float) mAnalysisChart.get_120UpTimes());
            max = max > mAnalysisChart.get_120UpTimes() ? max : mAnalysisChart.get_120UpTimes();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._120_UP_TIMES);
        }
        float f = 80f / (float) max;
        //80 = f *max;
        AppLog.debug(max + "max");
        final float data[] = new float[timesList.size()];
        for (int i = 0; i < timesList.size(); i++) {
            timesList.set(i, f * timesList.get(i));
            data[i] = timesList.get(i);
            AppLog.debug(f + " " + data[i]);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {

                mView.setData(data);
                mView.setAxis(formatter, 100);

                mView.setTimesTextViewArray("0-20分贝,很静、几乎感觉不到:"+mAnalysisChart.get_20Times(),0,mAnalysisChart.get_20Times()!=0);
                mView.setTimesTextViewArray("20-40分贝,安静、犹如轻声絮语:"+mAnalysisChart.get_40Times(),1,mAnalysisChart.get_40Times()!=0);
                mView.setTimesTextViewArray("40-60分贝,一般、普通室内谈话:"+mAnalysisChart.get_60Times(),2,mAnalysisChart.get_60Times()!=0);
                mView.setTimesTextViewArray("60-70分贝,吵闹、有损神经:"+mAnalysisChart.get_70Times(),3,mAnalysisChart.get_70Times()!=0);
                mView.setTimesTextViewArray("70-90分贝,很吵、神经细胞受到破坏:"+mAnalysisChart.get_90Times(),4,mAnalysisChart.get_90Times()!=0);
                mView.setTimesTextViewArray("90-100分贝,吵闹加剧、听力受损:"+mAnalysisChart.get_100Times(),5,mAnalysisChart.get_100Times()!=0);
                mView.setTimesTextViewArray("100-120分贝,难以忍受、呆一分钟即暂时致聋:"+mAnalysisChart.get_120Times(),6,mAnalysisChart.get_120Times()!=0);
                mView.setTimesTextViewArray("120+分贝,极度聋或全聋:"+mAnalysisChart.get_120UpTimes(),7,mAnalysisChart.get_120UpTimes()!=0);
            }
        });

    }

    private class AnalysisChart {
        /**
         * 0 －20 分贝 很静、几乎感觉不到
         */
        private int _20Times;


        /**
         * 20 －40 分贝 安静、犹如轻声絮语
         */
        private int _40Times;

        /**
         * 40 －60 分贝 一般、普通室内谈话
         */
        private int _60Times;
        /**
         * 60 －70 分贝 吵闹、有损神经；
         */
        private int _70Times;

        /**
         * 70 －90 分贝 很吵、神经细胞受到破坏
         */
        private int _90Times;

        /**
         * 90 －100 分贝 吵闹加剧、听力受损
         */
        private int _100Times;

        /**
         * 100 －120 分贝 难以忍受、呆一分钟即暂时致聋
         */
        private int _120Times;

        /**
         * 120分贝以上 极度聋或全聋
         */
        private int _120UpTimes;

        public int get_20Times() {
            return _20Times;
        }

        public void set_20Times(int _20Times) {
            this._20Times = _20Times;
        }

        public int get_120UpTimes() {
            return _120UpTimes;
        }

        public void set_120UpTimes(int _120UpTimes) {
            this._120UpTimes = _120UpTimes;
        }

        public int get_120Times() {
            return _120Times;
        }

        public void set_120Times(int _120Times) {
            this._120Times = _120Times;
        }

        public int get_100Times() {
            return _100Times;
        }

        public void set_100Times(int _100Times) {
            this._100Times = _100Times;
        }

        public int get_90Times() {
            return _90Times;
        }

        public void set_90Times(int _90Times) {
            this._90Times = _90Times;
        }

        public int get_70Times() {
            return _70Times;
        }

        public void set_70Times(int _70Times) {
            this._70Times = _70Times;
        }

        public int get_60Times() {
            return _60Times;
        }

        public void set_60Times(int _60Times) {
            this._60Times = _60Times;
        }

        public int get_40Times() {
            return _40Times;
        }

        public void set_40Times(int _40Times) {
            this._40Times = _40Times;
        }
    }
}
