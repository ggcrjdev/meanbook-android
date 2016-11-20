package com.brilgo.meanbookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.brilgo.meanbookandroidapp.api.MeanBookApi;

public class AddPostActivity extends AppCompatActivity {

    private MeanBookApi meanBookApi = MeanBookApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ActivityUtils.addDefaultToolbar(this);
    }

    public void addPost(View view) {
        EditText loginField = (EditText) findViewById(R.id.post_field);
        String postText = loginField.getText().toString();
        meanBookApi.addPost(postText);

        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }
}
