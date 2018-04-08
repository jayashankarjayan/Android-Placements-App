package com.example.jayashankarjayan.placements;


import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
public class ApplyCompanyFragment extends Fragment {


    SwipeRefreshLayout swipeRefreshLayout;
    List<Recycler_View_Content> mainList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    ProgressBar progressBar;
    Button fragment_main_post_button,fragment_main_company_button,fragment_main_qualification_button,
            fragment_main_vacancy_button,fragment_main_college_button,fragment_main_venue_button,
            fragment_main_personal_skills_button,fragment_main_technical_skills_button;
    ImageView fragment_apply_companies_nothing_to_show_icon;
    TextView fragment_apply_companies_nothing_to_show_text;

    int shortAnimationDuration;


    public ApplyCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_apply_company, container, false);

        final Functions functions = new Functions(getActivity().getApplicationContext());

        MainActivity main2Activity = (MainActivity)getActivity();
        main2Activity.getSupportActionBar().setTitle("Apply");

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh);

        progressBar = (ProgressBar)rootView.findViewById(R.id.activity_main_progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        fragment_apply_companies_nothing_to_show_icon = (ImageView) rootView.findViewById(R.id.fragment_apply_companies_nothing_to_show_icon);
        fragment_apply_companies_nothing_to_show_text = (TextView)rootView.findViewById(R.id.fragment_apply_companies_nothing_to_show_text);

        mAdapter = new RecyclerAdapter(mainList,getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setVisibility(View.GONE);

        fragment_main_post_button = (Button)rootView.findViewById(R.id.fragment_main_post_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_post_button,"bold");

        fragment_main_company_button = (Button)rootView.findViewById(R.id.fragment_main_company_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_company_button,"bold");

        fragment_main_qualification_button = (Button)rootView.findViewById(R.id.fragment_main_qualification_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_qualification_button,"bold");

        fragment_main_vacancy_button = (Button)rootView.findViewById(R.id.fragment_main_vacancy_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_vacancy_button,"bold");

        fragment_main_college_button = (Button)rootView.findViewById(R.id.fragment_main_college_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_college_button,"bold");

        fragment_main_venue_button = (Button)rootView.findViewById(R.id.fragment_main_venue_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_venue_button,"bold");

        fragment_main_personal_skills_button = (Button)rootView.findViewById(R.id.fragment_main_personal_skills_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_personal_skills_button,"bold");

        fragment_main_technical_skills_button = (Button)rootView.findViewById(R.id.fragment_main_technical_skills_button);
        functions.applyFont(getActivity().getApplicationContext(),fragment_main_technical_skills_button,"bold");

        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (functions.checkNetworkState()) {
                    clearViews();
                    new AsyncFetch().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        fragment_main_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","post");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_company_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","company");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_qualification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","qualification");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_vacancy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","vacancy");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_college_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","college");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_venue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","venue");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_personal_skills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","personal_skills");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        fragment_main_technical_skills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSortingActivity = new Intent();
                goToSortingActivity.setClass(getActivity(), SortingActivity.class);
                goToSortingActivity.putExtra("sort_by","technical_skills");
                getActivity().startActivity(goToSortingActivity);
            }
        });

        if (functions.checkNetworkState()) {
            new AsyncFetch().execute();
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"No not connection",Toast.LENGTH_LONG).show();
        }
        return rootView;
    }


    private void clearViews()
    {
        int size = mainList.size();
        mainList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data,link,result="",success="",filterCondition="";
        Functions functions = new Functions(getActivity().getApplicationContext());
        String username = functions.getUsername();
        int college_id = functions.getCollegeID();
        int course_id = functions.getCourseID();
        boolean emptyValidator;
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
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getCompanies","UTF-8")
                        +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(String.valueOf(username),"UTF-8")
                        +"&"+URLEncoder.encode("college_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(college_id),"UTF-8")
                        +"&"+URLEncoder.encode("course_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(course_id),"UTF-8");
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


                emptyValidator = new JSONObject(result).getBoolean("info");
                if(emptyValidator)
                {
                    JSONArray finalData = new JSONObject(result).getJSONArray("data");
                    for (int i=0;i<finalData.length();i++)
                    {
                        JSONObject jsonObject = finalData.getJSONObject(i);

                        success = jsonObject.getString("success");
                        if (success.charAt(0) == '1')
                        {
                            String companyName = jsonObject.getString("company_name");
                            String vacancy = jsonObject.getString("vacancy");
                            String buttonName = jsonObject.getString("applyButtonName");
                            String tableName = jsonObject.getString("table_name");
                            Recycler_View_Content data = new Recycler_View_Content(companyName, vacancy,buttonName,tableName);
                            mainList.add(data);
                        }
                        else if(success.charAt(0) == '0')
                        {
                            String noCompaniesToShow = new JSONObject(result).getString("message");
                            if(noCompaniesToShow.length()>0)
                            {
                                result = noCompaniesToShow;
                            }
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
            if(emptyValidator)
            {
                if(success.charAt(0) == '1')
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();

                }
            }
            else
            {
                fragment_apply_companies_nothing_to_show_icon.setVisibility(View.VISIBLE);
                fragment_apply_companies_nothing_to_show_text.setText("Apply to companies here");
                fragment_apply_companies_nothing_to_show_text.setVisibility(View.VISIBLE);

//                functions.setErrorAlert(getActivity().getApplicationContext(),result);
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
