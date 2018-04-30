package com.gnoemes.shikimoriapp.data.repository.anime.series.converters.impl;

import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.EpisodeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.series.converters.TranslationResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.series.data.network.TranslationResponse;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationQuality;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

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
    public List<Translation> apply(List<TranslationResponse> responses) {
        List<Translation> translations = new ArrayList<>();

        for (TranslationResponse response : responses) {
            translations.add(convertResponse(response));
        }

        return translations;
    }

    private Translation convertResponse(TranslationResponse response) {
        return new Translation(response.getId(),
                convertQuality(response.getQuality()),
                response.getTitle(),
                response.getPriority(),
                convertType(response.getType()),
                response.getUrl(),
                response.getEmbedUrl(),
                converter.convertFrom(response.getEpisodeResponse(), response.getSeriesResponses().getAnimeId()),
                response.getWidth(),
                response.getHeight(),
                response.getAuthors());
    }

    private TranslationType convertType(String type) {
        for (TranslationType translationQuality : TranslationType.values()) {
            if (translationQuality.equalType(type)) {
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
