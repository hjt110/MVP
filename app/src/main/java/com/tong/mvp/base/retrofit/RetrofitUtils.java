package com.tong.mvp.base.retrofit;

import com.tong.mvp.base.BaseConstant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Tong on 2018/4/20.
 */

public class RetrofitUtils {
    private static API api = null;

    //单例模式
    public static API getInstance() {
        if (api == null) {
            synchronized (RetrofitUtils.class) {
                if (api == null) {
                    Retrofit build = new Retrofit.Builder().baseUrl(BaseConstant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())//结果以String返回
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    api = build.create(API.class);
                }
            }
        }
        return api;
    }
}
