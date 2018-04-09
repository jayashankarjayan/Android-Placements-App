package com.example.jayashankarjayan.placements;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDiscussionTopic extends Fragment {


    EditText new_discussion_edit_text_new_topic;
    Button new_discussion_button_new_topic;
    public NewDiscussionTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_new_discussion_topic, container, false);

        new_discussion_edit_text_new_topic = (EditText)rootView.findViewById(R.id.new_discussion_edit_text_new_topic);

        new_discussion_button_new_topic = (Button)rootView.findViewById(R.id.new_discussion_button_new_topic);
        new_discussion_button_new_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),new_discussion_edit_text_new_topic.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
