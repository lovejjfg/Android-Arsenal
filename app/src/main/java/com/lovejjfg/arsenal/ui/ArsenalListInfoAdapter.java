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

package com.lovejjfg.arsenal.ui;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.ui.contract.ListInfoContract;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.glide.ImageTarget;
import com.lovejjfg.powerrecycle.PowerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2017/3/8.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfoAdapter extends PowerAdapter<ArsenalListInfo.ListInfo> {

    private final ListInfoContract.Presenter mPresenter;

    public ArsenalListInfoAdapter(ListInfoContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_arsenal_info, parent, false);
        return new ArsenalListInfoHolder(view, mPresenter);
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
        ((ArsenalListInfoHolder) holder).onBind(list.get(position));
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ArsenalListInfoHolder) {
            ((ArsenalListInfoHolder) holder).img.setVisibility(View.GONE);
        }
    }

    static final class ArsenalListInfoHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView title;
        @Bind(R.id.tv_tag)
        TextView tag;
        @Bind(R.id.tv_free)
        TextView badgeFree;
        @Bind(R.id.tv_new)
        TextView badgeNew;
        @Bind(R.id.tv_des)
        TextView desc;
        @Bind(R.id.iv_img)
        ImageView img;
        @Bind(R.id.ll_container)
        LinearLayout mContainer;
        @Bind(R.id.tv_date)
        TextView registeredDate;
        @Bind(R.id.iv_android)
        ImageView ivAndroid;
        @Bind(R.id.tv_user)
        TextView tvUser;
        private final ListInfoContract.Presenter mPresenter;
        private ArsenalListInfo.ListInfo mListInfo;
        private final ImageTarget mTarget;

        public ArsenalListInfoHolder(View itemView, ListInfoContract.Presenter mPresenter) {
            super(itemView);
            this.mPresenter = mPresenter;
            ButterKnife.bind(this, itemView);
            mTarget = new ImageTarget(img);
        }

        public void onBind(final ArsenalListInfo.ListInfo info) {
            mListInfo = info;
            title.setText(info.getTitle());
            tag.setText(info.getTag());
            badgeFree.setVisibility(info.isBadgeFree() ? View.VISIBLE : View.GONE);
            badgeNew.setVisibility(info.isBadgeNew() ? View.VISIBLE : View.GONE);
            String infoDesc = info.getDesc();
            initView(desc, infoDesc);
            if (!TextUtils.isEmpty(info.getImgUrl())) {
                img.setVisibility(View.VISIBLE);
                Glide.with(img.getContext())
                        .load(info.getImgUrl())
                        .into(mTarget);
            } else {
                img.setVisibility(View.GONE);
            }
            initView(registeredDate, info.getRegisteredDate());
            ivAndroid.setVisibility(info.isAndroid() ? View.VISIBLE : View.GONE);
            tvUser.setText(info.getUserName());
            tvUser.setVisibility(info.isUser() ? View.VISIBLE : View.GONE);
            tag.setOnClickListener(v -> JumpUtils.jumpToSearchList(tag.getContext(), info.getTag(), info.getTagUrl()));
            mContainer.setOnClickListener(v -> mPresenter.onItemClick(mListInfo.getUserDetailUrl()));
        }

        private static void initView(TextView view, String infoDesc) {
            if (TextUtils.isEmpty(infoDesc)) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
                view.setText(infoDesc, TextView.BufferType.SPANNABLE);
            }
        }
    }
}

