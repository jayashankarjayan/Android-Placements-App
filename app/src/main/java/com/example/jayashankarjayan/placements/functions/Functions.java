package com.example.jayashankarjayan.placements.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jayashankarjayan.placements.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Jayashankar Jayan on 11/19/2017.
 */

public class Functions {

    private String userId,username, userImage, nameofUser, email, rollNo, studentCV
            , numberofKT, strengths,weaknesses,achievements,technicalSkills,personalSkills,password;
    private int setCountPersonalSkills,setCountTechnicalSkills;
    private Context context;

    public Functions(Context context)
    {
        this.context = context;
    }

    public void applyFont(Context context,TextView textView, String type)
    {
        Typeface custom_font;
        switch (type)
        {
            case "bold":
                custom_font = Typeface.createFromAsset(context.getAssets(),"fonts/oxygenbold.ttf");
                textView.setTypeface(custom_font);
                break;
            default:
                custom_font = Typeface.createFromAsset(context.getAssets(),"fonts/oxygenregular.ttf");
                textView.setTypeface(custom_font);
        }
    }

    public void setNameOfUSer(String nameOfUSer)
    {
        this.nameofUser = nameOfUSer;
    }

    public String getNameOfUser()
    {
        return nameofUser;
    }

    //Check Network State
    public boolean checkNetworkState()
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkForEmptyView(TextView textView)
    {
        return textView.getText().equals("");
    }

    public boolean checkForEmptyEditText(android.support.design.widget.TextInputEditText editText) {
        if(editText.getText().toString().equals(""))
        {
            editText.requestFocus();
            editText.setError("Field cannot be left empty");
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setFontSize(TextView textView, float textSize)
    {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
    }

    public boolean loginCheck()
    {
        int Userid;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Userid = prefs.getInt("token", 0);
        return Userid > 0;
    }

    public int getId()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getInt("id", 0);

    }

    public int getCollegeID()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getInt("college_id", 0);

    }

    public int getCourseID()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getInt("course_id", 0);

    }

    public String getLink()
    {
//        return "https://always-live.000webhostapp.com/";
//        return "http://192.168.43.158/Project Placements/";
//        return "http://192.168.43.158/";
//           return "http://172.20.10.2/Project Placements/";
//           return "http://172.20.10.2/";
        return "http://119.226.13.174:9000/";
//        return "127.0.0.1/Project Placements/";
//        return "http://gnvsiom.org/placements/";
    }

    public String serializeString(String string)
    {
        char[] chars = string.toCharArray();
        for(int k=1;k<string.length()-1;k++){

            string += k+". "+string.replace("#",", ")+"\n";
        }
            return string;
    }

    public void setErrorAlert(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public String getUsername()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("username","");
    }

    public String getRole()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("role","");
    }

    public void loadImage(String link, ImageView imageView, String type, int radius, int margin){

        switch (type)
        {
            case "rounded":
                Glide.with(context)
                        .load(link)
                        .centerCrop()
                        .placeholder(R.drawable.no_user_image)
                        .error(R.drawable.no_user_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .bitmapTransform(new BlurTransformation())
                        .bitmapTransform(new RoundedCornersTransformation(context,radius, margin))
                        .into(imageView);
                break;
            case "normal":
                Glide.with(context)
                        .load(link)
                        .placeholder(R.drawable.no_user_image)
                        .error(R.drawable.no_user_image)
                        .fitCenter()
                        .into(imageView);
                break;
            default:
                break;
        }
    }

    public void setCountPersonalSkills(int setCountPersonalSkills)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("personalSkillCount", setCountPersonalSkills).apply();
    }

    public void setCountTechnicalSkills(int setCountTechnicalSkills) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("technicalSkillCount", setCountTechnicalSkills).apply();
    }

    public int getCountTechnicalSkills() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        setCountTechnicalSkills = prefs.getInt("technicalSkillCount", 0);
        return setCountTechnicalSkills;
    }

    public int getCountPersonalSkills() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        setCountPersonalSkills = prefs.getInt("personalSkillCount", 0);
        return setCountPersonalSkills;
    }
}
