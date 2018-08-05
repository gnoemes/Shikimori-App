package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonDetailsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RolesApi {

    @GET("/api/characters/{id}")
    Single<CharacterDetailsResponse> getCharacterDetails(@Path("id") long characterId);

    @GET("/api/people/{id}")
    Single<PersonDetailsResponse> getPersonDetails(@Path("id") long peopleId);
}
