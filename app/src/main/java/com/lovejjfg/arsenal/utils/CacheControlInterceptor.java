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

package com.lovejjfg.arsenal.utils;



import com.lovejjfg.arsenal.base.App;
import com.lovejjfg.arsenal.utils.logger.Logger;

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
        if (!App.NETWORK_UTILS.isNetworkConnected()) {
//            Log.e(TAG, "createApi: 没有网络创建带cache的request！！");
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
//        Log.e(TAG, "intercept: " + cacheControl);
        if (!App.NETWORK_UTILS.isNetworkConnected()) {
//            Log.e(TAG, "createApi: 依然没有网络的response！！");
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
//            Log.e(TAG, "createApi: 有网了的response！！");
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        }
        return originalResponse;
    }
}
