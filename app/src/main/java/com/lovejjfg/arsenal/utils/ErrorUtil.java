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

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.lovejjfg.arsenal.base.ArsenalException;
import com.lovejjfg.arsenal.base.IBaseView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Joe on 2017/1/5.
 * Email lovejjfg@gmail.com
 */

public class ErrorUtil {

    private static final SparseArray<String> map;

    static {
        map = new SparseArray<>();
        map.put(504, "Please make sure the network connection!");
    }


    public static void handleError(IBaseView view, Throwable throwable, boolean showToast, boolean showDialog) {
        Log.e("ErrorUtil", "handleError: ", throwable);
        view.showErrorView();
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            String s = map.get(code);
            if (!TextUtils.isEmpty(s)) {
                if (showToast) {
                    view.showToast(s);
                }
            }
            return;
        }
        if (throwable instanceof ArsenalException) {
            if (showToast) {
                view.showToast(((ArsenalException) throwable).message());
            }
            if (showDialog) {
                view.showLoadingDialog(((ArsenalException) throwable).message());
            }
            return;
        }
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            view.showToast("Network connection timeout!");
        }
        Log.e("unHandle", "handleError: " + throwable.toString());

    }
}
