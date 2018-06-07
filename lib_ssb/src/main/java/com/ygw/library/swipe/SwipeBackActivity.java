package com.ygw.library.swipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ygw.library.R;


/**
 * Created by sunsh on 2018/5/29.
 */
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackHelper.Delegate {
    private SwipeBackHelper mSwipeBackHelper;
    private ViewGroup swipe_layout;

    protected void onCreate(Bundle savedInstanceState) {
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        super.onCreate(savedInstanceState);
        StatusBarUtil.darkMode(this, isStatubarTextDark());
        if (isSupportSwipeBack()) {
            super.setContentView( R.layout.activity_swipe);

            swipe_layout = findViewById( R.id.swipe_layout);
            initSwipeBackFinish();
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        if (isSupportSwipeBack())
            LayoutInflater.from(this).inflate(layoutResID, swipe_layout);
        else super.setContentView(layoutResID);
    }

    public void setSwipeBackEnable(Boolean isEnable) {
        if (mSwipeBackHelper == null) {
            throw new NullPointerException(getClass().getSimpleName() + " is not support swipeback");
        } else {
            mSwipeBackHelper.setSwipeBackEnable(isEnable);
        }
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new SwipeBackHelper(this, this);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId( R.drawable.bg_swipe_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    protected boolean isStatubarTextDark() {
        return true;
    }

    public void setStatubarTextDark(boolean b) {
        StatusBarUtil.darkMode(this, b);
    }


}
