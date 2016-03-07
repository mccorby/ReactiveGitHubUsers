package com.mccorby.reactivegithubusers.domain.interactors;

import com.mccorby.reactivegithubusers.domain.repository.UserRepository;

import java.util.concurrent.Executor;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jco59 on 07/03/2016.
 */
public class GetUserList implements Interactor {

    private final Executor executionThread;
    private final Scheduler observerThread;
    private UserRepository repository;

    public GetUserList(Executor executionThread, Scheduler observerThread, UserRepository repository) {
        this.executionThread = executionThread;
        this.observerThread = observerThread;
        this.repository = repository;
    }

    @Override
    public void execute(Subscriber subscriber) {
        repository.getListOfUsers()
                .subscribeOn(Schedulers.from(executionThread))
                .observeOn(observerThread)
                .subscribe(subscriber);
    }
}
