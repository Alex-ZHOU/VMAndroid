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
package com.alex.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TabLayout extends LinearLayout implements ValueAnimator.AnimatorUpdateListener {

    private static final int SHAPE_NONE = 0;
    private int mIndicatorShape = SHAPE_NONE;
    private int mTabCount;
    private int mCurrentTab = 0;
    private int mLastTab = -1;

    private IndicatorPoint mCurrentP = new IndicatorPoint();
    private IndicatorPoint mLastP = new IndicatorPoint();

    private ValueAnimator mValueAnimator;
    private int mIndicatorAnimOffset = 0; // indicator horizontal offset when animation

    private boolean mIndicatorAnimEnabled = true;

    private OnTabSelectedListener mListener;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false); // 没有重载 onMeasure 时，需要手动设置，否则 onDraw 不会被调用
        setClipChildren(false);
        setClipToPadding(false);

        mValueAnimator = ValueAnimator.ofObject(new PointEvaluator(), mLastP, mCurrentP);
        mValueAnimator.addUpdateListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTabCount = getChildCount();
        initTabsWithListener();
    }

    private void initTabsWithListener() {
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = getChildAt(i);
            tabView.setTag(i);
            tabView.setOnClickListener(null);
        }
        setCurrentTab(0);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        View currentTabView = getChildAt(mCurrentTab);
        IndicatorPoint p = (IndicatorPoint) valueAnimator.getAnimatedValue();
        mIndicatorAnimOffset = (int) p.left - currentTabView.getLeft();
        invalidate();
    }

    public void applyConfigurationWithViewPager(@NonNull final ViewPager viewPager,
                                                final boolean alphaTransformEnabled) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mIndicatorShape != SHAPE_NONE && mIndicatorAnimEnabled) {
                    scrollIndicatorTo(position, positionOffset);
                }

                if (alphaTransformEnabled) {
                    scrollTabTo(position, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mIndicatorShape != SHAPE_NONE && mIndicatorAnimEnabled && mIndicatorBounceEnabled) {
                    updateState();
                }
            }
        });

        // 如果没有设置监听器，则设置一个默认的监听器
        if (mListener == null) {
            mListener = new OnTabSelectedListener() {
                @Override
                public void onTabSelect(View view, int position) {
                    viewPager.setCurrentItem(position, false);
                }

                @Override
                public void onTabReselect(View view, int position) {

                }
            };
            addOnTabSelectedListener();
        }
    }

    public int getTabCount() {
        return getChildCount();
    }

    private boolean mIndicatorBounceEnabled = true;

    /**
     * 启用抖动效果时，需要在 onPageScrollStateChanged 中调用，否则滑动页面时也会有抖动效果
     * onPageScrollStateChanged 仅在页面发生滑动时才会被调用。
     */
    public void updateState() {
        mTabClickTrigger = false;
    }

    private float mIndicatorWidth = -1; // equal with tab width

    /**
     * 如果需要指示器滑动效果，在 onPageScrolled 中调用，参数命名相对应
     */
    public void scrollIndicatorTo(int position, float positionOffset) {
        View tabView = getChildAt(position);
        View currentView = getChildAt(mCurrentTab);

        // 修正默认的增量
        mIndicatorAnimOffset = tabView.getLeft()
                + (int) ((tabView.getWidth() - mIndicatorWidth) / 2)
                + (int) (tabView.getWidth() * positionOffset)
                - currentView.getLeft()
                - (int) ((currentView.getWidth() - mIndicatorWidth) / 2);

        if (!mTabClickTrigger) {
            mValueAnimator.setInterpolator(null);
            mValueAnimator.setDuration(250);
        }

        invalidate();
    }

    /**
     * 当需要 tab alpha transform 时在 onPageScrolled 使用，参数命名相对应
     */
    public void scrollTabTo(int position, float positionOffset) {
        if (position + 1 > mTabCount) {
            throw new IllegalArgumentException("position must be smaller than tabCount");
        }

        // onScroll: position = min(source, dest), positionOffset = [0, 1]
        // from 0 to 1: position = 0
        // from 1 to 0: position = 0
        View view;
        if (positionOffset > 0) {
            view = getChildAt(position);
            view.setAlpha(1 - positionOffset);
            view = getChildAt(position + 1);
            view.setAlpha(positionOffset);
        }
    }


    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mListener = listener;
        addOnTabSelectedListener();
    }

    private boolean mTabClickTrigger = true;

    private void addOnTabSelectedListener() {
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = getChildAt(i);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    if (mCurrentTab != position) {
                        mTabClickTrigger = true;
                        setCurrentTab(position);
                        if (mListener != null) {
                            mListener.onTabSelect(v, position);
                        }
                    } else {
                        if (mListener != null) {
                            mListener.onTabReselect(v, position);
                        }
                    }
                }
            });
        }
    }


    public void setCurrentTab(int index) {
        if (index >= mTabCount) {
            throw new IllegalArgumentException("index must be smaller than tabCount");
        }

        if (mLastTab != -1 && index == mCurrentTab) {
            return;
        }

        resetState();
        mLastTab = mCurrentTab;
        mCurrentTab = index;
        View currentTabView = getChildAt(mCurrentTab);
        currentTabView.setAlpha(1);

        // change indicator
//        if (mIndicatorShape != SHAPE_NONE && mIndicatorAnimEnabled) {
//            calIndicatorOffset();
//        } else {
//            invalidate();
//        }
        invalidate();
    }

    private void resetState() {
        View view;
        for (int i = 0; i < mTabCount; i++) {
            view = getChildAt(mCurrentTab);
            view.setAlpha(0);
        }
    }

    class IndicatorPoint {
        public float left;
        public float right;
    }

    class PointEvaluator implements TypeEvaluator<IndicatorPoint> {
        @Override
        public IndicatorPoint evaluate(float fraction, IndicatorPoint startValue,
                                       IndicatorPoint endValue) {
            float left = startValue.left + fraction * (endValue.left - startValue.left);
            float right = startValue.right + fraction * (endValue.right - startValue.right);
            IndicatorPoint point = new IndicatorPoint();
            point.left = left;
            point.right = right;
            return point;
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelect(View view, int position);

        void onTabReselect(View view, int position);
    }

}
