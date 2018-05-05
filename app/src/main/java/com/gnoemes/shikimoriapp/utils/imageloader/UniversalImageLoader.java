package com.gnoemes.shikimoriapp.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import javax.inject.Inject;

public class UniversalImageLoader implements ImageLoader {

    private Context context;

    @Inject
    public UniversalImageLoader(Context context) {
        this.context = context;
    }


    @Override
    public void setCircleImage(ImageView imageView, @Nullable String url, int errorImage) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(BuildConfig.ShikimoriBaseUrl + url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(new CircleTransformation().transform(loadedImage));
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    @Override
    public void setCircleImage(ImageView imageView, int drawableRes) {
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(BuildConfig.ShikimoriBaseUrl + url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    @Override
    public void setImage(ImageView imageView, String url) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(BuildConfig.ShikimoriBaseUrl + url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
