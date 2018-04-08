package com.example.jayashankarjayan.placements.functions;

import android.app.ProgressDialog;
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
 * Created by Jayashankar Jayan on 12/26/2017.
 */

public class GetSkillsAsyncTask extends AsyncTask<String, String, String>
{
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECT_TIMEOUT = 10000;
    private String result="";
    private Context context;
    private Functions functions;
    private  ProgressDialog progressDialog;
    private LinkedHashMap<String,String> personalSkills = new LinkedHashMap<>();
    private LinkedHashMap<String,String> technicalSkills = new LinkedHashMap<>();
    private LocalDatabaseHandler localDatabaseHandler;

    GetSkillsAsyncTask(Context context)
    {
        this.context = context;//where are u calling this as
        functions = new Functions(context);
        localDatabaseHandler = new LocalDatabaseHandler(context);
    }
    @Override
    protected String doInBackground(String... strings) {
        try {

            String link = functions.getLink()+"RequestParams.php";
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode(String.valueOf("getSkills"), "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int response_code = conn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK)
            {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                while((line = reader.readLine())!= null)
                {
                    result+=line;
                }
            }
            else
            {
                return "Error. Please try again";
            }

            String success = new JSONObject(result).getString("success");
            for(int a = 0; a< success.length(); a++)
            {
                if (success.charAt(a) == '1') {
                    JSONArray personalSkillsArray = new JSONObject(result).getJSONArray("personal_skills");
                    for (int i = 0; i < personalSkillsArray.length(); i++) {
                        JSONObject json_data = personalSkillsArray.getJSONObject(i);
                        String personalSkillId = json_data.getString("ID");
                        String personalSkill = json_data.getString("Skill_Name");
                        personalSkills.put(personalSkillId, personalSkill);
                    }


                    JSONArray technicalSkillaArray = new JSONObject(result).getJSONArray("technical_skills");
                    for (int i = 0; i < technicalSkillaArray.length(); i++) {
                        JSONObject json_data = technicalSkillaArray.getJSONObject(i);
                        String technicalSkillId = json_data.getString("ID");
                        String technicalSkill = json_data.getString("Skill_Name");
                        technicalSkills.put(technicalSkillId, technicalSkill);
                    }
                }
            }
        } catch (JSONException | NullPointerException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(localDatabaseHandler.getPersonalSkillsCount() == 0)
        {
            if(localDatabaseHandler.insertPersonalSkillsData(personalSkills))
            {
            /*progressDialog.dismiss();*/
            }
        }
        if(localDatabaseHandler.getTechnicalSkillsCount() == 0)
        {
            if(localDatabaseHandler.insertTechnicalSkillsData(technicalSkills))
            {

            }
        }

        functions.setCountPersonalSkills(personalSkills.size());
        functions.setCountTechnicalSkills(technicalSkills.size());
        this.cancel(true);
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /*progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Looks like it is your first time here.\nPlease wait while we set up a few things for you");
        progressDialog.show(); //coz of this///let me do something
        progressDialog.setCancelable(false);*/
//        Toast.makeText(context,"Looks like it is your first time here.\nPlease wait while we set up a few things for you",
//                Toast.LENGTH_LONG).show();
    }
}