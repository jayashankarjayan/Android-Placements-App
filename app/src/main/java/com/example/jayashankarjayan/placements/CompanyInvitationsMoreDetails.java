package com.example.jayashankarjayan.placements;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class CompanyInvitationsMoreDetails extends AppCompatActivity {

    TextView activity_company_invitations_more_details_company,activity_company_invitations_more_details_selection_date,
            activity_company_invitations_more_details_maximum_backlog,activity_company_invitations_more_details_venue,
            activity_company_invitations_more_details_vacancies_for,activity_company_invitations_more_details_criteria,
            activity_company_invitations_more_details_additional_info;

    Button activity_company_invitations_more_details_accept,activity_company_invitations_more_details_decline;

    ProgressBar progressBar;

    int i=0;

    String inviteId,name_of_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_invitations_more_details);

        try
        {
            Intent getExtras = getIntent();
            inviteId = getExtras.getStringExtra("inviteId");
            name_of_company = getExtras.getStringExtra("name_of_company");

            if(getSupportActionBar() != null)
            {
                getSupportActionBar().setTitle(name_of_company);
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        progressBar = (ProgressBar)findViewById(R.id.activity_company_invitations_more_details_progressbar);

        activity_company_invitations_more_details_company = (TextView)findViewById(R.id.activity_company_invitations_more_details_company);
        activity_company_invitations_more_details_selection_date = (TextView)findViewById(R.id.activity_company_invitations_more_details_selection_date);
        activity_company_invitations_more_details_maximum_backlog = (TextView)findViewById(R.id.activity_company_invitations_more_details_maximum_backlog);
        activity_company_invitations_more_details_venue = (TextView)findViewById(R.id.activity_company_invitations_more_details_venue);
        activity_company_invitations_more_details_vacancies_for = (TextView)findViewById(R.id.activity_company_invitations_more_details_vacancies_for);
        activity_company_invitations_more_details_criteria = (TextView)findViewById(R.id.activity_company_invitations_more_details_criteria);
        activity_company_invitations_more_details_additional_info = (TextView)findViewById(R.id.activity_company_invitations_more_details_additional_info);

        activity_company_invitations_more_details_accept = (Button)findViewById(R.id.activity_company_invitations_more_details_accept);
        activity_company_invitations_more_details_decline = (Button)findViewById(R.id.activity_company_invitations_more_details_decline);

        activity_company_invitations_more_details_company.setVisibility(View.GONE);
        activity_company_invitations_more_details_vacancies_for.setVisibility(View.GONE);
        activity_company_invitations_more_details_criteria.setVisibility(View.GONE);
        activity_company_invitations_more_details_venue.setVisibility(View.GONE);
        activity_company_invitations_more_details_additional_info.setVisibility(View.GONE);
        activity_company_invitations_more_details_maximum_backlog.setVisibility(View.GONE);
        activity_company_invitations_more_details_selection_date.setVisibility(View.GONE);
        activity_company_invitations_more_details_accept.setVisibility(View.GONE);
        activity_company_invitations_more_details_decline.setVisibility(View.GONE);

        new CompanyInvitationsMoreDetailsAsyncFetch().execute();

        activity_company_invitations_more_details_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CompanyInvitationsUpdateInviteAsyncTask(getApplicationContext(),"acceptInvitations",inviteId).execute();
            }
        });

        activity_company_invitations_more_details_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class CompanyInvitationsMoreDetailsAsyncFetch extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data,link,result="",success="",accepted, companyName,vacancyFor,criteria,interviewVenue,maximumBacklog,
        dateOfSelection,additionalInfo;
        Functions functions = new Functions(getApplicationContext());
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
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getCompanyInvitations","UTF-8")
                        +"&"+URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(user_id),"UTF-8");
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

                        JSONArray each_invite = new JSONObject(result).getJSONArray("data");

                        for(int i=0;i<each_invite.length();i++)
                        {
                            JSONArray data = each_invite.getJSONArray(i);

                            JSONArray invites = data.getJSONArray(0);
                            JSONObject invitesjsonObject = invites.getJSONObject(i);
                            inviteId = invitesjsonObject.getString("ID");
                            accepted = invitesjsonObject.getString("accepted");



                            JSONArray companies = data.getJSONArray(2);
                            JSONObject companiesJSONObject = companies.getJSONObject(0);
                            companyName = companiesJSONObject.getString("Company_Name");
                            vacancyFor = companiesJSONObject.getString("Vacancies_for");
                            criteria = companiesJSONObject.getString("Criteria_for_Selection");
                            interviewVenue = companiesJSONObject.getString("Interview_Venue");
                            maximumBacklog = companiesJSONObject.getString("Maximum_Backlogs");
                            additionalInfo = companiesJSONObject.getString("Additional_Information");
                            dateOfSelection = companiesJSONObject.getString("Date_of_Selection");

                        }
                        /*JSONArray invitesArray = new JSONObject(result).getJSONArray("invites");
                        for (int i = 0; i < invitesArray.length(); i++) {
                            JSONObject json_data = invitesArray.getJSONObject(i);
                            accepted = json_data.getString("accepted");
                        }*/
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

            if(success.charAt(0) == '1'){
                progressBar.setVisibility(View.GONE);

                activity_company_invitations_more_details_company.append(companyName);
                activity_company_invitations_more_details_vacancies_for.append(vacancyFor);
                activity_company_invitations_more_details_criteria.append(criteria);
                activity_company_invitations_more_details_venue.append(interviewVenue);
                activity_company_invitations_more_details_additional_info.append(additionalInfo);
                activity_company_invitations_more_details_maximum_backlog.append(maximumBacklog);
                activity_company_invitations_more_details_selection_date.append(dateOfSelection);

                activity_company_invitations_more_details_company.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_vacancies_for.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_criteria.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_venue.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_additional_info.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_maximum_backlog.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_selection_date.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_accept.setVisibility(View.VISIBLE);
                activity_company_invitations_more_details_decline.setVisibility(View.VISIBLE);

                switch (accepted)
                {
                    case "1":
                        activity_company_invitations_more_details_accept.setBackground(getResources().getDrawable(R.drawable.recycler_view_button_positive_selected));
                        activity_company_invitations_more_details_accept.setTextColor(getResources().getColor(R.color.white));
                        activity_company_invitations_more_details_accept.setText("Accepted");

                        activity_company_invitations_more_details_decline.setVisibility(View.GONE);
                        break;
                    case "2":
                        activity_company_invitations_more_details_decline.setBackground(getResources().getDrawable(R.drawable.recycler_view_button_negative_selected));
                        activity_company_invitations_more_details_decline.setTextColor(getResources().getColor(R.color.white));
                        activity_company_invitations_more_details_decline.setText("Declined");

                        activity_company_invitations_more_details_accept.setVisibility(View.GONE);
                        break;
                }

            }
            else
            {
                functions.setErrorAlert(getApplicationContext(),"Error. Please try after some time.");
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
