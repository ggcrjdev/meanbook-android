package com.brilgo.meanbookandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brilgo.meanbookandroidapp.api.response.Post;

import java.util.List;

public class TimelinePostArrayAdapter extends ArrayAdapter<Post> {

    private int itemResource;

    public TimelinePostArrayAdapter(Context context, int resource, List<Post> items) {
        super(context, resource, items);
        this.itemResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(itemResource, null);
        }
        Post post = getItem(position);
        setItemValues(v, post);
        return v;
    }

    private void setItemValues(View v, Post post) {
        if (post != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.timeline_item_author);
            TextView tt2 = (TextView) v.findViewById(R.id.timeline_item_post);
            TextView tt3 = (TextView) v.findViewById(R.id.timeline_item_pub_date);
            if (tt1 != null) {
                tt1.setText(post.author);
            }
            if (tt2 != null) {
                tt2.setText(post.text);
            }
            if (tt3 != null) {
                tt3.setText(post.timestamp.toString());
            }
        }
    }
}
