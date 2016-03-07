package com.mccorby.reactivegithubusers.domain.repository;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

import rx.Observable;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface UserRepository {

    Observable<List<GitHubUser>> getListOfUsers();

    Observable<GitHubUser> getUserDetails(String id);
}

