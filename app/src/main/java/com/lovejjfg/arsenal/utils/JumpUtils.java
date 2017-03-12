package com.lovejjfg.arsenal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.ui.ArsenalUserInfoActivity;
import com.lovejjfg.arsenal.ui.ArsenalListInfoFragment;
import com.lovejjfg.arsenal.ui.ArsenalSearchActivity;
import com.lovejjfg.arsenal.ui.ArsenalDetailInfoActivity;

/**
 * Created by 张俊 on 2016/3/18.
 */
public class JumpUtils {
    public static void jumpToUserDetail(Context context, ArsenalUserInfo info) {
        Intent intent = new Intent(context, ArsenalUserInfoActivity.class);
        intent.putExtra(ArsenalUserInfoActivity.USER_INFO, info);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    public static void jumpToTagList(Context context, String tagKey, int type) {
        Intent intent = new Intent(context, ArsenalSearchActivity.class);
        intent.putExtra(ArsenalListInfoFragment.KEY, tagKey);
        intent.putExtra(ArsenalListInfoFragment.TYPE_NAME, type);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    public static void jumpToDetail(Context context, ArsenalDetailInfo detailUrl) {
        Intent intent = new Intent(context, ArsenalDetailInfoActivity.class);
        intent.putExtra(ArsenalDetailInfoActivity.INFO, detailUrl);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }


}
