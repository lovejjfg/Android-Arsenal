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

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import java.util.List;

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

interface ISupportFragment {
    void initFragments(Bundle savedInstanceState, SupportFragment fragment);

    @Nullable
    List<Fragment> getTopFragment();

    @Nullable
    <F extends SupportFragment> F findFragment(Class<F> className);

    void loadRoot(int containerViewId, SupportFragment... root);

    void addToShow(SupportFragment from, SupportFragment to);

    boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf);

    void replaceToShow(SupportFragment from, SupportFragment to);

    void addToParent(@IdRes int containerViewId, @NonNull SupportFragment parent, int pos, SupportFragment... children);


    void replaceToParent(int containerViewId, @NonNull SupportFragment parent, SupportFragment... children);

    void saveViewData(Bundle bundle);

    void saveViewData(String key, Bundle bundle);

    Toolbar getToolbar();

}
