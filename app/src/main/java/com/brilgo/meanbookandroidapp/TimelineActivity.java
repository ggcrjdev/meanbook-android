package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.brilgo.meanbookandroidapp.api.response.Post;

import java.text.MessageFormat;
import java.util.List;

public class TimelineActivity extends BaseActivity {

    private static final String TAG = TimelineActivity.class.getSimpleName();
    private static final int REQUEST_EXIT = 100;

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
        startActivityForResult(intent, REQUEST_EXIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EXIT && resultCode == RESULT_OK) {
            finish();
        }
    }

    private void loadUserPostsToListView() {
        List<Post> userPosts = meanBookApi.listPosts(getStoredCurrentUsername());

        ViewGroup layout = (ViewGroup) findViewById(R.id.content_timeline);
        ListView postsList = (ListView) layout.findViewById(R.id.posts_list);
        postsList.setAdapter(new TimelinePostArrayAdapter(
                this, R.layout.content_timeline_item, userPosts));
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
