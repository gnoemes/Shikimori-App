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
            if (translation.isValid()) {
                viewModels.add(convertTranslation(translation));
            }
        }
        return viewModels;
    }

    private TranslationViewModel convertTranslation(Translation translation) {
        return new TranslationViewModel(
                translation.getAnimeId(),
                translation.getEpisodeId(),
                translation.getVideoId(),
                translation.getType(),
                translation.getQuality(),
                translation.getHosting(),
                translation.getAuthor()
        );
    }

}
