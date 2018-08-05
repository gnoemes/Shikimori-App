package com.gnoemes.shikimoriapp.data.repository.alternative.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.alternative.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.alternative.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TranslationResponseConverterImpl implements TranslationResponseConverter {

    private EpisodeResponseConverter converter;

    @Inject
    public TranslationResponseConverterImpl(EpisodeResponseConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<AlternativeTranslation> apply(List<TranslationResponse> responses) {
        List<AlternativeTranslation> translations = new ArrayList<>();

        for (TranslationResponse response : responses) {
            translations.add(convertResponse(response));
        }

        return translations;
    }

    @Override
    public AlternativeTranslation convertResponse(TranslationResponse response) {
        return new AlternativeTranslation(response.getId(),
                convertQuality(response.getQuality()),
                response.getTitle(),
                response.getPriority(),
                convertType(response.getType()),
                response.getUrl(),
                response.getEmbedUrl(),
                converter.convertFrom(response.getEpisodeResponse(), response.getSeriesResponses().getAnimeId()),
                response.getWidth(),
                response.getHeight(),
                response.getAuthors(),
                response.getSeriesResponses().getTitlesResponse().getRu());
    }

    private AlternativeTranslationType convertType(String type) {
        for (AlternativeTranslationType translationQuality : AlternativeTranslationType.values()) {
            if (translationQuality.isEqualType(type)) {
                return translationQuality;
            }
        }
        return null;
    }

    private TranslationQuality convertQuality(String quality) {
        for (TranslationQuality translationQuality : TranslationQuality.values()) {
            if (translationQuality.equalQuality(quality)) {
                return translationQuality;
            }
        }
        return null;
    }
}
