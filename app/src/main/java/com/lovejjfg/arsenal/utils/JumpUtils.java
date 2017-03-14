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
import android.content.Intent;
import android.net.Uri;

import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.ui.AboutActivity;
import com.lovejjfg.arsenal.ui.ArsenalDetailInfoActivity;
import com.lovejjfg.arsenal.ui.ArsenalListInfoFragment;
import com.lovejjfg.arsenal.ui.ArsenalSearchActivity;
import com.lovejjfg.arsenal.ui.ArsenalUserInfoActivity;

/**
 * Created by Joe on 2016/3/18.
 * Email lovejjfg@gmail.com
 */
public class JumpUtils {
    public static void jumpToUserDetail(Context context, ArsenalUserInfo info) {
        Intent intent = new Intent(context, ArsenalUserInfoActivity.class);
        intent.putExtra(ArsenalUserInfoActivity.USER_INFO, info);
        context.startActivity(intent);

    }

    public static void jumpToSearchList(Context context, String tagKey) {
        Intent intent = new Intent(context, ArsenalSearchActivity.class);
        intent.putExtra(ArsenalListInfoFragment.KEY, tagKey);
        intent.putExtra(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_SEARCH);
        context.startActivity(intent);
    }

    public static void jumpToSearchList(Context context, String tagName, String tagKey) {
        Intent intent = new Intent(context, ArsenalSearchActivity.class);
        intent.putExtra(ArsenalListInfoFragment.KEY, tagKey);
        intent.putExtra(ArsenalListInfoFragment.TAG_NAME, tagName);
        intent.putExtra(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_SEARCH_TAG);
        context.startActivity(intent);
    }

    public static void jumpToDetail(Context context, ArsenalDetailInfo detailUrl) {
        Intent intent = new Intent(context, ArsenalDetailInfoActivity.class);
        intent.putExtra(ArsenalDetailInfoActivity.INFO, detailUrl);
        context.startActivity(intent);
    }


    public static void jumpToAbout(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void jumpToWeb(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        context.startActivity(Intent.createChooser(intent, url));
    }
}
