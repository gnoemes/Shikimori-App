package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.content.Context;

import com.gnoemes.shikimoriapp.data.repository.rates.converter.AnimeRateResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeDetailsResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.GenreResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse;
import com.gnoemes.shikimoriapp.entity.video.data.VideoResponse;
import com.gnoemes.shikimoriapp.entity.video.domain.Video;
import com.gnoemes.shikimoriapp.entity.video.domain.VideoType;
import com.gnoemes.shikimoriapp.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of data model converter
 */
public class AnimeDetailsResponseConverterImpl implements AnimeDetailsResponseConverter {

    private AnimeResponseConverter converter;
    private AnimeRateResponseConverter rateConverter;
    private RolesResponseConverter rolesResponseConverter;
    private Context context;

    @Inject
    public AnimeDetailsResponseConverterImpl(AnimeResponseConverter converter,
                                             AnimeRateResponseConverter rateConverter,
                                             RolesResponseConverter rolesResponseConverter,
                                             Context context) {
        this.converter = converter;
        this.rateConverter = rateConverter;
        this.rolesResponseConverter = rolesResponseConverter;
        this.context = context;
    }

    @Override
    public AnimeDetails convertDetailsWithCharacters(AnimeDetailsResponse response, List<RolesResponse> rolesResponses) {

        boolean isRomandziNaming = PrefUtils.isRomandziNaming(context);
        String name = isRomandziNaming ? response.getName() : response.getRussianName();
        String secondName = isRomandziNaming ? response.getRussianName() : response.getName();

        return new AnimeDetails(response.getId(),
                response.getTopicId(),
                name,
                secondName,
                converter.convertAnimeImage(response.getImage()),
                response.getUrl(),
                converter.convertAnimeType(response.getType()),
                converter.convertAnimeStatus(response.getStatus()),
                response.getEpisodes(),
                response.getEpisodesAired(),
                response.getAiredDate(),
                response.getReleasedDate(),
                response.getEnglishNames(),
                response.getJapaneseNames(),
                response.getDuration(),
                response.getScore(),
                response.getDescription(),
                convertGenres(response.getGenres()),
                rateConverter.convertUserRateResponse(response.getRateResponse()),
                convertVideos(response.getVideoResponses()),
                rolesResponseConverter.convertCharacters(rolesResponses)
        );
    }

    private List<Video> convertVideos(List<VideoResponse> videoResponses) {
        if (videoResponses == null) {
            return null;
        }

        List<Video> items = new ArrayList<>();

        for (VideoResponse response : videoResponses) {
            items.add(new Video(response.getId(),
                    response.getUrl(),
                    response.getName(),
                    comvertVideoType(response.getKind()),
                    response.getHosting()));
        }

        return items;
    }

    private VideoType comvertVideoType(String kind) {
        for (VideoType videoType : VideoType.values()) {
            if (videoType.equalsType(kind)) {
                return videoType;
            }
        }
        return VideoType.OTHER;
    }


    /**
     * Returns List<AnimeGenres> (ENUMS)
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
