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
public class AdminDisplayLevelDetailsFragment extends Fragment {


    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    private List<AdminDisplayLevelDetailsRecyclerViewContent> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdminDisplayLevelDetailsRecyclerAdapter mAdapter;
    private ImageView fragment_admin_display_level_details_nothing_to_show_icon;
    private TextView fragment_admin_display_level_details_count,fragment_admin_display_level_details_nothing_to_show_text;

    String view;
    public AdminDisplayLevelDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_display_level_details, container, false);

        try
        {

            String switcher = getArguments().getString("view");
            switch (switcher)
            {
                case "student":
                    view = "getAllStudents";
                    break;
                case "moderator":
                    view = "getAllModerators";
                    break;
                case "company":
                    view = "getAllCompanies";
                    break;
                default:
                    view = "getAllStudents";
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        final Functions functions = new Functions(getActivity().getApplicationContext());

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.fragment_admin_display_level_details_swipe_refresh);

        progressBar = (ProgressBar) rootView.findViewById(R.id.fragment_admin_display_level_details_progressBar);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.fragment_admin_display_level_details_recycler_view);

        fragment_admin_display_level_details_nothing_to_show_icon = (ImageView)rootView.findViewById(R.id.fragment_admin_display_level_details_nothing_to_show_icon);
        fragment_admin_display_level_details_nothing_to_show_text = (TextView)rootView.findViewById(R.id.fragment_admin_display_level_details_nothing_to_show_text);
        fragment_admin_display_level_details_count = (TextView)rootView.findViewById(R.id.fragment_admin_display_level_details_count);
        mAdapter = new AdminDisplayLevelDetailsRecyclerAdapter(mainList,getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (functions.checkNetworkState())
                {
                    clearViews();
                    new GetAdminLevelDetails().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        if (functions.checkNetworkState()) {
            new GetAdminLevelDetails().execute();
        }
        return  rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        mainList.clear();
    }

    private void clearViews()
    {
        int size = mainList.size();
        mainList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    class GetAdminLevelDetails extends AsyncTask<String,Void,String> {

        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        int course_id,college_id;
        Functions functions = new Functions(getActivity().getApplicationContext());
        String link = functions.getLink()+"RequestParams.php";
        String info,post_data,message="",success,result="",username,display_statement="";
        int number_of_rows;
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
                post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode(view, "UTF-8");
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
                if (success.charAt(0) == '1')
                {

                    if(view.equals("getAllModerators"))
                    {
                        JSONArray jArray = new JSONObject(result.toString()).getJSONArray("moderators");
                        if (jArray.length() > 0) {
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                info = json_data.getString("Name_of_the_Moderator");
                                AdminDisplayLevelDetailsRecyclerViewContent data = new AdminDisplayLevelDetailsRecyclerViewContent(info);
                                mainList.add(data);
                                number_of_rows = new JSONObject(result).getInt("count");
                            }
                            if(number_of_rows > 1)
                            {
                                 display_statement += number_of_rows+" Moderators Registered";
                            }
                            else
                            {
                                display_statement += number_of_rows+" Moderator Registered";
                            }
                        }
                    }
                    else if(view.equals("getAllCompanies"))
                    {
                        JSONArray jArray = new JSONObject(result.toString()).getJSONArray("companies");
                        if (jArray.length() > 0) {
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                info = json_data.getString("Company_Name");
                                AdminDisplayLevelDetailsRecyclerViewContent data = new AdminDisplayLevelDetailsRecyclerViewContent(info);
                                mainList.add(data);
                                number_of_rows = new JSONObject(result).getInt("count");
                            }
                            if(number_of_rows > 1)
                            {
                                display_statement += number_of_rows+" Companies Registered";
                            }
                            else
                            {
                                display_statement += number_of_rows+" Company Registered";
                            }
                        }
                    }
                    else
                    {
                        JSONArray jArray = new JSONObject(result.toString()).getJSONArray("students");
                        if (jArray.length() > 0) {
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                info = json_data.getString("Name_of_the_Student");
                                AdminDisplayLevelDetailsRecyclerViewContent data = new AdminDisplayLevelDetailsRecyclerViewContent(info);
                                mainList.add(data);
                                number_of_rows = new JSONObject(result).getInt("count");
                            }
                            if(number_of_rows > 1)
                            {
                                display_statement += number_of_rows+" Students Registered";
                            }
                            else
                            {
                                display_statement += number_of_rows+" Student Registered";
                            }

                        }
                    }

                }
                else if(success.charAt(0) == '0')
                {
                    String noDataFound = new JSONObject(result).getString("message");
                    if(noDataFound.length() > 0)
                    {
                        result = noDataFound;
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
                fragment_admin_display_level_details_count.setVisibility(View.VISIBLE);
                fragment_admin_display_level_details_count.setText(display_statement);
                this.cancel(true);
            }
            else
            {
                fragment_admin_display_level_details_nothing_to_show_icon.setVisibility(View.VISIBLE);
                fragment_admin_display_level_details_nothing_to_show_text.setVisibility(View.VISIBLE);
                fragment_admin_display_level_details_nothing_to_show_text.setText("Nothing to display");
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
