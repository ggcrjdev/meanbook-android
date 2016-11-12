package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;
import com.brilgo.meanbookandroidapp.api.response.Post;
import com.brilgo.meanbookandroidapp.api.response.User;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.message;

public class TimelineActivity extends AppCompatActivity {

    private MeanBookApi meanBookApi = new MeanBookApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadUserPostsToListView();
    }

    private void loadUserPostsToListView() {
        Intent intent = getIntent();
        User currentUser = (User) intent.getSerializableExtra(MainActivity.USER_KEY);
        List<Post> userPosts = meanBookApi.listPosts(currentUser.username);

        ViewGroup layout = (ViewGroup) findViewById(R.id.content_timeline);
        ListView postsList = (ListView) layout.findViewById(R.id.posts_list);
        postsList.setAdapter(createArrayAdapterWithPosts(userPosts));
    }

    private ArrayAdapter<String> createArrayAdapterWithPosts(List<Post> userPosts) {
        List<String> adaptedPosts = new ArrayList<>(userPosts.size());
        for (Post userPost : userPosts) {
            adaptedPosts.add(MessageFormat.format("[{0}] {1}: {2}",
                    userPost.timestamp, userPost.author, userPost.text));
        }
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adaptedPosts);
    }
}
