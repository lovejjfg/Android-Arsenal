package com.lovejjfg.arsenal.utils;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.lovejjfg.arsenal.base.ArsenalException;
import com.lovejjfg.arsenal.base.IBaseView;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Joe on 2017/1/5.
 * Email lovejjfg@gmail.com
 */

public class ErrorUtil {

    private static SparseArray<String> map;

    static {
        map = new SparseArray<>();
        map.put(504, "无网络,无缓存！");
    }


    public static void handleError(IBaseView view, Throwable throwable, boolean showToast, boolean showDialog) {
        Log.e("ErrorUtil", "handleError: ", throwable);
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
        view.showToast("未处理的异常：" + throwable.toString());

    }
}
