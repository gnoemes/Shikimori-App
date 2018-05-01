package com.gnoemes.shikimoriapp.presentation.view.translations.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;

import java.util.List;

import io.reactivex.functions.Function;

public interface TranslationViewModelConverter extends Function<List<Translation>, List<TranslationViewModel>> {
}
