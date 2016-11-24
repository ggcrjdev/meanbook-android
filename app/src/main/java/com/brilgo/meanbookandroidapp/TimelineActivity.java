package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brilgo.meanbookandroidapp.api.response.Post;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends BaseActivity {

    private static final String TAG = TimelineActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_timeline);
        setTitle(MessageFormat.format("{0} ({1})",
                getString(R.string.title_activity_timeline), getStoredCurrentUsername()));
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

    private void addFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddPostActivity(view);
            }
        });
    }

    private void startAddPostActivity(View view) {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }

    private void loadUserPostsToListView() {
        List<Post> userPosts = meanBookApi.listPosts(getStoredCurrentUsername());

        ViewGroup layout = (ViewGroup) findViewById(R.id.content_timeline);
        ListView postsList = (ListView) layout.findViewById(R.id.posts_list);
        postsList.setAdapter(new TimelinePostArrayAdapter(
                this, R.layout.content_timeline_item, userPosts));
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
        Log.d(TAG, "Executing the logout action...");
        meanBookApi.logout(getStoredCurrentUsername());
        userDataStore.removeUserData(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
