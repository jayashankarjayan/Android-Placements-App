package com.example.jayashankarjayan.placements.functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

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
 * Created by Jayashankar Jayan on 1/6/2018.
 */



public class StudentDataAsyncTask extends AsyncTask<String, String, String>
{
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECT_TIMEOUT = 10000;
    private  String student_id;
    private String name_of_the_student="";
    private String achievements="";
    private String strengths="";
    private String weaknesses="";
    private String personal_skills="";
    private String technical_skills="";
    private String studentImage;
    private String gender;
    private String email;
    private String registered_on;
    private String last_logged_in;
    private String student_cv;
    private String number_of_kt;
    private String contact;
    private  String roll_no;
    private String success;
    private String result="";
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private Functions functions;
    private int id;
    private LinkedHashMap<String,String> linkedHashMap;
    private LocalDatabaseHandler localDatabaseHandler;

    public StudentDataAsyncTask(Context context)
    {
        this.context = context;
        functions = new Functions(context);
        id = functions.getId();
        linkedHashMap = new LinkedHashMap<>();
        localDatabaseHandler = new LocalDatabaseHandler(context);
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
            String post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode("getUser", "UTF-8")
                    + "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
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
                    student_id = json_data.getString("Student_ID");
                    name_of_the_student = json_data.getString("Name_of_the_Student");
                    achievements = json_data.getString("Acheivements");
                    strengths = json_data.getString("Strengths");
                    weaknesses = json_data.getString("Weaknesses");
                    studentImage = json_data.getString("Student_Image");
                    gender = json_data.getString("Gender");
                    email = json_data.getString("Email");
                    registered_on = json_data.getString("registered_on");
                    last_logged_in = json_data.getString("last_logged_in");
                    roll_no = json_data.getString("Roll_No");
                    student_cv = json_data.getString("Student_CV");
                    number_of_kt = json_data.getString("Number_of_KT");
                    contact = json_data.getString("Contact_Number");
                }

                JSONArray personalSkillsArray = new JSONObject(result).getJSONArray("p_skills");

                for (int j =0; j < personalSkillsArray.length(); j++)
                {
                        /*personal_skills += j + ". " + personalSkillsArray.get(j) + "#";*/
                    personal_skills += personalSkillsArray.get(j) + "#";
                }

                    /*personal_skills = personal_skills.substring(0, personal_skills.length() - 1);*/

                JSONArray technicalSkillsArray = new JSONObject(result).getJSONArray("t_skills");
                for (int k = 0; k < technicalSkillsArray.length(); k++)
                {
                        /*technical_skills += k + ". " + technicalSkillsArray.get(k) + "#";*/
                    technical_skills += technicalSkillsArray.get(k) + "#";
                }
                    /*technical_skills = technical_skills.substring(0, technical_skills.length() - 1);*/

                String college = new JSONObject(result).getString("college");

                linkedHashMap.put("student_id",student_id);
                linkedHashMap.put("name_of_student",name_of_the_student);
                linkedHashMap.put("achievements",achievements);
                linkedHashMap.put("strengths",strengths);
                linkedHashMap.put("weaknesses",weaknesses);
                linkedHashMap.put("studentImage",studentImage);
                linkedHashMap.put("personal_skills",personal_skills);
                linkedHashMap.put("technical_skills",technical_skills);
                linkedHashMap.put("college", college);
                linkedHashMap.put("gender", gender);
                linkedHashMap.put("email", email);
                linkedHashMap.put("contact", contact);
                linkedHashMap.put("registered_on", registered_on);
                linkedHashMap.put("last_logged_in", last_logged_in);
                linkedHashMap.put("roll_no",roll_no);
                linkedHashMap.put("student_cv", student_cv);
                linkedHashMap.put("number_of_kt", number_of_kt);
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

        if (success.charAt(0) == '1') {

            if(localDatabaseHandler.insertStudentData(linkedHashMap))
            {

            }
        }
        this.cancel(true);
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressBar.setVisibility(View.VISIBLE);
    }
}