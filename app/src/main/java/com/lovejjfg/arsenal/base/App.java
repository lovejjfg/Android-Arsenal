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

import android.app.Application;
import android.util.Log;

import com.lovejjfg.arsenal.BuildConfig;
import com.lovejjfg.arsenal.utils.LiteOrmHelper;
import com.lovejjfg.arsenal.utils.NetWorkUtils;
import com.lovejjfg.arsenal.utils.ToastUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * Created by Joe on 2016-04-05
 * Email: lovejjfg@gmail.com
 */
public class App extends Application {

    public static File CACHE_DIRECTORY;
    public static NetWorkUtils NETWORK_UTILS;
    private static App APP;


    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        LeakCanary.install(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(BuildConfig.CHANNEL);
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY, BuildConfig.IS_DEBUG, strategy);
        CACHE_DIRECTORY = new File(getApplicationContext().getCacheDir(), "responses");
        NETWORK_UTILS = NetWorkUtils.getsInstance(this);
        ToastUtil.initToast(getApplicationContext());
    }

    public static App getInstance() {
        return APP;
    }
}
