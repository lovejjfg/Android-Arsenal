package com.lovejjfg.arsenal.api;

import android.util.Log;

import com.lovejjfg.arsenal.base.App;
import com.lovejjfg.arsenal.utils.CacheControlInterceptor;
import com.lovejjfg.arsenal.utils.LoggingInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lovejjfg on 2016/2/21.
 * <p/>
 * 用于统一管理api请求
 */
public class BaseDataManager {
    private static final String TAG = "TAG";
    private static final String API = "https://android-arsenal.com/";
    private static Retrofit userApi;

    private static <T> T createApi(Class<T> clazz) {
        if (userApi == null) {
            int cacheSize = 10 * 1024 * 1024;// 10 MiB
            Cache cache = new Cache(App.CacheDirectory, cacheSize);
//            CookieJar cookieJar = initCookies();
            userApi = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(ArsenalConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
//                            .cookieJar(cookieJar)
                            .cache(cache)
//                            .addInterceptor(chain -> chain.proceed(RequestUtils.createJustJsonRequest(chain.request())))
                            .addInterceptor(new CacheControlInterceptor())
                            .addInterceptor(new LoggingInterceptor())
//                            .addInterceptor(loggingInterceptor)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build())
                    .build();


            return userApi.create(clazz);
        }
        return userApi.create(clazz);
    }

    public static ArsenalService getArsenalApi() {
        return createApi(ArsenalService.class);
    }



    public static <R> Subscription handleNormalService(Observable<R> observable, Action1<R> callSuccess, Action1<Throwable> callError, Action0 doOnSubscribe) {
        return observable
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .doOnSubscribe(doOnSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())//doOnSubscribe产生在主线程
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(callSuccess, callError);
    }
}
