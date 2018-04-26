package com.gnoemes.shikimoriapp.utils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.di.app.qualifiers.ClientCacheApi;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Image loader Picasso realization
 */
public class PicassoImageLoaderImpl implements ImageLoader {

    private Context context;
    private OkHttpClient client;
    private Picasso picasso;

    @Inject
    public PicassoImageLoaderImpl(Context context, @ClientCacheApi OkHttpClient client) {
        this.client = client;
        this.context = context;
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .memoryCache(new LruCache(50 * 1024 * 1024))
                .indicatorsEnabled(true)
                .build();
    }

    @Override
    public void setImageWithFit(ImageView imageView, String url) {
//        Picasso.get()
//                .load(BuildConfig.ShikimoriBaseUrl + url)
//                .fit()
//                .into(imageView);

        picasso.load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .into(imageView);
    }

    @Override
    public void setImage(ImageView imageView, String url) {
//        Picasso.get()
//                .load(BuildConfig.ShikimoriBaseUrl + url)
//                .fit()
//                .centerCrop()
//                .into(imageView);
        picasso.load(BuildConfig.ShikimoriBaseUrl + url)
                .fit()
                .into(imageView);

    }

}
