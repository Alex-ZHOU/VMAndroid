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
package com.alex.vmandroid.display.main.fragments.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alex.view.loop.BannerPagerAdapter;
import com.alex.view.loop.BannerTimerTask;
import com.alex.view.loop.IndicatorView;
import com.alex.view.loop.Listener;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class LoopAdvertisementFragment extends BaseFragment implements LoopAdvertisementContract.View {

    private final String TAG = Base_TAG;

    private LoopAdvertisementContract.Presenter mPresenter;


    /**
     * 轮播图
     */
    private ViewPager mViewPager;
//    /**
//     * 指示器
//     */
//    private IndicatorView mIndicatorView;
    /**
     * 适配器
     */
    private BannerPagerAdapter mBannerPagerAdapter;
    /**
     * 图片资源
     */
    private List<Integer> pictureList = new ArrayList<>();
    /**
     * 当前轮播图位置
     */
    private int mBannerPosition;
    /**
     * 自动轮播计时器
     */
    private Timer timer = new Timer();

    /**
     * 自动轮播任务
     */
    private BannerTimerTask mBannerTimerTask;
    /**
     * 用户当前是否点击轮播图
     */
    private boolean mIsUserTouched = false;
    /**
     * 轮播图Handler
     */
    Handler bannerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // 当用户点击时,不进行轮播
            if (!mIsUserTouched) {
                // 获取当前的位置
                mBannerPosition = mViewPager.getCurrentItem();
                // 更换轮播图
                mBannerPosition = (mBannerPosition + 1) % mBannerPagerAdapter.FAKE_BANNER_SIZE;
                mViewPager.setCurrentItem(mBannerPosition);
            }
            return true;
        }
    });


    public static LoopAdvertisementFragment newInstance() {
        return new LoopAdvertisementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_discover_loop_advertisement, container, false);

        pictureList.add(R.drawable.pic_one);
        pictureList.add(R.drawable.pic_two);
        pictureList.add(R.drawable.pic_three);
        pictureList.add(R.drawable.pic_one);
        pictureList.add(R.drawable.pic_two);
        pictureList.add(R.drawable.pic_three);

        mViewPager = (ViewPager) view.findViewById(R.id.vp_banner);
        //mIndicatorView = (IndicatorView) view.findViewById(R.id.idv_banner);
        mBannerPagerAdapter = new BannerPagerAdapter(getActivity(), pictureList);
//        mBannerPagerAdapter.setListener(new Listener() {
//            @Override
//            public void onItemClick(int i) {
//                Log.i(TAG, "onItemClick: " + i);
//            }
//        });
        mViewPager.setAdapter(mBannerPagerAdapter);
        //mIndicatorView.setViewPager(pictureList.size(), mViewPager);
        // 设置默认起始位置,使开始可以向左边滑动
        //mViewPager.setCurrentItem(pictureList.size() * 100);
        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + mViewPager.getCurrentItem());
            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0 ;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        flage = 0 ;
                        break ;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1 ;
                        break ;
                    case  MotionEvent.ACTION_UP :
                        if (flage == 0) {
                            int item = mViewPager.getCurrentItem();

                            Log.i(TAG, "onTouch: "+item);
                        }
                        break ;


                }
                return false;
            }
        });



//        mIndicatorView.setOnPageChangeListener(new IndicatorView.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position1) {
//                position = position1;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                if (action == MotionEvent.ACTION_DOWN
//                        || action == MotionEvent.ACTION_MOVE) {
//                    mIsUserTouched = true;
//                } else if (action == MotionEvent.ACTION_UP) {
//                    mIsUserTouched = false;
//                }
//                Log.i(TAG, "onTouch: " + position);
//                return false;
//            }
//        });
        startBannerTimer();


        return view;
    }

    private int position = 0;

    /**
     * 开始轮播
     */
    private void startBannerTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        if (mBannerTimerTask != null) {
            mBannerTimerTask.cancel();
        }
        mBannerTimerTask = new BannerTimerTask(bannerHandler);
        if (timer != null) {
            // 循环5秒执行
            timer.schedule(mBannerTimerTask, 5000, 5000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoopAdvertisementContract.Presenter presenter) {
        mPresenter = presenter;
    }
}


//region 暂时不要删除，避免忘记写法
//        package com.alex.vmandroid.display.main.fragments.fragments;
//
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.os.Message;
//        import android.support.annotation.Nullable;
//        import android.support.v4.view.ViewPager;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.MotionEvent;
//        import android.view.View;
//        import android.view.ViewGroup;
//
//        import com.alex.view.loop.BannerPagerAdapter;
//        import com.alex.view.loop.BannerTimerTask;
//        import com.alex.view.loop.IndicatorView;
//        import com.alex.vmandroid.R;
//        import com.alex.vmandroid.base.BaseFragment;
//
//        import java.util.ArrayList;
//        import java.util.List;
//        import java.util.Timer;
//public class LoopAdvertisementFragment extends BaseFragment implements LoopAdvertisementContract.View{
//
//    private final String TAG = Base_TAG;
//
//    private LoopAdvertisementContract.Presenter mPresenter;
//
//
//    /**
//     * 轮播图
//     */
//    private ViewPager mViewPager;
//    /**
//     * 指示器
//     */
//    private IndicatorView mIndicatorView;
//    /**
//     * 适配器
//     */
//    private BannerPagerAdapter mBannerPagerAdapter;
//    /**
//     * 图片资源
//     */
//    private List<Integer> pictureList = new ArrayList<>();
//    /**
//     * 当前轮播图位置
//     */
//    private int mBannerPosition;
//    /**
//     * 自动轮播计时器
//     */
//    private Timer timer = new Timer();
//
//    /**
//     * 自动轮播任务
//     */
//    private BannerTimerTask mBannerTimerTask;
//    /**
//     * 用户当前是否点击轮播图
//     */
//    private boolean mIsUserTouched = false;
//    /**
//     * 轮播图Handler
//     */
//    Handler bannerHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            // 当用户点击时,不进行轮播
//            if (!mIsUserTouched) {
//                // 获取当前的位置
//                mBannerPosition = mViewPager.getCurrentItem();
//                // 更换轮播图
//                mBannerPosition = (mBannerPosition + 1) % mBannerPagerAdapter.FAKE_BANNER_SIZE;
//                mViewPager.setCurrentItem(mBannerPosition);
//            }
//            return true;
//        }
//    });
//
//
//    public static LoopAdvertisementFragment newInstance() {
//        return new LoopAdvertisementFragment();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_main_discover_loop_advertisement, container, false);
//
//        pictureList.add(R.drawable.pic_one);
//        pictureList.add(R.drawable.pic_two);
//        pictureList.add(R.drawable.pic_three);
//        pictureList.add(R.drawable.pic_one);
//        pictureList.add(R.drawable.pic_two);
//        pictureList.add(R.drawable.pic_three);
//
//        mViewPager = (ViewPager) view.findViewById(R.id.vp_banner);
//        mIndicatorView = (IndicatorView) view.findViewById(R.id.idv_banner);
//        mBannerPagerAdapter = new BannerPagerAdapter(getActivity(), pictureList);
//        mViewPager.setAdapter(mBannerPagerAdapter);
//        mIndicatorView.setViewPager(pictureList.size(), mViewPager);
//        // 设置默认起始位置,使开始可以向左边滑动
//        mViewPager.setCurrentItem(pictureList.size() * 100);
//        mIndicatorView.setOnPageChangeListener(new IndicatorView.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position1) {
//                position = position1;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//
//
//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                if (action == MotionEvent.ACTION_DOWN
//                        || action == MotionEvent.ACTION_MOVE) {
//                    mIsUserTouched = true;
//                } else if (action == MotionEvent.ACTION_UP) {
//                    mIsUserTouched = false;
//                }
//                Log.i(TAG, "onTouch: "+position);
//                return false;
//            }
//        });
//        startBannerTimer();
//
//
//        return view;
//    }
//    private int position = 0;
//    /**
//     * 开始轮播
//     */
//    private void startBannerTimer() {
//        if (timer == null) {
//            timer = new Timer();
//        }
//        if (mBannerTimerTask != null) {
//            mBannerTimerTask.cancel();
//        }
//        mBannerTimerTask = new BannerTimerTask(bannerHandler);
//        if (timer != null && mBannerTimerTask != null) {
//            // 循环5秒执行
//            timer.schedule(mBannerTimerTask, 5000, 5000);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mPresenter.start();
//    }
//
//    @Override
//    public void setPresenter(LoopAdvertisementContract.Presenter presenter) {
//        mPresenter = presenter;
//    }
//}
//endregion
