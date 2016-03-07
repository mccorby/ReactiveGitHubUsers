package com.mccorby.reactivegithubusers.datasource;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

import rx.Observable;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface UserDatasource {

    Observable<List<GitHubUser>> userList();
    Observable<GitHubUser> user(String id);
}
