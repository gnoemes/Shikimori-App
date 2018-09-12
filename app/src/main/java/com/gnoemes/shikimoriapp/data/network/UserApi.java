package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.AnimeRateResponse;
import com.gnoemes.shikimoriapp.entity.rates.data.UserRateCreateOrUpdateRequest;
import com.gnoemes.shikimoriapp.entity.user.data.FavoritesResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBanResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserHistoryResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserMessageResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserProfileResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserUnreadMessages;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    /**
     * Get user brief info
     *
     * @return
     */
    @GET("/api/users/whoami")
    Single<UserBriefResponse> getCurrentUserBriefInfo();

    /**
     * Get list of users
     */
    @GET("/api/users")
    Single<List<UserBriefResponse>> getUsersList(@Query("page") int page, @Query("limit") int limit);

    /**
     * Get full info about user
     */
    @GET("/api/users/{id}/info")
    Single<UserBriefResponse> getUserBriefInfo(@Path("id") long id);

    /**
     * Get user's friends
     */
    @GET("/api/users/{id}/friends")
    Single<List<UserBriefResponse>> getUserFriends(@Path("id") long id);

    /**
     * Get user's anime rates
     */
    @GET("/api/users/{id}/anime_rates")
    Single<List<AnimeRateResponse>> getUserAnimeRates(@Path("id") long id,
                                                      @Query("page") int page,
                                                      @Query("limit") int limit,
                                                      @Query("status") String status);

    /**
     * Delete rate
     */
    @DELETE("/api/v2/user_rates/{id}")
    Completable deleteRate(@Path("id") long id);

    /**
     * Create rate
     */
    @POST("/api/v2/user_rates")
    Completable createRate(@Body UserRateCreateOrUpdateRequest request);

    /**
     * Update Rate
     */
    @PATCH("/api/v2/user_rates/{id}")
    Completable updateRate(@Path("id") long id, @Body UserRateCreateOrUpdateRequest request);

    /**
     * Increment episode
     */
    @POST("/api/v2/user_rates/{id}/increment")
    Completable incrementEpisode(@Path("id") long id);

    /**
     * Get user's favourites
     */
    @GET("/api/users/{id}/favourites")
    Single<FavoritesResponse> getUserFavourites(@Path("id") long id);

    /**
     * Get user's unread messages
     * <p>
     * ~Need authorization~
     */
    @GET("/api/users/{id}/unread_messages")
    Single<UserUnreadMessages> getUnreadMessages(@Path("id") long id);

    /**
     * Get user's messages
     * <p>
     * ~Need authorization~
     */
    @GET("/api/users/{id}/messages")
    Single<List<UserMessageResponse>> getUserMessages(@Path("id") long id);

    /**
     * Get user history
     */
    @GET("/api/users/{id}/history")
    Single<List<UserHistoryResponse>> getUserHistory(@Path("id") long id, @Query("page") int page, @Query("limit") int limit);

    @GET("/api/users/{id}/clubs")
    Single<List<ClubResponse>> getUserClubs(@Path("id") long id);

    @GET("/api/users/{id}")
    Single<UserProfileResponse> getUserProfile(@Path("id") long id);

    @POST("/api/friends/{id}")
    Completable addToFriends(@Path("id") long id);

    @DELETE("/api/friends/{id}")
    Completable deleteFriend(@Path("id") long id);

    @POST(" /api/v2/users/{user_id}/ignore")
    Completable ignoreUser(@Path("user_id") long id);

    @DELETE(" /api/v2/users/{user_id}/ignore")
    Completable unignoreUser(@Path("user_id") long id);

    @GET("/api/users/{id}/bans")
    Single<List<UserBanResponse>> getUserBans(@Path("id") long id);
}
