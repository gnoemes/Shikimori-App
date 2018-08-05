package com.gnoemes.shikimoriapp.data.repository.alternative.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;

import java.util.List;

import io.reactivex.functions.Function;


public interface TranslationResponseConverter extends Function<List<TranslationResponse>, List<AlternativeTranslation>> {
    AlternativeTranslation convertResponse(TranslationResponse response);
}
