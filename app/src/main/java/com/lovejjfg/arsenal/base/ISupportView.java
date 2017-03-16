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

package com.lovejjfg.arsenal.base;

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

    boolean finishInner();

    /**
     * If you want Fragment to handle the back event ,you should override this method and return true.
     *
     * @return true: pop the current Fragment ,false otherwise.
     */
    boolean handleFinish();

    @LayoutRes
    int initLayoutRes();

//    void saveToSharedPrefs(String key, Object value);
//
//    Object getSharedPrefs(String key, Object defaultValue);

}
