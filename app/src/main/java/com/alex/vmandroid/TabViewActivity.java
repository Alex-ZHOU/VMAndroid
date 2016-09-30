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

package com.alex.vmandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.alex.view.TabView;


public class TabViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_tabview);

    final TabView tabView = (TabView) findViewById(R.id.tabView);

    final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tabView.setAlpha(progress / 255.0f);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

//    final Button toggle = (Button) findViewById(R.id.toggle);
//    toggle.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        tabView.setAlphaTransformEnabled(!tabView.isAlphaTransformEnabled());
//      }
//    });
//
//    final Button changeAttr = (Button) findViewById(R.id.changeAttr);
//    changeAttr.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        tabView.setTitleAttr("hi", tabView.getTextSize(), tabView.getTextColorNormal(),
//            tabView.getTextColorSelected());
//      }
//    });
  }
}
