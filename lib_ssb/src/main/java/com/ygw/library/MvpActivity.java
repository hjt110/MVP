package com.ygw.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.ygw.library.mvp.BaseModel;
import com.ygw.library.mvp.BasePresenter;
import com.ygw.library.mvp.BaseView;
import com.ygw.library.mvp.TUtil;
import com.ygw.library.swipe.SwipeBackActivity;

/**
 * Activity基类
 * Created by sunsh on 2018/5/29.
 */
public abstract class MvpActivity<T extends BasePresenter, E extends BaseModel> extends SwipeBackActivity {
    public T mPresenter;
    public E mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(getLayoutID());
        initIntentData(getIntent());
        initListener();
    }

    protected void init() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    protected void initIntentData(Intent intent) {

    }

    public abstract int getLayoutID();


    protected abstract void initListener();


}
