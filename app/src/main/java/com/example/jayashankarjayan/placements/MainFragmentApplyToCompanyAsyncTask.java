package com.example.jayashankarjayan.placements;

import android.content.Context;
import android.os.AsyncTask;
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

/**
 * Created by Jayashankar Jayan on 12/13/2017.
 */

public class MainFragmentApplyToCompanyAsyncTask extends AsyncTask<String, String, String> {
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECT_TIMEOUT = 10000;
    URL url = null;
    HttpURLConnection conn;
    private String result="";
    private String success="";
    private String status="";
    private Context context;
    private String view="",table_name;
    Functions functions;
    public MainFragmentApplyToCompanyAsyncTask(Context context, String view, String table_name)
    {
        this.context = context;
        this.view = view;
        this.table_name = table_name;
    }

    @Override
    protected String doInBackground(String... strings) {
        functions = new Functions(context);
        //    int user_id = functions.getId();


        try {
            String link = functions.getLink() + "RequestParams.php";
            String username = functions.getUsername();
            url = new URL(link);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream =  conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("view", "UTF-8") + "=" + URLEncoder.encode(view, "UTF-8")
                    + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                    + "&" + URLEncoder.encode("table_name", "UTF-8") + "=" + URLEncoder.encode(table_name, "UTF-8");
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

            System.out.println(line+"?"+post_data);

            success = new JSONObject(result).getString("success");
            if (success.charAt(0) == '1')
            {
                status = new JSONObject(result).getString("status");
                if(status.length()>0)
                {
                    result = status;
                }
                else
                {
                    return result;
                }
            }
            else
            {
                String message = new JSONObject(result).getString("message");
                if(message.length()>0)
                {
                    result = message;
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
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        }
        else if (success.charAt(0) == '0' || success.length() <= 0)
        {
            functions.setErrorAlert(context,result);
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

