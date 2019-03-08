package com.gnoemes.shikimoriapp.presentation.view.alternative.translations.adapter;

import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;

public interface TranslationItemCallback {

    void onTranslationClicked(AlternativeTranslationViewModel translation);

    void onDownloadTranslation(AlternativeTranslationViewModel translation);
}
