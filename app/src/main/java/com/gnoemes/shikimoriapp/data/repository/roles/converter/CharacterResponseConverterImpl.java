package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CharacterResponseConverterImpl implements CharacterResponseConverter {

    @Inject
    public CharacterResponseConverterImpl() {
    }

    @Override
    public List<Character> apply(List<CharacterResponse> characterResponses) {
        List<Character> characters = new ArrayList<>();

        for (CharacterResponse response : characterResponses) {
            characters.add(convertResponse(response));
        }

        return characters;
    }

    @Override
    public Character convertResponse(CharacterResponse response) {
        if (response == null) {
            return null;
        }
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
