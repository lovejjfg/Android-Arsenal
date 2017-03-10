package com.lovejjfg.arsenal.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;


/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public interface ISupportView {
    void showToast(String toast);

    void showToast(@StringRes int stringId);

    //显示dialog
    void showLoadingDialog(String msg);

    void closeLoadingDialog();

    void openKeyBoard();

    void openKeyBoard(View focusView);

    void closeKeyBoard();

    //直接关闭当前的Activity
    boolean finishSelf();

    void handleFinish();

    @LayoutRes int initLayoutRes();

//    void saveToSharedPrefs(String key, Object value);
//
//    Object getSharedPrefs(String key, Object defaultValue);

}
