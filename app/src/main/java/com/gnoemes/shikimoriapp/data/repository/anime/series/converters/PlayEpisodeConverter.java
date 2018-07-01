package com.gnoemes.shikimoriapp.data.repository.anime.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;

import java.util.List;

import io.reactivex.functions.Function;

public interface PlayEpisodeConverter extends Function<String, List<PlayEpisode>> {
}
