package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.datasource.entities.ApiMapper;
import com.mccorby.reactivegithubusers.datasource.entities.ApiSummaryUser;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jco59 on 07/03/2016.
 */
public class NetworkDatasourceImpl implements UserDatasource {

    private static final int LIMIT = 3;
    private GitHubUserApi api;

    public NetworkDatasourceImpl(GitHubUserApi api) {

        this.api = api;
    }

    @Override
    public Observable<List<GitHubUser>> userList() {
        return userList(LIMIT);
    }

    @Override
    public Observable<List<GitHubUser>> userList(int limit) {
        final ApiMapper mapper = new ApiMapper();
        int randomOffset = (int) Math.floor(Math.random()*500);
        Observable<List<GitHubUser>> listObservable = api.getUsers(randomOffset)
                // Uses a cache
                .cache()
                // Transform the observer with a list into a list of observers
                .flatMap(new Func1<List<ApiSummaryUser>, Observable<ApiSummaryUser>>() {
                    @Override
                    public Observable<ApiSummaryUser> call(List<ApiSummaryUser> apiSummaryUsers) {
                        return Observable.from(apiSummaryUsers);
                    }
                })
                // To each obversable in the stream of observable, applies mapper.toGitHubUser
                .map(new Func1<ApiSummaryUser, GitHubUser>() {
                    @Override
                    public GitHubUser call(ApiSummaryUser apiSummaryUser) {
                        return mapper.toGitHubUser(apiSummaryUser);
                    }
                })
                // Limit the stream to 3 elements
                .limit(limit)
                // Transform the stream into an observable of a list
                .toList();
        return listObservable;
    }

    @Override
    public Observable<GitHubUser> user(String id) {
        // TBD
        api.getUserDetails(id);
        return null;
    }
}
