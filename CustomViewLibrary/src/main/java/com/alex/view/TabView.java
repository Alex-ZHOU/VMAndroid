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
 *
 * Copyright 2016 TomeOkin
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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class TabView extends View {

    /**
     * 默认图标资源的ID号
     * 如果使用者没有设置id号,那它的值恒为-1。
     * Default icon resources id.
     * If user not set a icon id ,it value always is -1.
     */
    private int mDefaultIconId = -1;
    /**
     * 被选中时图标资源的ID号
     * 如果使用者没有设置id号,那它的值恒为-1。
     * Selected icon resources id.
     * If user not set a icon id ,it value always is -1.
     */
    private int mSelectedIconId = -1;
    /**
     * 默认图标
     * 如果mDefaultIconId值为-1,那么它的值为空。
     * Default icon Bitmap.
     * If mDefaultIconId's value is -1,so mDefaultIconBitmap's value is null.
     */
    private Bitmap mDefaultIconBitmap = null;
    /**
     * 选中的图标
     * 如果mSelectedIconId值为-1,那么它的值为空。
     * Selected icon Bitmap.
     * If mSelectedIconId's value is -1,so mSelectedIconBitmap's value is null.
     */
    private Bitmap mSelectedIconBitmap = null;
    /**
     * 绘制图标的画笔
     * The paint for icon.
     */
    private Paint mIconPaint = new Paint();
    /**
     * 图标的矩阵,即绘制图标的区域
     * The rect of icon which give the area to draw the icon.
     */
    private Rect mIconRect = new Rect();


    /**
     * TabView标题的文本信息
     * Tab view title text
     */
    private String mTitle;
    /**
     * TabView标题的文本大小
     * title text size
     */
    private int mTitleSize = (int) sp2px(10);
    /**
     * 默认标题的微文本的颜色
     * Default text color for title
     */
    private int mDefaultTextColor = Color.parseColor("#424242");
    /**
     * 选中时标题的文本的颜色
     * Selected text color for title.
     */
    private int mSelectedTextColor = Color.parseColor("#259b24");
    /**
     * 标题文本绘制的区域
     * Set the bound of title.
     */
    private Rect mTitleBound;
    /**
     * 绘制标题的画笔
     * The paint for title.
     */
    private Paint mTitlePaint = new Paint();

    /**
     * 设置显示的透明度
     * The alpha of show.
     */
    private float mAlpha;


    private int mInternalPadding = (int) dp2px(4);


    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // obtain user defined attr values
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView, defStyleAttr, 0);

        mDefaultIconId = typedArray.getResourceId(R.styleable.TabView_defaultIcon, mDefaultIconId);
        // set default icon image
        setDefaultIconId(mDefaultIconId);
        mSelectedIconId = typedArray.getResourceId(R.styleable.TabView_selectedIcon, mSelectedIconId);
        // set selected icon image
        setSelectedIconId(mSelectedIconId);
        // 绘制动画时忽略抗锯齿 When drawing the animation, ignore the anti aliasing.
        mIconPaint.setFilterBitmap(true);

        // get title string
        mTitle = typedArray.getString(R.styleable.TabView_title);
        if (mTitle == null) {
            throw new IllegalArgumentException("Title text could not bean empty.");
        }
        // get title size
        mTitleSize = typedArray.getDimensionPixelSize(R.styleable.TabView_titleSize, mTitleSize);
        // get title color
        mDefaultTextColor = typedArray.getResourceId(R.styleable.TabView_defaultTitleColor, mDefaultTextColor);
        mSelectedTextColor = typedArray.getResourceId(R.styleable.TabView_selectedTitleColor, mSelectedTextColor);

        typedArray.recycle();

        initTitle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        updateDrawBorder();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int alpha = (int) Math.ceil(mAlpha * 255);

        // 绘制图标
        // 静止状态下减少一次重绘
        if (alpha != 255) {
            mIconPaint.reset();
            mIconPaint.setAntiAlias(true); // 设置抗锯齿
            mIconPaint.setFilterBitmap(true); // 绘制动画时忽略抗锯齿
            mIconPaint.setAlpha(255 - alpha); // setAlpha 应该在 setColor 之后设置
            canvas.drawBitmap(mDefaultIconBitmap, null, mIconRect, mIconPaint);
        }

        if (mSelectedIconBitmap != null && alpha != 0) {
            mIconPaint.reset();
            mIconPaint.setAntiAlias(true); // 设置抗锯齿
            mIconPaint.setFilterBitmap(true); // 绘制动画时忽略抗锯齿
            mIconPaint.setAlpha(alpha); // setAlpha 应该在 setColor 之后设置
            canvas.drawBitmap(mSelectedIconBitmap, null, mIconRect, mIconPaint);
        }


        // 绘制标题 draw title
        if (mTitle != null) {
            mTitlePaint.setColor(mDefaultTextColor);
            mTitlePaint.setAlpha(255 - alpha); // setAlpha 应该在 setColor 之后设置

            // 默认情况下，textAlign 为 Paint.Align.LEFT，绘制文本时，x 为左上角 x 坐标，y 为 baseline 值
            // 如果 textAlign 为 Paint.Align.CENTER，绘制文本时，x 为文本的水平中心 x 坐标，y 为 baseline 值
            // see [amulyakhare/TextDrawable](https://github.com/amulyakhare/TextDrawable)
            // TextDrawable.onDraw()
            canvas.drawText(mTitle, mTitleBound.left, (mTitleBound.bottom + mTitleBound.top) / 2
                    - (mTitlePaint.descent() + mTitlePaint.ascent()) / 2, mTitlePaint);

            mTitlePaint.setColor(mSelectedTextColor);
            mTitlePaint.setAlpha(alpha); // setAlpha 应该在 setColor 之后设置
            //canvas.drawText(mTitle, mTextBound.left, mTextBound.bottom - mFmi.bottom / 2, mTextPaint);
            canvas.drawText(mTitle, mTitleBound.left, (mTitleBound.bottom + mTitleBound.top) / 2
                    - (mTitlePaint.descent() + mTitlePaint.ascent()) / 2, mTitlePaint);
        }
    }

    /**
     * set the rect of this view
     */
    private void updateDrawBorder() {
        int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        availableHeight -= (mTitleBound.height() + mInternalPadding);

        // the icon's width and height
        int iconWH = Math.min(availableWidth, availableHeight);

        // the area of icon
        mIconRect.left = getPaddingLeft() + (availableWidth - iconWH) / 2;
        mIconRect.top = getPaddingTop();
        mIconRect.right = mIconRect.left + iconWH;
        mIconRect.bottom = mIconRect.top + iconWH;


        // the area of title
        int textLeft = getPaddingLeft() + (availableWidth - mTitleBound.width()) / 2;
        int textTop = mIconRect.bottom + mInternalPadding;
        mTitleBound.set(textLeft, textTop, textLeft + mTitleBound.width(),
                textTop + mTitleBound.height());


    }


    /**
     * Set view alpha.
     *
     * @param alpha value of alpha
     */
    @Override
    public void setAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha value must between 0.0f to 1.0f.");
        }

        mAlpha = alpha;

        invalidate();
    }

    /**
     * Set default icon image.
     *
     * @param id resources id
     * @return succeed return true,failed return false
     */
    public boolean setDefaultIconId(int id) {
        if (id != -1) {
            mDefaultIconId = id;
            // Use user defined image for default ico.
            mDefaultIconBitmap = BitmapFactory.decodeResource(getResources(), mDefaultIconId);
            return true;
        } else {
            // Use default defined image for default icon.
            mDefaultIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_grey);
            return false;
        }
    }

    /**
     * Set select icon image.
     *
     * @param id resources id
     * @return succeed return true,failed return false
     */
    public boolean setSelectedIconId(int id) {
        if (id != -1) {
            mSelectedIconId = id;
            // Use user defined image for select icon.
            mSelectedIconBitmap = BitmapFactory.decodeResource(getResources(), mSelectedIconId);
            return true;
        } else {
            // Use default defined image for select ico.
            mSelectedIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            return false;
        }
    }

    /**
     * 初始化标题区域
     * Init the title rect.
     */
    private void initTitle() {
        if (mTitle != null) {
            mTitleBound = new Rect();
            mTitlePaint.setTextSize(mTitleSize);
            mTitlePaint.setAntiAlias(true);
            mTitlePaint.setDither(true);
            mTitlePaint.getTextBounds(mTitle, 0, mTitle.length(), mTitleBound);
        }
    }

    /**
     * 将sp值转换为px值
     * sp to px
     *
     * @param sp sp
     * @return px
     */
    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    /**
     * 将dp值转换为px值
     * dp to px
     *
     * @param dp dp
     * @return px
     */
    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
