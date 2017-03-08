package com.lovejjfg.arsenal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aspsine on 2015/3/13.
 */
public class NetWorkUtils {
    private static NetWorkUtils sInstance;
    private static Context mContext;

    private NetWorkUtils() {

    }

    public static NetWorkUtils getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (NetWorkUtils.class) {
                sInstance = new NetWorkUtils();
                mContext = context;
            }
        }
        return sInstance;

    }

    public static final boolean isNetWorkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }


}
