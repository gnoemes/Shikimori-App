package com.gnoemes.shikimoriapp.data.network;

import org.jsoup.nodes.Document;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api for video hosting
 */
public interface VideoApi {

    /**
     * Get html page of specific video
     */
    @GET("/animes/z{animeId}/video_online/{episode}/{videoId}")
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId, @Path("episode") int episode, @Path("videoId") long videoId);

    /**
     * Get html page of anime (information about episodes hostings etc)
     */
    @GET("/animes/z{animeId}/video_online/")
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId);

    /**
     * Get html page of anime with default video
     */
    @GET("/animes/z{animeId}/video_online/{episode}")
    Single<Document> getAnimeVideoInfo(@Path("animeId") long animeId, @Path("episode") int episode);

    /**
     * Get html source from vk hosting
     */
    @GET("http://vk.com/video_ext.php")
    Single<Document> getVkVideoInfo(@Query("oid") long oid, @Query("id") long id, @Query("hash") String hash);

    /**
     * Get html source from sibnet hosting
     */
    @GET("http://video.sibnet.ru/shell.php")
    Single<Document> getSibnetVideoInfo(@Query("videoid") long videoId);
}
