package com.ygw.library;

import android.os.Bundle;

import com.ygw.library.swipe.SwipeBackActivity;


public abstract class BaseActivity extends SwipeBackActivity {

    //must make swipehelper.init on application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
