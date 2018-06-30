package com.gnoemes.shikimoriapp.domain.roles;

import com.gnoemes.shikimoriapp.data.repository.roles.CharacterRepository;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;
import com.gnoemes.shikimoriapp.utils.rx.RxUtils;
import com.gnoemes.shikimoriapp.utils.rx.SingleErrorHandler;

import javax.inject.Inject;

import io.reactivex.Single;

public class CharacterInteractorImpl implements CharacterInteractor {

    private CharacterRepository repository;
    private SingleErrorHandler singleErrorHandler;
    private RxUtils rxUtils;

    @Inject
    public CharacterInteractorImpl(CharacterRepository repository,
                                   SingleErrorHandler singleErrorHandler,
                                   RxUtils rxUtils) {
        this.repository = repository;
        this.singleErrorHandler = singleErrorHandler;
        this.rxUtils = rxUtils;
    }

    @Override
    public Single<CharacterDetails> getCharacterDetails(long characterId) {
        return repository.getCharacterDetails(characterId)
                .compose((SingleErrorHandler<CharacterDetails>) singleErrorHandler)
                .compose(rxUtils.applySingleSchedulers());
    }
}
