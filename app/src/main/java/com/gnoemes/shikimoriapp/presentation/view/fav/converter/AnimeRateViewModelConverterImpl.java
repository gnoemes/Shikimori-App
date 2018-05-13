package com.gnoemes.shikimoriapp.presentation.view.fav.converter;

import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.rates.domain.AnimeRate;
import com.gnoemes.shikimoriapp.entity.rates.presentation.AnimeRateViewModel;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeRateViewModelConverterImpl implements AnimeRateViewModelConverter {

    private AnimeViewModelConverter converter;

    @Inject
    public AnimeRateViewModelConverterImpl(AnimeViewModelConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<BaseAnimeRateItem> convertFrom(List<AnimeRate> rates) {
        List<BaseAnimeRateItem> viewModels = new ArrayList<>();

        for (AnimeRate rate : rates) {
            viewModels.add(convertRate(rate));
        }

        return viewModels;
    }

    private AnimeRateViewModel convertRate(AnimeRate rate) {
        return new AnimeRateViewModel(rate.getId(),
                rate.getScore(),
                rate.getStatus(),
                rate.getText(),
                rate.getEpisodes(),
                rate.getChapters(),
                rate.getVolumes(),
                rate.getRewatches(),
                rate.getUserBrief(),
                (AnimeViewModel) converter.convertAnime(rate.getAnime()));
    }
}
