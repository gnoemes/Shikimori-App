package com.gnoemes.shikimoriapp.domain.roles;

import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;

import io.reactivex.Single;

public interface PersonInteractor {

    Single<PersonDetails> getPersonDetails(long personId);
}
