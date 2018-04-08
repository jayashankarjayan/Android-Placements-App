package com.example.jayashankarjayan.placements;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.jayashankarjayan.placements.functions.Functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscussionForumFragment extends Fragment {

    private List<ChatMessage> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatRecyclerAdapter mAdapter;
    ProgressBar progressBar;
    RelativeLayout activity_main;
    Functions functions;
    ChatMessage chatMessage;
    EditText new_discussion_edit_text_new_topic;;
    public DiscussionForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_discussion_forum, container, false);
        functions = new Functions(getActivity().getApplicationContext());

        MainActivity main2Activity = (MainActivity)getActivity();
        main2Activity.getSupportActionBar().setTitle("Discussion Forum");

        progressBar = (ProgressBar)rootView.findViewById(R.id.fragment_discussion_forum_progressbar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.chat_conversation_recycler_view);

        mAdapter = new ChatRecyclerAdapter(mainList, getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);

        activity_main = (RelativeLayout) rootView.findViewById(R.id.activity_chat);
        FloatingActionButton newThread = (FloatingActionButton) rootView.findViewById(R.id.newThread);
        newThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final LayoutInflater inflater = getActivity().getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.fragment_new_discussion_topic, null));
                builder.show();
            }
        });
        new GetMessages().execute();
        
        return rootView;
    }

    private class GetMessages extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data;
        String link;
        String result="";
        String success="",image;
        JSONArray imageOfUser;
        Functions functions = new Functions(getActivity().getApplicationContext());
        int user_id = functions.getId();
        @Override
        protected String doInBackground(String... strings) {

            try {
                link = functions.getLink()+"RequestParams.php";
                url = new URL(link);

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream =  conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getThreadMessaegs","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                int response_code = conn.getResponseCode();

                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                if (response_code == HttpURLConnection.HTTP_OK) {
                    while ((line = reader.readLine()) != null) {
                        result+=line;
                    }
                } else {
                    return "Error. Please try again";
                }

                reader.close();
                input.close();
                conn.disconnect();

                success = new JSONObject(result).getString("success");
                for(int a = 0;a<success.length();a++)
                {
                    if (success.charAt(a) == '1')
                    {
                        JSONArray messagesArray = new JSONObject(result).getJSONArray("messages");
                        for (int i = 0; i < messagesArray.length(); i++) {
                            JSONObject json_data = messagesArray.getJSONObject(i);
                            String threadId = json_data.getString("Thread_ID");
                            String threadText = json_data.getString("Thread_Text");
                            int MAX_CHAR = 20;
                            int maxLength = (threadText.length() < MAX_CHAR)?threadText.length():MAX_CHAR;
                            threadText = threadText.substring(0, maxLength);
                            String threadTime = json_data.getString("Sent_on");
                            String threadDate = threadTime.substring(0,10);
                            threadTime = threadTime.substring(11,16)+", "+threadDate;
                            String threadType = json_data.getString("Threader_Type");
                            switch (threadType)
                            {
                                case "mod":
                                    threadType = "moderator";
                                    break;
                                case "admin":
                                    threadType = "administrator";
                                    break;
                            }
                            threadType = threadType.toUpperCase();

                            chatMessage = new ChatMessage(threadId,threadText,threadTime,threadType);
                            mainList.add(chatMessage);
                        }
                    }
                    else if(success.charAt(0) == '0')
                    {
                        String noThreadsToShow = new JSONObject(result).getString("message");
                        if(noThreadsToShow.length() > 0)
                        {
                            result = noThreadsToShow;
                        }
                    }
                }
            }
            catch (JSONException | NullPointerException | IOException e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            progressBar.setVisibility(View.GONE);

            if(success.charAt(0) == '1')
            {
                mAdapter.notifyDataSetChanged();
            }
            else if (success.charAt(0) == '0')
            {
                functions.setErrorAlert(getActivity().getApplicationContext(),result);
            }
            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
