package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.data.repository.common.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CharacterResponseConverterImpl implements CharacterResponseConverter {

    private ImageResponseConverter imageResponseConverter;

    @Inject
    public CharacterResponseConverterImpl(ImageResponseConverter imageResponseConverter) {
        this.imageResponseConverter = imageResponseConverter;
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
                imageResponseConverter.convert(response.getImageResponse()),
                response.getUrl());
    }
}
