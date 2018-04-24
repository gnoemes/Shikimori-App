package com.gnoemes.shikimoriapp.utils.imageloader;

import android.widget.ImageView;

public interface ImageLoader {

    void setImageWithFit(ImageView imageView, String url);

    void setImage(ImageView imageView, String url);
}
