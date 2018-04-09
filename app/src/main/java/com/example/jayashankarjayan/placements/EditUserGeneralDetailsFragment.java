package com.example.jayashankarjayan.placements;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;
import com.example.jayashankarjayan.placements.functions.LocalDatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserGeneralDetailsFragment extends Fragment {

    ImageButton activity_personal_details_image_of_the_user;

    TextView activity_personal_details_text_view_gender;

    android.support.design.widget.TextInputEditText activity_personal_details_edit_text_name, activity_personal_details_edit_text_email,
            activity_personal_details_edit_text_contact,activity_personal_details_edit_text_college,
            activity_personal_details_edit_text_achievement,activity_user_details_strengths,
            activity_user_details_weaknesses;

    RadioButton activity_personal_details_radio_button_male,activity_personal_details_radio_button_female;
    RadioGroup activity_personal_details_radio_group_gender;
    ProgressBar progressBar;

    ScrollView activity_user_profile_scrollview;

    String imagepath;
    File sourceFile;
    int totalSize = 0;
    public static final int PICK_IMAGE = 1;
    Functions functions;
    LocalDatabaseHandler localDatabaseHandler;
    RelativeLayout activity_user_details_inner_relative_layout,fragment_edit_general_details_relative_layout;

    public EditUserGeneralDetailsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_user_general_details, container, false);

        functions = new Functions(getActivity().getApplicationContext());
//        localDatabaseHandler = new LocalDatabaseHandler(getActivity().getApplicationContext());


        fragment_edit_general_details_relative_layout = (RelativeLayout)rootView.findViewById(R.id.fragment_edit_general_details_relative_layout);
        activity_user_details_inner_relative_layout = (RelativeLayout)rootView.findViewById(R.id.activity_user_details_inner_relative_layout);
        activity_user_profile_scrollview = (ScrollView)rootView.findViewById(R.id.activity_user_profile_scrollview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.activity_edit_personal_details_progressBar);

        activity_personal_details_image_of_the_user = (ImageButton) rootView.findViewById(R.id.activity_personal_details_image_of_the_user);
        activity_personal_details_edit_text_name = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.activity_personal_details_edit_text_name);
        activity_personal_details_edit_text_email = (android.support.design.widget.TextInputEditText)rootView.findViewById(R.id.activity_personal_details_edit_text_email);
        activity_personal_details_text_view_gender = (TextView)rootView.findViewById(R.id.activity_personal_details_text_view_gender);
        activity_personal_details_radio_group_gender = (RadioGroup)rootView.findViewById(R.id.activity_personal_details_radio_group_gender);
        activity_personal_details_radio_button_male = (RadioButton)rootView.findViewById(R.id.activity_personal_details_radio_button_male);
        activity_personal_details_radio_button_female = (RadioButton)rootView.findViewById(R.id.activity_personal_details_radio_button_female);
        activity_personal_details_edit_text_contact = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.activity_personal_details_edit_text_contact);
        activity_personal_details_edit_text_college = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.activity_personal_details_edit_text_college);
        activity_personal_details_edit_text_college.setEnabled(false);
        activity_personal_details_edit_text_achievement = (android.support.design.widget.TextInputEditText) rootView.findViewById(R.id.activity_personal_details_edit_text_achievement);
        activity_user_details_strengths = (android.support.design.widget.TextInputEditText)rootView.findViewById(R.id.activity_user_details_strengths);
        activity_user_details_weaknesses = (android.support.design.widget.TextInputEditText)rootView.findViewById(R.id.activity_user_details_weaknesses);

        // set fonts
        functions.applyFont(getActivity().getApplicationContext(),activity_personal_details_text_view_gender,"bold");
        
        // set visibility to gone
//        hideAllViews();

        activity_personal_details_edit_text_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(activity_personal_details_edit_text_name.getText().toString().length() < 3)
                {
                    activity_personal_details_edit_text_name.requestFocus();
                    activity_personal_details_edit_text_name.setError("Name cannot be less than 3 letters");
                }
            }
        });

        activity_personal_details_edit_text_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(activity_personal_details_edit_text_contact.getText().toString().length() < 10)
                {
                    activity_personal_details_edit_text_contact.requestFocus();
                    activity_personal_details_edit_text_contact.setError("Contact number has to be 10 digits");
                }
            }
        });

        activity_personal_details_image_of_the_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        GetAllUserData getAllUserData = new GetAllUserData();
        getAllUserData.execute();
//        runThreadToGetStudentData();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagepath = cursor.getString(columnIndex);
                cursor.close();
            }
            activity_personal_details_image_of_the_user.setImageBitmap(BitmapFactory.decodeFile(imagepath));
            new UploadFileToServer().execute();

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.edit_general_profile_menu_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();

        switch (id)
        {
            case R.id.edit_profile_menu_item_save_profile:
                saveProfile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetAllUserData extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String name_of_the_student, achievements, studentImage,email,gender, contact, college,strengths,weaknesses,
                personal_skills,technical_skills,success,post_data,result="";
        Functions functions = new Functions(getActivity().getApplicationContext());
        int user_id = functions.getId();
        @Override
        protected String doInBackground(String... strings) {

            try {
                String link = functions.getLink() + "RequestParams.php";
                url = new URL(link);

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("getUser","UTF-8")
                        +"&"+URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(user_id),"UTF-8");
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
                for(int a = 0;a<success.length();a++)
                {
                    if(success.charAt(a) == '1')
                    {
                        JSONArray jArray = new JSONObject(result).getJSONArray("students");
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            studentImage = json_data.getString("Student_Image");
                            name_of_the_student = json_data.getString("Name_of_the_Student");
                            email = json_data.getString("Email");
                            contact = json_data.getString("Contact_Number");
                            gender = json_data.getString("Gender");
                            achievements = json_data.getString("Acheivements");
                            achievements = achievements.replace("#","\n");
                            strengths = json_data.getString("Strengths");
                            weaknesses = json_data.getString("Weaknesses");
                        }

                        college = new JSONObject(result).getString("college");


                        JSONArray personalSkillsArray = new JSONObject(result).getJSONArray("p_skills");
                        for (int j = 1; j < personalSkillsArray.length() - 1; j++)
                        {
                            personal_skills += j + ". " + personalSkillsArray.get(j) + "#";
                        }
//                        personal_skills = personal_skills.substring(0, personal_skills.length() - 1);

                        JSONArray technicalSkillsArray = new JSONObject(result).getJSONArray("t_skills");
                        for (int k = 1; k < technicalSkillsArray.length() - 1; k++)
                        {
                            technical_skills += k + ". " + technicalSkillsArray.get(k) + "#";
                        }
//                        technical_skills = technical_skills.substring(0, technical_skills.length() - 1);
                    }
                }

            } catch (JSONException | NullPointerException | IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            displayAllViews();
            progressBar.setVisibility(View.GONE);

            if(success.charAt(0) == '1')
            {
                functions.loadImage(functions.getLink() + studentImage, activity_personal_details_image_of_the_user, "normal", 0, 0);
                activity_personal_details_edit_text_name.setText(name_of_the_student);
                activity_personal_details_edit_text_email.setText(email);
                activity_personal_details_edit_text_contact.setText(contact);
                switch (gender)
                {
                    case "male":
                        activity_personal_details_radio_button_male.setChecked(true);
                        break;
                    case "female":
                        activity_personal_details_radio_button_female.setChecked(true);
                        break;
                }
                activity_personal_details_edit_text_college.setText(college);
                activity_personal_details_edit_text_achievement.setText(achievements);
                activity_user_details_strengths.setText(strengths);
                activity_user_details_weaknesses.setText(weaknesses);


                // TODO: Work around error messages
            }
            else if (success.charAt(0) == '0')
            {
                functions.setErrorAlert(getActivity().getApplicationContext(),"Error loading info. Please try again later.");
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

    private class UploadFileToServer extends AsyncTask<String, String, String> {

        //TODO: Make this link pass data properly. (Using POST method not current GET method)
        String result = "";
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            hideAllViews();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            sourceFile = new File(imagepath);
            totalSize = (int)sourceFile.length();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
//            Log.d("PROG", progress[0]);
            progressBar.setProgress(Integer.parseInt(progress[0])); //Updating progress
        }

        @Override
        protected String doInBackground(String... args) {
            int id = functions.getId();
            String FILE_UPLOAD_URL = functions.getLink()+"RequestParams.php?view=uploadImage&user_id="+id;
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            String fileName = sourceFile.getName();

            try {
                connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
                connection.setRequestMethod("POST");
                String boundary = "---------------------------boundary";
                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);


                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = sourceFile.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead;
                byte buf[] = new byte[1024];


                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead; // Here progress is total uploaded bytes

                    publishProgress(""+ (progress*100)/totalSize); // sending progress percent to publishProgress
                }

                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();

                // Get server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while((line = reader.readLine()) != null) {
                    result+=line;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            displayAllViews();
            progressBar.setVisibility(View.GONE);
            try {
                String success = new JSONObject(result).getString("success");
                if(success.charAt(0) == '1')
                {
                    Snackbar
                            .make(activity_user_details_inner_relative_layout, "File uploaded", Snackbar.LENGTH_LONG)
                            .show();
                }
                if(success.charAt(0) == '0')
                {
                    String errorMessage = new JSONObject(result).getString("message");
                    if(errorMessage.length()>0)
                    {
                        functions.setErrorAlert(getActivity().getApplicationContext(),errorMessage);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.e("Response", "Response from server: " + result);
            this.cancel(true);
            super.onPostExecute(result);
        }

    }

    public class Updation extends AsyncTask<String, String, String> {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        private String success,post_data,result="";
        private Context context;
        private Functions functions;
        private List<View> views = new ArrayList<>();
        public Updation(Context context, String post_data, List<View> views)
        {
            this.context = context;
            this.views = views;
            this.post_data = post_data;
            functions = new Functions(context);
        }
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

            for(int i=0;i<views.size();i++)
            {
                views.get(i).setVisibility(View.VISIBLE);
            }
            views.get(views.size()-1).setVisibility(View.GONE);

            if(success.charAt(0) == '1')
            {
                Snackbar successSnackbar = Snackbar.make(fragment_edit_general_details_relative_layout, "Profile updated",Snackbar.LENGTH_SHORT);
                successSnackbar.show();
                this.cancel(true);
            }
            else if (success.charAt(0) == '0')
            {
                functions.setErrorAlert(context,"Error updating profile. Please try again later.");
            }


            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            for(int i=0;i<views.size();i++)
            {
                views.get(i).setVisibility(View.GONE);
            }
            views.get(views.size()-1).setVisibility(View.VISIBLE);
        }
    }

    public void saveProfile()
    {
        //check for empty EditText name
        if(!functions.checkForEmptyEditText(activity_personal_details_edit_text_name))
        {
            //check for empty EditText email
            if(!functions.checkForEmptyEditText(activity_personal_details_edit_text_email))
            {
                //check for empty EditText contact
                if(!functions.checkForEmptyEditText(activity_personal_details_edit_text_contact))
                {
                    //check for empty RadioButtons (Male - Femail)
                    if((activity_personal_details_radio_button_male.isChecked() || activity_personal_details_radio_button_female.isChecked()))
                    {
                        //check for empty EditText achievements
                        if(!functions.checkForEmptyEditText(activity_personal_details_edit_text_achievement))
                        {
                            //All fields are filled up.
                            //Add views to list for parameter to Updation class's constructor
                            List<View> views= new ArrayList<>();
                            views.add(activity_personal_details_image_of_the_user);
                            views.add(activity_personal_details_edit_text_name);
                            views.add(activity_personal_details_edit_text_email);
                            views.add(activity_personal_details_edit_text_contact);
                            views.add(activity_personal_details_text_view_gender);
                            views.add(activity_personal_details_radio_button_male);
                            views.add(activity_personal_details_radio_button_female);
                            views.add(activity_personal_details_edit_text_college);
                            views.add(activity_personal_details_edit_text_achievement);
                            views.add(activity_user_details_strengths);
                            views.add(activity_user_details_weaknesses);
                            views.add(progressBar); //always add progress bar at the end

                            // EditText fields value to variables.
                            String name_of_the_student = activity_personal_details_edit_text_name.getText().toString();
                            String email = activity_personal_details_edit_text_email.getText().toString();
                            String contact = activity_personal_details_edit_text_contact.getText().toString();
                            String gender = "";
                            if(activity_personal_details_radio_button_male.isChecked())
                            {
                                gender = activity_personal_details_radio_button_male.getText().toString().toLowerCase();
                            }
                            else if(activity_personal_details_radio_button_female.isChecked())
                            {
                                gender = activity_personal_details_radio_button_female.getText().toString().toLowerCase();
                            }
                            String achievements = activity_personal_details_edit_text_achievement.getText().toString();
                            String strengths = activity_user_details_strengths.getText().toString();
                            String weaknesses = activity_user_details_weaknesses.getText().toString();


                            String username = functions.getUsername();
                            String post_data="";
                            try {
                                post_data = URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("updateGeneralDetails","UTF-8")
                                        +"&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name_of_the_student,"UTF-8")
                                        +"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                                        +"&"+URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")
                                        +"&"+URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")
                                        +"&"+URLEncoder.encode("achievements","UTF-8")+"="+URLEncoder.encode(achievements,"UTF-8")
                                        +"&"+URLEncoder.encode("strengths","UTF-8")+"="+URLEncoder.encode(strengths,"UTF-8")
                                        +"&"+URLEncoder.encode("weaknesses","UTF-8")+"="+URLEncoder.encode(weaknesses,"UTF-8")
                                        +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                            }
                            catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Updation updation = new Updation(getActivity().getApplicationContext(),post_data,views);
                            updation.execute();
                        }

                    }
                    else
                    {
                        activity_personal_details_radio_group_gender.requestFocus();
                    }
                }
            }
        }
    }
    public void hideAllViews()
    {
        activity_personal_details_image_of_the_user.setVisibility(View.GONE);
        activity_personal_details_edit_text_name.setVisibility(View.GONE);
        activity_personal_details_text_view_gender.setVisibility(View.GONE);
        activity_personal_details_radio_button_male.setVisibility(View.GONE);
        activity_personal_details_radio_button_female.setVisibility(View.GONE);
        activity_personal_details_edit_text_email.setVisibility(View.GONE);
        activity_personal_details_edit_text_contact.setVisibility(View.GONE);
        activity_personal_details_edit_text_college.setVisibility(View.GONE);
        activity_personal_details_edit_text_achievement.setVisibility(View.GONE);
        activity_user_details_strengths.setVisibility(View.GONE);
        activity_user_details_weaknesses.setVisibility(View.GONE);


    }

    public void displayAllViews()
    {
        activity_personal_details_image_of_the_user.setVisibility(View.VISIBLE);
        activity_personal_details_edit_text_name.setVisibility(View.VISIBLE);
        activity_personal_details_edit_text_email.setVisibility(View.VISIBLE);
        activity_personal_details_text_view_gender.setVisibility(View.VISIBLE);
        activity_personal_details_radio_button_male.setVisibility(View.VISIBLE);
        activity_personal_details_radio_button_female.setVisibility(View.VISIBLE);
        activity_personal_details_edit_text_contact.setVisibility(View.VISIBLE);
        activity_personal_details_edit_text_college.setVisibility(View.VISIBLE);
        activity_personal_details_edit_text_achievement.setVisibility(View.VISIBLE);
        activity_user_details_strengths.setVisibility(View.VISIBLE);
        activity_user_details_weaknesses.setVisibility(View.VISIBLE);

    }

    public void changePassword()
    {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                UpdatePassword updatePassword = new UpdatePassword();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.addToBackStack("edit_personal_details");
                fragmentTransaction.replace(R.id.activity_edit_profile_fragment_container,updatePassword);
                fragmentTransaction.commit();*/
        Snackbar
                .make(activity_user_details_inner_relative_layout, "Code commented out temporarily", Snackbar.LENGTH_LONG)
                .show();
                /*Intent goToUpdatePassword = new Intent(getActivity().getApplicationContext(),UpdatePassword.class);
                startActivity(goToUpdatePassword);*/
    }

    private void runThreadToGetStudentData() {

        new Thread() {
            public void run() {

                try {
//                    displayAllViews();
                    final String image,name,email,contact,gender,college,achievements,strengths,weaknesses;
                    image = functions.getLink()+localDatabaseHandler.getStudentDetails("student_image");
                    name = localDatabaseHandler.getStudentDetails("name_of_student");
                    email = localDatabaseHandler.getStudentDetails("email");
                    contact = localDatabaseHandler.getStudentDetails("contact");
                    gender = localDatabaseHandler.getStudentDetails("gender");
                    college = localDatabaseHandler.getStudentDetails("college");
                    achievements = localDatabaseHandler.getStudentDetails("achievements");
                    strengths = localDatabaseHandler.getStudentDetails("strengths");
                    weaknesses = localDatabaseHandler.getStudentDetails("weaknesses");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                new Main2Activity.GetAllUserData().execute();
                            functions.loadImage(image,activity_personal_details_image_of_the_user,"normal",0,0);
                            activity_personal_details_edit_text_name.setText(name);
                            activity_personal_details_edit_text_email.setText(email);
                            activity_personal_details_edit_text_contact.setText(contact);
                            switch (gender)
                            {
                                case "male":
                                    activity_personal_details_radio_button_male.setChecked(true);
                                    break;
                                case "female":
                                    activity_personal_details_radio_button_female.setChecked(true);
                            }
                            activity_personal_details_edit_text_college.setText(college);
                            activity_personal_details_edit_text_achievement.setText(achievements);
                            activity_user_details_strengths.setText(strengths);
                            activity_user_details_weaknesses.setText(weaknesses);



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
