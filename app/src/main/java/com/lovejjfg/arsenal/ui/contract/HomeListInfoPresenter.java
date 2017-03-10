package com.lovejjfg.arsenal.ui.contract;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.utils.JumpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class HomeListInfoPresenter extends BaseListInfoPresenter {


    public HomeListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }

    @Override
    public void onItemClick(View itemView, final ArsenalListInfo.ListInfo info) {

        DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalDetailInfo(info.getListDetailUrl()), new Action1<String>() {
            @Override
            public void call(String data) {
                Log.e("TAG", "call: " + data);
                JumpUtils.jumpToDetail(mView.getContext(), data);
            }
        }, this);
    }

    @Override
    public void onRefresh() {
        unSubscribe();
        mView.onRefresh(true);
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalListInfo(), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                mHasMore = info.getHasMore();
                mView.onRefresh(info);
                mView.onRefresh(false);
                if (TextUtils.isEmpty(mHasMore)) {
                    mView.atEnd();
                }
            }
        }, this);
        subscribe(subscription);
    }

    @Override
    public void onLoadMore() {
        unSubscribe();
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().loadMoreArsenalListInfo(mHasMore), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                mHasMore = info.getHasMore();
                mView.onLoadMore(info);
                if (TextUtils.isEmpty(mHasMore)) {
                    mView.atEnd();
                }
            }
        }, this);
        subscribe(subscription);

    }


}
