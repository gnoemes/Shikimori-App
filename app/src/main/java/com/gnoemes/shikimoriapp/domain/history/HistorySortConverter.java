package com.gnoemes.shikimoriapp.domain.history;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.LinkedHashSet;
import java.util.List;


public interface HistorySortConverter {

    List<Anime> sortAnimeByIds(List<Anime> animes, LinkedHashSet<Long> ids);
}
