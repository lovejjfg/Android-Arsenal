package com.lovejjfg.arsenal.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.arsenal.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Joe on 2017/2/21.
 * Email lovejjfg@gmail.com
 */

public class LoadingDialog extends DialogFragment implements JumpBall.onDismissListener {

    @Bind(R.id.jump_ball)
    JumpBall mJumpBall;
    private DismissListener dismissListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mJumpBall.setDismissListener(this);
        mJumpBall.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mJumpBall.pause();
    }

    public void finish(DismissListener listener) {
        dismissListener = listener;
        mJumpBall.finish();
    }

    @Override
    public void onDismiss() {
        if (dismissListener != null) {
            dismissListener.onDismissed();
        }
        dismiss();
    }

    interface DismissListener {
        void onDismissed();
    }

}
