package com.gnoemes.shikimoriapp.presentation.presenter.search.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.presentation.BaseSearchItem;

import java.util.List;

public interface AnimeViewModelConverter {

    List<BaseItem> convertListFrom(List<Anime> animeList);

    BaseSearchItem convertAnime(Anime anime);
}
