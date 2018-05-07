package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;

public interface AnimeItemCallback {

    void onAction(AnimeAction action, @Nullable Object data);
}
