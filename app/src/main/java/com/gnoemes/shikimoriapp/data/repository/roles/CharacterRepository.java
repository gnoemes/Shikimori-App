package com.gnoemes.shikimoriapp.data.repository.roles;

import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;

import io.reactivex.Single;

public interface CharacterRepository {

    Single<CharacterDetails> getCharacterDetails(long characterId);

}
