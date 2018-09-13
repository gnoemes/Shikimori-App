package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction;

public interface AnimeItemCallback {

    void onAction(DetailsAction action, @Nullable Object data);
}
