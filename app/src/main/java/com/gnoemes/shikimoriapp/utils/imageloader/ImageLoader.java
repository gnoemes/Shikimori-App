package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public interface ImageLoader {

    void setCircleImage(ImageView imageView, @Nullable String url, @DrawableRes int errorImage);

    void setCircleImage(ImageView imageView, @DrawableRes int drawableRes);

    void setImageWithFit(ImageView imageView, String url);

    void setImage(ImageView imageView, String url);

}
