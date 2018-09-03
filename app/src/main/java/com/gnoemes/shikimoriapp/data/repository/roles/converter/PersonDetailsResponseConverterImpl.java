package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaResponseConverter;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.SeyuRoleResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.WorkResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonFavoriteType;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonType;
import com.gnoemes.shikimoriapp.entity.roles.domain.Work;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonDetailsResponseConverterImpl implements PersonDetailsResponseConverter {

    private AnimeResponseConverter animeResponseConverter;
    private MangaResponseConverter mangaResponseConverter;
    private CharacterResponseConverter characterResponseConverter;
    private ImageResponseConverter imageResponseConverter;

    @Inject
    public PersonDetailsResponseConverterImpl(AnimeResponseConverter animeResponseConverter,
                                              MangaResponseConverter mangaResponseConverter,
                                              CharacterResponseConverter characterResponseConverter,
                                              ImageResponseConverter imageResponseConverter) {
        this.animeResponseConverter = animeResponseConverter;
        this.mangaResponseConverter = mangaResponseConverter;
        this.characterResponseConverter = characterResponseConverter;
        this.imageResponseConverter = imageResponseConverter;
    }

    @Override
    public PersonDetails apply(PersonDetailsResponse personDetailsResponse) {
        return new PersonDetails(
                personDetailsResponse.getId(),
                personDetailsResponse.getName(),
                personDetailsResponse.getRussianName(),
                personDetailsResponse.getJapaneseName(),
                imageResponseConverter.convert(personDetailsResponse.getImageResponse()),
                Utils.appendHostIfNeed(personDetailsResponse.getUrl()),
                personDetailsResponse.getJobTitle(),
                personDetailsResponse.getBirthDay(),
                converCharacters(personDetailsResponse.getSeyuRoleResponses()),
                convertWorks(personDetailsResponse.getWorkResponses()),
                personDetailsResponse.getRoleResponses(),
                personDetailsResponse.getTopicId(),
                convertPersonType(personDetailsResponse.isProducer(), personDetailsResponse.isMangaka(), personDetailsResponse.isSeyu()),
                convertPersonFavoriteType(personDetailsResponse.isFavoritePerson(), personDetailsResponse.isFavoriteProducer(), personDetailsResponse.isFavoriteMangaka(), personDetailsResponse.isFavoriteSeyu())
        );
    }

    private List<Character> converCharacters(List<SeyuRoleResponse> seyuRoleResponses) {
        if (seyuRoleResponses == null) {
            return null;
        }

        List<Character> characters = new ArrayList<>();

        for (SeyuRoleResponse response : seyuRoleResponses) {
            characters.addAll(characterResponseConverter.apply(response.getCharacterResponses()));
        }

        return characters;
    }

    private PersonFavoriteType convertPersonFavoriteType(boolean favoritePerson, boolean favoriteProducer, boolean favoriteMangaka, boolean favoriteSeyu) {
        return favoritePerson ? PersonFavoriteType.PERSON :
                favoriteProducer ? PersonFavoriteType.PRODUCER :
                        favoriteMangaka ? PersonFavoriteType.MANGAKA :
                                favoriteSeyu ? PersonFavoriteType.SEYU :
                                        PersonFavoriteType.NONE;
    }

    private PersonType convertPersonType(boolean producer, boolean mangaka, boolean seyu) {
        return producer ? PersonType.PRODUCER : mangaka ? PersonType.MANGAKA : seyu ? PersonType.SEYU : PersonType.NONE;
    }

    private List<Work> convertWorks(List<WorkResponse> workResponses) {
        List<Work> works = new ArrayList<>();

        for (WorkResponse response : workResponses) {
            works.add(convertWorkResponse(response));
        }

        return works;
    }

    private Work convertWorkResponse(WorkResponse response) {
        boolean isAnime = response.getAnimeResponse() != null;

        return new Work(
                isAnime ? Type.ANIME : Type.MANGA,
                animeResponseConverter.convertFrom(response.getAnimeResponse()),
                mangaResponseConverter.convertResponse(response.getMangaResponse()),
                response.getRole()
        );
    }
}
