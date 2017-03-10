package com.lovejjfg.arsenal.ui;

import android.os.Bundle;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.base.SupportActivity;

public class MainActivity extends SupportActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            loadRoot(R.id.main_container, new ListInfoFragment());
        }
    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_main;
    }


}
