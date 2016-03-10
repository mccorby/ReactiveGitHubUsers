package com.mccorby.reactivegithubusers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mccorby.reactivegithubusers.datasource.GitHubUserApi;
import com.mccorby.reactivegithubusers.datasource.NetworkDatasourceImpl;
import com.mccorby.reactivegithubusers.datasource.UserDatasource;
import com.mccorby.reactivegithubusers.domain.UserRepositoryImpl;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;
import com.mccorby.reactivegithubusers.domain.interactors.GetUserList;
import com.mccorby.reactivegithubusers.domain.repository.UserRepository;
import com.mccorby.reactivegithubusers.list.ListPresenter;
import com.mccorby.reactivegithubusers.list.UserListView;
import com.mccorby.reactivegithubusers.ui.UserItemListener;
import com.mccorby.reactivegithubusers.ui.UserListAdapter;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements UserListView, UserItemListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListPresenter presenter;
    private UserListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invokeGitHub();
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.users_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        listAdapter = new UserListAdapter(this);
        rv.setAdapter(listAdapter);

        injectObjects();

    }

    private void injectObjects() {

        GitHubUserApi gitHubUserApi = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubUserApi.class);

        UserDatasource userDatasource = new NetworkDatasourceImpl(gitHubUserApi);
        UserRepository repository = new UserRepositoryImpl(userDatasource);
        GetUserList getUserList = new GetUserList(Executors.newSingleThreadExecutor(),
                AndroidSchedulers.mainThread(),
                repository);
        presenter = new ListPresenter(getUserList, this);
    }

    private void invokeGitHub() {
        presenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dataCompleted() {
        Log.d(TAG, "Data Completed!!");
    }

    @Override
    public void showError(Throwable e) {
        Log.d(TAG, "Error thrown " + e);
    }

    @Override
    public void refresh(List<GitHubUser> o) {
        listAdapter.setData(o);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(int position) {
        presenter.delete(position);
    }
}
