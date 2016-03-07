package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;
import com.mccorby.reactivegithubusers.repository.datasource.UserDatasource;

import java.util.List;

import rx.Observable;

/**
 * Created by jco59 on 07/03/2016.
 */
public class NetworkDatasourceImpl implements UserDatasource {

    private RestApi api;

    public NetworkDatasourceImpl(RestApi api) {

        this.api = api;
    }

    @Override
    public Observable<List<GitHubUser>> userList() {
        api.getUsers();
        return null;
    }

    @Override
    public Observable<GitHubUser> user(String id) {
        api.getUserDetails(id);
        return null;
    }
}
