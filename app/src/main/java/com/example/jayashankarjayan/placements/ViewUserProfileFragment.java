package com.example.jayashankarjayan.placements;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;
import com.example.jayashankarjayan.placements.functions.LocalDatabaseHandler;

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

public class ViewUserProfileFragment extends Fragment {


    TextView editing_name_of_the_user, editing_college_of_user, edit_achievements_of_the_user,
            title_strengths,edit_strengths,
            title_weaknesses,edit_weaknesses,
            title_personal_skills,edit_personal_skills,
            title_technical_skills,edit_technical_skills,
            editing_academics;
    ImageButton /*edit_strengths_button,edit_weaknesses_button,
            edit_personal_skills_button,edit_technical_skills_button,
            edit_personal_details_button,*/editing_image_of_the_user;
    ProgressBar progressBar;
    RelativeLayout fragment_view_user_profile_layout_personal_details,fragment_view_user_profile_layout_personal_skilla,
            fragment_view_user_profile_layout_strengths,fragment_view_user_profile_layout_weaknesses,
            fragment_view_user_profile_layout_technical_skills,fragment_view_user_profile_layout_progressbar,
            fragment_view_user_profile_layout_academics;
    private String imagePath;
    SpannableString underLine;
    int i = 0;
    LocalDatabaseHandler localDatabaseHandler;
    Functions functions;

    public ViewUserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_view_user_profile, container, false);

        MainActivity main2Activity = (MainActivity)getActivity();
        if(main2Activity.getSupportActionBar() != null)
        {
            main2Activity.getSupportActionBar().setTitle("My Profile");
        }
        functions = new Functions(getActivity().getApplicationContext());
        localDatabaseHandler = new LocalDatabaseHandler(getActivity().getApplicationContext());

//        main2Activity.startService(new Intent(getActivity().getApplicationContext(), ServiceClass.class));
        /*ScheduledTimerTask scheduledTimerTask = new ScheduledTimerTask();
        scheduledTimerTask.run();*/

        final Functions functions = new Functions(getActivity().getApplicationContext());

        fragment_view_user_profile_layout_personal_details = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_personal_details);
        fragment_view_user_profile_layout_personal_skilla = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_personal_skilla);
        fragment_view_user_profile_layout_strengths = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_strengths);
        fragment_view_user_profile_layout_weaknesses = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_weaknesses);
        fragment_view_user_profile_layout_technical_skills = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_technical_skills);
        fragment_view_user_profile_layout_progressbar = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_progressbar);
        fragment_view_user_profile_layout_academics = (RelativeLayout)rootView.findViewById(R.id.fragment_view_user_profile_layout_academics);

        progressBar = (ProgressBar)rootView.findViewById(R.id.activity_edit_user_profile_progressBar);
        editing_image_of_the_user = (ImageButton)rootView.findViewById(R.id.editing_image_of_the_user);
        editing_name_of_the_user = (TextView)rootView.findViewById(R.id.editing_name_of_user);
        editing_college_of_user = (TextView)rootView.findViewById(R.id.editing_college_of_user);
        edit_achievements_of_the_user = (TextView)rootView.findViewById(R.id.editing_achievements_of_user);
//        edit_personal_details_button = (ImageButton)rootView.findViewById(R.id.edit_my_personal_details);
//        editing_strengths_button = (ImageButton)rootView.findViewById(R.id.edit_my_strengths);

        title_personal_skills = (TextView)rootView.findViewById(R.id.editing_personal_skills);
        edit_personal_skills = (TextView)rootView.findViewById(R.id.editing_list_of_my_personal_skills);
//        edit_personal_skills_button = (ImageButton)rootView.findViewById(R.id.edit_my_personal_skills);

        title_strengths = (TextView)rootView.findViewById(R.id.editing_strengths);
        edit_strengths = (TextView)rootView.findViewById(R.id.editing_list_of_my_strengths);
//        edit_strengths_button = (ImageButton)rootView.findViewById(R.id.edit_my_strengths);

        title_weaknesses = (TextView)rootView.findViewById(R.id.editing_weaknesses);
        edit_weaknesses = (TextView)rootView.findViewById(R.id.editing_list_of_my_weaknesses);
//        edit_weaknesses_button = (ImageButton)rootView.findViewById(R.id.edit_my_weaknesses);

        title_technical_skills = (TextView)rootView.findViewById(R.id.editing_technical_skills);
        edit_technical_skills = (TextView)rootView.findViewById(R.id.editing_list_of_my_technical_skills);
//        edit_technical_skills_button = (ImageButton)rootView.findViewById(R.id.edit_my_technical_skills);

        editing_academics = (TextView)rootView.findViewById(R.id.editing_academics);

        // Underline titles
        underLine = new SpannableString(getResources().getString(R.string.title_personal_skills));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        title_personal_skills.setText(underLine);

        underLine = new SpannableString(getResources().getString(R.string.title_strengths));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        title_strengths.setText(underLine);

        underLine = new SpannableString(getResources().getString(R.string.title_weaknesses));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        title_weaknesses.setText(underLine);

        underLine = new SpannableString(getResources().getString(R.string.title_technical_skills));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        title_technical_skills.setText(underLine);

        underLine = new SpannableString(getResources().getString(R.string.title_academics));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        editing_academics.setText(underLine);

        //font type
        functions.applyFont(getActivity().getApplicationContext(),editing_name_of_the_user,"");
        functions.applyFont(getActivity().getApplicationContext(),editing_college_of_user,"");
        functions.applyFont(getActivity().getApplicationContext(),edit_achievements_of_the_user,"");
        functions.applyFont(getActivity().getApplicationContext(),title_personal_skills,"");
        functions.applyFont(getActivity().getApplicationContext(),edit_personal_skills,"");
        functions.applyFont(getActivity().getApplicationContext(),title_technical_skills,"");
        functions.applyFont(getActivity().getApplicationContext(),edit_technical_skills,"");
        functions.applyFont(getActivity().getApplicationContext(),title_strengths,"");
        functions.applyFont(getActivity().getApplicationContext(),edit_strengths,"");
        functions.applyFont(getActivity().getApplicationContext(),title_weaknesses,"");
        functions.applyFont(getActivity().getApplicationContext(),edit_weaknesses,"");

        //font size
        functions.setFontSize(editing_college_of_user,18);
        functions.setFontSize(edit_achievements_of_the_user,18);
        functions.setFontSize(edit_personal_skills,18);
        functions.setFontSize(edit_technical_skills,18);
        functions.setFontSize(edit_strengths,18);
        functions.setFontSize(edit_weaknesses,18);

//        runThreadToGetStudentData();
        new GetAllUserData().execute();

        final Intent activity_edit_my_profile = new Intent(getActivity().getApplicationContext(),EditPersonalDetails.class);
        /*edit_personal_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_edit_my_profile.putExtra("detail","personal");
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        edit_strengths_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_edit_my_profile.putExtra("detail","strengths");
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        edit_weaknesses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_edit_my_profile.putExtra("detail","weaknesses");
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        edit_personal_skills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_edit_my_profile.putExtra("detail","personal_skills");
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        edit_technical_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_edit_my_profile.putExtra("detail","technical_skills");
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }); */
        FloatingActionButton fragment_view_user_profile_floating_action_button_edit_profile = (FloatingActionButton)rootView.findViewById(R.id.fragment_view_user_profile_floating_action_button_edit_profile);
        fragment_view_user_profile_floating_action_button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(activity_edit_my_profile);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        return rootView;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private class GetAllUserData extends AsyncTask<String, String, String>
    {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String name_of_the_student, college,achievements,strengths,weaknesses,personal_skills="",technical_skills="",studentImage,
                link,success,result="",post_data;
        Functions functions = new Functions(getActivity().getApplicationContext());
        int id = functions.getId();
        @Override
        protected String doInBackground(String... strings) {

            try {
                link= functions.getLink()+"RequestParams.php";
                url = new URL(link);

                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getUser","UTF-8")
                            +"&"+URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(id),"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                int response_code = conn.getResponseCode();

                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                if (response_code == HttpURLConnection.HTTP_OK)
                {
                    while((line = reader.readLine())!= null)
                    {
                        result+=line;
                    }
                }
                else
                {
                    return "Error. Please try again";
                }

                success = new JSONObject(result).getString("success");
                if (success.charAt(0) == '1')
                {
                    JSONArray jArray = new JSONObject(result).getJSONArray("students");
                    for (int i = 0; i < jArray.length(); i++)
                    {
                        JSONObject json_data = jArray.getJSONObject(i);
                        name_of_the_student = json_data.getString("Name_of_the_Student");
                        achievements = json_data.getString("Acheivements");
                        strengths = json_data.getString("Strengths");
                        weaknesses = json_data.getString("Weaknesses");
                        studentImage = json_data.getString("Student_Image");
                    }

                    JSONArray personalSkillsArray = new JSONObject(result).getJSONArray("p_skills");

                    for (int j =0; j < personalSkillsArray.length(); j++)
                    {
//                        personal_skills += j + ". " + personalSkillsArray.get(j) + "#";
                        personal_skills += personalSkillsArray.get(j) + "#";
                    }

                    personal_skills = personal_skills.substring(0, personal_skills.length() - 1);

                    JSONArray technicalSkillsArray = new JSONObject(result).getJSONArray("t_skills");
                    for (int k = 0; k < technicalSkillsArray.length(); k++)
                    {
//                        technical_skills += k + ". " + technicalSkillsArray.get(k) + "#";
                        technical_skills += technicalSkillsArray.get(k) + "#";
                    }
                    technical_skills = technical_skills.substring(0, technical_skills.length() - 1);

                    college = new JSONObject(result).getString("college");
                }
                else if (success.charAt(0) == '0')
                {
                    String message = new JSONObject(result).getString("message");
                    if(message.length() > 0)
                    {
                        result = message;
                    }
                    else
                    {
                        result = "Error fetching info. Please try again later";
                    }
                }

            } catch (JSONException | NullPointerException | IOException e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (success.charAt(0) == '1') {
                System.out.print(link+"?"+post_data);
                fragment_view_user_profile_layout_progressbar.setVisibility(View.GONE);
                fragment_view_user_profile_layout_personal_details.setVisibility(View.VISIBLE);
                fragment_view_user_profile_layout_personal_skilla.setVisibility(View.VISIBLE);
                fragment_view_user_profile_layout_strengths.setVisibility(View.VISIBLE);
                fragment_view_user_profile_layout_weaknesses.setVisibility(View.VISIBLE);
                fragment_view_user_profile_layout_technical_skills.setVisibility(View.VISIBLE);
//                fragment_view_user_profile_layout_academics.setVisibility(View.VISIBLE);

                if(!college.equals(""))
                {
                    editing_college_of_user.setText(college);
                }
                if (achievements.equals("")) {
                    edit_achievements_of_the_user.setText(getResources().getString(R.string.no_achievements_to_display));
                } else {
                    int MAX_CHAR = 20;
                    int maxLength = (achievements.length() < MAX_CHAR) ? achievements.length() : MAX_CHAR;
                    achievements = achievements.substring(0, maxLength);
                    achievements = achievements.replace("#", ", ");
                    edit_achievements_of_the_user.setText(achievements + " ...");
                }

                if ((strengths.equals(""))) {
                    edit_strengths.setText(getResources().getString(R.string.no_strengths_to_display));
                } else {
                    edit_strengths.setText(strengths.replace("#", "\n"));
                }

                if ((weaknesses.equals(""))) {
                    edit_weaknesses.setText(getResources().getString(R.string.no_weaknesses_to_display));
                } else {
                    edit_weaknesses.setText(weaknesses.replace("#", "\n"));
                }
                if ((personal_skills.equals(""))) {
                    edit_personal_skills.setText(getResources().getString(R.string.no_personal_skills_to_display));
                } else {
                    edit_personal_skills.setText(personal_skills.replace("#", "\n"));
                }
                if ((technical_skills.equals(""))) {
                    edit_technical_skills.setText(getResources().getString(R.string.no_technical_skills_to_display));
                } else {
                    edit_technical_skills.setText(technical_skills.replace("#", "\n"));
                }

                editing_name_of_the_user.setText(name_of_the_student);
                setImagePath(functions.getLink() + studentImage);
                functions.loadImage(functions.getLink() + studentImage, editing_image_of_the_user, "normal",0, 0);
            }
            if (success.charAt(0) == '0') {
                functions.setErrorAlert(getActivity().getApplicationContext(),result);
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

    private void runThreadToGetStudentData() {

        new Thread() {
            public void run() {

                    try {
                        final String image,name,college,achievements,strengths,weaknesses,temp_personal_skills,personal_skills,temp_technical_skills,technical_skills;
                        image = functions.getLink()+localDatabaseHandler.getStudentDetails("student_image");
                        name = localDatabaseHandler.getStudentDetails("name_of_student");
                        college = localDatabaseHandler.getStudentDetails("college");
                        achievements = localDatabaseHandler.getStudentDetails("achievements");
                        strengths = localDatabaseHandler.getStudentDetails("strengths");
                        weaknesses = localDatabaseHandler.getStudentDetails("weaknesses");
                        temp_personal_skills = localDatabaseHandler.getStudentDetails("personal_skills").substring(0,localDatabaseHandler.getStudentDetails("personal_skills").length()-1);
                        personal_skills = temp_personal_skills.replace("#","\n");
                        temp_technical_skills = localDatabaseHandler.getStudentDetails("technical_skills").substring(0,localDatabaseHandler.getStudentDetails("technical_skills").length()-1);
                        technical_skills = temp_technical_skills.replace("#","\n");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                new Main2Activity.GetAllUserData().execute();
                                functions.loadImage(image,editing_image_of_the_user,"normal",0,0);
                                editing_name_of_the_user.append(name);
                                editing_college_of_user.append(college);
                                edit_achievements_of_the_user.append(achievements);
                                edit_strengths.append(strengths);
                                edit_weaknesses.append(weaknesses);
                                edit_personal_skills.append(personal_skills);
                                edit_technical_skills.append(technical_skills);


                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        .start();
    }
}
