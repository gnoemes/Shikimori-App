package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.List;

import io.reactivex.functions.Function;

public interface CharacterResponseConverter extends Function<List<CharacterResponse>, List<Character>> {

    @Override
    List<Character> apply(List<CharacterResponse> characterResponses);

    Character convertResponse(CharacterResponse response);
}
