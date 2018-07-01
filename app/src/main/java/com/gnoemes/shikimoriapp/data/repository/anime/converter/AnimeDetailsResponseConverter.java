package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeDetailsResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse;

import java.util.List;

/**
 * Interface of converter. Anime full info data to domain model
 */
public interface AnimeDetailsResponseConverter {

    AnimeDetails convertDetailsWithCharacters(AnimeDetailsResponse animeDetailsResponse, List<RolesResponse> rolesResponses);
}
