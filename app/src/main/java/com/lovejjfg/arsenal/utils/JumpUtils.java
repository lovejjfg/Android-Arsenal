package com.lovejjfg.arsenal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.ui.ListInfoFragment;
import com.lovejjfg.arsenal.ui.SearchActivity;
import com.lovejjfg.arsenal.ui.UserInfoActivity;
import com.lovejjfg.arsenal.ui.WebViewActivity;

/**
 * Created by 张俊 on 2016/3/18.
 */
public class JumpUtils {
    public static void jumpToUserDetail(Context context, ArsenalUserInfo info) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(UserInfoActivity.USER_INFO, info);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    public static void jumpToTagList(Context context, String tagKey, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(ListInfoFragment.KEY, tagKey);
        intent.putExtra(ListInfoFragment.TYPE_NAME, type);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    public static void jumpToDetail(Context context, String detailUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL, detailUrl);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }


}
