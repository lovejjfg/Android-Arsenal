/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.api;

import com.lovejjfg.arsenal.base.App;
import com.lovejjfg.arsenal.utils.CacheControlInterceptor;
import com.lovejjfg.arsenal.utils.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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
public class DataManager {
    private static final String TAG = "TAG";
    private static final String API = "https://android-arsenal.com/";
    private static Retrofit USER_API;

    private static <T> T createApi(Class<T> clazz) {
        if (USER_API == null) {
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(App.CACHE_DIRECTORY, cacheSize);
//            CookieJar cookieJar = initCookies();
            USER_API = new Retrofit.Builder()
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


            return USER_API.create(clazz);
        }
        return USER_API.create(clazz);
    }

    public static ArsenalService getArsenalApi() {
        return createApi(ArsenalService.class);
    }


    public static <R> Subscription handleNormalService(Observable<R> observable, Action0 doOnSubscribe, Action1<R> callSuccess, Action1<Throwable> callError) {

        return observable
                //事件产生在子线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(doOnSubscribe)
                //doOnSubscribe产生在主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callSuccess, callError);
    }

    public static <R> Subscription handleNormalService(Observable<R> observable, Action1<R> callSuccess, Action1<Throwable> callError) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callSuccess, callError);
    }


}
