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

package com.lovejjfg.arsenal.utils.glide;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * A Glide {@see ViewTarget} for {@link ImageView}s. It applies a badge for animated
 * images, can prevent GIFs from auto-playing & applies a palette generated ripple.
 */
public class ImageTarget extends GlideDrawableImageViewTarget {


    private final float density;

    public ImageTarget(ImageView view) {
        super(view);
        density = view.getContext().getResources().getDisplayMetrics().density;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable>
            animation) {
        ImageView badgedImageView = getView();
        int intrinsicWidth = resource.getIntrinsicWidth();
        int intrinsicHeight = resource.getIntrinsicHeight();
        ViewGroup.LayoutParams layoutParams = badgedImageView.getLayoutParams();
        if (intrinsicHeight != -1 && intrinsicWidth != -1) {
            boolean portrait = intrinsicHeight >= intrinsicWidth;
            if (portrait) {
                float height = 160 * density;
                if (intrinsicHeight > height) {
                    intrinsicHeight = (int) height;
                }
                float present = intrinsicHeight / height;
                intrinsicWidth = (int) (intrinsicWidth / present);
                intrinsicHeight = (int) height;

            } else {
                float width = 300 * density;
                if (intrinsicWidth > width) {
                    intrinsicWidth = (int) width;
                }
                float present = intrinsicWidth / width;
                intrinsicHeight = (int) (intrinsicHeight / present);
                intrinsicWidth = (int) width;
            }
            layoutParams.height = intrinsicHeight;

            layoutParams.width = intrinsicWidth;
        }
        badgedImageView.setLayoutParams(layoutParams);
        super.onResourceReady(resource, animation);
    }

}
