package com.mccorby.reactivegithubusers.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mccorby.reactivegithubusers.R;
import com.mccorby.reactivegithubusers.domain.entities.GitHubUser;

import java.util.List;

/**
 * Created by JAC on 10/03/2016.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>{

    private List<GitHubUser> userList;
    private UserItemListener listener;

    public UserListAdapter(UserItemListener listener) {
        this.listener = listener;
    }

    public void setData(List<GitHubUser> data) {
        this.userList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (userList != null) {
            GitHubUser user = userList.get(position);
            holder.login.setText(user.getLogin());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delete(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView login;
        Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            delete = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
