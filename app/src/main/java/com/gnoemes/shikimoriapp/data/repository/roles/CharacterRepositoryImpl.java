package com.gnoemes.shikimoriapp.data.repository.roles;

import com.gnoemes.shikimoriapp.data.network.RolesApi;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterDetailsResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;

import javax.inject.Inject;

import io.reactivex.Single;

public class CharacterRepositoryImpl implements CharacterRepository {

    private RolesApi api;
    private CharacterDetailsResponseConverter responseConverter;

    @Inject
    public CharacterRepositoryImpl(RolesApi api, CharacterDetailsResponseConverter responseConverter) {
        this.api = api;
        this.responseConverter = responseConverter;
    }

    @Override
    public Single<CharacterDetails> getCharacterDetails(long characterId) {
        return api.getCharacterDetails(characterId)
                .map(responseConverter);
    }
}
