package com.brilgo.meanbookandroidapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.brilgo.meanbookandroidapp.api.response.Post;

import java.text.DateFormat;
import java.util.List;

import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;

public class TimelinePostArrayAdapter extends ArrayAdapter<Post> {

    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(MEDIUM, SHORT);

    private int itemResource;

    public TimelinePostArrayAdapter(Context context, int resource, List<Post> items) {
        super(context, resource, items);
        itemResource = resource;
    }

    @Override
    public  @NonNull View getView(int position,  @Nullable View convertView,
            @NonNull ViewGroup parent) {
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
            TextView authorText = (TextView) v.findViewById(R.id.timeline_item_author);
            if (authorText != null) {
                authorText.setText(post.author);
            }
            TextView postText = (TextView) v.findViewById(R.id.timeline_item_post);
            if (postText != null) {
                postText.setText(post.text);
                addClickListenerCopyToClipboard(postText);
            }
            TextView publicationDateText = (TextView) v.findViewById(R.id.timeline_item_pub_date);
            if (publicationDateText != null) {
                publicationDateText.setText(dateFormat.format(post.timestamp));
            }
        }
    }

    private void addClickListenerCopyToClipboard(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(
                        Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Post text",textView.getText().toString());
                cm.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
