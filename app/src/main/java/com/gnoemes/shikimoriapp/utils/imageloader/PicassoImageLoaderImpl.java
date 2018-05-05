package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Image loader Picasso realization
 */
public class PicassoImageLoaderImpl implements ImageLoader {

    private Picasso picasso;

    @Inject
    public PicassoImageLoaderImpl() {
        picasso = Picasso.get();
    }

    @Override
    public void setCircleImage(ImageView imageView, @Nullable String url, @DrawableRes int errorImage) {
        picasso.load(BuildConfig.ShikimoriBaseUrl + url)
                .transform(new CircleTransformation())
                .fit()
                .centerCrop()
                .error(errorImage)
                .into(imageView);
    }

    @Override
    public void setCircleImage(ImageView imageView, int drawableRes) {
        picasso.load(drawableRes)
                .transform(new CircleTransformation())
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
        picasso.load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void setImage(ImageView imageView, String url) {
        picasso.load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .centerCrop()
                .into(imageView);

    }
}
