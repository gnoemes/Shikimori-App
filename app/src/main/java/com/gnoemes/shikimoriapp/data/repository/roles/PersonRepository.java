package com.gnoemes.shikimoriapp.data.repository.roles;

import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;

import io.reactivex.Single;

public interface PersonRepository {

    Single<PersonDetails> getPersonDetails(long personId);
}
