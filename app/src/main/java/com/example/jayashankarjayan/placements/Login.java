package com.example.jayashankarjayan.placements;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.util.Random;

public class Login extends AppCompatActivity {

    Button login,activity_login_button_recover_account,activity_login_button_admin_login;
    TextInputEditText activity_login_edit_text_username,activity_login_edit_text_password,
            activity_login_edit_text_forgot_password_username;
    ProgressDialog progressDialog;
    RelativeLayout activity_login_relative_layout,activity_login_login_layout;
    LinearLayout activity_login_forgot_password_layout;
    TextView activity_login_text_view_forgot_password;
    String uname,pwd;
    boolean loginScreenVisible = true;
    Functions functions = new Functions(Login.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (functions.loginCheck()) {
            finish();
            Intent userIsAlreadyLoggedIn = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(userIsAlreadyLoggedIn);
        }
        activity_login_login_layout = (RelativeLayout)findViewById(R.id.activity_login_login_layout);
        activity_login_relative_layout = (RelativeLayout) findViewById(R.id.activity_login_relative_layout);
        activity_login_edit_text_username = (TextInputEditText) findViewById(R.id.activity_login_edit_text_username);
        activity_login_edit_text_password = (TextInputEditText) findViewById(R.id.activity_login_edit_text_password);
        login = (Button) findViewById(R.id.activity_login_button_login);
        activity_login_forgot_password_layout = (LinearLayout)findViewById(R.id.activity_login_forgot_password_layout);
        activity_login_text_view_forgot_password = (TextView)findViewById(R.id.activity_login_text_view_forgot_password);
        activity_login_button_recover_account = (Button)findViewById(R.id.activity_login_button_recover_account);
        activity_login_button_admin_login =(Button)findViewById(R.id.activity_login_button_admin_login);

        if(loginScreenVisible)
        {
            displayLoginScreen();
        }
        else
        {
            displayForgotPasswordScreen();
        }

        activity_login_edit_text_forgot_password_username  = (TextInputEditText) findViewById(R.id.activity_login_edit_text_forgot_password_username);

        // Underline text
        SpannableString underLine = new SpannableString(getResources().getString(R.string.forgot_password));
        underLine.setSpan(new UnderlineSpan(), 0, underLine.length(), 0);
        activity_login_text_view_forgot_password.setText(underLine);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "Login";
                uname = activity_login_edit_text_username.getText().toString();
                pwd = activity_login_edit_text_password.getText().toString();

                BackgroundWorker backgroundWorker = new BackgroundWorker(Login.this);
                backgroundWorker.execute(type, uname, pwd);
            }
        });

        activity_login_button_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "Login";
                uname = activity_login_edit_text_username.getText().toString();
                pwd = activity_login_edit_text_password.getText().toString();

                AdminLoginAsyncTask adminLoginAsyncTask = new AdminLoginAsyncTask(Login.this);
                adminLoginAsyncTask.execute(type, uname, pwd);
            }
        });

        activity_login_text_view_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginScreenVisible = false;
                displayForgotPasswordScreen();
            }
        });

        activity_login_button_recover_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ForgotPassword().execute();
            }
        });
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(loginScreenVisible)
        {
            super.onBackPressed();
        }
        else
        {
            displayLoginScreen();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(functions.loginCheck())
        {
            finish();
            Intent userIsAlreadyLoggedIn = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(userIsAlreadyLoggedIn);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(functions.loginCheck())
        {
            finish();
            Intent userIsAlreadyLoggedIn = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(userIsAlreadyLoggedIn);
        }
    }

    private void displayLoginScreen()
    {
        activity_login_login_layout.setVisibility(View.VISIBLE);
        activity_login_forgot_password_layout.setVisibility(View.GONE);
    }

    private void displayForgotPasswordScreen()
    {
        activity_login_forgot_password_layout.setVisibility(View.VISIBLE);
        activity_login_login_layout.setVisibility(View.GONE);
    }
    class BackgroundWorker extends AsyncTask<String,Void,String> {

        private Context context;
        private AlertDialog alertDialog;
        BackgroundWorker(Context context)
        {
            this.context = context;
        }
        String username, id,token,college_id,course_id,type,result="",success ;
        @Override
        protected String doInBackground(String... params) {
            type = params[0];
            String post_data = "";
            String login_url = functions.getLink()+"RequestParams.php";
            if (type.equals("Login"))
            {
                try
                {
                    String username = params[1];
                    String password = params[2];
                    URL url = new URL (login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    post_data =URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("login","UTF-8")
                            +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                            +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    String line;
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        while((line = bufferedReader.readLine()) != null)
                        {
                            result += line;
                        }
                    }
                    else
                    {
                        result = "Error. Please Try Again";
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            try {

                progressDialog.dismiss();
                success = new JSONObject(result).getString("success");
                System.out.println(post_data);
                for(int a = 0;a<success.length();a++)
                {
                    if(success.charAt(a) == '1')
                    {
                        String switcher = new JSONObject(result).getString("role");
                        if(switcher.equals("admin"))
                        {
                            JSONArray adminLoginArray = new JSONObject(result).getJSONArray("login");
                            if(success.length() > 0)
                            {
                                for(int j=0;j<adminLoginArray.length();j++)
                                {
                                    Random r = new Random();
                                    int i1 = r.nextInt(10000000 - 1000000) + 1000000;
                                    JSONObject json_data = adminLoginArray.getJSONObject(j);
                                    username = json_data.getString("Username");
                                    id = json_data.getString("Admin_ID");
                                    token = id + "" + i1;

                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                    prefs.edit().putInt("token", Integer.parseInt(token)).apply();
                                    prefs.edit().putString("username", username).apply();
                                    prefs.edit().putString("role", "admin").apply();
                                    Intent loginSuccessful = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                    startActivity(loginSuccessful);
                                    finish();
                                }
                            }

                        }
                        else
                        {

                            JSONArray loginArray = new JSONObject(result).getJSONArray("login");
                            if (success.length() > 0) {
                                for (int i = 0; i < loginArray.length(); i++) {
                                    Random r = new Random();
                                    int i1 = r.nextInt(10000000 - 1000000) + 1000000;
                                    JSONObject json_data = loginArray.getJSONObject(i);
                                    username = json_data.getString("Username");
                                    id = json_data.getString("Student_ID");
                                    token = id + "" + i1;
                                    college_id = json_data.getString("College_ID");
                                    course_id = json_data.getString("Course");
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                    prefs.edit().putInt("token", Integer.parseInt(token)).apply();
                                    prefs.edit().putString("username", username).apply();
                                    prefs.edit().putInt("id", Integer.parseInt(id)).apply();
                                    prefs.edit().putInt("college_id", Integer.parseInt(college_id)).apply();
                                    prefs.edit().putInt("course_id", Integer.parseInt(course_id)).apply();
                                    prefs.edit().putString("role", "student").apply();
                                    Intent loginSuccessful = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(loginSuccessful);
                                    finish();
                                }
                            }
                            else
                            {
                                JSONArray errorArray = new JSONObject(result).getJSONArray("message");
                                for(int i=0;i<errorArray.length();i++) {
                                    JSONObject json_data = errorArray.getJSONObject(i);
                                    String message = json_data.getString("message");
//                                TODO: Work around error messages
                                }
                            }
                        }
                    }
                }
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("Error. Please Try Again")) {
                if (success.charAt(0) == '0') {
                    Snackbar errorSnackbar = Snackbar.make(activity_login_relative_layout,
                            R.string.incorrect_username_or_password, Snackbar.LENGTH_SHORT);
                    errorSnackbar.show();
                }
            } else
            {
                AlertDialog.Builder serverError = new AlertDialog.Builder(Login.this);
                serverError.setMessage("Looks like something went wrong. Please try again later");
                serverError.setNegativeButton("Ok", null);
                serverError.show();
            }
            this.cancel(true);
            super.onPostExecute(result);
        }
    }

    class AdminLoginAsyncTask extends AsyncTask<String,Void,String> {

        private Context context;
        private AlertDialog alertDialog;
        AdminLoginAsyncTask(Context context)
        {
            this.context = context;
        }
        String username, id,token,college_id,course_id,type,result="",success ;
        @Override
        protected String doInBackground(String... params) {
            type = params[0];
            String post_data = "";
            String login_url = functions.getLink()+"RequestParams.php";
            if (type.equals("Login"))
            {
                try
                {
                    String username = params[1];
                    String password = params[2];
                    URL url = new URL (login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    post_data =URLEncoder.encode("view","UTF-8")+"="+URLEncoder.encode("adminLogin","UTF-8")
                            +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                            +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                    String line;
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        while((line = bufferedReader.readLine()) != null)
                        {
                            result += line;
                        }
                    }
                    else
                    {
                        result = "Error. Please Try Again";
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            try {

                progressDialog.dismiss();
                success = new JSONObject(result).getString("success");
                System.out.println(post_data);
                for(int a = 0;a<success.length();a++)
                {
                    if(success.charAt(a) == '1')
                    {
                        String switcher = new JSONObject(result).getString("role");
                        if(switcher.equals("admin"))
                        {
                            JSONArray adminLoginArray = new JSONObject(result).getJSONArray("login");
                            if(success.length() > 0)
                            {
                                for(int j=0;j<adminLoginArray.length();j++)
                                {
                                    Random r = new Random();
                                    int i1 = r.nextInt(10000000 - 1000000) + 1000000;
                                    JSONObject json_data = adminLoginArray.getJSONObject(j);
                                    username = json_data.getString("Username");
                                    id = json_data.getString("Admin_ID");
                                    token = id + "" + i1;

                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                    prefs.edit().putInt("token", Integer.parseInt(token)).apply();
                                    prefs.edit().putString("username", username).apply();
                                    prefs.edit().putString("role", "admin").apply();
                                    Intent loginSuccessful = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                    startActivity(loginSuccessful);
                                    finish();
                                }
                            }

                        }
                        else
                        {

                            JSONArray loginArray = new JSONObject(result).getJSONArray("login");
                            if (success.length() > 0) {
                                for (int i = 0; i < loginArray.length(); i++) {
                                    Random r = new Random();
                                    int i1 = r.nextInt(10000000 - 1000000) + 1000000;
                                    JSONObject json_data = loginArray.getJSONObject(i);
                                    username = json_data.getString("Username");
                                    id = json_data.getString("Student_ID");
                                    token = id + "" + i1;
                                    college_id = json_data.getString("College_ID");
                                    course_id = json_data.getString("Course");
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                    prefs.edit().putInt("token", Integer.parseInt(token)).apply();
                                    prefs.edit().putString("username", username).apply();
                                    prefs.edit().putInt("id", Integer.parseInt(id)).apply();
                                    prefs.edit().putInt("college_id", Integer.parseInt(college_id)).apply();
                                    prefs.edit().putInt("course_id", Integer.parseInt(course_id)).apply();
                                    prefs.edit().putString("role", "student").apply();
                                    Intent loginSuccessful = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(loginSuccessful);
                                    finish();
                                }
                            }
                            else
                            {
                                JSONArray errorArray = new JSONObject(result).getJSONArray("message");
                                for(int i=0;i<errorArray.length();i++) {
                                    JSONObject json_data = errorArray.getJSONObject(i);
                                    String message = json_data.getString("message");
//                                TODO: Work around error messages
                                }
                            }
                        }
                    }
                }
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("Error. Please Try Again")) {
                if (success.charAt(0) == '0') {
                    Snackbar errorSnackbar = Snackbar.make(activity_login_relative_layout,
                            R.string.incorrect_username_or_password, Snackbar.LENGTH_SHORT);
                    errorSnackbar.show();
                }
            } else
            {
                AlertDialog.Builder serverError = new AlertDialog.Builder(Login.this);
                serverError.setMessage("Looks like something went wrong. Please try again later");
                serverError.setNegativeButton("Ok", null);
                serverError.show();
            }
            this.cancel(true);
            super.onPostExecute(result);
        }
    }

    class ForgotPassword extends AsyncTask<String, Void, String> {

        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        Functions functions = new Functions(getApplicationContext());
        String link = functions.getLink() + "RequestParams.php";
        String post_data, message = "", success, result = "", username;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {

            username = activity_login_edit_text_forgot_password_username.getText().toString();
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
                    post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode("forgotPassword", "UTF-8")
                        + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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
            progressDialog.dismiss();
            AlertDialog.Builder successfulyEmailSentDialog = new AlertDialog.Builder(Login.this);
            successfulyEmailSentDialog.setMessage("Please check your email for further process");
            successfulyEmailSentDialog.setPositiveButton("Ok",null);
            successfulyEmailSentDialog.show();
            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

}