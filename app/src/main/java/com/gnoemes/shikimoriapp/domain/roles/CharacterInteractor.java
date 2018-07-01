package com.gnoemes.shikimoriapp.domain.roles;

import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;

import io.reactivex.Single;

public interface CharacterInteractor {

    Single<CharacterDetails> getCharacterDetails(long characterId);
}
