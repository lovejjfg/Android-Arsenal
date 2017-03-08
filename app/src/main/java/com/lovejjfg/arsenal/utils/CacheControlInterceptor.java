package com.lovejjfg.arsenal.utils;

import android.util.Log;


import com.lovejjfg.arsenal.base.App;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Joe on 2017/1/4.
 * Email lovejjfg@gmail.com
 */

public class CacheControlInterceptor implements Interceptor {
    private static final String TAG = CacheControlInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!App.netWorkUtils.isNetworkConnected()) {
            Log.e(TAG, "createApi: 没有网络创建带cache的request！！");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        /**
         * Only accept the response if it is in the cache. If the response isn't cached, a {@code 504
         * Unsatisfiable Request} response will be returned.
         */
        String cacheControl = request.cacheControl().toString();
        Log.e(TAG, "intercept: " + cacheControl);
        if (!App.netWorkUtils.isNetworkConnected()) {
            Log.e(TAG, "createApi: 依然没有网络的response！！");
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            Log.e(TAG, "createApi: 有网了的response！！");
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        }
        return originalResponse;
    }
}
