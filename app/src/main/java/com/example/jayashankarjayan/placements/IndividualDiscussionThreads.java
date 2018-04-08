package com.example.jayashankarjayan.placements;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class IndividualDiscussionThreads extends AppCompatActivity {

    TextView activity_individual_discussion_threads_thread_text,activity_individual_discussion_threads_thread_time,
            activity_individual_discussion_threads_posted_by;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_discussion_threads);

        Intent getExtrasFromChatAdapter = getIntent();
        String thread_text = getExtrasFromChatAdapter.getStringExtra("thread_text");
        String thread_time = getExtrasFromChatAdapter.getStringExtra("thread_time");
        String thread_posted_by = getExtrasFromChatAdapter.getStringExtra("thread_posted_by");

        activity_individual_discussion_threads_thread_text = (TextView)findViewById(R.id.activity_individual_discussion_threads_thread_text);
        activity_individual_discussion_threads_thread_time = (TextView)findViewById(R.id.activity_individual_discussion_threads_thread_time);
        activity_individual_discussion_threads_posted_by = (TextView)findViewById(R.id.activity_individual_discussion_threads_posted_by);

        activity_individual_discussion_threads_thread_text.setText(Html.fromHtml(thread_text));
        activity_individual_discussion_threads_thread_time.setText(Html.fromHtml(thread_time));
        activity_individual_discussion_threads_posted_by.setText(thread_posted_by);
    }
}
