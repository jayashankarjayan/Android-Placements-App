package com.example.jayashankarjayan.placements;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jayashankarjayan.placements.functions.Functions;

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

public class UpdatePassword extends Fragment {

    android.support.design.widget.TextInputEditText fragment_update_password_edit_text_new_password,fragment_update_password_edit_text_confirm_new_password;
    Button fragment_update_password_update_password_button;
    ProgressBar fragment_update_password_progressBar;
    public UpdatePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_update_password, container, false);

        final Functions functions = new Functions(getActivity().getApplicationContext());

        setHasOptionsMenu(false);
        fragment_update_password_progressBar = (ProgressBar)rootView.findViewById(R.id.fragment_update_password_progressBar);
        fragment_update_password_edit_text_new_password = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.fragment_update_password_edit_text_new_password );
        fragment_update_password_edit_text_confirm_new_password  = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.fragment_update_password_edit_text_confirm_new_password);
        
        fragment_update_password_update_password_button = (Button)rootView.findViewById(R.id.fragment_update_password_update_password_button);


        fragment_update_password_update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!functions.checkForEmptyEditText(fragment_update_password_edit_text_new_password))
                {
                    if(!functions.checkForEmptyEditText(fragment_update_password_edit_text_confirm_new_password))
                    {
                        String new_password = fragment_update_password_edit_text_new_password.getText().toString();
                        String confirm_new_password = fragment_update_password_edit_text_confirm_new_password.getText().toString();
                        if(new_password.equals(confirm_new_password))
                        {
                            if(new_password.length() >= 6)
                            {
                                new UpdatePasswordAsyncTask().execute();
                            }
                            else
                            {
                                Toast.makeText(getActivity().getApplicationContext(),"Passwoord cannot be less than 6 characters",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"New passwords and confirm passwords donot match",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return rootView;
    }

    private class UpdatePasswordAsyncTask extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        private String success="",post_data,result="";
        private Functions functions = new Functions(getActivity().getApplicationContext());;
        String username = functions.getUsername();
        String new_password, confirm_password;
        @Override
        protected String doInBackground(String... strings) {

            try {
                String link = functions.getLink() + "RequestParams.php";
                URL url = new URL(link);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("updatePassword","UTF-8")
                        +"&"+URLEncoder.encode("new_password","UTF-8")+"="+URLEncoder.encode(String.valueOf(new_password),"UTF-8")
                        +"&"+URLEncoder.encode("confirm_password","UTF-8")+"="+URLEncoder.encode(String.valueOf(confirm_password),"UTF-8")
                        +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(String.valueOf(username),"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result+=line;
                    }
                } else {
                    return "Error. Please Try Again";
                }

                success = new JSONObject(result).getString("success");

            } catch ( NullPointerException | IOException | JSONException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            fragment_update_password_edit_text_new_password.setVisibility(View.VISIBLE);
            fragment_update_password_edit_text_confirm_new_password.setVisibility(View.VISIBLE);

            fragment_update_password_progressBar.setVisibility(View.GONE);

            if(success.charAt(0) == '1')
            {
                this.cancel(true);
                Toast.makeText(getActivity().getApplicationContext(),"Password updated",Toast.LENGTH_SHORT).show();
                // TODO: Work around error messages
            }
            else if (success.charAt(0) == '0')
            {
                functions.setErrorAlert(getActivity().getApplicationContext(),"Error updating profile. Please try again later.");
            }

            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            new_password = fragment_update_password_edit_text_new_password.getText().toString();
            confirm_password = fragment_update_password_edit_text_confirm_new_password.getText().toString();

            fragment_update_password_edit_text_new_password.setVisibility(View.GONE);
            fragment_update_password_edit_text_confirm_new_password.setVisibility(View.GONE);

            fragment_update_password_progressBar.setVisibility(View.VISIBLE);
        }
    }
}
