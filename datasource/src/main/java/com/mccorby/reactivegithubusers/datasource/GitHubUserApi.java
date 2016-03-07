package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.datasource.entities.ApiSummaryUser;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GitHubUserApi {

    @GET("users")
    Observable<List<ApiSummaryUser>> getUsers();

    @GET("users/{login}")
    Observable<ApiSummaryUser> getUserDetails(@Path("login") String login);
}
