package com.mccorby.reactivegithubusers.domain.interactors;

import rx.Subscriber;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface Interactor {
    void execute(Subscriber subscriber);
}
