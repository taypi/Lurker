package com.taypih.lurker.api;

import com.taypih.lurker.model.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RedditApiService {
    @GET("/r/All/hot/.json?")
    Observable<ApiResponse> getResponse();
}
