package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterDetailsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharactersApi {

    @GET("/api/characters/{id}")
    Single<CharacterDetailsResponse> getCharacterDetails(@Path("id") long characterId);
}
