package com.example.jayashankarjayan.placements;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
public class NoticesFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    private List<NoticeRecyclerViewContent> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoticeRecyclerAdapter mAdapter;
    private ImageView fragment_notices_nothing_to_show_icon;
    private TextView fragment_notices_nothing_to_show_text;

    public NoticesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notices, container, false);

        MainActivity main2Activity = (MainActivity)getActivity();
        main2Activity.getSupportActionBar().setTitle("Notices");

        final Functions functions = new Functions(getActivity().getApplicationContext());

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.activity_notices_swipe_refresh);

        progressBar = (ProgressBar) rootView.findViewById(R.id.activity_notices_progressBar);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.activity_notices_recycler_view);

        fragment_notices_nothing_to_show_icon = (ImageView)rootView.findViewById(R.id.fragment_notices_nothing_to_show_icon);
        fragment_notices_nothing_to_show_text = (TextView)rootView.findViewById(R.id.fragment_notices_nothing_to_show_text);
        mAdapter = new NoticeRecyclerAdapter(mainList,getActivity().getApplicationContext(),R.layout.notice_row);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (functions.checkNetworkState())
                {
                    clearViews();
                    new GetNotices().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        if (functions.checkNetworkState()) {
            new GetNotices().execute();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainList.clear();
        new GetNotices().execute();
    }

    private void clearViews()
    {
        int size = mainList.size();
        mainList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    class GetNotices extends AsyncTask<String,Void,String> {

        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        int course_id,college_id;
        Functions functions = new Functions(getActivity().getApplicationContext());
        String link = functions.getLink()+"RequestParams.php";
        String notice_title,notice_content,adder_name,notice_date,post_data,message="",success,result="",username;
        @Override
        protected String doInBackground(String... strings) {

            username = functions.getUsername();
            college_id = functions.getCollegeID();
            course_id = functions.getCourseID();
            try {
                url = new URL(link);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode("getNotices", "UTF-8")
                        +"&"+URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("college_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(college_id), "UTF-8")
                        + "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(course_id), "UTF-8");
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

                success = new JSONObject(result.toString()).getString("success");
                if (success.charAt(0) == '1') {
                    JSONArray jArray = new JSONObject(result.toString()).getJSONArray("notices");
                    if (jArray.length() > 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            notice_title = json_data.getString("Notice_Title");
                            notice_content = json_data.getString("Notice_Text");
                            adder_name = json_data.getString("Adder_Name");
                            notice_date = json_data.getString("Added_on");
                            NoticeRecyclerViewContent data = new NoticeRecyclerViewContent(notice_title, notice_content, "- " + adder_name,notice_date);
                            mainList.add(data);
                        }

                    }
                }
                else if(success.charAt(0) == '0')
                {
                    String noNoticesFound = new JSONObject(result).getString("message");
                    if(noNoticesFound.length() > 0)
                    {
                        result = noNoticesFound;
                    }
                }
            } catch (IOException | JSONException | NullPointerException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

            if (success.charAt(0) == '1')
            {
                mAdapter.notifyDataSetChanged();
            }
            else
            {
                fragment_notices_nothing_to_show_icon.setVisibility(View.VISIBLE);
                fragment_notices_nothing_to_show_text.setVisibility(View.VISIBLE);
                fragment_notices_nothing_to_show_text.setText("Your notices come here");
            }
            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}