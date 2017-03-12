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
import com.lovejjfg.powerrecycle.PowerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joe on 2017/3/8.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfoAdapter extends PowerAdapter<ArsenalListInfo.ListInfo> {

    private  ListInfoContract.Presenter mPresenter;

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
        private  ListInfoContract.Presenter mPresenter;
        private ArsenalListInfo.ListInfo mListInfo;
        public ArsenalListInfoHolder(View itemView, ListInfoContract.Presenter mPresenter) {
            super(itemView);
            this.mPresenter = mPresenter;
            ButterKnife.bind(this, itemView);
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
                // TODO: 2017/3/10 resize image
                img.setVisibility(View.VISIBLE);
                Glide.with(img.getContext())
                        .load(info.getImgUrl())
                        .into(img);
            } else {
                img.setVisibility(View.GONE);
            }
            initView(registeredDate,info.getRegisteredDate());
            ivAndroid.setVisibility(info.isAndroid() ? View.VISIBLE : View.GONE);
            tvUser.setText(info.getUserName());
            tvUser.setVisibility(info.isUser() ? View.VISIBLE : View.GONE);
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpUtils.jumpToTagList(tag.getContext(), info.getTagUrl(), ArsenalListInfoFragment.TYPE_SEARCH_TAG);
                }
            });
            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    JumpUtils.jumpToUserDetail(tvUser.getContext(), );
                    mPresenter.onItemClick(mListInfo.getUserDetailUrl());
                }
            });


//            itemView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    // check if it's an event we care about, else bail fast
//                    final int action = event.getAction();
//                    if (!(action == MotionEvent.ACTION_DOWN
//                            || action == MotionEvent.ACTION_UP
//                            || action == MotionEvent.ACTION_CANCEL)) return false;
//
//                    // get the image and check if it's an animated GIF
//                    final Drawable drawable = img.getDrawable();
//                    if (drawable == null) return false;
//                    GifDrawable gif = null;
//                    if (drawable instanceof GifDrawable) {
//                        gif = (GifDrawable) drawable;
//                    } else if (drawable instanceof TransitionDrawable) {
//                        // we fade in images on load which uses a TransitionDrawable; check its layers
//                        TransitionDrawable fadingIn = (TransitionDrawable) drawable;
//                        for (int i = 0; i < fadingIn.getNumberOfLayers(); i++) {
//                            if (fadingIn.getDrawable(i) instanceof GifDrawable) {
//                                gif = (GifDrawable) fadingIn.getDrawable(i);
//                                break;
//                            }
//                        }
//                    }
//                    if (gif == null) return false;
//                    // GIF found, start/stop it on press/lift
//                    switch (action) {
//                        case MotionEvent.ACTION_DOWN:
//                            gif.start();
//                            break;
//                        case MotionEvent.ACTION_UP:
//                        case MotionEvent.ACTION_CANCEL:
//                            gif.stop();
//                            break;
//                    }
//                    return false;
//                }
//            });

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

