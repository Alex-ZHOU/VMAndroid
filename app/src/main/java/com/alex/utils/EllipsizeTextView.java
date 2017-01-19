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
package com.alex.utils;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.alex.vmandroid.R;

//转载：http://blog.csdn.net/l_lhc/article/details/50879287
public class EllipsizeTextView {
    /**
     * 添加监听
     *
     * @param tv   要实现伸缩效果的 TextView
     * @param desc TextView 要展示的文字
     */
    public static void toggleEllipsize(final TextView tv, final String desc) {
        if (desc == null) {
            return;
        }

        //去除点击图片后的背景色（ SpannableString 在点击时会使背景变色 ，填上这句则可不变色 ）
        tv.setHighlightColor(Color.TRANSPARENT);

        //添加 TextView 的高度监听
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                int paddingLeft = tv.getPaddingLeft();
                int paddingRight = tv.getPaddingRight();
                TextPaint paint = tv.getPaint();
                float moreText = tv.getTextSize() * 3;
                float availableTextWidth = (tv.getWidth() - paddingLeft - paddingRight) * 2 - moreText;
                CharSequence ellipsizeStr = TextUtils.ellipsize(desc, paint, availableTextWidth, TextUtils.TruncateAt.END);

                // TextView 实际显示的文本长度  < 应该显示文本的长度(收缩状态)
                if (ellipsizeStr.length() < desc.length()) {
                    openFun(tv, ellipsizeStr, desc);//显示收缩状态的文本和图标
                }
                // TextView 实际显示的文本长度  == 应该显示文本的长度(正常状态)
                else if (ellipsizeStr.length() == desc.length()) {
                    tv.setText(desc);//正常显示Textview
                }
                // TextView 实际显示的文本长度  > 应该显示文本的长度(展开状态)
                else {
                    closeFun(tv, ellipsizeStr, desc);//显示展开状态的文本和图标
                }

                if (Build.VERSION.SDK_INT >= 16) {
                    tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    // 显示收缩状态的文本，设置点击图标，并添加点击事件
    private static void openFun(final TextView tv, final CharSequence ellipsizeStr, final String desc) {
        CharSequence temp = ellipsizeStr + ".";
        SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
        //Drawable dd = tv.getResources().getDrawable(R.drawable.ic_expand);
        Drawable dd = tv.getResources().getDrawable(R.drawable.arrow_down);
        dd.setBounds(0, 0, dd.getIntrinsicWidth(), dd.getIntrinsicHeight());
        ClickableImageSpan is = new ClickableImageSpan(dd) {
            @Override
            public void onClick(View view) {
                closeFun(tv, ellipsizeStr, desc);
            }

        };
        ssb.setSpan(is, temp.length() - 1, temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);
        tv.setMovementMethod(ClickableMovementMethod.getInstance());
    }

    // 显示展开状态的文本，设置点击图标，并添加点击事件
    private static void closeFun(final TextView tv, final CharSequence ellipsizeStr, final String desc) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(desc);
        //Drawable dd = tv.getResources().getDrawable(R.drawable.ic_normal);
        Drawable dd = tv.getResources().getDrawable(R.drawable.arrow_up);
        dd.setBounds(0, 0, dd.getIntrinsicWidth(), dd.getIntrinsicHeight());
        ClickableImageSpan is = new ClickableImageSpan(dd) {
            @Override
            public void onClick(View view) {
                openFun(tv, ellipsizeStr, desc);
            }
        };
        ssb.setSpan(is, desc.length() - 1, desc.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);
        tv.setMovementMethod(ClickableMovementMethod.getInstance());
    }


}

/**
 * ClickableMovementMethod 继承自 LinkMovementMethod，使其能响应 ClickableImageSpan
 *
 * @author lee
 */
class ClickableMovementMethod extends LinkMovementMethod {

    private static ClickableMovementMethod sInstance;

    public static ClickableMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ClickableMovementMethod();
        }
        return sInstance;
    }


    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            /** 修改位置【1】 START **/
            ClickableImageSpan[] imageSpans = buffer.getSpans(off, off, ClickableImageSpan.class);
            /******    END    ******/

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }

                return true;
            }
            /** 修改位置【2】START  **/
            else if (imageSpans.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    imageSpans[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(imageSpans[0]),
                            buffer.getSpanEnd(imageSpans[0]));
                }

                return true;
            }
            /******   END     ******/

            else {
                Selection.removeSelection(buffer);
            }
        }

        return false;
    }
}

/**
 * ClickableImageSpan 继承自 ImageSpan，使其能响应点击事件，并图片垂直居中显示
 *
 * @author lee
 */
abstract class ClickableImageSpan extends ImageSpan {

    public ClickableImageSpan(Drawable b) {
        super(b);
    }

    /**
     * 图片垂直居中显示
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fontMetricsInt) {

        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fontMetricsInt.ascent = -bottom;
            fontMetricsInt.top = -bottom;
            fontMetricsInt.bottom = top;
            fontMetricsInt.descent = top;
        }
        return rect.right;
    }

    /**
     * 图片垂直居中显示
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {

        Drawable drawable = getDrawable();
        canvas.save();
        int transY;
        transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }


    /**
     * 添加点击事件
     */
    public abstract void onClick(View view);
}
