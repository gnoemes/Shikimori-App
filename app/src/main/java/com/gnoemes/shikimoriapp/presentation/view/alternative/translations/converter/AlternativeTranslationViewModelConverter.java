package com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;

import java.util.List;

import io.reactivex.functions.Function;

public interface AlternativeTranslationViewModelConverter extends Function<List<AlternativeTranslation>, List<AlternativeTranslationViewModel>> {
}
