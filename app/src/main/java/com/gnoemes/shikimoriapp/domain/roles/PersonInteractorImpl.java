package com.gnoemes.shikimoriapp.domain.roles;

import com.gnoemes.shikimoriapp.data.repository.roles.PersonRepository;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Inject;

import io.reactivex.Single;

public class PersonInteractorImpl implements PersonInteractor {

    private PersonRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public PersonInteractorImpl(PersonRepository repository, SingleErrorHandler singleErrorHandler, RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<PersonDetails> getPersonDetails(long personId) {
        return repository.getPersonDetails(personId)
                .compose((SingleErrorHandler<PersonDetails>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
