package com.gnoemes.shikimoriapp.presentation.view.alternative.translations.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.AlternativeTranslation;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AlternativeTranslationViewModelConverterImpl implements AlternativeTranslationViewModelConverter {

    @Inject
    public AlternativeTranslationViewModelConverterImpl() {
    }

    @Override
    public List<AlternativeTranslationViewModel> apply(List<AlternativeTranslation> translations) {
        List<AlternativeTranslationViewModel> models = new ArrayList<>();

        for (AlternativeTranslation translation : translations) {
            models.add(convertTranslation(translation));
        }

        return models;
    }

    private AlternativeTranslationViewModel convertTranslation(AlternativeTranslation translation) {
        return new AlternativeTranslationViewModel(translation.getId(),
                translation.getTitle(),
                null,
                translation.getQuality(),
                translation.getType(),
                translation.getEmbedUrl());
    }
}
