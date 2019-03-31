package com.test.shaddditest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.test.shaddditest.customview.SnapHelper;
import com.test.shaddditest.customview.UserListAdapter;
import com.test.shaddditest.json.User;
import com.test.shaddditest.json.UserResponseJson;
import com.test.shaddditest.viewmodel.UserListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView userlistview;
    UserListAdapter userListAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        toolbar = findViewById(R.id.toolbar);
        userlistview = findViewById(R.id.userlist);
        progressBar = findViewById(R.id.progressbar);
        initToolbar();
        initRecyclerView();
        setData();
    }

    private void setData()
    {
        final UserListViewModel viewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        viewModel.getUserListObservable().observe(this, new Observer<UserResponseJson>() {
            @Override
            public void onChanged(@Nullable UserResponseJson userResponseJson) {
                if(userResponseJson.results!=null) {
                    progressBar.setVisibility(View.GONE);
                    userListAdapter.updateData(userResponseJson.results);
                }
            }
        });
    }

    private void initToolbar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_title);
    }

    private void initRecyclerView()
    {
        progressBar.setVisibility(View.VISIBLE);
        SnapHelper snapper = new SnapHelper();
        userListAdapter = new UserListAdapter(getApplicationContext(), new ArrayList<User>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        userlistview.setLayoutManager(mLayoutManager);
        userlistview.setItemAnimator(new DefaultItemAnimator());
        userlistview.setAdapter(userListAdapter);
        snapper.attachToRecyclerView(userlistview);
    }
}
