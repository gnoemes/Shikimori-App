package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.roles.data.CharacterDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RolesApi {

    @GET("/api/characters/{id}")
    Single<CharacterDetailsResponse> getCharacterDetails(@Path("id") long characterId);

    @GET("/api/characters/search")
    Single<List<CharacterResponse>> getCharacterList(@QueryMap(encoded = true) Map<String, String> filter);

    @GET("/api/people/{id}")
    Single<PersonDetailsResponse> getPersonDetails(@Path("id") long peopleId);

    @GET("/api/people/search")
    Single<List<PersonResponse>> getPersonList(@QueryMap(encoded = true) Map<String, String> filter);
}
