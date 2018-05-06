package com.gnoemes.shikimoriapp.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import javax.inject.Inject;

public class UniversalImageLoader implements ImageLoader {

    private Context context;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    @Inject
    public UniversalImageLoader(Context context) {
        this.context = context;
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    }


    @Override
    public void setCircleImage(ImageView imageView, @Nullable String url, int errorImage) {
        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(new CircleTransformation().transform(loadedImage));
            }
        });
    }

    @Override
    public void setCircleImage(ImageView imageView, int drawableRes) {
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(loadedImage);
            }
        });
    }

    @Override
    public void setImage(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(loadedImage);
            }
        });
    }
}
