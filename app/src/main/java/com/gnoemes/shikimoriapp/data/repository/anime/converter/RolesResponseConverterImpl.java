package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.data.repository.common.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.RolesResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RolesResponseConverterImpl implements RolesResponseConverter {

    private ImageResponseConverter imageResponseConverter;

    @Inject
    public RolesResponseConverterImpl(ImageResponseConverter imageResponseConverter) {
        this.imageResponseConverter = imageResponseConverter;
    }

    @Override
    public List<Character> convertCharacters(List<RolesResponse> responses) {
        List<Character> items = new ArrayList<>();

        for (RolesResponse response : responses) {
            if (response.getCharacterResponse() != null) {
                items.add(convertResponse(response.getCharacterResponse()));
            }
        }

        Collections.reverse(items);
        return items;
    }

    @Override
    public Character convertResponse(@Nullable CharacterResponse response) {
        if (response == null) {
            return null;
        }
        return new Character(response.getId(),
                response.getName(),
                response.getRussianName(),
                imageResponseConverter.convert(response.getImageResponse()),
                response.getUrl());
    }
}
