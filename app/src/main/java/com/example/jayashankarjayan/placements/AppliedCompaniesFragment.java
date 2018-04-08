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
public class AppliedCompaniesFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    private List<AppliedCompaniesRecyclerViewContent> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppliedCompaniesRecyclerViewAdapter mAdapter;
    ProgressBar progressBar;
    ImageView fragment_applied_companies_nothing_to_show_icon;
    TextView fragment_applied_companies_nothing_to_show_text;
    public AppliedCompaniesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_applied_companies, container, false);
        final Functions functions = new Functions(getActivity().getApplicationContext());

        MainActivity main2Activity = (MainActivity)getActivity();
        main2Activity.getSupportActionBar().setTitle("Applied Companies");

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_applied_companies_swipe_refresh);

        progressBar = (ProgressBar)rootView.findViewById(R.id.fragment_applied_companies_progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_applied_companies_recycler_view);
        fragment_applied_companies_nothing_to_show_icon = (ImageView)rootView.findViewById(R.id.fragment_applied_companies_nothing_to_show_icon);
        fragment_applied_companies_nothing_to_show_text = (TextView)rootView.findViewById(R.id.fragment_applied_companies_nothing_to_show_text);

        mAdapter = new AppliedCompaniesRecyclerViewAdapter(mainList,getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (functions.checkNetworkState()) {
                     clearViews();
                    new AppliedCompaniesAsyncFetch().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        if (functions.checkNetworkState()) {
            new AppliedCompaniesAsyncFetch().execute();
        }
        return rootView;
    }

    private void clearViews()
    {
        int size = mainList.size();
        mainList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    private class AppliedCompaniesAsyncFetch extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data,link,result="",success="";
        Functions functions = new Functions(getActivity().getApplicationContext());
        int college_id = functions.getCollegeID();
        int course_id = functions.getCourseID();
        String username = functions.getUsername();

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
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getAppliedCompanies","UTF-8")
                        +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&"+URLEncoder.encode("course_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(course_id),"UTF-8")
                        +"&"+URLEncoder.encode("college_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(college_id),"UTF-8");
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
                if (success.charAt(0) == '1')
                {
                    JSONArray jArray = new JSONObject(result).getJSONArray("appliedCompanies");

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONArray jsonArray = new JSONArray(jArray.get(i).toString());
                        for(int j=0;j<jsonArray.length();j++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            String companyName = jsonObject.getString("Company_Name");
                            String jobName = jsonObject.getString("Vacancies_for");
                            String tableName = jsonObject.getString("Table_Name");
                            AppliedCompaniesRecyclerViewContent data = new AppliedCompaniesRecyclerViewContent(companyName, jobName, tableName);
                            mainList.add(data);
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
            else
            {
                fragment_applied_companies_nothing_to_show_icon.setVisibility(View.VISIBLE);
                fragment_applied_companies_nothing_to_show_text.setText("Companies you have applied to come here");
                fragment_applied_companies_nothing_to_show_text.setVisibility(View.VISIBLE);
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
