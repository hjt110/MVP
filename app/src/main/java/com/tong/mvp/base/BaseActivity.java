package com.tong.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    private Unbinder mUnbinder;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        mUnbinder = ButterKnife.bind(this);
        activity = this;

        init(savedInstanceState);
    }

    protected abstract int getLayoutResID();

    protected abstract void init(Bundle savedInstanceState);

    public Activity getActivity() {
        return activity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
