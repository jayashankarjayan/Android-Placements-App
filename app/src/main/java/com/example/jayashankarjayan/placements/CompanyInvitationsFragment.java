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
public class CompanyInvitationsFragment extends Fragment {


    SwipeRefreshLayout swipeRefreshLayout;
    private List<CompanyInvitationsContent> mainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CompanyInvitationsAdapter mAdapter;
    ProgressBar progressBar;
    ImageView fragment_company_invitations_nothing_to_show_icon;
    TextView fragment_company_invitations_nothing_to_show_text;

    public CompanyInvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_company_invitations, container, false);
        final Functions functions = new Functions(getActivity().getApplicationContext());

        MainActivity main2Activity = (MainActivity)getActivity();
        main2Activity.getSupportActionBar().setTitle("Company Invitations");

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_company_invitations_swipe_refresh);

        progressBar = (ProgressBar)rootView.findViewById(R.id.fragment_company_invitations_progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_company_invitations_recycler_view);

        fragment_company_invitations_nothing_to_show_icon = (ImageView)rootView.findViewById(R.id.fragment_company_invitations_nothing_to_show_icon);
        fragment_company_invitations_nothing_to_show_text = (TextView)rootView.findViewById(R.id.fragment_company_invitations_nothing_to_show_text) ;
        mAdapter = new CompanyInvitationsAdapter(mainList,getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (functions.checkNetworkState()) {
                    clearViews();
                    new CompanyInvitationsAsyncFetch().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        if (functions.checkNetworkState()) {
            new CompanyInvitationsAsyncFetch().execute();
        }
        return rootView;
    }

    private void clearViews()
    {
        int size = mainList.size();
        mainList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    private class CompanyInvitationsAsyncFetch extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data,link,result="",success="",inviteId,accepted,message;
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
                            String companyName = companiesJSONObject.getString("Company_Name");
                            String dateOfSelection = companiesJSONObject.getString("Date_of_Selection");

                            CompanyInvitationsContent info = new CompanyInvitationsContent(inviteId,companyName, dateOfSelection,accepted);
                            mainList.add(info);
                        }




                        /*JSONArray invitesArray = new JSONObject(result).getJSONArray("invites");
                        for (int i = 0; i < invitesArray.length(); i++) {
                            JSONObject json_data = invitesArray.getJSONObject(i);
                            inviteId = json_data.getString("ID");
                            accepted = json_data.getString("accepted");
                            String companyName = new JSONObject(result).getString("company_name");
                            String dateOfSelection = new JSONObject(result).getString("date_of_selection");

                            CompanyInvitationsContent data = new CompanyInvitationsContent(inviteId,companyName, dateOfSelection,accepted);
                            mainList.add(data);
                        }*/

                        /*JSONArray companyDetailsArray = new JSONObject(result).getJSONArray("companyDetails");
                        for (int i = 0; i < companyDetailsArray.length(); i++) {
                            JSONObject json_data = companyDetailsArray.getJSONObject(i);
                            String companyName = json_data.getString("Company_Name");
                            String dateOfSelection = json_data.getString("Date_of_Selection");
                            CompanyInvitationsContent data = new CompanyInvitationsContent(inviteId,companyName, dateOfSelection,accepted);
                            mainList.add(data);

                        }*/
                    }
                    else if(success.charAt(a) == '0')
                    {
                        message = new JSONObject(result).getString("message");
                        if(message.length() > 0)
                        {
                            result = message;
                        }
                        else
                        {
                            result = "Something seems to be wrong. Please try again after some time";
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
            if(success.charAt(0) == '1'){
                recyclerView.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }
            else
            {
                fragment_company_invitations_nothing_to_show_icon.setVisibility(View.VISIBLE);
                fragment_company_invitations_nothing_to_show_text.setVisibility(View.VISIBLE);
                fragment_company_invitations_nothing_to_show_text.setText("Company invitations come here");

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
