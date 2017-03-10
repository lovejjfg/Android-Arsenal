package com.lovejjfg.arsenal.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.base.SupportFragment;
import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class UserDetailListInfoFragment extends SupportFragment implements AdapterLoader.OnItemClickListener {
    public static final String ARSENAL_LIST_INFO = "ARSENAL_LIST_INFO";
    @Bind(R.id.recycler_view)
    PowerRecyclerView mRecyclerView;
    private ArsenalListInfoAdapter listInfoAdapter;
    private int currentPage;

    @Override
    public void handleFinish() {

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
        mRecyclerView.setAdapter(listInfoAdapter);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setPullRefreshEnable(false);
        if (beans != null) {
            listInfoAdapter.setList(beans);
            listInfoAdapter.setTotalCount(beans.size());
        }

    }

    @Override
    public void onItemClick(View itemView, int position) {
//        ArsenalListInfo.ListInfo info = listInfoAdapter.list.get(position);
//        Intent intent = new Intent(getContext(), UserInfoActivity.class);
//        intent.putExtra("id", info.getUserDetailUrl());
//        startActivity(intent);
        Log.e(TAG, "onItemClick: " + position);
    }
}
