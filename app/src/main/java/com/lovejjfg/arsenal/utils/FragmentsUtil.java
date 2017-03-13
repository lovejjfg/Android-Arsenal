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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lovejjfg.arsenal.base.SupportFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public class FragmentsUtil {
    private final FragmentManager manager;

    public FragmentsUtil(FragmentManager manager) {
        this.manager = manager;
    }

    public void addToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.add(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .hide(from)
                .show(to)
                .commit();

    }

    public void addToParent(int containerViewId, @NonNull SupportFragment parent, int pos, SupportFragment... childs) {
        FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
        if (childs != null && childs.length > 0) {
            addFragmentsToStack(parent, containerViewId, pos, transaction, false, childs);
        }
    }

    public void replaceToParent(int containerViewId, @NonNull SupportFragment parent, SupportFragment... childs) {
        FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
        if (childs != null && childs.length > 0) {
            for (SupportFragment child : childs) {
                bindContainerId(containerViewId, child);
                String tag = child.getClass().getSimpleName();
                transaction.replace(containerViewId, child, tag)
                        .addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    public void replaceToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.replace(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .commit();

    }

    public void loadRoot(int containerViewId, int pos, SupportFragment... roots) {
        FragmentTransaction transaction = manager.beginTransaction();
        addFragmentsToStack(null, containerViewId, pos, transaction, true, roots);
    }

    private void addFragmentsToStack(SupportFragment parent, int containerViewId, int pos, FragmentTransaction transaction, boolean isRoot, SupportFragment[] fragments) {
        if (fragments != null && fragments.length > 0) {
            if (pos >= fragments.length || pos < 0) {
                throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + fragments.length);
            }
            for (int i = 0; i < fragments.length; i++) {
                SupportFragment f = fragments[i];
                f.isRoot = isRoot;
                bindContainerId(containerViewId, f);
                String tag = f.getClass().getSimpleName();
                transaction.add(containerViewId, f, tag)
                        .addToBackStack(tag);

                if (i == pos) {
                    transaction.show(f);
                } else {
                    transaction.hide(f);
                }
            }
            transaction.commit();
        }
    }

    private void bindContainerId(int containerId, SupportFragment to) {
        Bundle args = to.getArguments();
        if (args == null) {
            args = new Bundle();
            to.setArguments(args);
        }
        args.putInt(SupportFragment.ARG_CONTAINER, containerId);
    }

    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        if (savedInstanceState == null) {
            return;
        }
        boolean isSupportHidden = savedInstanceState.getBoolean(SupportFragment.ARG_IS_HIDDEN);

        FragmentTransaction ft = manager.beginTransaction();
        if (isSupportHidden) {
            ft.hide(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }


    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        int flag;
        if (includeSelf) {
            flag = FragmentManager.POP_BACK_STACK_INCLUSIVE;
        } else {
            flag = 0;
        }
        return manager.popBackStackImmediate(target.getSimpleName(), flag);
    }

    public boolean popSelf() {
        return manager.popBackStackImmediate();
    }

    public <T extends SupportFragment> T findFragment(@NonNull Class<T> className) {
        Fragment tagFragment = manager.findFragmentByTag(className.getSimpleName());
        try {
            return (T) tagFragment;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public List<Fragment> getTopFragments() {
        List<Fragment> fragments = manager.getFragments();
        List<Fragment> topFragments = new ArrayList<>();
        if (fragments == null) {
            return null;
        }
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (!f.isHidden()) {
                Fragment t = getTopFragment(f.getChildFragmentManager());//递归
                if (t != null) {
                    topFragments.add(t);
                } else {
                    topFragments.add(f);
                }
            }

        }
        return topFragments;
    }

    @Nullable
    private Fragment getTopFragment(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments == null) {
            return null;
        }
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (!f.isHidden()) {
                Fragment tTopFragment = getTopFragment(f.getChildFragmentManager());
                return tTopFragment == null ? f : tTopFragment;
            }
        }
        return null;
    }
}
