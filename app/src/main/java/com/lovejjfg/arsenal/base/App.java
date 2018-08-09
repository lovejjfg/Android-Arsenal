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
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.lovejjfg.arsenal.BuildConfig;
import com.lovejjfg.arsenal.R;
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

    private GoogleAnalytics sAnalytics;
    private Tracker sTracker;

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
        Log.e("TAG", "APP:onCreate初始化。。。 ");
        sAnalytics = GoogleAnalytics.getInstance(this);
        if (sAnalytics.isInitialized()) {
            Log.e("TAG", "Analytics:onCreate初始化。。。)");
        }
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
            sTracker.enableExceptionReporting(true);
            sTracker.enableAutoActivityTracking(true);
        }

        return sTracker;
    }

    public static App getInstance() {
        return APP;
    }
}
