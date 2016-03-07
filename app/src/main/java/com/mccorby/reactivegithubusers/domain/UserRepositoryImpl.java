package com.mccorby.reactivegithubusers.domain;

import com.mccorby.reactivegithubusers.datasource.UserDatasource;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;
import com.mccorby.reactivegithubusers.domain.repository.UserRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by jco59 on 07/03/2016.
 */
public class UserRepositoryImpl implements UserRepository {

    private UserDatasource userDatasource;

    public UserRepositoryImpl(UserDatasource userDatasource) {

        this.userDatasource = userDatasource;
    }

    @Override
    public Observable<List<GitHubUser>> getListOfUsers() {
        return userDatasource.userList();
    }

    @Override
    public Observable<GitHubUser> getUserDetails(String id) {
        return userDatasource.user(id);
    }
}
