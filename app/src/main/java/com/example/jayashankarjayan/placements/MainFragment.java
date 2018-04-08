package com.example.jayashankarjayan.placements;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainFragment extends Fragment {

    TextView fragment_home_title_new_notices,fragment_home_no_new_notices;
    RecyclerView fragment_home_recyclerview_new_notices;
    NoticeRecyclerAdapter noticeRecyclerAdapter;
    List<NoticeRecyclerViewContent> noticeRecyclerViewContentList = new ArrayList<>();
    SpannableString underLine;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        MainActivity mainActivity = (MainActivity)getActivity();
        if(mainActivity.getSupportActionBar() != null)
        {
            mainActivity.getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
        fragment_home_title_new_notices = (TextView) rootView.findViewById(R.id.fragment_home_title_new_notices);
        fragment_home_no_new_notices = (TextView)rootView.findViewById(R.id.fragment_home_no_new_notices);
        fragment_home_recyclerview_new_notices = (RecyclerView) rootView.findViewById(R.id.fragment_home_recyclerview_new_notices);

        noticeRecyclerAdapter = new NoticeRecyclerAdapter(noticeRecyclerViewContentList, getActivity().getApplicationContext(),R.layout.notice_home_row);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        fragment_home_recyclerview_new_notices.setLayoutManager(linearLayoutManager);
        fragment_home_recyclerview_new_notices.setItemAnimator(new DefaultItemAnimator());
        fragment_home_recyclerview_new_notices.setAdapter(noticeRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(fragment_home_recyclerview_new_notices.getContext(),
                linearLayoutManager.getOrientation());
        fragment_home_recyclerview_new_notices.addItemDecoration(dividerItemDecoration);

        underLine = new SpannableString(getResources().getString(R.string.title_new_notices));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 1);
        fragment_home_title_new_notices.setText(underLine);

//        new GetLatestNotices().execute();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        noticeRecyclerViewContentList.clear();
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.menu_home.setChecked(true);
        new GetLatestNotices().execute();
    }

    class GetLatestNotices extends AsyncTask<String, Void, String> {

        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        int course_id, college_id;
        Functions functions = new Functions(getActivity().getApplicationContext());
        String link = functions.getLink() + "RequestParams.php";
        String notice_title, notice_content, adder_name, notice_date, post_data, message = "", success, result = "", username;

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
                        + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("college_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(college_id), "UTF-8")
                        + "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(course_id), "UTF-8")
                        + "&" + URLEncoder.encode("extra_condition", "UTF-8") + "=" + URLEncoder.encode("LIMIT 5", "UTF-8");
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
                        result += line;
                    }
                } else {
                    return "Error. Please try again";
                }

                reader.close();
                input.close();
                conn.disconnect();

                success = new JSONObject(result).getString("success");
                if (success.charAt(0) == '1') {
                    JSONArray jArray = new JSONObject(result).getJSONArray("notices");
                    if (jArray.length() > 0) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            notice_title = json_data.getString("Notice_Title");
                            notice_content = json_data.getString("Notice_Text");
                            adder_name = json_data.getString("Adder_Name");
                            notice_date = json_data.getString("Added_on");

                            NoticeRecyclerViewContent data = new NoticeRecyclerViewContent(notice_title, notice_content, "- " + adder_name,notice_date);
                            noticeRecyclerViewContentList.add(data);
                        }

                    }
                } else if (success.charAt(0) == '0') {
                    String noNoticesFound = new JSONObject(result).getString("message");
                    if (noNoticesFound.length() > 0) {
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
//            progressBar.setVisibility(View.GONE);
            if (success.charAt(0) == '1') {
                noticeRecyclerAdapter.notifyDataSetChanged();
                if (noticeRecyclerViewContentList.size() <= 0) {
                    fragment_home_no_new_notices.setText(getResources().getString(R.string.no_new_notices));
                    fragment_home_no_new_notices.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    fragment_home_no_new_notices.setVisibility(View.VISIBLE);
                }
            } else if (success.charAt(0) == '0') {
                fragment_home_no_new_notices.setText(result);
                fragment_home_no_new_notices.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                fragment_home_no_new_notices.setVisibility(View.VISIBLE);
//                functions.setErrorAlert(getActivity().getApplicationContext(), result);
            }
            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
