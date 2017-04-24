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
package com.alex.vmandroid.display.exhibition.history.details;

import android.content.Context;

import com.alex.utils.AverageAlgorithm;
import com.alex.vmandroid.entities.History;

import java.util.List;

public class HistoricalDetailsPresenter implements HistoricalDetailsContract.Presenter {

    private HistoricalDetailsContract.View mView;

    private Context mContext;

    private History mHistory;

    private int mMax = Integer.MIN_VALUE;

    private int mMin = Integer.MAX_VALUE;


    public HistoricalDetailsPresenter(HistoricalDetailsContract.View view,
                                      Context context, History history) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;
        mHistory = history;
    }

    @Override
    public void start() {
        List<History.Data> dataList = mHistory.getRecordList();
        int a[] = new int[dataList.size()];

        for (int i = 0; i < dataList.size(); i++) {
            History.Data data = dataList.get(i);

            int db = data.getDb();

            if (db > mMax) {
                mMax = db;
            }

            if (db < mMin) {
                mMin = db;
            }

            a[i] = db;
        }

        int average = (int) new AverageAlgorithm().getAverage(a);

        mView.setAverageTextView(String.valueOf(average));
        mView.setMaxTextView(String.valueOf(mMax));
        mView.setMinTextView(String.valueOf(mMin));
        mView.setTimekeeperTextView(String.valueOf(mHistory.getRecordList().get(dataList.size() - 1).getTimekeeper()));
        mView.setRecordTimeTextView(mHistory.getRecordList().get(mHistory.getRecordList().size() - 1).getTime());
    }
}
