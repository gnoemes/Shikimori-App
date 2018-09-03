package com.gnoemes.shikimoriapp.presentation.presenter.search.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeViewModelConverterImpl implements AnimeViewModelConverter {

    @Inject
    public AnimeViewModelConverterImpl() {
    }

    @Override
    public List<BaseItem> convertListFrom(List<Anime> animeList) {
        List<BaseItem> viewModels = new ArrayList<>();

        for (Anime anime : animeList) {
            viewModels.add(convertAnime(anime));
        }

        return viewModels;
    }

    @Override
    public BaseSearchItem convertAnime(Anime anime) {
        return new AnimeViewModel(
                anime.getId(),
                anime.getName(),
                anime.getSecondName(),
                anime.getImage().getOriginal(),
                anime.getImage().getPreview(),
                anime.getImage().getX96(),
                anime.getImage().getX48(),
                anime.getUrl(),
                anime.getType(),
                anime.getStatus(),
                anime.getEpisodes(),
                anime.getEpisodesAired(),
                anime.getAiredDate(),
                anime.getReleasedDate());
    }
}
