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

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Joe on 2017/1/4.
 * Email lovejjfg@gmail.com
 */
public class ToastUtil {
    private static Toast TOAST;

    public static void initToast(Context context) {
        if (TOAST == null) {
            TOAST = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
    }

    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String msg, String duration) {
        initToast(context);
        TOAST.setText(msg);
        TOAST.setDuration(Toast.LENGTH_SHORT);
        TOAST.show();
    }

    private static void showToast(Context context, String msg, int duration) {
        initToast(context);
        TOAST.setText(msg);
        TOAST.setDuration(duration);
        TOAST.show();
    }
}
