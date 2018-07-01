package com.gnoemes.shikimoriapp.utils.imageloader;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.widget.ImageView;

import javax.inject.Inject;

public class UniversalImageLoader implements ImageLoader {

//    private Context context;
//    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    @Inject
    public UniversalImageLoader(Context context) {
//        this.context = context;
//        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    }

    @Override
    public void setImageWithPlaceHolder(ImageView imageView, String url, @AttrRes int attrColor) {
//        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                Bitmap bitmap = DrawableHelper
//                        .withContext(imageView.getContext())
//                        .withDrawable(R.drawable.circle_image_placeholder)
//                        .withAttributeColor(attrColor)
//                        .tint()
//                        .asBitmap();
//
//                imageView.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                imageView.setImageBitmap(loadedImage);
//            }
//        });
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
//        imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageBitmap(loadedImage);
//            }
//        });
    }

    @Override
    public void clearImage(ImageView imageView) {

    }
}
