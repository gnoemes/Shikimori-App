package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.roles.data.PersonDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;

import io.reactivex.functions.Function;

public interface PersonDetailsResponseConverter extends Function<PersonDetailsResponse, PersonDetails> {
}
