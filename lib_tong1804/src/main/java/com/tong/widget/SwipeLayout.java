package com.tong.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.List;

/**
 * 侧滑返回控件
 *
 */

public class SwipeLayout extends FrameLayout {

    private int xInt;
    private int yInt;
    private int tempInt;
    private int sileInt;
    private int widthInt;
    private boolean sileBoolean;
    private boolean finishBoolean;

    private View mainView;
    private Context context;
    private Scroller mainScroller;
    private Activity mainActivity;
    private Drawable mainDrawable;
    private List<ViewPager> mainList = new LinkedList<>();

    @Override
    public void computeScroll() {
        if (mainScroller.computeScrollOffset()) {
            mainView.scrollTo(mainScroller.getCurrX(), mainScroller.getCurrY());
            postInvalidate();
            if (mainScroller.isFinished() && finishBoolean) {
                mainActivity.finish();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mainDrawable != null && mainView != null) {
            int left = mainView.getLeft() - mainDrawable.getIntrinsicWidth();
            int right = left + mainDrawable.getIntrinsicWidth();
            int top = mainView.getTop();
            int bottom = mainView.getBottom();
            mainDrawable.setBounds(left, top, right, bottom);
            mainDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempInt - moveX;
                tempInt = moveX;
                if (moveX - xInt > sileInt && Math.abs((int) event.getRawY() - yInt) < sileInt) {
                    sileBoolean = true;
                }
                if (moveX - xInt >= 0 && sileBoolean) {
                    mainView.scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                sileBoolean = false;
                if (mainView.getScrollX() <= -widthInt / 2) {
                    finishBoolean = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    finishBoolean = false;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        ViewPager viewPager = getTouchViewPager(mainList, event);
        if (viewPager != null && viewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInt = tempInt = (int) event.getRawX();
                yInt = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                if (moveX - xInt > sileInt && Math.abs((int) event.getRawY() - yInt) < sileInt) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            widthInt = this.getWidth();
            getAlLViewPager(mainList, this);
        }
    }

    //私有方法

    private void scrollRight() {
        int delInt = (widthInt + mainView.getScrollX());
        mainScroller.startScroll(mainView.getScrollX(), 0, -delInt + 1, 0, Math.abs(delInt));
        postInvalidate();
    }

    private void scrollOrigin() {
        int delInt = mainView.getScrollX();
        mainScroller.startScroll(mainView.getScrollX(), 0, -delInt, 0, Math.abs(delInt));
        postInvalidate();
    }

    private void setContentView(View view) {

        mainView = (View) view.getParent();

    }

    private void getAlLViewPager(List<ViewPager> list, ViewGroup viewGroup) {
        int childInt = viewGroup.getChildCount();
        for (int i = 0; i < childInt; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewPager) {
                list.add((ViewPager) view);
            } else if (view instanceof ViewGroup) {
                getAlLViewPager(list, (ViewGroup) view);
            }
        }
    }

    private ViewPager getTouchViewPager(List<ViewPager> list, MotionEvent event) {
        if (list == null || list.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager viewPager : list) {
            viewPager.getHitRect(mRect);
            if (mRect.contains((int) event.getX(), (int) event.getY())) {
                return viewPager;
            }
        }
        return null;
    }

    //公共方法

    public SwipeLayout(Context context) {
        super(context);
        this.context = context;
        mainScroller = new Scroller(context);
        sileInt = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SwipeLayout(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mainScroller = new Scroller(context);
        sileInt = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void attachToActivity(Activity activity) {
        mainActivity = activity;
        TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = typedArray.getResourceId(0, 0);
        typedArray.recycle();
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup childViewGroup = (ViewGroup) viewGroup.getChildAt(0);
        childViewGroup.setBackgroundResource(background);
        viewGroup.removeView(childViewGroup);
        addView(childViewGroup);
        setContentView(childViewGroup);
        viewGroup.addView(this);
    }

    public void setMainDrawable(int id) {

        mainDrawable = ContextCompat.getDrawable(context, id);

    }

}
