package com.mccorby.reactivegithubusers.list;

import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;
import com.mccorby.reactivegithubusers.domain.interactors.DeleteUser;
import com.mccorby.reactivegithubusers.domain.interactors.GetUserList;
import com.mccorby.reactivegithubusers.ui.Presenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by jco59 on 07/03/2016.
 */
public class ListPresenter implements Presenter {

    private GetUserList getUserList;
    private DeleteUser deleteUser;
    private UserListView view;


    public ListPresenter(GetUserList getUserList, DeleteUser deleteUser, UserListView view) {
        this.getUserList = getUserList;
        this.deleteUser = deleteUser;
        this.view = view;
    }



    @Override
    public void onResume() {
        getUserList.execute(new Subscriber<List<GitHubUser>>() {
            @Override
            public void onCompleted() {
                view.dataCompleted();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e);
            }

            @Override
            public void onNext(List<GitHubUser> o) {
                view.refresh(o);
            }
        });
    }

    @Override
    public void onPause() {

    }

    @Override
    public void delete(final int position) {
        deleteUser.setPosition(position);
        deleteUser.execute(new Subscriber<List<GitHubUser>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GitHubUser> o) {
                if (o.size() > 0) {
                    view.replace(o.get(0), position);
                }
            }
        });
    }
}
