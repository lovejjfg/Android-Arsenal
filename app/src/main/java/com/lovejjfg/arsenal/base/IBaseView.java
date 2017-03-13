package com.lovejjfg.arsenal.base;

import android.content.Context;

public interface IBaseView<P extends BasePresenter> extends ISupportView {
    P initPresenter();

    Context getContext();

}
