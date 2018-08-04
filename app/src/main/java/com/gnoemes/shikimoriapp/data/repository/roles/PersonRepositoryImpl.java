package com.gnoemes.shikimoriapp.data.repository.roles;

import com.gnoemes.shikimoriapp.data.network.RolesApi;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonDetailsResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;

import javax.inject.Inject;

import io.reactivex.Single;

public class PersonRepositoryImpl implements PersonRepository {

    private RolesApi api;
    private PersonDetailsResponseConverter converter;

    @Inject
    public PersonRepositoryImpl(RolesApi api, PersonDetailsResponseConverter converter) {
        this.api = api;
        this.converter = converter;
    }

    @Override
    public Single<PersonDetails> getPersonDetails(long personId) {
        return api.getPersonDetails(personId)
                .map(converter);
    }
}
