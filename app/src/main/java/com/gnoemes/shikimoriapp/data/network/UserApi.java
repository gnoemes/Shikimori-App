package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.user.data.AnimeRateResponse;
import com.gnoemes.shikimoriapp.entity.user.data.FavouritesResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserHistoryResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserMessageResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserUnreadMessages;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/api/users/whoami")
    Single<UserBriefResponse> getCurrentUserBriefInfo();

    @GET("/api/users")
    Single<List<UserBriefResponse>> getUsersList(@Query("page") int page, @Query("limit") int limit);

    @GET("/api/users/{id}/info")
    Single<UserBriefResponse> getUserBriefInfo(@Path("id") long id);

    @GET("/api/users/{id}/friends")
    Single<List<UserBriefResponse>> getUserFriends(@Path("id") long id);

    @GET("/api/users/{id}/anime_rates")
    Single<List<AnimeRateResponse>> getUserAnimeRates(@Query("page") int page, @Query("limit") int limit);

    @GET("/api/users/{id}/favourites")
    Single<FavouritesResponse> getUserFavourites(@Path("id") long id);

    @GET("/api/users/{id}/unread_messages")
    Single<UserUnreadMessages> getUnreadMessages(@Path("id") long id);

    @GET("/api/users/{id}/messages")
    Single<List<UserMessageResponse>> getUserMessages(@Path("id") long id);

    @GET("/api/users/{id}/history")
    Single<List<UserHistoryResponse>> getUserHistory(@Path("id") long id, @Query("page") int page, @Query("limit") int limit);

    //TODO model and api
    @GET("/api/users/{id}/clubs")
    Single getClubs();

    //TODO model and api
    @GET("/api/users/{id}")
    Single getUserProfile(@Path("id") long id);

}
