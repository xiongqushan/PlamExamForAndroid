package com.ihaozuo.plamexam.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;

public class Width16Heigth9DraweeView extends SimpleDraweeView {
    int width = 0;
    int height = 0;

    public Width16Heigth9DraweeView(Context context) {
        super(context);
    }

    @SuppressWarnings("deprecation")
    public Width16Heigth9DraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        double proportion = (((double) 2) / 3);
        height = (int) (width * proportion);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
