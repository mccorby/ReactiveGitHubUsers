package com.mccorby.reactivegithubusers.ui;

/**
 * Created by jco59 on 07/03/2016.
 */
public interface Presenter {
    void onResume();
    void onPause();

    void delete(int position);
}
