package com.mccorby.reactivegithubusers.repository;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

import rx.Observable;

public interface UserRepository {

    Observable<List<GitHubUser>> getListOfUsers();

    Observable<GitHubUser> getUserDetails(long id);
}
