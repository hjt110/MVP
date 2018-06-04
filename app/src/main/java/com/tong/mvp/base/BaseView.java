package com.tong.mvp.base;

import android.content.Context;

/**
 * Created by Tong on 2018/4/23.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    Context getContext();

}
