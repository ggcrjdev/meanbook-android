package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.brilgo.meanbookandroidapp.api.response.Post;

import java.text.MessageFormat;
import java.util.List;

public class TimelineActivity extends BaseActivity {

    private static final String TAG = TimelineActivity.class.getSimpleName();
    private static final int REQUEST_EXIT = 100;
    private static final int FIRST_PAGE = 1;

    private TimelinePostArrayAdapter timelinePostAdapter;
    private int currentPageOfPosts = FIRST_PAGE;
    private boolean endOfPoages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_timeline);
        setTitle(MessageFormat.format("{0} ({1})",
                getString(R.string.title_activity_timeline), getStoredCurrentUsername()));
        addFabButton();

        createListViewAdapterLoadingFirstPageOfPosts();
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

    private void createListViewAdapterLoadingFirstPageOfPosts() {
        List<Post> userPosts = loadNextPageOfPosts();

        ViewGroup layout = (ViewGroup) findViewById(R.id.content_timeline);
        ListView postsList = (ListView) layout.findViewById(R.id.posts_list);
        timelinePostAdapter = new TimelinePostArrayAdapter(
                this, R.layout.content_timeline_item, userPosts);
        postsList.setAdapter(timelinePostAdapter);
        addScrollListenerLoadMorePosts(postsList);
    }

    private List<Post> loadNextPageOfPosts() {
        List<Post> userPosts = api().listPosts(
                getStoredCurrentUsername(), currentPageOfPosts);
        Log.d(TAG, MessageFormat.format("Loaded {0} posts.", userPosts.size()));
        if (userPosts.size() < 10) {
            endOfPoages = true;
        } else {
            currentPageOfPosts++;
        }
        return userPosts;
    }

    private void addScrollListenerLoadMorePosts(ListView postsList) {
        postsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView lw, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lw.getId() == R.id.posts_list &&
                        lastItem == totalItemCount && !endOfPoages) {
                    addNextPageOfPostsToAdapter();
                }
            }
        });
    }

    private void addNextPageOfPostsToAdapter() {
        List<Post> userPosts = loadNextPageOfPosts();
        timelinePostAdapter.addAll(userPosts);
    }

    public void logout() {
        Log.d(TAG, "Executing the logout action...");
        api().logout(getStoredCurrentUsername());
        dataStore().removeUserData(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
