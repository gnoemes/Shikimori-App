package com.gnoemes.shikimoriapp.data.network;

import org.jsoup.nodes.Document;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Api for video hosting
 */
public interface VideoApi {

    /**
     * Get html page of specific video
     */
    @GET("/animes/a{animeId}/video_online/{episode}/{videoId}")
    @Headers({"User-Agent: okhttp 3.11"})
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId, @Path("episode") int episode, @Path("videoId") long videoId);

    /**
     * Get html page of anime (information about episodes hostings etc)
     */
    @GET("/animes/a{animeId}/video_online/")
    @Headers({"User-Agent: okhttp 3.11"})
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId);

    /**
     * Get html page of anime with default video
     */
    @GET("/animes/a{animeId}/video_online/{episode}")
    @Headers({"User-Agent: okhttp 3.11"})
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId, @Path("episode") int episode);

    /**
     * Get html source from hosting
     */
    @GET
    Single<Document> getVideoSource(@Url String url);

    /**
     * Handle raw response with headers and redirect urls
     */
    @GET
    Single<Response<ResponseBody>> getRawVideoResponse(@Url String url, @Header("Referer") String refererHeader);
}
