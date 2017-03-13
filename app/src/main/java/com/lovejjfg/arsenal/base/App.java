package com.lovejjfg.arsenal.base;

import android.app.Application;
import android.util.Log;

import com.lovejjfg.arsenal.BuildConfig;
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

    public static File CacheDirectory;
    public static NetWorkUtils netWorkUtils;
//    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
//        FreelineCore.init(this);
        LeakCanary.install(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(BuildConfig.CHANNEL);
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY, true);

        CacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        netWorkUtils = NetWorkUtils.getsInstance(this);
        ToastUtil.initToast(getApplicationContext());
        Log.e("TAG", "APP:onCreate初始化。。。 ");
    }

}
