package com.lovejjfg.arsenal.ui;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.ui.contract.ListInfoContract;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.glide.CircleTransform;
import com.lovejjfg.powerrecycle.PowerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joe on 2017/3/8.
 * Email lovejjfg@gmail.com
 */

public class ArsenalListInfoAdapter extends PowerAdapter<ArsenalListInfo.ListInfo> {
    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        return new ArsenalListInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_arsenal_info, parent, false));
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
        ((ArsenalListInfoHolder) holder).onBind(list.get(position));
    }


    static final class ArsenalListInfoHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_location)
        TextView title;
        @Bind(R.id.tv_tag)
        TextView tag;
        @Bind(R.id.iv_free)
        ImageView badgeFree;
        @Bind(R.id.iv_new)
        ImageView badgeNew;
        @Bind(R.id.tv_des)
        TextView desc;
        @Bind(R.id.iv_img)
        ImageView img;
        @Bind(R.id.tv_date)
        TextView registeredDate;
        @Bind(R.id.iv_android)
        ImageView ivAndroid;
        @Bind(R.id.tv_user)
        TextView tvUser;

        public ArsenalListInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(final ArsenalListInfo.ListInfo info) {
            title.setText(info.getTitle());
            tag.setText(info.getTag());
            badgeFree.setVisibility(info.isBadgeFree() ? View.VISIBLE : View.GONE);
            badgeNew.setVisibility(info.isBadgeNew() ? View.VISIBLE : View.GONE);
            desc.setText(info.getDesc());
            if (!TextUtils.isEmpty(info.getImgUrl())) {
                img.setVisibility(View.VISIBLE);
                Glide.with(img.getContext())
                        .load(info.getImgUrl())
                        .into(img);
            } else {
                img.setVisibility(View.GONE);
            }
            registeredDate.setText(info.getRegisteredDate());
            ivAndroid.setVisibility(info.isAndroid() ? View.VISIBLE : View.GONE);
            tvUser.setText(info.getUserName());
            tvUser.setVisibility(info.isUser() ? View.VISIBLE : View.GONE);
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpUtils.jumpToTagList(tag.getContext(), info.getTagUrl());
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

        @OnClick({R.id.tv_tag})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_tag:

                    break;

            }
        }


    }
}

