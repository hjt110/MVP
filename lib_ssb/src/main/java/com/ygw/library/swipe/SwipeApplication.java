package com.ygw.library.swipe;

import android.app.Application;


/**
 * Created by sunsh on 2018/5/29.
 */
public class SwipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SwipeBackHelper.init(this,null);
    }
}
