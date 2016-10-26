package com.ihaozuo.plamexam.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jerry on 2016/5/14.
 */
public class WaveView extends ImageView {

    /**
     * 间隔时间 默认设置为20毫秒
     */
    private static final long sIntervalTime = 1000;

    private static final int sDelayedTime = 20;

    private static final int sSpeed = 3;

    private long mLastTime;

    private long mCurrentTime;

    // 小圆半径
    private int mSmallRadius;

    // 是否是按压下去的状态
    private boolean mIsPress = false;

    // 大圆半径
    private int mLargeRadius;

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private Timer timer;
    private TimerTask timerTask;

    private LinkedList<XiuXiuItem> items = new LinkedList<>();
    private LinkedList<XiuXiuItem> removes = new LinkedList<>();

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // wrap_content请自行计算控件大小

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mSmallRadius = mWidth / 8;

        mLargeRadius = mHeight / 2;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.TRANSPARENT);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (XiuXiuItem item : items) {
            item.drawSelf(canvas);
            if (item.getmCurrentRadius() == mLargeRadius) {
                removes.add(item);
            }
        }

        canvas.drawCircle(mWidth / 2, mHeight / 2, mSmallRadius, mPaint);

        for (XiuXiuItem item : removes) {
            items.remove(item);
        }

        removes.clear();

        this.postInvalidateDelayed(sDelayedTime);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//
//        mCurrentTime = System.currentTimeMillis();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mIsPress = true;
//                if (mCurrentTime - mLastTime >= sIntervalTime) {
//                    items.add(new XiuXiuItem());
//                    mLastTime = mCurrentTime;
//                } else {
//                    System.out.println("按太快了");
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//
//                mIsPress = false;
//
//                break;
//        }
//
//        return true;
//    }





    public int evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 0) {
                items.add(new XiuXiuItem());
            }
        }
    };

    public void showWave(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask , 1000, sIntervalTime);
    }

    public void stopWave(){
        if (timer == null ){
            return;
        }
        timer.cancel();
    }

    private class XiuXiuItem {

        private int mSmallRadius;

        private int mLargeRadius;

        private int mCurrentRadius;

        private Paint mPaint;

        public XiuXiuItem() {
            this.mSmallRadius = WaveView.this.mSmallRadius;
            this.mLargeRadius = WaveView.this.mLargeRadius;
            this.mCurrentRadius = mSmallRadius;
            this.mPaint = new Paint();

            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.BLACK);
        }

        public void drawSelf(Canvas canvas) {

            float fraction = (mCurrentRadius - mSmallRadius) * 1.0f / (mLargeRadius - mSmallRadius) * 1.0f;
            System.out.println(fraction);
            int color = evaluate(fraction, 0xaa9ccff9, 0x009ccff9);
            mPaint.setColor(color);

            canvas.drawCircle(mWidth / 2, mHeight / 2, mCurrentRadius, mPaint);

            mCurrentRadius += sSpeed;
            mCurrentRadius = mCurrentRadius > mLargeRadius ? mLargeRadius : mCurrentRadius;

        }

        public int getmCurrentRadius() {
            return mCurrentRadius;
        }

        public void setmCurrentRadius(int mCurrentRadius) {
            this.mCurrentRadius = mCurrentRadius;
        }

        public int getmSmallRadius() {
            return mSmallRadius;
        }

        public void setmSmallRadius(int mSmallRadius) {
            this.mSmallRadius = mSmallRadius;
        }

        public int getmLargeRadius() {
            return mLargeRadius;
        }

        public void setmLargeRadius(int mLargeRadius) {
            this.mLargeRadius = mLargeRadius;
        }


    }

}
