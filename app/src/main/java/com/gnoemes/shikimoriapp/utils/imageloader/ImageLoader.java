package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.AttrRes;
import android.widget.ImageView;

public interface ImageLoader {
    void setImageWithPlaceHolder(ImageView avatar, String avatarUrl, @AttrRes int color);

    void setImageWithFit(ImageView imageView, String url);
}
