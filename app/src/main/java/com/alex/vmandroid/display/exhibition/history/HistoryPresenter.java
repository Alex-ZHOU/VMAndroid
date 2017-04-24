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

import android.content.Context;
import android.os.Handler;

import com.alex.businesses.HistoryBiz;
import com.alex.vmandroid.databases.UserInfo;
import com.alex.vmandroid.entities.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View mView;

    private Context mContext;

    private Handler handler = new Handler();

    private List<History> mHistoryList;

    public HistoryPresenter(HistoryContract.View view, Context context) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        new HistoryBiz().getData(UserInfo.getUsrId(mContext), new HistoryBiz.Listener() {
            @Override
            public void succeed(final List<History> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setListViewData(list);
                    }
                });
            }

            @Override
            public void failed() {

            }
        });
    }


    private void setListViewData(List<History> historyList) {
        if (historyList.size() > 0) {
            mHistoryList = historyList;
            List<HistoryContract.HistoryString> list = new ArrayList<>();


            for (int i = 0; i < historyList.size(); i++) {

                History history = historyList.get(i);
                HistoryContract.HistoryString hchs = new HistoryContract.HistoryString();
                hchs.setTimes(String.valueOf(history.getTimes()));

                hchs.setLine2(history.getYear() + "-" + history.getMonth() + "-" + history.getDay());

                hchs.setLine1(history.getRecordList().get(history.getRecordList().size() - 1).getTime());

                list.add(hchs);
            }

            mView.setListViewData(list);
        } else {
            mView.showToast("查询无历史记录");
        }

    }

    @Override
    public void onItemClickListener(int i) {
        mView.showHistoricalDetailsActivity(mHistoryList.get(i));
    }
}
