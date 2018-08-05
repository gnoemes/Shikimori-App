package com.gnoemes.shikimoriapp.data.network;

import com.gnoemes.shikimoriapp.entity.forum.data.ForumResponse;
import com.gnoemes.shikimoriapp.entity.topic.data.TopicResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TopicApi {

    @GET("/api/topics")
    Single<List<TopicResponse>> getTopics(@Query("page") int page,
                                          @Query("limit") int limit,
                                          @Query("forum") String forum);

    @GET("/api/topics/{id}")
    Single<TopicResponse> getTopic(@Path("id") long id);


    @GET("/api/forums")
    Single<List<ForumResponse>> getForums();
}
