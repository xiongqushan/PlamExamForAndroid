package com.ihaozuo.plamexam.common.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable {
    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    public PullableListView(Context context) {
        this(context, null, 0);
    }

    public PullableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }


    private boolean mLocked = false;
    @Override
    public boolean isLocked() {
        return mLocked;
    }

    @Override
    public void lock() {
        mLocked = true;
    }

    @Override
    public void unLock() {
        mLocked = false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev)
//                && mGestureDetector.onTouchEvent(ev);
//    }
//
//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                float distanceX, float distanceY) {
//            if (distanceY != 0 && distanceX != 0) {
//                if (Math.abs(distanceY) >= Math.abs(distanceX)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
}
