package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;

import io.reactivex.functions.Function;

public interface CharacterDetailsResponseConverter extends Function<CharacterDetailsResponse, CharacterDetails> {
}
