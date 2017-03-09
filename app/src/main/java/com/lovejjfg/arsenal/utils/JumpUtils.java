package com.lovejjfg.arsenal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.ui.ListInfoFragment;
import com.lovejjfg.arsenal.ui.TagSearchActivity;
import com.lovejjfg.arsenal.ui.UserInfoActivity;

/**
 * Created by 张俊 on 2016/3/18.
 */
public class JumpUtils {
    public static void jumpToUserDetail(Activity context, ArsenalUserInfo info) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(UserInfoActivity.USER_INFO, info);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context);
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    public static void jumpToTagList(Context context, String tagKey) {
        Intent intent = new Intent(context, TagSearchActivity.class);
        intent.putExtra(ListInfoFragment.KEY, tagKey);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) context));
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }


}
