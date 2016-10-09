package com.ihaozuo.plamexam.common.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CommentLoadMoreListView extends ListView implements Pullable {
    private int firstListSize = 0;

    public CommentLoadMoreListView(Context context) {
        this(context, null, 0);
    }

    public CommentLoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentLoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFadingEdgeLength(0);
    }

    public void setFirstListSize(int firstListSize) {
        this.firstListSize = firstListSize;
    }

    @Override
    public boolean canPullDown() {
        return false;
    }

    @Override
    public boolean canPullUp() {
//        if (getCount() == 0) {
//            // 没有item的时候也可以上拉加载
//            return true;
//        } else if (getLastVisiblePosition() == (getCount() - 1)) {
//            // 滑到底部了
//            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
//                    && getChildAt(
//                    getLastVisiblePosition()
//                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
//                return true;
//        }
//        return false;
        if (firstListSize >= 20) {
            if (getLastVisiblePosition() == (getCount() - 1)) {
                // 滑到底部了
                if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                        && getChildAt(
                        getLastVisiblePosition()
                                - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                    return true;
            }
        }
        return false;
    }


    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public void lock() {
    }

    @Override
    public void unLock() {
    }

}
