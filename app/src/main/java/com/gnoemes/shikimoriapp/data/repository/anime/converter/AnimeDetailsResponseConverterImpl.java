package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeDetailsResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.GenreResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of data model converter
 */
public class AnimeDetailsResponseConverterImpl implements AnimeDetailsResponseConverter {

    private AnimeResponseConverter converter;

    @Inject
    public AnimeDetailsResponseConverterImpl(AnimeResponseConverter converter) {
        this.converter = converter;
    }

    @Override
    public AnimeDetails apply(AnimeDetailsResponse animeDetailsResponse) {
        return new AnimeDetails(animeDetailsResponse.getId(),
                animeDetailsResponse.getName(),
                animeDetailsResponse.getRussianName(),
                converter.convertAnimeImage(animeDetailsResponse.getImage()),
                animeDetailsResponse.getUrl(),
                converter.convertAnimeType(animeDetailsResponse.getType()),
                converter.convertAnimeStatus(animeDetailsResponse.getStatus()),
                animeDetailsResponse.getEpisodes(),
                animeDetailsResponse.getEpisodesAired(),
                animeDetailsResponse.getAiredDate(),
                animeDetailsResponse.getReleasedDate(),
                animeDetailsResponse.getEnglishNames(),
                animeDetailsResponse.getJapaneseNames(),
                animeDetailsResponse.getDuration(),
                animeDetailsResponse.getScore(),
                animeDetailsResponse.getDescription(),
                convertGenres(animeDetailsResponse.getGenres()));
    }


    /**
     * Returns List<AnimeGenres> (ENUMS)
     *
     * @param responses
     * @return enums
     */
    private List<AnimeGenre> convertGenres(List<GenreResponse> responses) {
        List<AnimeGenre> animeGenres = new ArrayList<>();

        for (GenreResponse genre : responses) {
            for (AnimeGenre animeGenre : AnimeGenre.values()) {
                String name = convertGenreName(genre.getName());
                if (animeGenre.equalsName(name)) {
                    animeGenres.add(animeGenre);
                }
            }
        }
        return animeGenres;
    }

    /**
     * Converts genre name for future action (e.g. slice_of_life)
     *
     * @param name
     * @return
     */
    private String convertGenreName(String name) {
        StringBuilder builder = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isWhitespace(c)) {
                builder.append('_');
            } else {
                builder.append(c);
            }
        }
        return builder.toString().toLowerCase();
    }
}
