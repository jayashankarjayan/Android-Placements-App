package com.example.jayashankarjayan.placements;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.jayashankarjayan.placements.functions.Functions;
import com.example.jayashankarjayan.placements.functions.LocalDatabaseHandler;

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
public class EditPersonalSkillsFragment extends Fragment {

    List<String> personalSkillsArrayList = new ArrayList<>();
    ArrayAdapter personalSkillsListAdapter;
    ListView activity_user_details_listview_personal_skills;
    android.support.v7.widget.AppCompatAutoCompleteTextView activity_user_details_personal_skills;
    LocalDatabaseHandler localDatabaseHandler;
    RelativeLayout fragment_edit_personal_skills_relative_layout;


    public EditPersonalSkillsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_personal_skills, container, false);

        localDatabaseHandler = new LocalDatabaseHandler(getActivity().getApplicationContext());

        fragment_edit_personal_skills_relative_layout = (RelativeLayout)rootView.findViewById(R.id.fragment_edit_personal_skills_relative_layout);
        activity_user_details_personal_skills = (android.support.v7.widget.AppCompatAutoCompleteTextView)rootView.findViewById(R.id.activity_user_details_personal_skills);

        activity_user_details_listview_personal_skills = (ListView)rootView.findViewById(R.id.activity_user_details_listview_personal_skills);

        personalSkillsListAdapter = new  ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, personalSkillsArrayList);

        TextWatcher personalSkillsTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List personalSkillsList = localDatabaseHandler.getPersonalSkills(activity_user_details_personal_skills.getText().toString());
                if(personalSkillsList.isEmpty() || personalSkillsList.get(0).equals(""))
                {
                    personalSkillsList.add("Nothing to display");
                }
                ArrayAdapter<String> personalSkillsadapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line, personalSkillsList);
                activity_user_details_personal_skills.setThreshold(0);
                activity_user_details_personal_skills.setAdapter(personalSkillsadapter);

                activity_user_details_personal_skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        String selectedPersonalSkill = activity_user_details_personal_skills.getText().toString();

                        if(!personalSkillsArrayList.contains(selectedPersonalSkill))
                        {
                            personalSkillsArrayList.add(selectedPersonalSkill);
                            Snackbar
                                    .make(fragment_edit_personal_skills_relative_layout, selectedPersonalSkill+" added", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                        else
                        {
                            Snackbar
                                    .make(fragment_edit_personal_skills_relative_layout, selectedPersonalSkill+" skill already selected", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                        activity_user_details_listview_personal_skills.setAdapter(personalSkillsListAdapter);
                        activity_user_details_personal_skills.setText("");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        activity_user_details_personal_skills.addTextChangedListener(personalSkillsTextWatcher);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.edit_personal_skills_menu_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();
        switch (id)
        {
            case R.id.edit_personal_skills_menu_item_save_profile:
                UpdatePersonalSkills updatePersonalSkills = new UpdatePersonalSkills();
                updatePersonalSkills.execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class UpdatePersonalSkills extends AsyncTask<String, Void, String> {

        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        Functions functions = new Functions(getActivity().getApplicationContext());
        String link = functions.getLink() + "RequestParams.php";
        String notice_title, notice_content, adder_name, post_data, message = "", success,
                result = "",username,personalSkills;

        @Override
        protected String doInBackground(String... strings) {

            try {

                for(int j=0;j<personalSkillsArrayList.size();j++)
                {
                    personalSkills += "#"+personalSkillsArrayList.get(j);
                }

                username = functions.getUsername();
                url = new URL(link);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode("updatePersonalDetails", "UTF-8")
                        + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("personal_skills", "UTF-8") + "=" + URLEncoder.encode(personalSkills, "UTF-8");
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
            } catch (IOException | JSONException | NullPointerException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
//            progressBar.setVisibility(View.GONE);

            if (success.charAt(0) == '1') {
                Snackbar successSnackbar = Snackbar.make(fragment_edit_personal_skills_relative_layout, "Personal Skills Updated",
                        Snackbar.LENGTH_SHORT);
                successSnackbar.show();
            } else if (success.charAt(0) == '0') {
                functions.setErrorAlert(getActivity().getApplicationContext(), result);
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