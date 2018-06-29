package com.gnoemes.shikimoriapp.utils.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import javax.inject.Inject;

/**
 * Image loader Picasso realization
 */
public class PicassoImageLoaderImpl implements ImageLoader {

//    private Picasso picasso;

    @Inject
    public PicassoImageLoaderImpl() {
//        picasso = Picasso.get();
    }

    public void setImageWithPlaceHolder(ImageView imageView, @Nullable String url, @DrawableRes int errorImage) {
//        picasso.load(url)
//                .transform(new CircleTransformation())
//                .fit()
//                .centerCrop()
//                .error(errorImage)
//                .into(imageView);
    }
    @Override
    public void setImageWithFit(ImageView imageView, String url) {
//        picasso.load(url)
//                .fit()
//                .centerCrop()
//                .into(imageView);
    }

    @Override
    public void clearImage(ImageView imageView) {

    }
}
