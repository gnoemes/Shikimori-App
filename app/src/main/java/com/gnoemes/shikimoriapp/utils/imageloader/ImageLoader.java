package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {

    void setCircleImage(ImageView imageView, @DrawableRes int drawableRes);

    void setCircleImage(ImageView avatar, String avatarUrl, @AttrRes int color);

    void setImageWithFit(ImageView imageView, String url);

    void setImage(ImageView imageView, String url);
}
