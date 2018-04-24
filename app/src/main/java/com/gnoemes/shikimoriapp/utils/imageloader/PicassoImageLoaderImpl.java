package com.gnoemes.shikimoriapp.utils.imageloader;

import android.widget.ImageView;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Image loader Picasso realization
 */
public class PicassoImageLoaderImpl implements ImageLoader {

    @Inject
    public PicassoImageLoaderImpl() {
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
        Picasso.get()
                .load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .into(imageView);
    }

    @Override
    public void setImage(ImageView imageView, String url) {
        Picasso.get()
                .load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .centerCrop()
                .into(imageView);

    }
}
