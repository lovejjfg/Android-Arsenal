package com.lovejjfg.arsenal.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public interface ISupportFragment extends ISupportView {
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

}
