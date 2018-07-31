package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Person;

import java.util.List;

import io.reactivex.functions.Function;

public interface PersonResponseConverter extends Function<List<PersonResponse>, List<Person>> {
    Person convertResponse(PersonResponse response);
}
