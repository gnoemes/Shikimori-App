package com.gnoemes.shikimoriapp.presentation.view.main.provider;

import android.support.annotation.ColorRes;

import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType;

public interface MangaResourceProvider {

    @ColorRes
    int getColorByMangaType(MangaType type);

    String getLocalizedType(MangaType type);
}
