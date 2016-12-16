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
package com.alex.vmandroid.display.gadget;

import java.util.ArrayList;
import java.util.List;

public class GadgetPresenter implements GadgetContract.Presenter {

    public GadgetContract.View mView;

    private List<String> mList;

    public GadgetPresenter(GadgetContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mList = new ArrayList<>();
        mList.add("天气查询");
        mList.add("天气查询1");
        mList.add("天气查询2");
        mList.add("天气查询3");
        mList.add("天气查询4");
    }

    @Override
    public void start() {
        mView.setListViewData(mList);
    }
}
