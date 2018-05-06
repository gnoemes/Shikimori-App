package com.gnoemes.shikimoriapp.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.AttrRes;
import android.view.View;
import android.widget.ImageView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
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
    public void setCircleImage(ImageView imageView, String url, @AttrRes int attrColor) {
        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Bitmap bitmap = DrawableHelper
                        .withContext(imageView.getContext())
                        .withDrawable(R.drawable.circle_image_placeholder)
                        .withAttributeColor(attrColor)
                        .tint()
                        .asBitmap();

                imageView.setImageBitmap(new CircleTransformation().transform(bitmap));
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(new CircleTransformation().transform(loadedImage));
            }
        });
    }

    @Override
    public void setCircleImage(ImageView imageView, int drawableRes) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        imageView.setImageBitmap(new CircleTransformation().transform(bitmap));
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
