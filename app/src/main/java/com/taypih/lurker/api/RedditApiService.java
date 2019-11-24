package com.taypih.lurker.api;

import com.taypih.lurker.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RedditApiService {
    @GET("/r/All/hot/.json")
    Call<ApiResponse> getResponse();
}
