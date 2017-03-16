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

import android.util.Log;

import com.lovejjfg.arsenal.BuildConfig;
import com.lovejjfg.arsenal.utils.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Joe on 2017/1/4.
 * Email lovejjfg@gmail.com
 */

public class LoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!BuildConfig.IS_DEBUG) {
            return chain.proceed(request);
        }
        long t1 = System.nanoTime();
        Logger.i(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();

        long t2 = System.nanoTime();
        String format = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());
        Logger.i(format);
        Log.e("TAG", "intercept: " + format);
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        long contentLength = responseBody.contentLength();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                Log.e(TAG, "intercept: " + "Couldn't decode the response body; charset is likely malformed.");
                Log.e(TAG, "intercept: " + "<-- END HTTP");
                return response;
            }
        }

        if (contentLength != 0) {
            Logger.json(buffer.clone().readString(charset));
        }

        Log.e(TAG, "intercept: " + "<-- END HTTP (" + buffer.size() + "-byte body)");
        return response;
    }
}
