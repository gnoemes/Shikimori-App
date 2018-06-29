package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.ColorRes;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.utils.imageloader.glide.GlideApp;

import javax.inject.Inject;

public class GlideImageLoaderImpl implements ImageLoader {

    @Inject
    public GlideImageLoaderImpl() {
    }

    @Override
    public void setImageWithPlaceHolder(ImageView avatar, String avatarUrl, @ColorRes int color) {
        GlideApp.with(avatar.getContext())
                .asBitmap()
                .load(avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .skipMemoryCache(true)
                .priority(Priority.NORMAL)
                .placeholder(R.drawable.circle_image_placeholder)
                .into(avatar);
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .optionalCenterCrop()
                .skipMemoryCache(true)
                .placeholder(0)
                .error(R.drawable.missing_original)
                .into(imageView);
    }

    @Override
    public void clearImage(ImageView imageView) {
        GlideApp.with(imageView).clear(imageView);
    }
}
