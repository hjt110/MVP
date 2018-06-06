package com.ygw.library.http.retrofit.http.base;

import android.accounts.NetworkErrorException;
import android.content.Context;


import com.ygw.library.http.ok3.entity.HttpResponse;
import com.ygw.library.http.retrofit.widget.ProgressDialog;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by sunsh on 2018/5/29.
 */
public abstract class HttpObserver<T> implements Observer<HttpResponse<T>> {
    protected Context mContext;

    public HttpObserver(Context cxt) {
        this.mContext = cxt;
    }

    public HttpObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(HttpResponse<T> tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(HttpResponse<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(HttpResponse<T> t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
        ProgressDialog.show(mContext, false, "请稍后");
    }

    public void closeProgressDialog() {
        ProgressDialog.cancle();
    }

}
