package com.gnoemes.shikimoriapp.data.repository.anime.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;

import java.util.List;

import io.reactivex.functions.Function;

public interface TranslationResponseConverter extends Function<List<TranslationResponse>, List<Translation>> {
    Translation convertResponse(TranslationResponse response);
}
