package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;
import com.brilgo.meanbookandroidapp.api.response.Post;
import com.brilgo.meanbookandroidapp.api.response.User;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.brilgo.meanbookandroidapp.MainActivity.USER_KEY;

public class TimelineActivity extends AppCompatActivity {

    private MeanBookApi meanBookApi = new MeanBookApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MessageFormat.format("{0} ({1})",
                getString(R.string.title_activity_timeline), getCurrentUser().username));
        setContentView(R.layout.activity_timeline);
        addToolbar();
        addFabButton();

        loadUserPostsToListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void addFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private User getCurrentUser() {
        return (User) getIntent().getSerializableExtra(USER_KEY);
    }

    private void loadUserPostsToListView() {
        User currentUser = getCurrentUser();
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

    public void logout() {
        User currentUser = getCurrentUser();
        Log.d(getClass().getName(), "Executing the logout action...");
        meanBookApi.logout(currentUser.username);
        Intent intent = new Intent(this, MainActivity.class);
        intent.removeExtra(MainActivity.USER_KEY);
        startActivity(intent);
    }
}
