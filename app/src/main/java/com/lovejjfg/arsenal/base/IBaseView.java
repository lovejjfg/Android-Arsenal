package com.lovejjfg.arsenal.base;

public interface IBaseView<P extends BasePresenter> extends ISupportView {
    P initPresenter();
}
