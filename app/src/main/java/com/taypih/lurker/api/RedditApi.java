package com.taypih.lurker.api;

import com.taypih.lurker.model.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditApi {
    @GET("/r/All/hot.json")
    Observable<ApiResponse> getTop(@Query("limit") int limit);

    @GET("/r/All/hot.json")
    Observable<ApiResponse> getTopAfter(
            @Query("limit") int limit,
            @Query("after") String after);

    @GET("/r/All/hot.json")
    Observable<ApiResponse> getTopBefore(
            @Query("limit") int limit,
            @Query("before") String before);
}
