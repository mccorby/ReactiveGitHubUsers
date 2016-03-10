package com.mccorby.reactivegithubusers.datasource.entities;

import com.mccorby.reactivegithubusers.domain.entities.DomainMapper;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

/**
 * Created by jco59 on 07/03/2016.
 */
public class ApiMapper implements DomainMapper<ApiSummaryUser> {

    @Override
    public GitHubUser toGitHubUser(ApiSummaryUser source) {
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setLogin(source.getLogin());
        return gitHubUser;
    }
}
