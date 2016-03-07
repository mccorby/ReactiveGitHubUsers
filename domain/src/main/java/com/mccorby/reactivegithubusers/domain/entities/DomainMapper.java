package com.mccorby.reactivegithubusers.domain.entities;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface DomainMapper<T> {

    GitHubUser toGitHubUser(T source);
}
