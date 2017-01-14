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

import android.content.Context;
import android.os.Handler;

import com.alex.businesses.AnalysisBiz;
import com.alex.style.formatter.AnalysisXAxisValueFormatter;
import com.alex.utils.AppLog;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.entities.Analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisPresenter implements AnalysisContract.Presenter {


    private AnalysisContract.View mView;

    private Context mContext;

    private Analysis mAnalysis;

    private Handler handle = new Handler();

    public AnalysisPresenter(AnalysisContract.View view, Context context) {
        mView = view;
        mContext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgressDialog();

        new AnalysisBiz().getData(UserInfo.getUsrId(mContext), new AnalysisBiz.Listener() {
            @Override
            public void succeed(Analysis analysis) {
                mView.closeProgressDialog();
                if (analysis != null) {
                    mAnalysis = analysis;
                    showAnalysis();
                }
            }

            @Override
            public void failed() {
                mView.closeProgressDialog();
            }
        });
    }


    @Deprecated
    private void showAnalysis() {
        int max = -1;
        List<Float> timesList = new ArrayList<>();
        final AnalysisXAxisValueFormatter formatter = new AnalysisXAxisValueFormatter();
        if (mAnalysis.get_20Times() != 0) {
            timesList.add((float) mAnalysis.get_20Times());
            max = max > mAnalysis.get_20Times() ? max : mAnalysis.get_20Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._20_TIMES);
        }
        if (mAnalysis.get_40Times() != 0) {
            timesList.add((float) mAnalysis.get_40Times());
            max = max > mAnalysis.get_40Times() ? max : mAnalysis.get_40Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._40_TIMES);
        }
        if (mAnalysis.get_60Times() != 0) {
            timesList.add((float) mAnalysis.get_60Times());
            max = max > mAnalysis.get_60Times() ? max : mAnalysis.get_60Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._60_TIMES);
        }
        if (mAnalysis.get_70Times() != 0) {
            timesList.add((float) mAnalysis.get_70Times());
            max = max > mAnalysis.get_70Times() ? max : mAnalysis.get_70Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._70_TIMES);
        }
        if (mAnalysis.get_90Times() != 0) {
            timesList.add((float) mAnalysis.get_90Times());
            max = max > mAnalysis.get_90Times() ? max : mAnalysis.get_90Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._90_TIMES);
        }
        if (mAnalysis.get_100Times() != 0) {
            timesList.add((float) mAnalysis.get_100Times());
            max = max > mAnalysis.get_100Times() ? max : mAnalysis.get_100Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._100_TIMES);
        }
        if (mAnalysis.get_120Times() != 0) {
            timesList.add((float) mAnalysis.get_120Times());
            max = max > mAnalysis.get_120Times() ? max : mAnalysis.get_120Times();
            formatter.addXAxisText(AnalysisXAxisValueFormatter._120_TIMES);
        }
        if (mAnalysis.get_120UpTimes() != 0) {
            timesList.add((float) mAnalysis.get_120UpTimes());
            max = max > mAnalysis.get_120UpTimes() ? max : mAnalysis.get_120UpTimes();
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

        handle.post(new Runnable() {
            @Override
            public void run() {
                mView.setRecordMinter(String.valueOf(mAnalysis.getRecordMinter()));
                mView.setRecordTimes(String.valueOf(mAnalysis.getTimes()));
                mView.setAverageDb(String.valueOf(mAnalysis.getAverageDb()));
                mView.setMaxMin(String.valueOf(mAnalysis.getMaxDb()+"/"+mAnalysis.getMinDb()));

                mView.setData(data);
                mView.setAxis(formatter, 100);

                mView.setTimesTextViewArray("0-20分贝,很静、几乎感觉不到:"+mAnalysis.get_20Times(),0,mAnalysis.get_20Times()!=0);
                mView.setTimesTextViewArray("20-40分贝,安静、犹如轻声絮语:"+mAnalysis.get_40Times(),1,mAnalysis.get_40Times()!=0);
                mView.setTimesTextViewArray("40-60分贝,一般、普通室内谈话:"+mAnalysis.get_60Times(),2,mAnalysis.get_60Times()!=0);
                mView.setTimesTextViewArray("60-70分贝,吵闹、有损神经:"+mAnalysis.get_70Times(),3,mAnalysis.get_70Times()!=0);
                mView.setTimesTextViewArray("70-90分贝,很吵、神经细胞受到破坏:"+mAnalysis.get_90Times(),4,mAnalysis.get_90Times()!=0);
                mView.setTimesTextViewArray("90-100分贝,吵闹加剧、听力受损:"+mAnalysis.get_100Times(),5,mAnalysis.get_100Times()!=0);
                mView.setTimesTextViewArray("100-120分贝,难以忍受、呆一分钟即暂时致聋:"+mAnalysis.get_120Times(),6,mAnalysis.get_120Times()!=0);
                mView.setTimesTextViewArray("120+分贝,极度聋或全聋:"+mAnalysis.get_120UpTimes(),7,mAnalysis.get_120UpTimes()!=0);
            }
        });

    }
}
