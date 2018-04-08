package com.example.jayashankarjayan.placements;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class SortingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private List<SortingRecyclerViewContent> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SortingRecyclerAdapter mAdapter;
    TextView activity_sorting_title;
    String sortBy="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        progressBar = (ProgressBar)findViewById(R.id.activity_sorting_progressBar);
        activity_sorting_title = (TextView)findViewById(R.id.activity_sorting_title);

        recyclerView = (RecyclerView)findViewById(R.id.activity_sorting_recycler_view);
        mAdapter = new SortingRecyclerAdapter(mainList,getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        new AsyncFetch().execute();
        try
        {
            Intent extraData = getIntent();
            sortBy = extraData.getStringExtra("sort_by");
            switch (sortBy)
            {
                case "post":
                    activity_sorting_title.append(" Post");
                    try
                    {
                        getSupportActionBar().setTitle("Posts");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "company":
                    activity_sorting_title.append(" Company");
                    try
                    {
                        getSupportActionBar().setTitle("Company");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "qualification":
                    activity_sorting_title.append(" Qualification");
                    try
                    {
                        getSupportActionBar().setTitle("Qualification");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "vacancy":
                    activity_sorting_title.append(" Vacancy");
                    try
                    {
                        getSupportActionBar().setTitle("Vacancy");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "college":
                    activity_sorting_title.append(" College");
                    try
                    {
                        getSupportActionBar().setTitle("College");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "venue":
                    activity_sorting_title.append(" Venue");
                    try
                    {
                        getSupportActionBar().setTitle("Interview Venue");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "personal_skills":
                    activity_sorting_title.append(" Personal Skills");
                    try
                    {
                        getSupportActionBar().setTitle("Personal Skills");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "technical_skills":
                    activity_sorting_title.append(" Technical Skills");
                    try
                    {
                        getSupportActionBar().setTitle("Technical Skills");
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data="",link,result="",view = "";
        Functions functions = new Functions(getApplicationContext());
        int college_id = functions.getCollegeID();
        int course_id = functions.getCourseID();

        @Override
        protected String doInBackground(String... strings) {

            try {
                link = functions.getLink()+"RequestParams.php";
                url = new URL(link);

                switch (sortBy)
                {
                    case "post":
                        view = "getPostFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "company":
                        view = "getCompanyFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "venue":
                        view = "getVenueFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "vacancy":
                        view = "getVacancyFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "qualification":
                        view = "getQualificationFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "college":
                        view = "getAllColleges";
                        break;
                    case "personal_skills":
                        view = "getPersonalSkillsFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                    case "technical_skills":
                        view = "getTechnicalSkillsFilter";
                        post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode(view,"UTF-8");
                        break;
                }
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream =  conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
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


                switch (view)
                {
                    case "getPostFilter":
                        for(int j=0;j<=10;j++) {
                            SortingRecyclerViewContent postData = new SortingRecyclerViewContent("Posts");
                            mainList.add(postData);
                        }
                        break;
                    case "getCompanyFilter":
                        JSONArray companyArray = new JSONObject(result).getJSONArray("companyFilter");
                        for (int i = 0; i < companyArray.length(); i++) {
                            JSONObject json_data = companyArray.getJSONObject(i);
                            String companyName = json_data.getString("Company_Name");
                            SortingRecyclerViewContent companyData = new SortingRecyclerViewContent(companyName);
                            mainList.add(companyData);
                        }
                        break;
                    case "getVacancyFilter":
                        JSONArray vacancyArray = new JSONObject(result).getJSONArray("vacancyFilter");
                        for (int i = 0; i < vacancyArray.length(); i++) {
                            JSONObject json_data = vacancyArray.getJSONObject(i);
                            String vacancies = json_data.getString("Vacancies_For");
                            SortingRecyclerViewContent jobData = new SortingRecyclerViewContent(vacancies);
                            mainList.add(jobData);
                        }
                        break;
                    case "getVenueFilter":
                        JSONArray venueArray = new JSONObject(result).getJSONArray("venueFilter");
                        for (int i = 0; i < venueArray.length(); i++)
                        {
                            JSONObject json_data = venueArray.getJSONObject(i);
                            String venueName = json_data.getString("Interview_Venue");
                            SortingRecyclerViewContent venueData = new SortingRecyclerViewContent(venueName);
                            mainList.add(venueData);
                        }
                        break;
                    case "getAllColleges":
                        JSONArray collegesArray = new JSONObject(result).getJSONArray("colleges");
                        for (int i = 0; i < collegesArray.length(); i++)
                        {
                            JSONObject json_data = collegesArray.getJSONObject(i);

                            String college = json_data.getString("College_Name");
                            String location = json_data.getString("Location");
                            SortingRecyclerViewContent collegeData = new SortingRecyclerViewContent(college+", "+location);
                            mainList.add(collegeData);
                        }
                        break;
                    case "getQualificationFilter":
                        JSONArray qualificationsArray = new JSONObject(result).getJSONArray("qualificationsFilter");
                        for (int i = 0; i < qualificationsArray.length(); i++)
                        {
                            JSONObject json_data = qualificationsArray.getJSONObject(i);

                            String courses = json_data.getString("Courses");
                            SortingRecyclerViewContent qualificationData = new SortingRecyclerViewContent(courses);
                            mainList.add(qualificationData);
                        }
                        break;
                    case "getPersonalSkillsFilter":
                        JSONArray personalSkillsArray = new JSONObject(result).getJSONArray("personal_skills_filter");
                        for (int i = 0; i < personalSkillsArray.length(); i++)
                        {
                            JSONObject json_data = personalSkillsArray.getJSONObject(i);

                            String personalSkills = json_data.getString("Skill_Name");
                            SortingRecyclerViewContent personalSkillsData = new SortingRecyclerViewContent(personalSkills);
                            mainList.add(personalSkillsData);
                        }
                        break;
                    case "getTechnicalSkillsFilter":
                        JSONArray technicalSkillsArray = new JSONObject(result).getJSONArray("technical_skills_filter");
                        for (int i = 0; i < technicalSkillsArray.length(); i++)
                        {
                            JSONObject json_data = technicalSkillsArray.getJSONObject(i);

                            String technicalSkills = json_data.getString("Skill_Name");
                            SortingRecyclerViewContent technicalSkillsData = new SortingRecyclerViewContent(technicalSkills);
                            mainList.add(technicalSkillsData);
                        }
                        break;
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
            mAdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
