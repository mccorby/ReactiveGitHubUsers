package com.mccorby.reactivegithubusers.domain.interactors;

import com.mccorby.reactivegithubusers.domain.repository.UserRepository;

import java.util.concurrent.Executor;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by JAC on 10/03/2016.
 */
public class DeleteUser implements Interactor {

    private final Executor executionThread;
    private final Scheduler observerThread;
    private final UserRepository repository;
    private int position;

    public DeleteUser(Executor executionThread, Scheduler observerThread, UserRepository repository) {
        this.executionThread = executionThread;
        this.observerThread = observerThread;
        this.repository = repository;
    }

    @Override
    public void execute(Subscriber subscriber) {
        repository.deleteAndRetrieveAnother(position)
                .subscribeOn(Schedulers.from(executionThread))
                .observeOn(observerThread)
                .subscribe(subscriber);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
