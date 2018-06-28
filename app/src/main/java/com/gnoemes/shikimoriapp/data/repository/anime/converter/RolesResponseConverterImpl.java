package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RolesResponseConverterImpl implements RolesResponseConverter {

    @Inject
    public RolesResponseConverterImpl() {
    }

    @Override
    public List<Character> convertCharacters(List<RolesResponse> responses) {
        List<Character> items = new ArrayList<>();

        for (RolesResponse response : responses) {
            if (response.getCharacterResponse() != null) {
                items.add(convertCharacterResponse(response.getCharacterResponse()));
            }
        }

        Collections.reverse(items);
        return items;
    }

    private Character convertCharacterResponse(CharacterResponse response) {
        return new Character(response.getId(),
                response.getName(),
                response.getRussianName(),
                new AnimeImage(
                        BuildConfig.ShikimoriBaseUrl + response.getImageResponse().getImageOriginalUrl(),
                        BuildConfig.ShikimoriBaseUrl + response.getImageResponse().getImagePreviewUrl(),
                        BuildConfig.ShikimoriBaseUrl + response.getImageResponse().getImageX96Url(),
                        BuildConfig.ShikimoriBaseUrl + response.getImageResponse().getImageX48Url()),
                response.getUrl());
    }
}
