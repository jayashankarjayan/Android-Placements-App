package com.example.jayashankarjayan.placements;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;

public class IndividualNotices extends AppCompatActivity {

    TextView activity_individual_notices_title,activity_individual_notices_content,
            activity_individual_notices_adder_name;
    android.support.v7.widget.Toolbar individual_notices_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_notices);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Notices");
        }

        individual_notices_toolbar = (Toolbar)findViewById(R.id.individual_notices_toolbar);

        Functions functions = new Functions(getApplicationContext());

        Intent getExtrasFromNoticesAdapter = getIntent();



        String title = getExtrasFromNoticesAdapter.getStringExtra("notice_title");
        String content = getExtrasFromNoticesAdapter.getStringExtra("notice_content");
        String adder_name = getExtrasFromNoticesAdapter.getStringExtra("notice_adder_name");

        individual_notices_toolbar.setTitle("Notice "+adder_name);
        SpannableString underLineText = new SpannableString(title);
        underLineText.setSpan(new UnderlineSpan(), 0, underLineText.length(), 0);

        activity_individual_notices_title = (TextView)findViewById(R.id.activity_individual_notices_title);
        activity_individual_notices_content = (TextView)findViewById(R.id.activity_individual_notices_content);
        activity_individual_notices_adder_name = (TextView)findViewById(R.id.activity_individual_notices_adder_name);

        functions.applyFont(getApplicationContext(),activity_individual_notices_title,"bold");
        functions.applyFont(getApplicationContext(),activity_individual_notices_content,"bold");
        functions.applyFont(getApplicationContext(),activity_individual_notices_adder_name,"bold");

        activity_individual_notices_title.setText(underLineText);
        activity_individual_notices_content.setText(Html.fromHtml(content));
        activity_individual_notices_adder_name.setText(adder_name);
    }
}
