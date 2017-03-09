package com.lovejjfg.arsenal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.BaseDataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.base.SupportFragment;
import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class ListInfoFragment extends SupportFragment implements AdapterLoader.OnItemClickListener, PowerRecyclerView.OnRefreshLoadMoreListener {
    public static final String ARSENAL_LIST_INFO = "ARSENAL_LIST_INFO";
    @Bind(R.id.power_recycle_view)
    PowerRecyclerView mRecyclerView;
    private ArsenalListInfoAdapter listInfoAdapter;
    private String hasMore;

    @Override
    public void handleFinish() {
        getActivity().finish();
    }

    @Override
    public int initLayoutRes() {
        return R.layout.fragment_list_info;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<ArsenalListInfo.ListInfo> beans = getArguments().getParcelableArrayList(ARSENAL_LIST_INFO);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listInfoAdapter = new ArsenalListInfoAdapter();
        listInfoAdapter.setTotalCount(100);
        mRecyclerView.setAdapter(listInfoAdapter);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setOnRefreshListener(this);
        if (beans != null) {
            listInfoAdapter.setList(beans);
        } else {
            mRecyclerView.setRefresh(true);
            onRefresh();
        }

    }

    @Override
    public void onItemClick(View itemView, int position) {
        ArsenalListInfo.ListInfo info = listInfoAdapter.list.get(position);
        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().getArsenalUserInfo(info.getUserDetailUrl()), new Action1<ArsenalUserInfo>() {
            @Override
            public void call(final ArsenalUserInfo info) {
                closeLoadingDialog();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), UserInfoActivity.class);
                        intent.putExtra(UserInfoActivity.USER_INFO, info);
                        startActivity(intent);
                    }
                }, 200);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call: ", throwable);
                closeLoadingDialog();
            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });

        Log.e(TAG, "onItemClick: " + position);
    }

    @Override
    public void onRefresh() {
        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().getArsenalListInfo(), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo arsenalListInfos) {
                Log.e("TAG", "call:HaseMore:: " + arsenalListInfos.getHasMore());
                listInfoAdapter.setList(arsenalListInfos.getInfos());
                mRecyclerView.setRefresh(false);
                hasMore = arsenalListInfos.getHasMore();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mRecyclerView.setRefresh(false);
                Log.e(TAG, "call: ", throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
            }
        });
    }

    @Override
    public void onLoadMore() {
        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().loadMoreArsenalListInfo(hasMore), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo arsenalListInfos) {
                hasMore = arsenalListInfos.getHasMore();
                Log.e("TAG", "call:HaseMore:: " + arsenalListInfos.getHasMore());
                listInfoAdapter.appendList(arsenalListInfos.getInfos());
                if (TextUtils.isEmpty(hasMore)) {
                    listInfoAdapter.setTotalCount(listInfoAdapter.getItemRealCount());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call: ", throwable);
            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });

    }
}
