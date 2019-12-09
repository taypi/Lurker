package com.taypih.lurker.api;

import com.taypih.lurker.model.DetailResponse;
import com.taypih.lurker.model.ListResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApi {
    @GET("/r/All/hot.json")
    Observable<ListResponse> getTop(@Query("limit") int limit);

    @GET("/r/All/hot.json")
    Observable<ListResponse> getTopAfter(
            @Query("limit") int limit,
            @Query("after") String after);

    @GET("/r/All/hot.json")
    Observable<ListResponse> getTopBefore(
            @Query("limit") int limit,
            @Query("before") String before);

    @GET("/{id}.json")
    Observable<List<DetailResponse>> getPostDetails(@Path("id") String id);
}
