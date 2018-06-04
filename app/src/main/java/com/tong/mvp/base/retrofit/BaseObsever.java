package com.tong.mvp.base.retrofit;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Tong on 2018/4/20.
 */
//观察者的封装
public abstract class BaseObsever<T> implements Observer<T> {

    //不需要下面重写的四个方法,只需要自己写的两个抽象方法
    public abstract void onSuccess(T t);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError",e.toString());
    }

    @Override
    public void onComplete() {

    }
}
