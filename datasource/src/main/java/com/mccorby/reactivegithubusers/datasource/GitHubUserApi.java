package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.datasource.entities.ApiSummaryUser;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface GitHubUserApi {

    @GET("users?since=offset")
    Observable<List<ApiSummaryUser>> getUsers(@Query("since") int offset);

    @GET("users/{login}")
    Observable<ApiSummaryUser> getUserDetails(@Path("login") String login);
}
