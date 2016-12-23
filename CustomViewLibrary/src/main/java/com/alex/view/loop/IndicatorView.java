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
 *
  * @author Alex_ZHOU
 *
 * @author junweiliu 2016/6/14
 */

package com.alex.view.loop;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alex.view.R;

/**
 * 轮播图圆形指示器
 * Created by junweiliu on 16/6/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class IndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener {
    /**
     * 需要创建的指示器个数
     */
    private int childViewCount = 0;
    /**
     * 设置圆点间margin
     */
    private int mInterval;
    /**
     * 当前选中的位置
     */
    private int mCurrentPostion = 0;
    /**
     * 普通显示的图片
     */
    private Bitmap normalBp;
    /**
     * 选中时显示的图片
     */
    private Bitmap selectBp;
    /**
     * 设置的轮播图Vp
     */
    private ViewPager mViewPager;
    /**
     * 指示器单项宽度
     */
    private int mWidth;
    /**
     * 指示器单项高度
     */
    private int mHeight;
    /**
     * 圆点半径
     */
    private int mRadius;
    /**
     * 普通状态圆点颜色
     */
    private int normalColor;
    /**
     * 选中状态圆点颜色
     */
    private int selectColor;
    /**
     * 是否是循环
     */
    private boolean isCirculate;


    /**
     * 对外提供ViewPager的回调接口
     */
    public interface OnPageChangeListener {

         void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

         void onPageSelected(int position);

         void onPageScrollStateChanged(int state);

    }

    /**
     * 回调接口
     */
    private OnPageChangeListener mListener;

    /**
     * 设置回调
     *
     * @param listener
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        normalBp = drawableToBitamp(ta.getDrawable(R.styleable.IndicatorView_normalDrawable));
        selectBp = drawableToBitamp(ta.getDrawable(R.styleable.IndicatorView_selectDrawable));
        mInterval = ta.getDimensionPixelOffset(R.styleable.IndicatorView_indicatorInterval, 6);
        normalColor = ta.getColor(R.styleable.IndicatorView_normalColor, Color.GRAY);
        selectColor = ta.getColor(R.styleable.IndicatorView_selectColor, Color.RED);
        mRadius = ta.getInteger(R.styleable.IndicatorView_indicatorRadius, 6);
        isCirculate = ta.getBoolean(R.styleable.IndicatorView_isCirculate, true);
        ta.recycle();
        // 初始化
        init();

    }

    /**
     * 初始化数据
     */
    private void init() {
        // 处理自定义属性
        if (null == normalBp) {
            normalBp = makeIndicatorBp(normalColor);
        }
        if (null == selectBp) {
            selectBp = makeIndicatorBp(selectColor);
        }
        mWidth = normalBp.getWidth();
        mHeight = normalBp.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 如果是wrap_content设置为图片宽高,否则设置为父容器宽高
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : (mWidth + mInterval) * childViewCount
                , (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                        : mHeight);
    }

    /**
     * 重绘
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 创建指示器圆点
        if (getChildCount() < childViewCount && getChildCount() == 0) {
            for (int i = 0; i < childViewCount; i++) {
                addView(makeIndicatorItem());
            }
            // 设置默认选中指示器
            setIndicatorState(mCurrentPostion);
        }
        super.dispatchDraw(canvas);
    }


    /**
     * 设置Vp
     *
     * @param viewpager
     */
    public void setViewPager(ViewPager viewpager) {
        if (null == viewpager) {
            return;
        }
        if (null == viewpager.getAdapter()) {
            throw new IllegalStateException("ViewPager does not have adapter.");
        }

        this.mViewPager = viewpager;
        this.mViewPager.addOnPageChangeListener(this);
        this.childViewCount = viewpager.getAdapter().getCount();
        invalidate();
    }

    /**
     * 设置Vp
     *
     * @param viewpager
     * @param currposition 当前选中的位置
     */
    public void setViewPager(ViewPager viewpager, int currposition) {
        if (null == viewpager) {
            return;
        }
        if (null == viewpager.getAdapter()) {
            throw new IllegalStateException("ViewPager does not have adapter.");
        }
        this.mViewPager = viewpager;
        this.mViewPager.addOnPageChangeListener(this);
        this.childViewCount = viewpager.getAdapter().getCount();
        this.mCurrentPostion = currposition;
        invalidate();
    }

    /**
     * 设置vp
     *
     * @param childViewCount 需要显示指示器的个数
     * @param viewpager      vp
     */
    public void setViewPager(int childViewCount, ViewPager viewpager) {
        if (null == viewpager) {
            return;
        }
        if (null == viewpager.getAdapter()) {
            throw new IllegalStateException("ViewPager does not have adapter.");
        }
        this.mViewPager = viewpager;
        this.mViewPager.addOnPageChangeListener(this);
        this.childViewCount = childViewCount;
        removeAllViews();
        invalidate();
    }

    /**
     * 创建指示器
     *
     * @return
     */
    private View makeIndicatorItem() {
        ImageView iv = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = normalBp.getWidth();
        lp.height = normalBp.getHeight();
        lp.rightMargin = mInterval;
        iv.setImageBitmap(normalBp);
        iv.setLayoutParams(lp);
        return iv;
    }


    /**
     * 创建圆点指示器图片
     *
     * @param color 创建不同颜色的指示器项
     * @return
     */
    private Bitmap makeIndicatorBp(int color) {
        Bitmap normalBp = Bitmap.createBitmap(mRadius * 2, mRadius * 2,
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        Canvas canvas = new Canvas(normalBp);
        canvas.drawCircle(mRadius, mRadius, mRadius, paint);
        return normalBp;
    }


    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mListener) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (isCirculate && 0 != childViewCount) {
            position %= childViewCount;
        }
        setIndicatorState(position);
        if (null != mListener) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (null != mListener) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    /**
     * 设置指示器的状态
     *
     * @param position
     */
    public void setIndicatorState(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            if (i == position)
                ((ImageView) getChildAt(i)).setImageBitmap(selectBp);
            else
                ((ImageView) getChildAt(i)).setImageBitmap(normalBp);
        }
    }

}
