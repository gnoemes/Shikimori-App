package com.gnoemes.shikimoriapp.data.repository.app.converter;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.RolesResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.club.ClubResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonResponseConverter;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;

import javax.inject.Inject;

public class LinkedContentResponseConverterImpl implements LinkedContentResponseConverter {

    private AnimeResponseConverter animeResponseConverter;
    private MangaResponseConverter mangaResponseConverter;
    private RolesResponseConverter rolesResponseConverter;
    private PersonResponseConverter personResponseConverter;
    private ClubResponseConverter clubResponseConverter;

    @Inject
    public LinkedContentResponseConverterImpl(AnimeResponseConverter animeResponseConverter,
                                              MangaResponseConverter mangaResponseConverter,
                                              RolesResponseConverter rolesResponseConverter,
                                              PersonResponseConverter personResponseConverter,
                                              ClubResponseConverter clubResponseConverter
    ) {
        this.animeResponseConverter = animeResponseConverter;
        this.mangaResponseConverter = mangaResponseConverter;
        this.rolesResponseConverter = rolesResponseConverter;
        this.personResponseConverter = personResponseConverter;
        this.clubResponseConverter = clubResponseConverter;
    }


    @Override
    public LinkedContent convertResponse(LinkedContentResponse response) {
        if (response instanceof AnimeResponse) {
            return animeResponseConverter.convertFrom((AnimeResponse) response);
        } else if (response instanceof MangaResponse) {
            return mangaResponseConverter.convertResponse((MangaResponse) response);
        } else if (response instanceof CharacterResponse) {
            return rolesResponseConverter.convertResponse((CharacterResponse) response);
        } else if (response instanceof PersonResponse) {
            return personResponseConverter.convertResponse((PersonResponse) response);
        } else if (response instanceof ClubResponse) {
            return clubResponseConverter.convertResponse((ClubResponse) response);
        }
        return null;
    }
}
