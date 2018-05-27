package com.gnoemes.shikimoriapp.presentation.view.translations.converter;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TranslationViewModelConverterImpl implements TranslationViewModelConverter {

    @Inject
    public TranslationViewModelConverterImpl() {
    }

    @Override
    public List<TranslationViewModel> apply(List<Translation> translations) {
        List<TranslationViewModel> viewModels = new ArrayList<>();

        for (Translation translation : translations) {
            viewModels.add(convertTranslation(translation));
        }
        return viewModels;
    }

    private TranslationViewModel convertTranslation(Translation translation) {
        return new TranslationViewModel(
                convertTitle(translation.getTitle()),
                convertResolution(translation.getWidth(), translation.getHeight()),
                translation.getQuality(),
                translation.getType(),
                translation.getEmbedUrl());
    }

    private String convertTitle(String title) {
        String converted = title.substring(title.lastIndexOf('/')).replaceFirst("/", "");
        return converted.charAt(0) == ' ' ? converted.replaceFirst(" ", "") : converted;
    }

    private String convertResolution(int width, int height) {
        return String.format("%sx%s", String.valueOf(width), String.valueOf(height));
    }
}
