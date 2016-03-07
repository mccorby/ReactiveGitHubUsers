package com.mccorby.reactivegithubusers.list;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface UserListView {

    void dataCompleted();

    void showError(Throwable e);

    void refresh(List<GitHubUser> o);
}
