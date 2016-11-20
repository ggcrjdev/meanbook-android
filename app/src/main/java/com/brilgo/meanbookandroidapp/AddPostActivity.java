package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_post);
    }

    public void addPost(View view) {
        EditText loginField = (EditText) findViewById(R.id.post_field);
        String postText = loginField.getText().toString();
        meanBookApi.addPost(postText);

        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }
}
