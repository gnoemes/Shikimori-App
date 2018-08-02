package com.gnoemes.shikimoriapp.presentation.presenter.history.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DividerItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.history.presentation.HistoryItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HistoryViewModelConverterImpl implements HistoryViewModelConverter {

    @Inject
    public HistoryViewModelConverterImpl() {
    }

    @Override
    public List<BaseItem> convertFrom(List<Anime> animes) {
        List<BaseItem> items = new ArrayList<>();

        for (Anime anime : animes) {
            items.add(convertAnime(anime));
            items.add(new DividerItem());
        }

        return items;
    }

    private BaseItem convertAnime(Anime anime) {
        return new HistoryItem(
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
                anime.getReleasedDate());
    }
}
