package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.app.data.TokenResponse;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {

    @POST("/oauth/token")
    Single<TokenResponse> getAccessToken(@Query("grant_type") String grantType,
                                         @Query("client_id") String clientId,
                                         @Query("client_secret") String clientSecret,
                                         @Query("code") String code,
                                         @Query("redirect_uri") String redirectUri,
                                         @Query("refresh_token") String refreshToken);
}
