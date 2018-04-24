package com.gnoemes.shikimoriapp.presentation.presenter.search.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeViewModelConverterImpl implements AnimeViewModelConverter {

    @Inject
    public AnimeViewModelConverterImpl() {
    }

    @Override
    public List<BaseSearchItem> convertListFrom(List<Anime> animeList) {
        List<BaseSearchItem> viewModels = new ArrayList<>();

        for (Anime anime : animeList) {
            viewModels.add(new AnimeViewModel(
                    anime.getId(),
                    anime.getName(),
                    anime.getRussianName(),
                    anime.getAnimeImage().getImageOriginalUrl(),
                    anime.getAnimeImage().getImagePreviewUrl(),
                    anime.getAnimeImage().getImageX96Url(),
                    anime.getAnimeImage().getImageX48Url(),
                    anime.getUrl(),
                    anime.getType(),
                    anime.getStatus(),
                    anime.getEpisodes(),
                    anime.getEpisodesAired(),
                    anime.getAiredDate(),
                    anime.getReleasedDate()));
        }

        return viewModels;
    }
}
