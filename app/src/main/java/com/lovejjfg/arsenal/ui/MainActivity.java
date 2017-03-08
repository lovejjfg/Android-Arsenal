package com.lovejjfg.arsenal.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.BaseDataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends SupportActivity implements AdapterLoader.OnItemClickListener, PowerRecyclerView.OnRefreshLoadMoreListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.power_recycle_view)
    PowerRecyclerView mRecyclerView;
    private ArsenalListInfoAdapter listInfoAdapter;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listInfoAdapter = new ArsenalListInfoAdapter();
        listInfoAdapter.setTotalCount(100);
        mRecyclerView.setAdapter(listInfoAdapter);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setRefresh(true);
        mRecyclerView.setOnRefreshListener(this);

        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().getArsenalListInfo(), new Action1<List<ArsenalListInfo>>() {


            @Override
            public void call(List<ArsenalListInfo> arsenalListInfos) {
                Log.e("TAG", "call: " + arsenalListInfos.size());
                listInfoAdapter.setList(arsenalListInfos);
                mRecyclerView.setRefresh(false);
                currentPage = 1;

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
    public int initLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Log.e(TAG, "onItemClick: " + position);
    }

    @Override
    public void onRefresh() {
        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().getArsenalListInfo(), new Action1<List<ArsenalListInfo>>() {
            @Override
            public void call(List<ArsenalListInfo> arsenalListInfos) {
                Log.e("TAG", "call: " + arsenalListInfos.size());
                listInfoAdapter.setList(arsenalListInfos);
                mRecyclerView.setRefresh(false);
                currentPage = 1;

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
        currentPage++;
        BaseDataManager.handleNormalService(BaseDataManager.getArsenalApi().loadMoreArsenalListInfo(currentPage), new Action1<List<ArsenalListInfo>>() {
            @Override
            public void call(List<ArsenalListInfo> arsenalListInfos) {
                listInfoAdapter.appendList(arsenalListInfos);

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
