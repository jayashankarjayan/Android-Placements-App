package com.example.jayashankarjayan.placements.functions;//dont mind those errors

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.util.LinkedHashMap;

/**
 * Created by Jayashankar Jayan on 11/21/2017.
 */

public class GetUserData extends AsyncTask<String, String, LinkedHashMap<String,String>>
{
    ProgressDialog progressDialog;
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECT_TIMEOUT = 10000;
    URL url = null;
    HttpURLConnection conn;
    String result="",post_data = "";

    LinkedHashMap<String,String> contents = new LinkedHashMap<String,String>();
    Functions functions;

    Context context;
    String userId,username, userImage, nameOfStudent, email, rollNo, studentCV
            , numberofKT, strengths,weaknesses,achievements,technicalSkills,personalSkills,password;

    public GetUserData(Context context)
    {
        this.context = context;
        functions = new Functions(context);
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password= password;
    }

    @Override
    protected LinkedHashMap<String,String> doInBackground(String... strings) {

        try
        {
            String login_url = "https://always-live.000webhostapp.com/login.php";
            URL url = new URL (login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
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
                result = "Unsuccessfull";
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        try {
//            progressDialog.dismiss();
            JSONArray jArray = new JSONObject(result).getJSONArray("login");
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);

                String userId = json_data.getString("Student_ID");
                contents.put("userId",userId);

                String username = json_data.getString("Username");
                contents.put("username",username);

                String userImage = json_data.getString("Student_Image");
                contents.put("userImage",userImage);

                String nameOfStudent = json_data.getString("Name_of_the_Student");
                contents.put("nameOfStudent",nameOfStudent);
                functions.setNameOfUSer(nameOfStudent);

                String email = json_data.getString("Email");
                contents.put("email",email);

                String rollNo = json_data.getString("Roll_No");
                contents.put("rollNo",rollNo);

                String studentCV = json_data.getString("Student_CV");
                contents.put("studentCV",studentCV);

                String numberofKT = json_data.getString("Number_of_KT");
                contents.put("numberofKT",numberofKT);

                String strengths = json_data.getString("Strengths");
                contents.put("strengths",strengths);

                String weakneses = json_data.getString("Weaknesses");
                contents.put("weakneses",weakneses);

                String achievemnets = json_data.getString("Acheivements");
                contents.put("achievemnets",achievemnets);

                String personalSkills = json_data.getString("Personal_Skills");
                contents.put("personalSkills",personalSkills);

                String technicalSkills = json_data.getString("Technical_Skills");
                contents.put("technicalSkills",technicalSkills);

                cancel(true);   
            }

        } catch (JSONException | NullPointerException e) {
            Toast.makeText(context,""+result,Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

        return contents;
    }

    @Override
    protected void onPostExecute(LinkedHashMap<String,String> result) {
        super.onPostExecute(result);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
//        progressDialog.show();
    }
}