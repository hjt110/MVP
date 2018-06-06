package com.ygw.library.http.retrofit.http;



import com.ygw.library.http.ok3.entity.BasicRequest;
import com.ygw.library.http.retrofit.http.base.HttpObserver;
import com.ygw.library.http.retrofit.http.config.HttpConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sunsh on 2018/5/29.
 */
public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;
    private static RetrofitService mRetrofitService;
    public static final String HTTP_MEDIA_TYPE = "application/json ; charset=utf-8";

    private RetrofitManager() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.tokenInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return null;
                    }
                })
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mRetrofitService = mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitManager getInstence() {
        //双重检查锁
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null)
                    mRetrofitManager = new RetrofitManager();
            }

        }
        return mRetrofitManager;
    }


    public void post(BasicRequest request, HttpObserver baseObserver) {
        mRetrofitService.json(request.getHttpRequestPath(), RequestBody.create(MediaType.parse(HTTP_MEDIA_TYPE), request.getObjectString())).compose(setThread()).subscribe(baseObserver);
    }

    public void put(BasicRequest request, HttpObserver baseObserver) {
       mRetrofitService.get(request.getHttpRequestPath(), RequestBody.create(MediaType.parse(HTTP_MEDIA_TYPE), request.getObjectString())).compose(setThread()).subscribe(baseObserver);
    }

    public <T> ObservableTransformer<T,T> setThread(){
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public RetrofitService service() {
        return mRetrofitService;
    }
}
