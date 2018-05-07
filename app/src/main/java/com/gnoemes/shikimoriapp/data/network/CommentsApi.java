package com.gnoemes.shikimoriapp.data.network;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.comments.data.CommentResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentsApi {

    @GET("/api/comments")
    Single<List<CommentResponse>> getComments(@Query("commentable_id") long id,
                                              @Query("commentable_type") @NonNull String type,
                                              @Query("page") int page,
                                              @Query("limit") int limit);
}
