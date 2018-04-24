package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.List;

import io.reactivex.functions.Function;

public interface AnimeListResponseConverter extends Function<List<AnimeResponse>, List<Anime>> {
}
