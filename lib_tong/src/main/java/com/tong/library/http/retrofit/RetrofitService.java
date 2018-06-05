package com.tong.library.http.retrofit;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Tong on 2018/4/20.
 */

public interface RetrofitService {

    @GET("s?wd=baid&ie=UTF-8")
    Observable<String> getInfo();
}
