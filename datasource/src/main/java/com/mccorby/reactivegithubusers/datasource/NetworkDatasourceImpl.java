package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.datasource.entities.ApiSummaryUser;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jco59 on 07/03/2016.
 */
public class NetworkDatasourceImpl implements UserDatasource {

    private GitHubUserApi api;

    public NetworkDatasourceImpl(GitHubUserApi api) {

        this.api = api;
    }

    @Override
    public Observable<List<GitHubUser>> userList() {
        return api.getUsers()
                .map(new Func1<List<ApiSummaryUser>, List<GitHubUser>>() {
                    @Override
                    public List<GitHubUser> call(List<ApiSummaryUser> s) {
                        List<GitHubUser> result = new ArrayList();
                        for (ApiSummaryUser summaryUser : s) {
                            GitHubUser gitHubUser = new GitHubUser();
                            gitHubUser.setLogin(summaryUser.getLogin());
                            result.add(gitHubUser);
                        }
                        return result;
                    }
                });
    }

    @Override
    public Observable<GitHubUser> user(String id) {
        api.getUserDetails(id);
        return null;
    }
}
