package com.mccorby.reactivegithubusers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mccorby.reactivegithubusers.datasource.NetworkDatasourceImpl;
import com.mccorby.reactivegithubusers.datasource.RestApi;
import com.mccorby.reactivegithubusers.datasource.UserDatasource;
import com.mccorby.reactivegithubusers.domain.UserRepositoryImpl;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;
import com.mccorby.reactivegithubusers.domain.interactors.GetUserList;
import com.mccorby.reactivegithubusers.domain.repository.UserRepository;
import com.mccorby.reactivegithubusers.list.ListPresenter;
import com.mccorby.reactivegithubusers.list.UserListView;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements UserListView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListPresenter presenter;

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

        injectObjects();

    }

    private void injectObjects() {

        RestApi restApi = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi.class);

        UserDatasource userDatasource = new NetworkDatasourceImpl(restApi);
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
        for (GitHubUser user : o) {
            Log.d(TAG, user.toString());
        }
    }
}
