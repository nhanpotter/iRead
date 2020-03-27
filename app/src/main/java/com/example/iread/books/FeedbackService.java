package com.example.iread.books;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackService {

    @GET("book/{id}/comment/")
    Single<List<Comment>> getCommentList(@Path("id") int id);

    @POST("book/{id}/comment/")
    Single<Comment> postComment(@Path("id") int id, @Body PostComment postComment);

    @GET("book/{id}/rating/")
    Single<Rating> getRating(@Path("id") int id);

    @POST("book/{id}/rating/")
    Single<Rating> postRating(@Path("id") int id, @Body PostRating postRating);
}
