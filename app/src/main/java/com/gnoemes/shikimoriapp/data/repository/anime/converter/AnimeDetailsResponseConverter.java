package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeDetailsResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;

import io.reactivex.functions.Function;

/**
 * Interface of converter. Anime full info data to domain model
 */
public interface AnimeDetailsResponseConverter extends Function<AnimeDetailsResponse, AnimeDetails> {
}
