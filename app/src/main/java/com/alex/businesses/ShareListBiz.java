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
package com.alex.businesses;

import com.alex.vmandroid.entities.ShareRecordDb;

import java.util.ArrayList;
import java.util.List;

public class ShareListBiz {


    public void get(Listener listener) {

        List<ShareRecordDb> list = new ArrayList<>();

        // TODO 真实逻辑等待修改
        for (int i = 0; i < 5; i++) {
            ShareRecordDb share = new ShareRecordDb();
            share.setStoreName("酒店1");

            share.setUserHeadPortraitImageId(1);
            share.setTime("2017-1-1");

            list.add(share);
        }
        listener.succeed(list);

    }

    public interface Listener {
        void succeed(List<ShareRecordDb> list);

        void failed();
    }
}
