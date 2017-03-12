package com.lovejjfg.arsenal.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.base.BaseFragment;
import com.lovejjfg.arsenal.ui.contract.HomeListInfoPresenter;
import com.lovejjfg.arsenal.ui.contract.ListInfoContract;
import com.lovejjfg.arsenal.ui.contract.SearchListInfoPresenter;
import com.lovejjfg.arsenal.ui.contract.TagSearchListInfoPresenter;
import com.lovejjfg.arsenal.ui.contract.UserDetailListInfoPresenter;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfoFragment extends BaseFragment<ListInfoContract.Presenter> implements AdapterLoader.OnItemClickListener, PowerRecyclerView.OnRefreshLoadMoreListener, ListInfoContract.View {
    public static final String ARSENAL_LIST_INFO = "ARSENAL_LIST_INFO";
    public static final String TYPE_NAME = "TYPE_NAME";
    public static final String KEY = "KEY";
    public static final int TYPE_HOME = 0;
    public static final int TYPE_SEARCH = 1;
    public static final int TYPE_SEARCH_TAG = 2;
    public static final int TYPE_USER_DETAIL = 3;
    @Bind(R.id.recycler_view)
    PowerRecyclerView mRecyclerView;
    private ArsenalListInfoAdapter listInfoAdapter;
    private ArsenalListInfo mArsenalListInfo;

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
        ButterKnife.bind(this, view);
        ArrayList<ArsenalListInfo.ListInfo> beans = getArguments().getParcelableArrayList(ARSENAL_LIST_INFO);
        if (savedInstanceState != null) {
            beans = savedInstanceState.getParcelableArrayList(ARSENAL_LIST_INFO);
        }
        String key = getArguments().getString("KEY");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listInfoAdapter = new ArsenalListInfoAdapter(mPresenter);
        listInfoAdapter.setTotalCount(100);
        mRecyclerView.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRecyclerView.setAdapter(listInfoAdapter);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setOnRefreshListener(this);

        mPresenter.onViewPrepared();
        if (beans != null) {
            listInfoAdapter.setList(beans);
        } else if (!TextUtils.isEmpty(key)) {
            mPresenter.startSearch(key);
        } else {
            mPresenter.onRefresh();
        }

    }

    @Override
    public void onItemClick(View itemView, int position) {
        mPresenter.onItemClick(itemView, listInfoAdapter.getList().get(position));
        Log.e(TAG, "onItemClick: " + position);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.onLoadMore();
    }

    @Override
    public void onRefresh(ArsenalListInfo info) {
        listInfoAdapter.setList(info.getInfos());
        mArsenalListInfo = info;
    }

    @Override
    public void onLoadMore(ArsenalListInfo info) {
        listInfoAdapter.appendList(info.getInfos());
    }

    @Override
    public void jumpToTarget(ArsenalUserInfo userInfo) {
        JumpUtils.jumpToUserDetail(getActivity(), userInfo);
    }

    @Override
    public void atEnd() {
        listInfoAdapter.setTotalCount(listInfoAdapter.getItemRealCount());
    }

    @Override
    public void onRefresh(boolean refresh) {
        mRecyclerView.setRefresh(refresh);
    }

    @Override
    public void setPullRefreshEnable(boolean enable) {
        mRecyclerView.setPullRefreshEnable(false);
    }

    @Override
    public ListInfoContract.Presenter initPresenter() {
        int type = getArguments().getInt(TYPE_NAME);
        switch (type) {

            case TYPE_SEARCH:
                return new SearchListInfoPresenter(this);
            case TYPE_SEARCH_TAG:
                return new TagSearchListInfoPresenter(this);
            case TYPE_USER_DETAIL:
                return new UserDetailListInfoPresenter(this);
            case TYPE_HOME:
            default:
                return new HomeListInfoPresenter(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARSENAL_LIST_INFO, (ArrayList<ArsenalListInfo.ListInfo>) listInfoAdapter.getList());
        super.onSaveInstanceState(outState);
    }

    @Nullable
    public ArsenalListInfo getArsenalInfo() {
        return mArsenalListInfo;
    }

}
