package com.tong.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import legow.com.lib_tong1804.R;


/**
 * Created by Tong on 2018/4/17.
 */

public abstract class SwipeActivity extends AppCompatActivity{

    /**
     * 使用：必须让activity的theme拥有MyTheme的属性
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeLayout swipeLayout = new SwipeLayout(this);
        swipeLayout.setMainDrawable(R.mipmap.bg_shadow);
        swipeLayout.attachToActivity(this);
    }
}
