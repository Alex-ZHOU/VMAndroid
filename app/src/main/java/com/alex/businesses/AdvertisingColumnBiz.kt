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
package com.alex.businesses

import com.alex.utils.EncapsulateParseJson
import com.alex.utils.URLs
import com.alex.vmandroid.entities.AdvertisingColumn
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

import java.io.IOException
import java.lang.reflect.Type
import java.util.ArrayList

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * 获取主页界面广告栏的信息
 */
class AdvertisingColumnBiz {

    fun getData(listener: Listener) {

        val client = OkHttpClient()

        val request = Request.Builder().url(URLs.URL_ADVERTISING_COLUMN).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                listener.failed()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val json = response.body().string()
                response.close()

                try {
                    val list = jsonToArrayList<AdvertisingColumn>(json, AdvertisingColumn::class.java)
                    if (list != null && list.size > 0) {
                        listener.succeed(list)
                    } else {
                        listener.failed()
                    }
                } catch (e: Exception) {
                    listener.failed()
                }

                //                List<AdvertisingColumn> list = jsonToArrayList(json, AdvertisingColumn.class);
                //                if (list != null && list.size()>0) {
                //                    listener.succeed(list);
                //                }else{
                //                    listener.failed();
                //                }

            }
        })

    }

    private fun <T> jsonToArrayList(json: String, clazz: Class<T>): ArrayList<T>? {
        val type = object : TypeToken<ArrayList<JsonObject>>() {

        }.type
        val jsonObjects = EncapsulateParseJson.getGson().fromJson<ArrayList<JsonObject>>(json, type)

        val arrayList = ArrayList<T>()
        for (jsonObject in jsonObjects) {
            arrayList.add(EncapsulateParseJson.getGson().fromJson(jsonObject, clazz))
        }
        return arrayList
    }

    interface Listener {
        fun succeed(list: List<AdvertisingColumn>)

        fun failed()
    }
}
