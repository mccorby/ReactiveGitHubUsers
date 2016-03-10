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
        final ApiMapper mapper = new ApiMapper();
        Observable<List<GitHubUser>> listObservable = api.getUsers()
                .flatMap(new Func1<List<ApiSummaryUser>, Observable<ApiSummaryUser>>() {
                    @Override
                    public Observable<ApiSummaryUser> call(List<ApiSummaryUser> apiSummaryUsers) {
                        return Observable.from(apiSummaryUsers);
                    }
                })
                .map(new Func1<ApiSummaryUser, GitHubUser>() {
                    @Override
                    public GitHubUser call(ApiSummaryUser apiSummaryUser) {
                        return mapper.toGitHubUser(apiSummaryUser);
                    }
                })
                .limit(LIMIT)
                .toList();
        return listObservable;
    }

    @Override
    public Observable<GitHubUser> user(String id) {
        api.getUserDetails(id);
        return null;
    }
}
