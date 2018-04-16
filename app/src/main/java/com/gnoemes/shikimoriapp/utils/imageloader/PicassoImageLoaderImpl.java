package com.gnoemes.shikimoriapp.utils.imageloader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Image loader Picasso realization
 */
public class PicassoImageLoaderImpl implements ImageLoader {

    @Override
    public void setImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }
}
